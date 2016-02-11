package com.bits.protocolanalyzer.analyzer.event;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;

import lombok.Getter;

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
