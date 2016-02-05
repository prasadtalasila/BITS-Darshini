/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.network;

import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.IpNumber;
import org.pcap4j.packet.namednumber.IpVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.event.NetworkLayerEvent;
import com.bits.protocolanalyzer.analyzer.transport.TransportAnalyzer;
import com.bits.protocolanalyzer.persistence.entity.NetworkAnalyzerEntity;
import com.bits.protocolanalyzer.repository.NetworkAnalyzerRepository;
import com.bits.protocolanalyzer.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;

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

    @Autowired
    private EventBusFactory eventBusFactory;

    private PacketWrapper packetWrapper;

    public PacketWrapper getPacket() {
        return packetWrapper;
    }

    public void setPacket(PacketWrapper packet) {
        this.packetWrapper = packet;
    }

    public String getSource() {
        return null;
    }

    public String getDestination() {
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
        Packet p = packetWrapper.getPacket();
        return p.getPayload();
    }

    public void passToHook(NetworkAnalyzerEntity nae) {
        /* post the event to corresponding event-bus */
        EventBus layerEventBus = eventBusFactory.getEventBus("layer_event_bus");
        layerEventBus.post(new NetworkLayerEvent(packetWrapper, nae));
        networkAnalyzerRepository.save(nae);
    }

    public void analyzeNetworkLayer() {

        // analyze and pass to hooks
        NetworkAnalyzerEntity nae = new NetworkAnalyzerEntity();
        nae.setPacketIdEntity(packetWrapper.getPacketIdEntity());
        networkAnalyzerRepository.save(nae);

        passToHook(nae);

        // get payload and pass to next analyzer
        Packet p = getPayload();
        packetWrapper.setPacket(p);
        if (p != null) {
            transportAnalyzer.setPacket(packetWrapper);
            transportAnalyzer.analyzeTransportLayer();
        }
    }
}
