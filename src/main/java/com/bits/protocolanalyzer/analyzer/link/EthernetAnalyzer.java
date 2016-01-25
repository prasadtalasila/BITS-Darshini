/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.link;

import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.MacAddress;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.event.LinkLayerEvent;
import com.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;
import com.google.common.eventbus.Subscribe;

/**
 *
 * @author amit
 */
public class EthernetAnalyzer extends LinkAnalyzer {

    private EthernetPacket ethernetPacket;

    @Override
    public String getSource() {
        EthernetPacket.EthernetHeader eh = ethernetPacket.getHeader();
        MacAddress src = eh.getSrcAddr();
        return src.toString();
    }

    @Override
    public String getDestination() {
        EthernetPacket.EthernetHeader eh = ethernetPacket.getHeader();
        MacAddress dest = eh.getDstAddr();
        return dest.toString();
    }

    @Override
    public Packet getPayload() {
        return ethernetPacket.getPayload();
    }

    @Subscribe
    public void analyzeEthernetLayer(LinkLayerEvent linkLayerEvent) {
        PacketWrapper packetWrapper = linkLayerEvent.getPacketWrapper();
        LinkAnalyzerEntity lae = linkLayerEvent.getLinkAnalyzerEntity();
        if (packetWrapper.getPacket().getHeader() instanceof EthernetPacket.EthernetHeader) {
            this.ethernetPacket = (EthernetPacket) packetWrapper.getPacket();
            lae.setSource(getSource());
            lae.setDestination(getDestination());
        }
    }
}
