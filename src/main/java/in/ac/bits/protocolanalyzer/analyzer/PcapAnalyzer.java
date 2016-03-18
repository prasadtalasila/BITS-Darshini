/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ac.bits.protocolanalyzer.analyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;

import in.ac.bits.protocolanalyzer.analyzer.event.PacketProcessEndEvent;
import in.ac.bits.protocolanalyzer.persistence.repository.PacketIdRepository;

/**
 *
 * @author amit
 * @author crygnus
 */

@Component
public class PcapAnalyzer {

    @Autowired
    private PacketIdRepository packetIdRepository;

    @Autowired
    private Session session;

    private long sequenceValue = -1L;

    private AnalyzerCell nextAnalyzerCell;
    private long packetProcessedCount = 0;
    private long packetReadCount = 0;
    private boolean endAnalysis = false;

    public void setNextAnalyzerCell(AnalyzerCell cell) {
        this.nextAnalyzerCell = cell;
    }

    public AnalyzerCell getNextAnalyzerCell() {
        return this.nextAnalyzerCell;
    }

    public void endAnalysis(long count) {
        this.packetReadCount = count;
        this.endAnalysis = true;
    }

    @Subscribe
    public void incrementPacketProcessingCount(PacketProcessEndEvent event) {
        this.packetProcessedCount++;
        if (this.endAnalysis && packetProcessedCount == packetReadCount) {
            session.endSession();
        }
    }

    public void analyzePacket(PacketWrapper currentPacket) {
        if (sequenceValue == -1) {
            sequenceValue = packetIdRepository.findSequenceValue();
        }
        currentPacket.getPacketIdEntity().setPacketId(sequenceValue);
        sequenceValue++;

        packetIdRepository.save(currentPacket.getPacketIdEntity());
        AnalyzerCell cell = getNextAnalyzerCell();
        cell.takePacket(currentPacket);
    }

}
