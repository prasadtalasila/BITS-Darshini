/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.network;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.transport.TransportAnalyzer;
import com.bits.protocolanalyzer.persistence.entity.NetworkAnalyzerEntity;
import com.bits.protocolanalyzer.repository.NetworkAnalyzerRepository;
import java.net.Inet4Address;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.IpNumber;
import org.pcap4j.packet.namednumber.IpVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

/**
 *
 * @author amit
 */
@Service
@Configurable
public class NetworkAnalyzer {

	@Autowired
	private NetworkAnalyzerRepository networkAnalyzerRepository;
	
	@Autowired
	private TransportAnalyzer transportAnalyzer;
	
	private PacketWrapper packetWrapper;

	public PacketWrapper getPacket() {
		return packetWrapper;
	}

	public void setPacket(PacketWrapper packet) {
		this.packetWrapper = packet;
	}

	public Inet4Address getSource() {
		return null;
	}

	public Inet4Address getDestination() {
		return null;
	}

	public IpVersion getVersion() {
		return null;
	}

	public IpNumber getIpNumber() {
		return null;
	}

	public int getHeaderLength() {
		return 0;
	}

	public int getLength() {
		return 0;
	}

	public int getId() {
		return 0;
	}

	public int getHeaderChecksum() {
		return 0;
	}
	
	public Packet getPayload() {
		//extract packet
		//get packet payload and return.
		return null;
	}

	public void passToHook() {
		NetworkAnalyzerEntity nae = new NetworkAnalyzerEntity();
		nae.setPacketId(packetWrapper.getPacketId());
		networkAnalyzerRepository.save(nae);

		//Send packet to hooks for further analysis.
//		Ipv4Analyzer ia = new Ipv4Analyzer();
//		ea.analyzeEthernetLayer(null, lae);
//		ethernetAnalyzer.analyzeEthernetLayer((EthernetPacket) packetWrapper.getPacket(), lae);
	}

	public void analyzeNetworkLayer() {

		passToHook();

		Packet p = getPayload();
		packetWrapper.setPacket(p);
//		networkAnalyzer.setPacket(packetWrapper);
//		networkAnalyzer.analyzeNetworkLayer();
	}
}
