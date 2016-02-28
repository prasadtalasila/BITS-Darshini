/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.input;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.DataLinkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.PcapAnalyzer;
import com.bits.protocolanalyzer.analyzer.Protocol;
import com.bits.protocolanalyzer.persistence.entity.PacketIdEntity;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author amit
 * @author crygnus
 */

@Component
@Getter
@Setter
public class PcapFileReader {

    @Autowired
    private PcapAnalyzer pcapAnalyzer;

    private static String defaultNextPacketType = Protocol.ETHERNET;

    public long readFile() {

        String sysFile = System.getProperty("PROTOCOL_DATA_FILE");
        long packetCount = 0;
        try {
            PcapHandle handle = Pcaps.openOffline(sysFile);
            Packet packet = handle.getNextPacket();
            while (packet != null) {
                PacketIdEntity packetIdEntity = new PacketIdEntity();
                String packetType = getPacketType(handle);
                int startByte = 0;
                int endByte = packet.length() - 1;
                PacketWrapper packetWrapper = new PacketWrapper(packet,
                        packetIdEntity, packetType, startByte, endByte);
                packetWrapper.setPacketTimestamp(handle.getTimestamp());

                this.pcapAnalyzer.analyzePacket(packetWrapper);

                packet = handle.getNextPacket();
                packetCount++;
            }
            pcapAnalyzer.endAnalysis(packetCount);
            handle.close();
        } catch (PcapNativeException ex) {
            Logger.getLogger(PcapFileReader.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (NotOpenException ex) {
            Logger.getLogger(PcapFileReader.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return packetCount;
    }

    private String getPacketType(PcapHandle handle) {
        String packetType = defaultNextPacketType;
        if (handle.getDlt().equals(DataLinkType.EN10MB)) {
            packetType = Protocol.ETHERNET;
        }
        return packetType;
    }

}
