package in.ac.bits.protocolanalyzer.mvc.controller;

import java.util.Random;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.WebApplicationContext;

import in.ac.bits.protocolanalyzer.analyzer.Session;
import in.ac.bits.protocolanalyzer.protocol.Protocol;
import in.ac.bits.protocolanalyzer.protocol.ProtocolChecker;
import in.ac.bits.protocolanalyzer.protocol.ProtocolGraphParser;

import java.util.concurrent.Callable;

@Component
@Scope("prototype")
@Qualifier("concurrentExp")
@Getter
@Setter
@Log4j
public class ConcurrentExp implements Callable<Long> {

	@Autowired
	private WebApplicationContext context;

	private Session session;

	private ProtocolGraphParser graphParser;

	@Autowired
	private Protocol protocol;

	@Autowired
	private ProtocolChecker checker;

	public Long call() throws Exception {
		log.info("In call method of the Concurrent exp");
		return analyze();
	}

	public long analyze() {
		// Initializing session and protocol
		log.info("Starting to analyze ..........");
		String pcapPath = "/home/vagrant/darshini/data/packet/sample_capture_test76.pcap";
		String protocolGraphStr = "graph start {\n\tethernet;\n}\ngraph ethernet {\n\tswitch(ethertype) {\n\t\tcase 0800:			 ipv4;\n\t}\n}\ngraph ipv4 {\n\tswitch(protocol) {\n\t\tcase 06: tcp;\n\t}\n}\ngraph tcp {\n}\ngraph end {\n}";
		init(pcapPath);
		graphParser = context.getBean(ProtocolGraphParser.class);
		graphParser.configureSession(session, protocolGraphStr);
		log.info("Successfully completed session configuration!!");

		long readCount = session.startExperiment();
		return readCount;
	}

	/*
	 * later this method can be converted to an API.
	 */
	private void init(String pcapPath) {
		this.session = context.getBean(Session.class);
		Random rand = new Random();
		session.init("session_" + rand.nextInt(), pcapPath);
		log.info("Session init complete!!");
		protocol.init();
		checker.checkNAdd();
		log.info("Successfully completed init method in session controller!!");
	}

}
