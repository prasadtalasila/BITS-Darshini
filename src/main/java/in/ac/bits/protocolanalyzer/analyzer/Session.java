package in.ac.bits.protocolanalyzer.analyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.ac.bits.protocolanalyzer.analyzer.event.EndAnalysisEvent;
import in.ac.bits.protocolanalyzer.analyzer.link.LinkAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.network.NetworkAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.transport.TransportAnalyzer;
import in.ac.bits.protocolanalyzer.protocol.Protocol;
import lombok.Getter;

/**
 * 
 * @author crygnus
 *
 */
@Component
@Getter
public class Session {

    public static final String CONTROLLER_BUS = "pipeline_controller_bus";

    @Autowired
    private PcapAnalyzer pcapAnalyzer;

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
    private PcapFileReader pcapFileReader;

    @Autowired
    Protocol protocol;

    private String sessionName;
    private Map<Integer, AnalyzerCell> cellMap;

    public void init(String sessionName) {
        this.sessionName = sessionName;
        this.cellMap = new HashMap<Integer, AnalyzerCell>();
        setLinkCell();
        setNetworkCell();
        setTransportCell();
        /* Create pcap analyzer and connect linkCell with it */
        this.pcapAnalyzer.setNextAnalyzerCell(linkCell);
        /* Register pcap analyzer to controller event bus */
        factory.getEventBus(CONTROLLER_BUS).register(pcapAnalyzer);
    }

    public long startExperiment() {
        linkCell.start();
        networkCell.start();
        transportCell.start();
        return pcapFileReader.readFile();
    }

    public void attachCustomAnalyzer(int cellNumber,
            CustomAnalyzer customAnalyzer) {
        System.out.println("Attaching custom analyzer in session!!");
        AnalyzerCell cell = cellMap.get(cellNumber);
        System.out.println("Cell got from local map = " + cell.getCellID());
        cell.addCustomAnalyzer(customAnalyzer);
        System.out.println("Added custom analyzer in cell");
    }

    public void setLinkCell() {
        linkCell.configure(sessionName, "linkCell", linkAnalyzer);
        cellMap.put(1, linkCell);
    }

    private void setNetworkCell() {
        networkCell.configure(sessionName, "networkCell", networkAnalyzer);
        cellMap.put(2, networkCell);
    }

    private void setTransportCell() {
        transportCell.configure(sessionName, "transportCell",
                transportAnalyzer);
        cellMap.put(3, transportCell);
    }

    public void connectCells(Map<String, Set<String>> protocolGraph) {
        for (Entry<String, Set<String>> node : protocolGraph.entrySet()) {
            AnalyzerCell cell = cellMap
                    .get(protocol.getCellNumber(node.getKey()));
            System.out.println(
                    "Analyzer cell to be configured = " + cell.getCellID());
            Set<String> toNodes = node.getValue();
            for (String protocolNode : toNodes) {
                System.out.println(
                        "Protocol name received for attaching to cell ="
                                + protocolNode);
                AnalyzerCell destinationCell = cellMap
                        .get(protocol.getCellNumber(protocolNode));
                System.out.println("Destination cell to be saved = "
                        + destinationCell.getCellID());
                cell.configureDestinationStageMap(protocolNode,
                        destinationCell);
            }
        }
    }

    public void endSession() {
        factory.getEventBus(CONTROLLER_BUS).post(new EndAnalysisEvent());
    }

    public String getSessionName() {
        return sessionName;
    }

    public PcapAnalyzer getPcapAnalyzer() {
        return pcapAnalyzer;
    }

}
