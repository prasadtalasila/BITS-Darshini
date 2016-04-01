package in.ac.bits.protocolanalyzer.analyzer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import in.ac.bits.protocolanalyzer.analyzer.event.EndAnalysisEvent;
import in.ac.bits.protocolanalyzer.analyzer.event.PacketTypeDetectionEvent;
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
public class AnalyzerCell implements Runnable {

    public static final String CONTROLLER_BUS = "pipeline_controller_bus";

    @Autowired
    private EventBusFactory eventBusFactory;

    @Autowired
    private PcapAnalyzer pcapAnalyzer;

    private String cellID;
    private Session session;
    private String eventBusName;
    private EventBus eventBus;
    private GenericAnalyzer genericAnalyzer;
    private List<CustomAnalyzer> customAnalyzers;
    private PacketWrapper packetProcessing;
    private ArrayBlockingQueue<PacketWrapper> inputQueue;
    private boolean isProcessing;
    private boolean isRunning;
    private Map<String, AnalyzerCell> destinationStageMap;

    /**
     * Provide the sessionId in which this cell is placed, the generic analyzer
     * to be placed in the cell and the suffix for eventbus name. The eventual
     * eventbus name will be "sessionId_eventBusNameSuffix".
     * 
     * @param sessionId
     * @param cellID
     * @param analyzer
     */
    public void configure(Session session, String cellID,
            GenericAnalyzer analyzer) {

        this.cellID = cellID;
        this.session = session;
        this.eventBusName = session.getSessionName() + "_" + cellID
                + "_event_bus";
        this.eventBus = eventBusFactory.getEventBus(eventBusName);
        this.genericAnalyzer = analyzer;
        this.customAnalyzers = new LinkedList<CustomAnalyzer>();
        this.genericAnalyzer.setEventBus(eventBus);
        this.eventBus.register(this);
        eventBusFactory.getEventBus(CONTROLLER_BUS).register(this);
        this.inputQueue = new ArrayBlockingQueue<PacketWrapper>(100);
        this.isProcessing = false;
        this.isRunning = true;
        this.destinationStageMap = new ConcurrentHashMap<String, AnalyzerCell>();

    }

    /**
     * Adds a {@link CustomAnalyzer} in the list maintained if it isn't already
     * there and invokes its configure method with input parameter as this
     * cell's eventBus
     * 
     * @param analyzer
     */
    public void addCustomAnalyzer(CustomAnalyzer analyzer) {
        if (!this.customAnalyzers.contains(analyzer)) {
            this.customAnalyzers.add(analyzer);
            analyzer.configure(eventBus);
        }
    }

    /**
     * Receives the next packet (or the same packet if packet type detected has
     * the corresponding analyzer in this cell itself)
     * 
     * @param packet
     * @return true if packet is inserted in queue false if otherwise
     */
    public boolean takePacket(PacketWrapper packet) {
        try {
            return this.inputQueue.offer(packet, 1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Subscribe
    public void end(EndAnalysisEvent event) {
        System.out.println("End analysis is called by session in: " + cellID);
        this.isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!isProcessing) {
                if (!inputQueue.isEmpty()) {
                    isProcessing = true;
                    process(inputQueue.poll());
                }
            }
        }
            System.out.println(this.cellID + " execution stop time = "
                    + System.currentTimeMillis());
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
        } else {
            session.incrementPacketProcessedCount();
        }
        this.isProcessing = false;
    }

    /**
     * Adds an entry (packetType, destinationCell) in the destinationStageMap
     * for this object
     * 
     * @param protocol
     * @param destinationCell
     */
    public void configureDestinationStageMap(String protocol,
            AnalyzerCell destinationCell) {
        this.destinationStageMap.put(protocol, destinationCell);
    }

}
