/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.link;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.network.NetworkAnalyzer;
import com.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;
import com.bits.protocolanalyzer.repository.LinkAnalyzerRepository;
import org.pcap4j.packet.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

/**
 *
 * @author amit
 */
@Service
@Configurable
public class LinkAnalyzer {

	@Autowired
	private NetworkAnalyzer networkAnalyzer;
	
	@Autowired
	private LinkAnalyzerRepository linkAnalyzerRepository;

	private PacketWrapper packetWrapper;

	public PacketWrapper getPacket() {
		return packetWrapper;
	}

	public void setPacket(PacketWrapper packet) {
		this.packetWrapper = packet;
	}

	public byte[] getSource() {
		return null;
	}

	public byte[] getDestination() {
		return null;
	}

	public Packet getPayload() {
		//extract packet
		//get packet payload and return.
		return null;
	}

	public void passToHook() {
		LinkAnalyzerEntity lae = new LinkAnalyzerEntity();
		EthernetAnalyzer ea = new EthernetAnalyzer();
		lae.setPacketId(packetWrapper.getPacketId());
		linkAnalyzerRepository.save(lae);
		
		//Send packet to hooks for further analysis.
//		ea.analyzeEthernetLayer(null, lae);
//		ethernetAnalyzer.analyzeEthernetLayer((EthernetPacket) packetWrapper.getPacket(), lae);
	}

	public void analyzeLinkLayer() {

		passToHook();
		
		Packet p = getPayload();
		packetWrapper.setPacket(p);
		networkAnalyzer.setPacket(packetWrapper);
		networkAnalyzer.analyzeLinkLayer();
	}

}
