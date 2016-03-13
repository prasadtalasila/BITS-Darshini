/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.transport;

import java.util.Arrays;

import org.pcap4j.packet.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.Protocol;
import com.bits.protocolanalyzer.analyzer.event.PacketTypeDetectionEvent;
import com.bits.protocolanalyzer.persistence.entity.TcpEntity;
import com.bits.protocolanalyzer.persistence.repository.TcpRepository;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 *
 * @author amit
 * @author crygnus
 */
@Component
public class TcpAnalyzer {

    public static final String PACKET_TYPE_OF_RELEVANCE = Protocol.TCP;

    @Autowired
    private TcpRepository tcpRepository;

    private EventBus eventBus;
    private byte[] tcpHeader;
    private int startByte;
    private int endByte;

    public void configure(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
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
    public void analyzePacket(PacketWrapper packetWrapper) {
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

            /*
             * Save to database
             */
            TcpEntity entity = new TcpEntity();

            entity.setSourcePort(TcpHeader.getSourcePort(tcpHeader));
            entity.setDestinationPort(TcpHeader.getDestinationPort(tcpHeader));
            entity.setSequenceNumber(TcpHeader.getSequenceNumber(tcpHeader));
            entity.setAckNumber(TcpHeader.getAckNumber(tcpHeader));
            entity.setDataOffset(TcpHeader.getDataOffset(tcpHeader));
            entity.setCwrFlagSet(TcpHeader.isCWRFlagSet(tcpHeader));
            entity.setEceFlagSet(TcpHeader.isECEFlagSet(tcpHeader));
            entity.setUrgFlagSet(TcpHeader.isURGFlagSet(tcpHeader));
            entity.setAckFlagSet(TcpHeader.isACKFlagSet(tcpHeader));
            entity.setPshFlagSet(TcpHeader.isPSHFlagSet(tcpHeader));
            entity.setRstFlagSet(TcpHeader.isRSTFlagSet(tcpHeader));
            entity.setSynFlagSet(TcpHeader.isSYNFlagSet(tcpHeader));
            entity.setFinFlagSet(TcpHeader.isFINFlagSet(tcpHeader));
            entity.setWindowSize(TcpHeader.getWindowSize(tcpHeader));
            entity.setChecksum(TcpHeader.getChecksum(tcpHeader));
            entity.setUrgentPointer(TcpHeader.getUrgentPointer(tcpHeader));
            entity.setNextProtocol(nextProtocol);

            entity.setPacketIdEntity(packetWrapper.getPacketIdEntity());

            tcpRepository.save(entity);
        }
    }

    private String getNextProtocol(byte[] tcpHeader) {

        int dstPortNo = TcpHeader.getDestinationPort(tcpHeader);
        switch (dstPortNo) {
        case 80:
            return Protocol.HTTP;

        case 443:
            return Protocol.HTTPS;

        default:
            return Protocol.END_PROTOCOL;
        }
    }

    private void publishTypeDetectionEvent(String nextProtocol, int startByte,
            int endByte) {
        this.eventBus.post(
                new PacketTypeDetectionEvent(nextProtocol, startByte, endByte));
    }
}
