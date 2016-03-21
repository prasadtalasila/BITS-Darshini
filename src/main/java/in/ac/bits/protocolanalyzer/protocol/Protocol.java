package in.ac.bits.protocolanalyzer.protocol;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import in.ac.bits.protocolanalyzer.analyzer.CustomAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.link.EthernetAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.network.IPv4Analyzer;
import in.ac.bits.protocolanalyzer.analyzer.transport.TcpAnalyzer;
import in.ac.bits.protocolanalyzer.utils.ApplicationContextUtils;

/**
 * 
 * @author crygnus
 *
 */
@Component
public class Protocol {

    public static final String ETHERNET = "ETHERNET";
    public static final String IPV4 = "IPV4";
    public static final String IPV6 = "IPV6";
    public static final String TCP = "TCP";
    public static final String UDP = "UDP";
    public static final String HTTP = "HTTP";
    public static final String HTTPS = "HTTPS";
    public static final String END_PROTOCOL = "End or Unknown Protocol";

    private static Map<String, CustomAnalyzer> classTable;
    private static Map<String, Integer> cellTable;

    @Autowired
    ApplicationContextUtils contextUtils;

    public void init() {
        ApplicationContext context = contextUtils.getApplicationContext();
        if (context == null) {
            System.out.println("null context received in Protocol init");
        }
        classTable = new HashMap<String, CustomAnalyzer>();
        cellTable = new HashMap<String, Integer>();
        initDefaultClassTable(context);
        initDefaultCellTable();
    }

    private void initDefaultClassTable(ApplicationContext context) {
        System.out.println("Creating beans with context in protocol!!");
        classTable.put(ETHERNET,
                (EthernetAnalyzer) context.getBean(EthernetAnalyzer.class));
        classTable.put(IPV4,
                (IPv4Analyzer) context.getBean(IPv4Analyzer.class));
        classTable.put(TCP, (TcpAnalyzer) context.getBean(TcpAnalyzer.class));
        System.out.println("All custom beans created in Protocol!!");
    }

    private void initDefaultCellTable() {
        cellTable.put(ETHERNET, 1);
        cellTable.put(IPV4, 2);
        cellTable.put(TCP, 3);
    }

    public CustomAnalyzer getCustomAnalyzer(String protocolName) {
        // possible null (exception)
        return classTable.get(protocolName.toUpperCase());
    }

    public int getCellNumber(String protocolName) {
        // possible null (exception)
        return cellTable.get(protocolName.toUpperCase());
    }

    public void addCustomAnalyzer(CustomAnalyzer analyzer, String protocolName,
            int cellStage) {
        protocolName = protocolName.toUpperCase();
        classTable.put(protocolName, analyzer);
        cellTable.put(protocolName.toUpperCase(), cellStage);
    }

}
