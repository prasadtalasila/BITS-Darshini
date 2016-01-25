package com.bits.protocolanalyzer.analyzer.transport;

import com.google.common.eventbus.EventBus;

/**
 * 
 * @author crygnus
 *
 */

public class TransportLayerEventBus {

    private static EventBus transportLayerEventBus;

    private TransportLayerEventBus() {
    }

    public static EventBus getTransportLayerEventBus() {
        if (null == transportLayerEventBus) {
            transportLayerEventBus = new EventBus("transport_layer_event_bus");
        }
        return transportLayerEventBus;
    }
}
