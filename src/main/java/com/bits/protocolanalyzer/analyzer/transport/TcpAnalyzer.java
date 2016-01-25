/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.transport;

import org.pcap4j.packet.TcpPacket;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.event.TransportLayerEvent;
import com.bits.protocolanalyzer.persistence.entity.TransportAnalyzerEntity;
import com.google.common.eventbus.Subscribe;

/**
 *
 * @author amit
 */
public class TcpAnalyzer extends TransportAnalyzer {

    private TcpPacket tcpPacket;
    private TcpPacket.TcpHeader tcpHeader;

    @Override
    public String getSourcePort() {
        return this.tcpHeader.getSrcPort().valueAsString();
    }

    @Override
    public String getDestinationPort() {
        return this.tcpHeader.getDstPort().valueAsString();
    }

    @Override
    public Long getAckNumber() {
        return this.tcpHeader.getAcknowledgmentNumberAsLong();
    }

    @Override
    public Long getSeqNumber() {
        return this.tcpHeader.getSequenceNumberAsLong();
    }

    @Subscribe
    public void analyzeTcpLayer(TransportLayerEvent transportLayerEvent) {
        PacketWrapper packetWrapper = transportLayerEvent.getPacketWrapper();
        TransportAnalyzerEntity tae = transportLayerEvent
                .getTransportAnalyzerEntity();
        if (packetWrapper.getPacket().getHeader() instanceof TcpPacket.TcpHeader) {
            this.tcpPacket = (TcpPacket) packetWrapper.getPacket();
            this.tcpHeader = tcpPacket.getHeader();

            tae.setSourcePort(getSourcePort());
            tae.setDestinationPort(getDestinationPort());
            tae.setAckNumber(getAckNumber());
            tae.setSeqNumber(getSeqNumber());
        }
    }
}
