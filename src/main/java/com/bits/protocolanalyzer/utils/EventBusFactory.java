package com.bits.protocolanalyzer.utils;

import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;

/**
 * 
 * @author crygnus
 *
 */

@Service
@Configurable
public class EventBusFactory {

    private HashSet<String> eventBusIds = new HashSet<String>();
    private HashMap<String, EventBus> eventBuses = new HashMap<String, EventBus>();

    public EventBus getEventBus(String id) {
        if (id == null) {
            // TODO: Define a custom exception for application to throw. Avoid
            // returning null anywhere
            return null;
        } else if (!eventBusIds.contains(id)) {
            EventBus eventBus = new EventBus(id);
            eventBusIds.add(id);
            eventBuses.put(id, eventBus);
            return eventBus;
        } else {
            return eventBuses.get(id);
        }
    }
}
