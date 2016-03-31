/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ac.bits.protocolanalyzer.analyzer.network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;

import in.ac.bits.protocolanalyzer.analyzer.GenericAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.PacketWrapper;
import in.ac.bits.protocolanalyzer.analyzer.event.EndAnalysisEvent;
import in.ac.bits.protocolanalyzer.analyzer.event.PacketProcessEndEvent;
import in.ac.bits.protocolanalyzer.persistence.entity.NetworkAnalyzerEntity;
import in.ac.bits.protocolanalyzer.persistence.repository.NetworkAnalyzerRepository;

/**
 *
 * @author amit
 * @author crygnus
 */

@Component
public class NetworkAnalyzer implements GenericAnalyzer {

    @Autowired
    private NetworkAnalyzerRepository networkAnalyzerRepository;

    private EventBus networkLayerEventBus;

    public void setEventBus(EventBus eventBus) {
        this.networkLayerEventBus = eventBus;
    }

    public void publishToEventBus(PacketWrapper packetWrapper) {
        /* post the event to corresponding event-bus */
        this.networkLayerEventBus.post(packetWrapper);
    }

    public void analyzePacket(PacketWrapper packetWrapper) {

        // analyze and pass to hooks
        NetworkAnalyzerEntity nae = new NetworkAnalyzerEntity();
        nae.setPacketId(packetWrapper.getPacketId());
        /*networkAnalyzerRepository.save(nae);*/

        publishToEventBus(packetWrapper);

    }

    public void end() {
        networkLayerEventBus.post(new PacketProcessEndEvent());
    }
}
