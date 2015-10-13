/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer;

import java.util.List;

/**
 *
 * @author amit
 */
public class PcapAnalyzer {
	
	private List<PacketWrapper> packets;

	public PcapAnalyzer(List<PacketWrapper> packets) {
		this.packets = packets;
	}

	public List<PacketWrapper> getPackets() {
		return packets;
	}

	public void setPackets(List<PacketWrapper> packets) {
		this.packets = packets;
	}
	
	public void analyzePackets(){
		
	}
	
}
