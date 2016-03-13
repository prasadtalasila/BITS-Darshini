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
import com.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;
import com.bits.protocolanalyzer.persistence.repository.LinkAnalyzerRepository;
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

    private void publishToEventBus(PacketWrapper packetWrapper) {
        /* post the event to corresponding event-bus */
        linkLayerEventBus.post(packetWrapper);
    }

    public void analyzePacket(PacketWrapper packetWrapper) {

        LinkAnalyzerEntity lae = new LinkAnalyzerEntity();
        lae.setPacketIdEntity(packetWrapper.getPacketIdEntity());
        lae.setTimestamp(packetWrapper.getPacketTimestamp());
        linkAnalyzerRepository.save(lae);

        publishToEventBus(packetWrapper);
    }

}
