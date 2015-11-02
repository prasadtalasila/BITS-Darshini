/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.transport;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.persistence.entity.TransportAnalyzerEntity;
import com.bits.protocolanalyzer.repository.TransportAnalyzerRepository;
import org.pcap4j.packet.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author amit
 */
@Component
public class TransportAnalyzer {
	
	@Autowired
	private TransportAnalyzerRepository transportAnalyzerRepository;
	
	private PacketWrapper packetWrapper;
	
	public PacketWrapper getPacket() {
		return packetWrapper;
	}

	public void setPacket(PacketWrapper packet) {
		this.packetWrapper = packet;
	}
	
	public String getSourcePort(){
		return null;
	}
	
	public String getDestinationPort(){
		return null;
	}
	
	public Long getAckNumber(){
		return null;
	}
	
	public Long getSeqNumber(){
		return null;
	}
	
	public Packet getPayload() {
		Packet p = packetWrapper.getPacket();
		return p.getPayload();
	}
	
	public void passToHook(TransportAnalyzerEntity tae){
		
		TcpAnalyzer tcpAnalyzer = new TcpAnalyzer();
		tcpAnalyzer.analyzeTcpLayer(packetWrapper, tae);
		
		transportAnalyzerRepository.save(tae);
	}
	
	public void analyzeTransportLayer(){
		
		//analyze and pass to hooks
		TransportAnalyzerEntity tae = new TransportAnalyzerEntity();
		tae.setPacketId(packetWrapper.getPacketId());
		transportAnalyzerRepository.save(tae);
		passToHook(tae);
		
		//pass to next layer
		Packet p = getPayload();
		packetWrapper.setPacket(p);
		if(p != null){
			//pass to next layer
		}
		
	}
	
	
}
