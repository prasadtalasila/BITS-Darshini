/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.link;

import java.util.Arrays;

import org.pcap4j.packet.Packet;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.Protocol;
import com.bits.protocolanalyzer.analyzer.event.LinkLayerEvent;
import com.bits.protocolanalyzer.analyzer.event.PacketTypeDetectionEvent;
import com.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 *
 * @author crygnus
 */

public class EthernetAnalyzer extends LinkAnalyzer {

    private static final String PACKET_TYPE_OF_RELEVANCE = Protocol.ETHERNET;
    private EventBus eventBus;

    private byte[] ethernetHeader;
    private int startByte;
    private int endByte;

    /**
     * Constructs the object and registers itself on the eventbus of the
     * corresponding cell
     * 
     * @param eventBus
     */
    public EthernetAnalyzer(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    @Override
    public String getSource(PacketWrapper packetWrapper) {
        MacAddress srcAddr = EthernetHeader.getSource(this.ethernetHeader);
        return srcAddr.toString();
    }

    @Override
    public String getDestination(PacketWrapper packetWrapper) {
        MacAddress dstAddr = EthernetHeader.getDestination(this.ethernetHeader);
        return dstAddr.toString();
    }

    private void setEthernetHeader(PacketWrapper packetWrapper) {
        Packet packet = packetWrapper.getPacket();
        int startByte = packetWrapper.getStartByte();
        byte[] rawPacket = packet.getRawData();
        this.ethernetHeader = Arrays.copyOfRange(rawPacket, startByte,
                EthernetHeader.HEADER_LENGTH_IN_BYTES + 1);
    }

    private void setStartByte(PacketWrapper packetWrapper) {
        this.startByte = packetWrapper.getStartByte()
                + EthernetHeader.HEADER_LENGTH_IN_BYTES;
    }

    private void setEndByte(PacketWrapper packetWrapper) {
        /* Account for last 4 bytes of trailer */
        this.endByte = packetWrapper.getEndByte() - 4;
    }

    @Subscribe
    public void analyzePacket(LinkLayerEvent linkLayerEvent) {

        PacketWrapper packetWrapper = linkLayerEvent.getPacketWrapper();
        if (PACKET_TYPE_OF_RELEVANCE
                .equalsIgnoreCase(packetWrapper.getPacketType())) {

            /* Set ethernet header */
            setEthernetHeader(packetWrapper);
            /* Do type detection first and publish the event */
            String nextPacketType = setNextPacketType(packetWrapper);
            setStartByte(packetWrapper);
            setEndByte(packetWrapper);
            publishTypeDetectionEvent(nextPacketType, startByte, endByte);

            /*
             * Save corresponding field values to DB (can be spun off as a
             * separate thread)
             */
            LinkAnalyzerEntity lae = linkLayerEvent.getLinkAnalyzerEntity();
            lae.setSource(getSource(packetWrapper));
            lae.setDestination(getDestination(packetWrapper));
        }

    }

    private String setNextPacketType(PacketWrapper packetWrapper) {

        String nextHeaderTypeHex = EthernetHeader
                .getEtherType(this.ethernetHeader);

        switch (nextHeaderTypeHex) {
        case "0800":
            return Protocol.IPV4;
        case "86DD":
            return Protocol.IPV6;

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
