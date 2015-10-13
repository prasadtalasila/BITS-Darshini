/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.link;

import com.bits.protocolanalyzer.repository.LinkAnalyzerRepository;
import com.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.MacAddress;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author amit
 */
public class EthernetAnalyzer extends LinkAnalyzer{
	
	private EthernetPacket ethernetPacket;
	
	@Autowired
	private LinkAnalyzerRepository linkAnalyzerRepository;
	
	@Override
	public byte[] getSource() {
		EthernetPacket.EthernetHeader eh = ethernetPacket.getHeader();
		MacAddress dest = eh.getSrcAddr();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput o;
		try {
			o = new ObjectOutputStream(bos);
			o.writeObject(dest);
		} catch (IOException ex) {
			Logger.getLogger(EthernetAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
		}
		byte[] destByte = bos.toByteArray();
		return destByte;
	}

	@Override
	public byte[] getDestination() {
		EthernetPacket.EthernetHeader eh = ethernetPacket.getHeader();
		MacAddress dest = eh.getDstAddr();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput o;
		try {
			o = new ObjectOutputStream(bos);
			o.writeObject(dest);
		} catch (IOException ex) {
			Logger.getLogger(EthernetAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
		}
		byte[] destByte = bos.toByteArray();
		return destByte;
	}

	@Override
	public Packet getPayload() {
		return ethernetPacket.getPayload();
	}
	
	public void analyzeEthernetLayer(EthernetPacket ep, LinkAnalyzerEntity lae){
		this.ethernetPacket = ep;
//		byte[] src = getSource();
//		byte[] dst = getDestination();
//		lae.setSource(src);
//		lae.setDestination(dst);
		linkAnalyzerRepository.save(lae);
	}
}
