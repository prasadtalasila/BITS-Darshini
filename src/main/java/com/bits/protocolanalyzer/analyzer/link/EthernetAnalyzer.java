/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.link;

import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;
import org.pcap4j.packet.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.Protocol;
import com.bits.protocolanalyzer.analyzer.event.PacketTypeDetectionEvent;
import com.bits.protocolanalyzer.persistence.entity.EthernetEntity;
import com.bits.protocolanalyzer.persistence.repository.EthernetRepository;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 *
 * @author crygnus
 */

@Component
public class EthernetAnalyzer {

    private static final String PACKET_TYPE_OF_RELEVANCE = Protocol.ETHERNET;

    @Autowired
    private EthernetRepository ethernetRepository;

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
    public void configure(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    /* Field extraction methods - Start */
    public String getSource(byte[] ethernetHeader) {
        byte[] sourceAddr = Arrays.copyOfRange(ethernetHeader,
                EthernetHeader.SOURCE_MAC_ADDR_START_BYTE,
                EthernetHeader.SOURCE_MAC_ADDR_END_BYTE + 1);

        MacAddress srcAddr = new MacAddress(Hex.encodeHexString(sourceAddr));
        return srcAddr.toString();
    }

    public String getDestination(byte[] ethernetHeader) {
        byte[] destinationAddr = Arrays.copyOfRange(ethernetHeader,
                EthernetHeader.DESTINATION_MAC_ADDR_START_BYTE,
                EthernetHeader.DESTINATION_MAC_ADDR_END_BYTE + 1);

        MacAddress dstAddr = new MacAddress(
                Hex.encodeHexString(destinationAddr));
        return dstAddr.toString();
    }

    public String getEtherType(byte[] ethernetHeader) {
        byte[] etherType = Arrays.copyOfRange(ethernetHeader,
                EthernetHeader.ETHER_TYPE_START_BYTE,
                EthernetHeader.ETHER_TYPE_END_BYTE + 1);

        return Hex.encodeHexString(etherType);
    }
    /* Field extraction methods - End */

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
    public void analyzePacket(PacketWrapper packetWrapper) {
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
             * Save corresponding field values to DB
             */
            EthernetEntity entity = new EthernetEntity();
            entity.setSourceAddr(getSource(ethernetHeader));
            entity.setDstAddr(getDestination(ethernetHeader));
            entity.setEtherType(nextPacketType);
            entity.setPacketIdEntity(packetWrapper.getPacketIdEntity());
            ethernetRepository.save(entity);
        }

    }

    private String setNextPacketType(PacketWrapper packetWrapper) {

        String nextHeaderTypeHex = getEtherType(this.ethernetHeader);

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
