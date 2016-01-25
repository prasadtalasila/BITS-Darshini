package com.bits.protocolanalyzer.analyzer.link;

import com.google.common.eventbus.EventBus;

/**
 * 
 * @author crygnus
 *
 */

public class LinkLayerEventBus {

    private static EventBus linkLayerEventBus;

    private LinkLayerEventBus() {
    }

    public static EventBus getLinkLayerEventBus() {
        if (null == linkLayerEventBus) {
            linkLayerEventBus = new EventBus("link_layer_event_bus");
        }
        return linkLayerEventBus;
    }
}
