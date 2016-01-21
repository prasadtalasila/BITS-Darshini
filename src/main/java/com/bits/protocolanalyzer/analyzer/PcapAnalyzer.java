/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.bits.protocolanalyzer.analyzer.link.LinkAnalyzer;
import com.bits.protocolanalyzer.repository.PacketIdRepository;

/**
 *
 * @author amit
 */
@Service
@Configurable
public class PcapAnalyzer {

    @Autowired
    private LinkAnalyzer linkAnalyzer;

    @Autowired
    private PacketIdRepository packetIdRepository;

    private List<PacketWrapper> packets;

    public List<PacketWrapper> getPackets() {
        return packets;
    }

    public void setPackets(List<PacketWrapper> packets) {
        this.packets = packets;
    }

    public void analyzePackets() {
        for (PacketWrapper p : packets) {
            Object[] temp = packetIdRepository.findSequenceValue();
            p.getPacketIdEntity().setPacketId(
                    Integer.parseInt((temp[0].toString()) + 1));
            packetIdRepository.save(p.getPacketIdEntity());
            linkAnalyzer.setPacket(p);
            linkAnalyzer.analyzeLinkLayer();
        }
    }

}
