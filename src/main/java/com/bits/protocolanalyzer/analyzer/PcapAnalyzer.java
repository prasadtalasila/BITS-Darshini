/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer;

import com.bits.protocolanalyzer.analyzer.link.LinkAnalyzer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

/**
 *
 * @author amit
 */
@Service
@Configurable
public class PcapAnalyzer {
	
	@Autowired
	private LinkAnalyzer linkAnalyzer;
	
	private List<PacketWrapper> packets;

	public List<PacketWrapper> getPackets() {
		return packets;
	}

	public void setPackets(List<PacketWrapper> packets) {
		this.packets = packets;
	}
	
	public void analyzePackets(){
		for(PacketWrapper p : packets){
			linkAnalyzer.setPacket(p);
			linkAnalyzer.analyzeLinkLayer();
		}
	}
	
}
