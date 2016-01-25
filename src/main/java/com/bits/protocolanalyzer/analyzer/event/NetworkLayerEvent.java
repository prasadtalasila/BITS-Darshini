package com.bits.protocolanalyzer.analyzer.event;

import lombok.Getter;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.persistence.entity.NetworkAnalyzerEntity;

/**
 * 
 * @author crygnus
 *
 */

@Getter
public class NetworkLayerEvent {

    private PacketWrapper packetWrapper;
    private NetworkAnalyzerEntity networkAnalyzerEntity;

    public NetworkLayerEvent(PacketWrapper packetWrapper,
            NetworkAnalyzerEntity nae) {
        this.packetWrapper = packetWrapper;
        this.networkAnalyzerEntity = nae;
    }

}
