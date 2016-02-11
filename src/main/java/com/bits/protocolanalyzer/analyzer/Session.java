package com.bits.protocolanalyzer.analyzer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.protocolanalyzer.analyzer.link.EthernetAnalyzer;
import com.bits.protocolanalyzer.analyzer.link.LinkAnalyzer;
import com.bits.protocolanalyzer.analyzer.network.IPv4Analyzer;
import com.bits.protocolanalyzer.analyzer.network.NetworkAnalyzer;
import com.bits.protocolanalyzer.analyzer.transport.TcpAnalyzer;
import com.bits.protocolanalyzer.analyzer.transport.TransportAnalyzer;
import com.bits.protocolanalyzer.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;

import lombok.Getter;

/**
 * 
 * @author crygnus
 *
 */

@Component
@Getter
public class Session {

    public static String defaultCellStageOne = "LINK_CELL";
    public static String defaultCellStageTwo = "NETWORK_CELL";
    public static String defaultCellStageThree = "TRANSPORT_CELL";

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

    private String sessionName;
    private Map<String, AnalyzerCell> cellMap;

    private EventBusFactory factory;

    public void configureSession(String sessionName, EventBusFactory factory) {
        this.sessionName = sessionName;
        this.cellMap = new HashMap<String, AnalyzerCell>();
        this.factory = factory;
    }

    public String getSessionName() {
        return this.sessionName;
    }

    public PcapAnalyzer getPcapAnalyzer() {
        return this.pcapAnalyzer;
    }

    private void setLinkCell() {
        linkCell.configure(sessionName, linkAnalyzer, "link_layer_bus",
                factory);
        /* Attach Ethernet Analyzer Hook */
        EventBus linkEventBus = linkCell.getEventBus();
        linkEventBus.register(new EthernetAnalyzer(linkEventBus));
        this.cellMap.put(defaultCellStageOne, linkCell);
    }

    private void setNetworkCell() {
        networkCell.configure(sessionName, networkAnalyzer, "network_layer_bus",
                factory);
        /* Attach IPv4 Analyzer Hook */
        EventBus networkEventBus = networkCell.getEventBus();
        networkEventBus.register(new IPv4Analyzer(networkEventBus));
        this.cellMap.put(defaultCellStageTwo, networkCell);
    }

    private void setTransportCell() {
        transportCell.configure(sessionName, transportAnalyzer,
                "transport_layer_bus", factory);
        /* Attach TCP Analyzer Hook */
        EventBus transportEventBus = transportCell.getEventBus();
        transportEventBus.register(new TcpAnalyzer(transportEventBus));
        this.cellMap.put(defaultCellStageThree, transportCell);
    }

    public void setDefault() {

        setLinkCell();

        setNetworkCell();

        setTransportCell();

        /* Create pcap analyzer and connect linkCell with it */
        this.pcapAnalyzer.setNextAnalyzerCell(linkCell);

        linkCell.configureDestinationStageMap(Protocol.IPV4, networkCell);
        networkCell.configureDestinationStageMap(Protocol.TCP, transportCell);
        /*
         * Configure each cell with default graph
         * graphParser.configureCellWithDefaultGraph(linkCell);
         * graphParser.configureCellWithDefaultGraph(networkCell);
         * graphParser.configureCellWithDefaultGraph(transportCell);
         */
    }

}
