/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.input;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.persistence.entity.PacketIdEntity;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

/**
 *
 * @author amit
 */
public class PcapFileReader {

	public ArrayList<PacketWrapper> readFile() {
//		ArrayList<Packet> packets = new ArrayList<>();
		ArrayList<PacketWrapper> packetWrappers = new ArrayList<>();
		String sysFile = System.getProperty("PROTOCOL_DATA_FILE");
		try {
			PcapHandle captor = Pcaps.openOffline(sysFile);
			Packet p = captor.getNextPacket();
			while(p != null){
				PacketIdEntity packetIdEntity = new PacketIdEntity();
				PacketWrapper pr = new PacketWrapper(p, packetIdEntity);
				packetWrappers.add(pr);
				p = captor.getNextPacket();
			}
		} catch (PcapNativeException ex) {
			Logger.getLogger(PcapFileReader.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NotOpenException ex) {
			Logger.getLogger(PcapFileReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return packetWrappers;
	}

}
