/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.protocolanalyzer.analyzer.GenericAnalyzer;
import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.event.LinkLayerEvent;
import com.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;
import com.bits.protocolanalyzer.repository.LinkAnalyzerRepository;
import com.google.common.eventbus.EventBus;

/**
 *
 * @author amit
 * @author crygnus
 */

@Component
public class LinkAnalyzer implements GenericAnalyzer {

    @Autowired
    private LinkAnalyzerRepository linkAnalyzerRepository;

    private EventBus linkLayerEventBus;

    public void setEventBus(EventBus eventBus) {
        this.linkLayerEventBus = eventBus;
    }

    public String getSource(PacketWrapper packetWrapper) {
        return null;
    }

    public String getDestination(PacketWrapper packetWrapper) {
        return null;
    }

    private void publishToEventBus(LinkAnalyzerEntity lae,
            PacketWrapper packetWrapper) {

        /* post the event to corresponding event-bus */
        linkLayerEventBus.post(new LinkLayerEvent(packetWrapper, lae));
        linkAnalyzerRepository.save(lae);
    }

    @Override
    public void analyzePacket(PacketWrapper packetWrapper) {

        LinkAnalyzerEntity lae = new LinkAnalyzerEntity();
        lae.setPacketIdEntity(packetWrapper.getPacketIdEntity());
        linkAnalyzerRepository.save(lae);

        publishToEventBus(lae, packetWrapper);
    }

}
