/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.input;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.persistence.entity.PacketIdEntity;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

/**
 *
 * @author amit
 */

@Log4j
@Getter
public class PcapFileReader {

    public ArrayList<PacketWrapper> readFile() {
        ArrayList<PacketWrapper> packetWrappers = new ArrayList<>();
        String sysFile = System.getProperty("PROTOCOL_DATA_FILE");
        long count = 0;
        try {
            PcapHandle captor = Pcaps.openOffline(sysFile);
            Packet p = captor.getNextPacket();
            while (p != null) {
                count++;
                PacketIdEntity packetIdEntity = new PacketIdEntity();
                PacketWrapper pr = new PacketWrapper(p, packetIdEntity);
                pr.setPacketTimestamp(captor.getTimestamp());
                packetWrappers.add(pr);
                p = captor.getNextPacket();
            }
            captor.close();
        } catch (PcapNativeException ex) {
            Logger.getLogger(PcapFileReader.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (NotOpenException ex) {
            Logger.getLogger(PcapFileReader.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return packetWrappers;
    }

}
