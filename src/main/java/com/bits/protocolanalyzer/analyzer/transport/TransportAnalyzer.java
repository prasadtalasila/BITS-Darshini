/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.transport;

import org.pcap4j.packet.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.event.TransportLayerEvent;
import com.bits.protocolanalyzer.persistence.entity.TransportAnalyzerEntity;
import com.bits.protocolanalyzer.repository.TransportAnalyzerRepository;
import com.bits.protocolanalyzer.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;

/**
 *
 * @author amit
 */
@Component
public class TransportAnalyzer {

    @Autowired
    private TransportAnalyzerRepository transportAnalyzerRepository;

    @Autowired
    private EventBusFactory eventBusFactory;

    private PacketWrapper packetWrapper;

    public PacketWrapper getPacket() {
        return packetWrapper;
    }

    public void setPacket(PacketWrapper packet) {
        this.packetWrapper = packet;
    }

    public String getSourcePort() {
        return null;
    }

    public String getDestinationPort() {
        return null;
    }

    public Long getAckNumber() {
        return null;
    }

    public Long getSeqNumber() {
        return null;
    }

    public Packet getPayload() {
        Packet p = packetWrapper.getPacket();
        return p.getPayload();
    }

    public void passToHook(TransportAnalyzerEntity tae) {

        /* post the event to corresponding event-bus */
        EventBus layerEventBus = eventBusFactory.getEventBus("layer_event_bus");
        layerEventBus.post(new TransportLayerEvent(packetWrapper, tae));
        transportAnalyzerRepository.save(tae);
    }

    public void analyzeTransportLayer() {

        // analyze and pass to hooks
        TransportAnalyzerEntity tae = new TransportAnalyzerEntity();
        tae.setPacketIdEntity(packetWrapper.getPacketIdEntity());
        transportAnalyzerRepository.save(tae);

        passToHook(tae);

        // pass to next layer
        Packet p = getPayload();
        packetWrapper.setPacket(p);
        if (p != null) {
            // pass to next layer
        }

    }

}
