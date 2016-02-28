/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.protocolanalyzer.analyzer.event.PacketProcessEndEvent;
import com.bits.protocolanalyzer.repository.PacketIdRepository;
import com.google.common.eventbus.Subscribe;

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
        Object[] temp = packetIdRepository.findSequenceValue();
        currentPacket.getPacketIdEntity()
                .setPacketId(Integer.parseInt((temp[0].toString()) + 1));

        packetIdRepository.save(currentPacket.getPacketIdEntity());
        AnalyzerCell cell = getNextAnalyzerCell();
        cell.takePacket(currentPacket);
    }

}
