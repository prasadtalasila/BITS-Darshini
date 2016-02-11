package com.bits.protocolanalyzer.analyzer;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bits.protocolanalyzer.analyzer.event.PacketTypeDetectionEvent;
import com.bits.protocolanalyzer.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author crygnus
 *
 */

@Component
@Scope(value = "prototype")
@Getter
@Setter
public class AnalyzerCell {

    private String eventBusName;
    private EventBus eventBus;
    private GenericAnalyzer genericAnalyzer;
    private PacketWrapper packetProcessing;
    private Queue<PacketWrapper> inputQueue;
    private boolean isProcessing;
    private Map<String, AnalyzerCell> destinationStageMap;

    /**
     * Provide the sessionId in which this cell is placed, the generic analyzer
     * to be placed in the cell and the suffix for eventbus name. The eventual
     * eventbus name will be "sessionId_eventBusNameSuffix".
     * 
     * @param sessionId
     * @param analyzer
     * @param eventBusNameSuffix
     */
    public void configure(String sessionId, GenericAnalyzer analyzer,
            String eventBusNameSuffix, EventBusFactory factory) {

        this.eventBusName = sessionId + "_" + eventBusNameSuffix;
        this.eventBus = factory.getEventBus(this.eventBusName);
        this.genericAnalyzer = analyzer;
        this.genericAnalyzer.setEventBus(eventBus);
        this.eventBus.register(this);
        this.inputQueue = new ConcurrentLinkedQueue<PacketWrapper>();
        this.isProcessing = false;
        this.destinationStageMap = new ConcurrentHashMap<String, AnalyzerCell>();
    }

    /**
     * Receives the next packet (or the same packet if packet type detected has
     * the corresponding analyzer in this cell itself), the type of the packet
     * and startByte location from which the packet should be processed further.
     * 
     * @param packet
     */
    public void takePacket(PacketWrapper packet) {

        this.inputQueue.add(packet);
        if (!this.isProcessing) {
            this.isProcessing = true;
            process(this.inputQueue.poll());
        }
    }

    private void process(PacketWrapper packet) {
        this.packetProcessing = packet;
        this.genericAnalyzer.analyzePacket(this.packetProcessing);
    }

    /**
     * This method implementation must be annotated with @Subscibe annotation
     * from Google guava library. This is the interface for any custom analyzer
     * in this cell to communicate the detected next packet type and byte-range
     * to be processed with this cell.
     * 
     * @param event
     */
    @Subscribe
    public void setNextPacketInfo(PacketTypeDetectionEvent event) {

        this.packetProcessing.setPacketType(event.getNextPacketType());
        this.packetProcessing.setStartByte(event.getStartByte());
        this.packetProcessing.setEndByte(event.getEndByte());

        sendPacket();
    }

    private void sendPacket() {

        String destinationStageKey = this.packetProcessing.getPacketType();

        if (destinationStageMap.containsKey(destinationStageKey)) {
            AnalyzerCell nextCell = this.destinationStageMap
                    .get(destinationStageKey);
            nextCell.takePacket(this.packetProcessing);
        }

        if (this.inputQueue.isEmpty()) {
            this.isProcessing = false;
        } else {
            process(this.inputQueue.poll());
        }
    }

    /**
     * Adds an entry (packetType, destinationCell) in the destinationStageMap
     * for this object
     * 
     * @param packetType
     * @param destinationCell
     */
    public void configureDestinationStageMap(String packetType,
            AnalyzerCell destinationCell) {
        this.destinationStageMap.put(packetType, destinationCell);
    }

}
