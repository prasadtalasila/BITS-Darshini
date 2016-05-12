package in.ac.bits.protocolanalyzer.protocol;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import in.ac.bits.protocolanalyzer.analyzer.CustomAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.link.EthernetAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.network.IPv4Analyzer;
import in.ac.bits.protocolanalyzer.analyzer.transport.TcpAnalyzer;

/**
 * 
 * @author crygnus
 *
 */
@Component
@Log4j
public class Protocol {

	private static Map<String, String> protocolTable;
	private static Map<String, CustomAnalyzer> classTable;
	private static Map<String, Integer> cellTable;

	@Autowired
	private WebApplicationContext context;

	public void init() {
		if (context == null) {
			log.info("null context received in Protocol init");
		}
		protocolTable = new HashMap<String, String>();
		classTable = new HashMap<String, CustomAnalyzer>();
		cellTable = new HashMap<String, Integer>();
	}

	public void defaultCustoms() {
		initDefaultProtocolTable();
		initDefaultClassTable();
		initDefaultCellTable();
	}

	public static String get(String protocol) {
		String proto = protocolTable.get(protocol);
		if (proto == null) {
			proto = "NULL";
		}
		return proto;
	}

	private void initDefaultProtocolTable() {
		protocolTable.put("ETHERNET", "ETHERNET");
		protocolTable.put("IPV4", "IPV4");
		protocolTable.put("TCP", "TCP");
		protocolTable.put("END_PROTOCOL", "End or Unknown Protocol");
	}

	private void initDefaultClassTable() {
		log.info("Creating beans with context in protocol!!");
		classTable.put(get("ETHERNET"),
				(EthernetAnalyzer) context.getBean(EthernetAnalyzer.class));
		classTable.put(get("IPV4"),
				(IPv4Analyzer) context.getBean(IPv4Analyzer.class));
		classTable.put(get("TCP"),
				(TcpAnalyzer) context.getBean(TcpAnalyzer.class));
		log.info("All custom beans created in Protocol!!");
	}

	private void initDefaultCellTable() {
		cellTable.put(get("ETHERNET"), 1);
		cellTable.put(get("IPV4"), 2);
		cellTable.put(get("TCP"), 3);
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
		protocolTable.put(protocolName, protocolName);
	}

}
