package in.ac.bits.protocolanalyzer.analyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import in.ac.bits.protocolanalyzer.analyzer.event.EndAnalysisEvent;
import in.ac.bits.protocolanalyzer.analyzer.link.LinkAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.network.NetworkAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.transport.TransportAnalyzer;
import in.ac.bits.protocolanalyzer.persistence.repository.AnalysisRepository;
import in.ac.bits.protocolanalyzer.protocol.Protocol;
import lombok.Getter;

/**
 * 
 * @author crygnus
 *
 */
@Component
@Scope(value = "prototype")
@Getter
public class Session {

    public static final String CONTROLLER_BUS = "pipeline_controller_bus";

    @Autowired
    private PcapAnalyzer pcapAnalyzer;

    @Autowired
    private TimedStorage timedStorage;

    @Autowired
    private AnalyzerCell linkCell;
    @Autowired
    private LinkAnalyzer linkAnalyzer;

    @Autowired
    private AnalyzerCell networkCell;
    @Autowired
    private NetworkAnalyzer networkAnalyzer;

    @Autowired
    private AnalyzerCell transportCell;
    @Autowired
    private TransportAnalyzer transportAnalyzer;

    @Autowired
    private EventBusFactory factory;

    @Autowired
    private Protocol protocol;

    @Autowired
    private AnalysisRepository repository;

    private long packetProcessedCount = 0;
    private long packetReadCount = 0;

    private ExecutorService executorService;
    private String sessionName;
    private Map<Integer, AnalyzerCell> cellMap;

    public void init(String sessionName) {
        this.sessionName = sessionName;
        this.cellMap = new HashMap<Integer, AnalyzerCell>();
        setLinkCell();
        setNetworkCell();
        setTransportCell();
        repository.configure();
        /* Create pcap analyzer and connect linkCell with it */
        this.pcapAnalyzer.setNextAnalyzerCell(linkCell);
        /* Register pcap analyzer to controller event bus */
        factory.getEventBus(CONTROLLER_BUS).register(pcapAnalyzer);
    }

    public long startExperiment() {
        executorService = Executors.newFixedThreadPool(5);
        executorService.execute(linkCell);
        executorService.execute(networkCell);
        executorService.execute(transportCell);
        repository.start();
        this.packetReadCount = pcapAnalyzer.readFile();
        System.out.println("Read count at session = " + packetReadCount
                + " Process count now = " + packetProcessedCount);
        if (packetReadCount == packetProcessedCount) {
            endSession();
        }
        return packetReadCount;
    }

    public void attachCustomAnalyzer(int cellNumber,
            CustomAnalyzer customAnalyzer) {
        AnalyzerCell cell = cellMap.get(cellNumber);
        cell.addCustomAnalyzer(customAnalyzer);
    }

    public void setLinkCell() {
        linkCell.configure(this, "linkCell", linkAnalyzer);
        cellMap.put(1, linkCell);
    }

    private void setNetworkCell() {
        networkCell.configure(this, "networkCell", networkAnalyzer);
        cellMap.put(2, networkCell);
    }

    private void setTransportCell() {
        transportCell.configure(this, "transportCell", transportAnalyzer);
        cellMap.put(3, transportCell);
    }

    public void connectCells(Map<String, Set<String>> protocolGraph) {
        for (Entry<String, Set<String>> node : protocolGraph.entrySet()) {
            AnalyzerCell cell = cellMap
                    .get(protocol.getCellNumber(node.getKey()));
            Set<String> toNodes = node.getValue();
            for (String protocolNode : toNodes) {
                AnalyzerCell destinationCell = cellMap
                        .get(protocol.getCellNumber(protocolNode));
                cell.configureDestinationStageMap(protocolNode,
                        destinationCell);
            }
        }
    }

    public void incrementPacketProcessedCount() {
        this.packetProcessedCount++;
        if (packetReadCount == packetProcessedCount) {
            endSession();
        }
    }

    public void endSession() {
        System.out.println("Ending session...");
        factory.getEventBus(CONTROLLER_BUS).post(new EndAnalysisEvent());
        executorService.shutdown();
        repository.terminate();
        System.out.println("Session ended!");
    }

    public String getSessionName() {
        return sessionName;
    }

    public PcapAnalyzer getPcapAnalyzer() {
        return pcapAnalyzer;
    }

}
