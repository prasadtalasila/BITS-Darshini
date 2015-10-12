/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer;

import java.io.Serializable;

/**
 *
 * @author amit
 */
public class PacketWrapper implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int packetId;

	public int getPacketId() {
		return packetId;
	}

	public void setPacketId(int packetId) {
		this.packetId = packetId;
	}
	
	
	
}
