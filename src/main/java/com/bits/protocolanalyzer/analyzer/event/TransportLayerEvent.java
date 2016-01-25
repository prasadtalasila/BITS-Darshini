package com.bits.protocolanalyzer.analyzer.event;

import lombok.Getter;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.persistence.entity.TransportAnalyzerEntity;

/**
 * 
 * @author crygnus
 *
 */

@Getter
public class TransportLayerEvent {

    private PacketWrapper packetWrapper;
    private TransportAnalyzerEntity transportAnalyzerEntity;

    public TransportLayerEvent(PacketWrapper packetWrapper,
            TransportAnalyzerEntity tae) {
        this.packetWrapper = packetWrapper;
        this.transportAnalyzerEntity = tae;
    }

}
