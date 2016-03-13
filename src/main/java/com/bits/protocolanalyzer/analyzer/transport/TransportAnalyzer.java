/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.transport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.protocolanalyzer.analyzer.GenericAnalyzer;
import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.persistence.entity.TransportAnalyzerEntity;
import com.bits.protocolanalyzer.persistence.repository.TransportAnalyzerRepository;
import com.google.common.eventbus.EventBus;

/**
 *
 * @author amit
 * @author crygnus
 * 
 */

@Component
public class TransportAnalyzer implements GenericAnalyzer {

    @Autowired
    private TransportAnalyzerRepository transportAnalyzerRepository;

    private EventBus transportEventBus;

    public void setEventBus(EventBus eventBus) {
        this.transportEventBus = eventBus;
    }

    public void publishToEventBus(PacketWrapper packetWrapper) {
        /* post the event to corresponding event-bus */
        this.transportEventBus.post(packetWrapper);
    }

    public void analyzePacket(PacketWrapper packetWrapper) {

        // analyze and pass to hooks
        TransportAnalyzerEntity tae = new TransportAnalyzerEntity();
        tae.setPacketIdEntity(packetWrapper.getPacketIdEntity());
        transportAnalyzerRepository.save(tae);

        publishToEventBus(packetWrapper);

    }

}
