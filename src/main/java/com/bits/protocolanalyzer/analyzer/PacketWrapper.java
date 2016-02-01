/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

import org.pcap4j.packet.Packet;

import com.bits.protocolanalyzer.persistence.entity.PacketIdEntity;

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

    public PacketWrapper(Packet packet, PacketIdEntity packetIdEntity) {
        this.packet = packet;
        this.packetIdEntity = packetIdEntity;
    }

}
