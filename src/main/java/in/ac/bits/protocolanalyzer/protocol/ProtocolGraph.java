package in.ac.bits.protocolanalyzer.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import in.ac.bits.protocolanalyzer.analyzer.CustomAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.Session;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Component
@Scope(value = "prototype")
@Getter
@Log4j
public class ProtocolGraph {

	@Autowired
	private Protocol protocol;

	private Map<String, Set<String>> protocolGraph = new HashMap<String, Set<String>>();

	public void configureNode(Session session, ArrayList<String> graphLines,
			int startLine, int endLine) {
		String nodeName = graphLines.get(startLine);
		String[] tokens = nodeName.split("\\s");
		nodeName = tokens[1].toUpperCase();
		log.info("Node name extracted = " + nodeName);
		configureProtocol(session, nodeName);
		Set<String> toNodes = new HashSet<String>();
		int linePtr = startLine + 2;
		while (linePtr < endLine - 1) {
			String[] parts = graphLines.get(linePtr).split(":");
			String protocolName = parts[1].substring(0, parts[1].length() - 1)
					.trim();
			log.info("Protocol name extracted in case statement ="
					+ protocolName);
			toNodes.add(protocolName.toUpperCase());
			linePtr++;
		}
		protocolGraph.put(nodeName.toUpperCase(), toNodes);
	}

	public void configureSessionCells(Session session) {
		if (protocolGraph == null) {
			log.info("protocolgraph is null in Protocolgraph!!");
		} else {
			for (Entry<String, Set<String>> entry : protocolGraph.entrySet()) {
				log.info("for protocol :" + entry.getKey());
				for (String str : entry.getValue()) {
					log.info("Attaching: " + str);
				}
			}
		}
		session.connectCells(protocolGraph);
	}

	public void configureStartNode(Session session,
			ArrayList<String> graphLines, int startLine, int endLine) {
		String protocolName = graphLines.get(startLine + 1);
		protocolName = protocolName.substring(0, protocolName.length() - 1)
				.trim();
		log.info("Protocol name in startGraph = " + protocolName);
		configureProtocol(session, protocolName);

	}

	private void configureProtocol(Session session, String protocolName) {
		CustomAnalyzer analyzer = protocol.getCustomAnalyzer(protocolName);
		log.info("Custom analyzer obtained in protocol graph = "
				+ analyzer.toString());
		int cellNumber = protocol.getCellNumber(protocolName);
		log.info("Cell number returned by protocol = " + cellNumber);
		session.attachCustomAnalyzer(cellNumber, analyzer);
	}

}