package com.bits.protocolanalyzer.analyzer.event;

import lombok.Getter;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;

/**
 * 
 * @author crygnus
 *
 */

@Getter
public class LinkLayerEvent {

    private PacketWrapper packetWrapper;
    private LinkAnalyzerEntity linkAnalyzerEntity;

    public LinkLayerEvent(PacketWrapper packetWrapper, LinkAnalyzerEntity lae) {
        this.packetWrapper = packetWrapper;
        this.linkAnalyzerEntity = lae;
    }

}
