/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ac.bits.protocolanalyzer.analyzer;

import java.io.Serializable;
import java.sql.Timestamp;

import org.pcap4j.packet.Packet;

import in.ac.bits.protocolanalyzer.persistence.entity.PacketIdEntity;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author amit
 */
@Getter
@Setter
public class PacketWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Packet packet;
    private PacketIdEntity packetIdEntity;
    private Timestamp packetTimestamp;
    private String packetType;
    private int startByte;
    private int endByte;

    public PacketWrapper(Packet packet, PacketIdEntity packetIdEntity,
            String packetType, int startByte, int endByte) {
        this.packet = packet;
        this.packetIdEntity = packetIdEntity;
        this.packetType = packetType;
        this.startByte = startByte;
        this.endByte = endByte;
    }

}
