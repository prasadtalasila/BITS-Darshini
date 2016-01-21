/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.network;

import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.namednumber.IpNumber;
import org.pcap4j.packet.namednumber.IpVersion;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.persistence.entity.NetworkAnalyzerEntity;

/**
 *
 * @author amit
 */
public class Ipv4Analyzer extends NetworkAnalyzer {

    private IpV4Packet ipv4Packet;
    private IpV4Packet.IpV4Header ipv4Header;

    public IpV4Packet getIpPacket() {
        return ipv4Packet;
    }

    public void setIpPacket(IpV4Packet ipv4Packet) {
        this.ipv4Packet = ipv4Packet;
    }

    @Override
    public String getSource() {
        return ipv4Header.getSrcAddr().toString();
    }

    @Override
    public String getDestination() {
        return ipv4Header.getDstAddr().toString();
    }

    @Override
    public IpVersion getVersion() {
        return ipv4Header.getVersion();
    }

    @Override
    public IpNumber getIpNumber() {
        return ipv4Header.getProtocol();
    }

    @Override
    public int getHeaderLength() {
        return ipv4Header.getIhlAsInt();
    }

    @Override
    public int getLength() {
        return ipv4Header.getTotalLengthAsInt();
    }

    @Override
    public int getId() {
        return ipv4Header.getIdentification();
    }

    @Override
    public int getHeaderChecksum() {
        return ipv4Header.getHeaderChecksum();
    }

    public void analyzeIpv4Layer(PacketWrapper packetWrapper,
            NetworkAnalyzerEntity nae) {
        if (packetWrapper.getPacket().getHeader() instanceof IpV4Packet.IpV4Header) {
            this.ipv4Packet = (IpV4Packet) packetWrapper.getPacket();
            this.ipv4Header = ipv4Packet.getHeader();
            nae.setHeaderLength(getHeaderLength());
            nae.setChecksum(getHeaderChecksum());
            nae.setPacketLength(getLength());
            nae.setSource(getSource());
            nae.setDestination(getDestination());
        }
    }

}
