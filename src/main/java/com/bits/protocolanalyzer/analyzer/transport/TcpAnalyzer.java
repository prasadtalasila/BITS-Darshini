/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.transport;

import java.util.Arrays;

import org.pcap4j.packet.Packet;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.Protocol;
import com.bits.protocolanalyzer.analyzer.event.PacketTypeDetectionEvent;
import com.bits.protocolanalyzer.analyzer.event.TransportLayerEvent;
import com.bits.protocolanalyzer.persistence.entity.TransportAnalyzerEntity;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 *
 * @author amit
 * @author crygnus
 */
public class TcpAnalyzer extends TransportAnalyzer {

    public static final String PACKET_TYPE_OF_RELEVANCE = Protocol.TCP;

    private EventBus eventBus;
    private byte[] tcpHeader;
    private int startByte;
    private int endByte;

    public TcpAnalyzer(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    @Override
    public String getSourcePort() {
        return String.valueOf(TcpHeader.getSourcePort(this.tcpHeader));
    }

    @Override
    public String getDestinationPort() {
        return String.valueOf(TcpHeader.getDestinationPort(this.tcpHeader));
    }

    @Override
    public int getAckNumber() {
        return TcpHeader.getAckNumber(this.tcpHeader);
    }

    @Override
    public int getSeqNumber() {
        return TcpHeader.getSequenceNumber(this.tcpHeader);
    }

    private void setTcpHeader(PacketWrapper packetWrapper) {
        Packet packet = packetWrapper.getPacket();
        byte[] rawPacket = packet.getRawData();
        int startByte = packetWrapper.getStartByte();
        this.tcpHeader = Arrays.copyOfRange(rawPacket, startByte,
                startByte + TcpHeader.DEFAULT_HEADER_LENGTH_IN_BYTES + 1);
    }

    private void setStartByte(PacketWrapper packetWrapper) {
        int startByte = packetWrapper.getStartByte();
        this.startByte = startByte + TcpHeader.DEFAULT_HEADER_LENGTH_IN_BYTES;
    }

    private void setEndByte(PacketWrapper packetWrapper) {
        this.endByte = packetWrapper.getEndByte();
    }

    @Subscribe
    public void analyzePacket(TransportLayerEvent transportLayerEvent) {
        PacketWrapper packetWrapper = transportLayerEvent.getPacketWrapper();
        if (PACKET_TYPE_OF_RELEVANCE
                .equalsIgnoreCase(packetWrapper.getPacketType())) {

            /* Do type detection first and publish the event */

            /* Set the tcp header */
            this.setTcpHeader(packetWrapper);
            /* Set start and end bytes */
            this.setStartByte(packetWrapper);
            this.setEndByte(packetWrapper);

            String nextProtocol = getNextProtocol(this.tcpHeader);

            publishTypeDetectionEvent(nextProtocol, this.startByte,
                    this.endByte);
        }

        TransportAnalyzerEntity tae = transportLayerEvent
                .getTransportAnalyzerEntity();

        tae.setSourcePort(getSourcePort());
        tae.setDestinationPort(getDestinationPort());
        tae.setAckNumber(getAckNumber());
        tae.setSeqNumber(getSeqNumber());
    }

    private String getNextProtocol(byte[] tcpHeader) {

        int dstPortNo = TcpHeader.getDestinationPort(tcpHeader);
        switch (dstPortNo) {
        case 80:
            return Protocol.HTTP;

        default:
            return Protocol.HTTP;
        }
    }

    private void publishTypeDetectionEvent(String nextPacketType, int startByte,
            int endByte) {
        this.eventBus.post(new PacketTypeDetectionEvent(nextPacketType,
                startByte, endByte));
    }
}
