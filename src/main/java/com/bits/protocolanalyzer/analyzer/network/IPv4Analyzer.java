/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.network;

import java.util.Arrays;

import org.pcap4j.packet.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.Protocol;
import com.bits.protocolanalyzer.analyzer.event.PacketTypeDetectionEvent;
import com.bits.protocolanalyzer.persistence.entity.IPv4Entity;
import com.bits.protocolanalyzer.persistence.repository.IPv4Repository;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 *
 * @author crygnus
 */
@Component
public class IPv4Analyzer {

    public static final String PACKET_TYPE_OF_RELEVANCE = Protocol.IPV4;

    @Autowired
    private IPv4Repository ipPv4Repository;

    private EventBus eventBus;

    private byte[] ipv4Header;
    private int headerLength;
    private int startByte;
    private int endByte;

    public void configure(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    private void setIpv4Header(PacketWrapper packetWrapper) {
        Packet packet = packetWrapper.getPacket();
        byte[] rawPacket = packet.getRawData();
        int startByte = packetWrapper.getStartByte();
        this.ipv4Header = Arrays.copyOfRange(rawPacket, startByte,
                startByte + IPv4Header.DEFAULT_HEADER_LENTH_IN_BYTES + 1);
    }

    private void setStartByte(PacketWrapper packetWrapper) {
        int ihl = IPv4Header.getIhl(this.ipv4Header);
        this.headerLength = IPv4Header.DEFAULT_HEADER_LENTH_IN_BYTES;
        this.startByte = packetWrapper.getStartByte() + headerLength;
    }

    public void setEndByte(PacketWrapper packetWrapper) {
        int totalPacketLength = IPv4Header.getTotalLength(this.ipv4Header);
        this.endByte = packetWrapper.getStartByte() + totalPacketLength;
    }

    public String getSource() {
        byte[] sourceBytes = IPv4Header.getSouceAddress(ipv4Header);
        IPv4Address srcAddr = new IPv4Address(sourceBytes);
        return srcAddr.toString();

    }

    public String getDestination() {
        byte[] dstBytes = IPv4Header.getDestinationAddress(ipv4Header);
        IPv4Address dstAddr = new IPv4Address(dstBytes);
        return dstAddr.toString();
    }

    public int getHeaderLength() {
        return this.headerLength;
    }

    @Subscribe
    public void analyzePacket(PacketWrapper packetWrapper) {
        if (PACKET_TYPE_OF_RELEVANCE
                .equalsIgnoreCase(packetWrapper.getPacketType())) {

            /*
             * Do type detection first and publish the event.
             */
            this.setIpv4Header(packetWrapper);
            this.setStartByte(packetWrapper);
            this.setEndByte(packetWrapper);
            String nextPacketType = getNextProtocol(ipv4Header);
            publishTypeDetectionEvent(nextPacketType, this.startByte,
                    this.endByte);

            /*
             * Persist the packet attributes to ipv4_analysis table
             */
            IPv4Entity entity = new IPv4Entity();

            entity.setPacketIdEntity(packetWrapper.getPacketIdEntity());
            entity.setVersion(IPv4Header.IP_VERSION);
            entity.setIhl(IPv4Header.getIhl(ipv4Header));
            entity.setTotalLength(IPv4Header.getTotalLength(ipv4Header));
            entity.setIdentification(IPv4Header.getIdentification(ipv4Header));
            if (IPv4Header.getDontFragmentBit(ipv4Header) == 0) {
                entity.setDontFragment(false);
            } else {
                entity.setDontFragment(true);
            }
            if (IPv4Header.getMoreFragmentBit(ipv4Header) == 0) {
                entity.setMoreFragment(false);
            } else {
                entity.setMoreFragment(true);
            }
            entity.setTtl(IPv4Header.getTTL(ipv4Header));
            entity.setNextProtocol(nextPacketType);
            entity.setChecksum(IPv4Header.getHeaderChecksum(ipv4Header));
            entity.setSourceAddr(this.getSource());
            entity.setDestinationAddr(this.getDestination());

            ipPv4Repository.save(entity);
        }
    }

    private String getNextProtocol(byte[] ipv4Header) {
        int protocolInt = IPv4Header.getNextProtocol(ipv4Header);

        switch (protocolInt) {
        case 6:
            return Protocol.TCP;
        case 17:
            return Protocol.UDP;

        default:
            return Protocol.END_PROTOCOL;
        }
    }

    private void publishTypeDetectionEvent(String nextPacketType, int startByte,
            int endByte) {
        this.eventBus.post(new PacketTypeDetectionEvent(nextPacketType,
                startByte, endByte));
    }

}
