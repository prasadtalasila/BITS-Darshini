package com.bits.protocolanalyzer.analyzer.network;

import com.google.common.eventbus.EventBus;

/**
 * 
 * @author crygnus
 *
 */

public class NetworkLayerEventBus {

    private static EventBus networkLayerEventBus;

    private NetworkLayerEventBus() {
    }

    public static EventBus getNetworkLayerEventBus() {
        if (null == networkLayerEventBus) {
            networkLayerEventBus = new EventBus("network_layer_event_bus");
        }
        return networkLayerEventBus;
    }
}
