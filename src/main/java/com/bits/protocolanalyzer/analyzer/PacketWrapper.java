/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer;

import com.bits.protocolanalyzer.persistence.entity.PacketIdEntity;
import java.io.Serializable;
import org.pcap4j.packet.Packet;

/**
 *
 * @author amit
 */
public class PacketWrapper implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Packet packet;
	private PacketIdEntity packetIdEntity;

	public PacketWrapper(Packet packet, PacketIdEntity packetIdEntity) {
		this.packet = packet;
		this.packetIdEntity = packetIdEntity;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public PacketIdEntity getPacketIdEntity() {
		return packetIdEntity;
	}

	public void setPacketIdEntity(PacketIdEntity packetIdEntity) {
		this.packetIdEntity = packetIdEntity;
	}
	
	
	
}
