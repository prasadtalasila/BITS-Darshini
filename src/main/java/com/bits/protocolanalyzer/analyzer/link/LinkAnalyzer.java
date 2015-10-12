/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.link;

import org.pcap4j.packet.Packet;
import org.pcap4j.util.MacAddress;

/**
 *
 * @author amit
 */
public abstract class LinkAnalyzer {
	
	private Packet packet;

	public LinkAnalyzer(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}
	
	public abstract MacAddress getSource();
	public abstract MacAddress getDestination();
	public abstract Packet getPayload();
	
	public void passToHook(){
		//if the packet has ethernet header, pass it to EthernetAnalyzer
	}
	
}
