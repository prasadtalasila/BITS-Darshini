package com.bits.protocolanalyzer.analyzer;

import com.google.common.eventbus.EventBus;

/**
 * 
 * @author crygnus
 *
 */

public interface GenericAnalyzer {

    /**
     * Analyzes the incoming packet
     * 
     * @return String
     */
    public void analyzePacket(PacketWrapper packetWrapper);

    /**
     * Sets the eventbus to which this generic analyzer will post events
     * 
     * @param eventBus
     */
    public void setEventBus(EventBus eventBus);

}
