/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.network;

import java.util.Arrays;

import org.pcap4j.packet.Packet;

import com.bits.protocolanalyzer.address.IPv4Address;
import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.Protocol;
import com.bits.protocolanalyzer.analyzer.event.NetworkLayerEvent;
import com.bits.protocolanalyzer.analyzer.event.PacketTypeDetectionEvent;
import com.bits.protocolanalyzer.persistence.entity.NetworkAnalyzerEntity;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 *
 * @author crygnus
 */
public class IPv4Analyzer extends NetworkAnalyzer {

    public static final String PACKET_TYPE_OF_RELEVANCE = Protocol.IPV4;
    private EventBus eventBus;

    private byte[] ipv4Header;
    private int headerLength;
    private int startByte;
    private int endByte;

    public IPv4Analyzer(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    private void setIpv4Header(PacketWrapper packetWrapper) {
        Packet packet = packetWrapper.getPacket();
        byte[] rawPacket = packet.getRawData();
        int startByte = packetWrapper.getStartByte();
        this.ipv4Header = Arrays.copyOfRange(rawPacket, startByte,
                startByte + IPv4Header.DEFAULT_HEADER_LENTH_IN_BYTES);
    }

    private void setStartByte(PacketWrapper packetWrapper) {
        int ihl = IPv4Header.getIhl(this.ipv4Header);
        /* this.headerLength = IPv4Header.getIhl(this.ipv4Header) * 4; */
        this.headerLength = IPv4Header.DEFAULT_HEADER_LENTH_IN_BYTES;
        this.startByte = packetWrapper.getStartByte() + headerLength;
    }

    public void setEndByte(PacketWrapper packetWrapper) {
        int totalPacketLength = IPv4Header.getTotalLength(this.ipv4Header);
        this.endByte = packetWrapper.getStartByte() + totalPacketLength;
    }

    @Override
    public String getSource() {
        byte[] sourceBytes = IPv4Header.getSouceAddress(ipv4Header);
        IPv4Address srcAddr = new IPv4Address(sourceBytes);
        return srcAddr.toString();

    }

    @Override
    public String getDestination() {
        byte[] dstBytes = IPv4Header.getDestinationAddress(ipv4Header);
        IPv4Address dstAddr = new IPv4Address(dstBytes);
        return dstAddr.toString();
    }

    @Override
    public int getIPversion() {
        return 4;
    }

    @Override
    public int getHeaderLength() {
        return this.headerLength;
    }

    @Override
    public int getLength() {
        return IPv4Header.getTotalLength(ipv4Header);
    }

    @Override
    public int getId() {
        return IPv4Header.getIdentification(ipv4Header);
    }

    @Override
    public int getHeaderChecksum() {
        return IPv4Header.getHeaderChecksum(ipv4Header);
    }

    @Subscribe
    public void analyzePacket(NetworkLayerEvent networkLayerEvent) {
        PacketWrapper packetWrapper = networkLayerEvent.getPacketWrapper();
        if (PACKET_TYPE_OF_RELEVANCE
                .equalsIgnoreCase(packetWrapper.getPacketType())) {

            /* Do type detection first and publish the event */

            /* Set the ipv4 header */
            this.setIpv4Header(packetWrapper);
            /* Set start and end bytes */
            this.setStartByte(packetWrapper);
            this.setEndByte(packetWrapper);

            String nextPacketType = getNextProtocol(ipv4Header);

            publishTypeDetectionEvent(nextPacketType, this.startByte,
                    this.endByte);

            NetworkAnalyzerEntity nae = networkLayerEvent
                    .getNetworkAnalyzerEntity();

            nae.setHeaderLength(this.headerLength);
            nae.setChecksum(getHeaderChecksum());
            nae.setPacketLength(getLength());
            nae.setSource(getSource());
            nae.setDestination(getDestination());
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
            return Protocol.TCP;
        }
    }

    private void publishTypeDetectionEvent(String nextPacketType, int startByte,
            int endByte) {
        this.eventBus.post(new PacketTypeDetectionEvent(nextPacketType,
                startByte, endByte));
    }

}
