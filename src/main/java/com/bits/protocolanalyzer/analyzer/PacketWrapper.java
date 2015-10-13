/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer;

import java.io.Serializable;
import org.pcap4j.packet.Packet;

/**
 *
 * @author amit
 */
public class PacketWrapper implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Packet packet;
	private int packetId;

	public PacketWrapper(Packet packet, int packetId) {
		this.packet = packet;
		this.packetId = packetId;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public int getPacketId() {
		return packetId;
	}

	public void setPacketId(int packetId) {
		this.packetId = packetId;
	}
	
	
	
}
