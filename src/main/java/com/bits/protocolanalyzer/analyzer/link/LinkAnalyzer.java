/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.link;

import org.pcap4j.packet.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.event.LinkLayerEvent;
import com.bits.protocolanalyzer.analyzer.network.NetworkAnalyzer;
import com.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;
import com.bits.protocolanalyzer.repository.LinkAnalyzerRepository;
import com.bits.protocolanalyzer.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;

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

    public Packet getPayload() {
        Packet p = packetWrapper.getPacket();
        return p.getPayload();
    }

    public void passToHook(LinkAnalyzerEntity lae) {
        /* post the event to corresponding event-bus */
        EventBus layerEventBus = eventBusFactory.getEventBus("layer_event_bus");
        layerEventBus.post(new LinkLayerEvent(packetWrapper, lae));
        linkAnalyzerRepository.save(lae);
    }

    public void analyzeLinkLayer() {

        // analyze and pass to hooks
        LinkAnalyzerEntity lae = new LinkAnalyzerEntity();
        lae.setPacketIdEntity(packetWrapper.getPacketIdEntity());
        linkAnalyzerRepository.save(lae);

        passToHook(lae);

        // get payload and pass to next analyzer
        Packet p = getPayload();
        packetWrapper.setPacket(p);
        if (p != null) {
            networkAnalyzer.setPacket(packetWrapper);
            networkAnalyzer.analyzeNetworkLayer();
        }
    }

}
