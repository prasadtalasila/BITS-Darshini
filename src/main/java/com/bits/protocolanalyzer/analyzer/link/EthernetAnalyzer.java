/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.link;

import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.MacAddress;

/**
 *
 * @author amit
 */
public class EthernetAnalyzer extends LinkAnalyzer{
	
	private EthernetPacket ethernetPacket;

	public EthernetAnalyzer(EthernetPacket ethernetPacket, Packet packet) {
		super(packet);
		this.ethernetPacket = ethernetPacket;
	}
	
	public Packet getPacket() {
		return ethernetPacket;
	}

	public void setPacket(EthernetPacket packet) {
		this.ethernetPacket = packet;
	}
	
	

	@Override
	public MacAddress getSource() {
		EthernetPacket.EthernetHeader eh = ethernetPacket.getHeader();
		return eh.getSrcAddr();
	}

	@Override
	public MacAddress getDestination() {
		EthernetPacket.EthernetHeader eh = ethernetPacket.getHeader();
		return eh.getDstAddr();
	}

	@Override
	public Packet getPayload() {
		return ethernetPacket.getPayload();
	}
	
	
	
}
