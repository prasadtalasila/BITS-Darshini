/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.protocolanalyzer.repository.PacketIdRepository;

/**
 *
 * @author amit
 * @author crygnus
 */

@Component
public class PcapAnalyzer {

    @Autowired
    private PacketIdRepository packetIdRepository;

    private AnalyzerCell nextAnalyzerCell;

    public void setNextAnalyzerCell(AnalyzerCell cell) {
        this.nextAnalyzerCell = cell;
    }

    public AnalyzerCell getNextAnalyzerCell() {
        return this.nextAnalyzerCell;
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
