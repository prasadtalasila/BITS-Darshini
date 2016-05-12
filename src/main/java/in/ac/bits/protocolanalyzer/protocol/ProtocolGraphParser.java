package in.ac.bits.protocolanalyzer.protocol;

import java.util.ArrayList;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import in.ac.bits.protocolanalyzer.analyzer.Session;

@Component
@Scope(value = "prototype")
@Log4j
public class ProtocolGraphParser {

	@Autowired
	private ProtocolGraph protocolGraph;

	private ArrayList<String> graphLines = new ArrayList<String>();

	public void configureSession(Session session, String graphString) {

		String[] lines = graphString.split("\\r?\\n");
		removeBlankLines(lines);
		for (int i = 0; i < graphLines.size(); i++) {
			log.info("Line: " + i + " " + graphLines.get(i));
		}
		int linePtr = 0;
		/*
		 * Configure start node
		 */

		if (graphLines.get(linePtr).contains("start")) {
			int startPtr = linePtr;
			while (!graphLines.get(linePtr).contains("}")) {
				linePtr++;
			}
			protocolGraph.configureStartNode(session, graphLines, startPtr,
					linePtr);
		}
		linePtr++;
		int count = 0;
		while (!graphLines.get(linePtr).contains("end") && count < 5) {
			linePtr = collectNodes(session, linePtr);
			log.info("Line pointer now = " + linePtr);
			count++;
		}
		protocolGraph.configureSessionCells(session);
	}

	private int collectNodes(Session session, int linePtr) {
		int startLine = linePtr;
		int lines = graphLines.size();
		linePtr++;
		while (linePtr < lines) {
			if (!graphLines.get(linePtr).contains("graph")) {
				linePtr++;
			} else {
				break;
			}
		}

		protocolGraph
				.configureNode(session, graphLines, startLine, linePtr - 1);
		return linePtr;
	}

	private void removeBlankLines(String[] lines) {
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].trim().length() != 0) {
				graphLines.add(lines[i]);
			}
		}
	}
}
