package in.ac.bits.protocolanalyzer.mvc.controller;

import java.util.Random;

import lombok.extern.log4j.Log4j;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import in.ac.bits.protocolanalyzer.analyzer.Session;
import in.ac.bits.protocolanalyzer.protocol.Protocol;
import in.ac.bits.protocolanalyzer.protocol.ProtocolChecker;
import in.ac.bits.protocolanalyzer.protocol.ProtocolGraphParser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("/test")
@Log4j
public class TestController {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	@Qualifier("concurrentExp")
	private ConcurrentExp exp1,exp2;

	@RequestMapping(value = "/srun", method = RequestMethod.GET)
	public void runSequentialExp()throws Exception {
		String pcapPath = "/home/vagrant/darshini/data/packet/packet_data.pcap";
		String protocolGraphStr = "graph start {\n\tethernet;\n}\ngraph ethernet {\n\tswitch(ethertype) {\n\t\tcase 0800:			 ipv4;\n\t}\n}\ngraph ipv4 {\n\tswitch(protocol) {\n\t\tcase 06: tcp;\n\t}\n}\ngraph tcp {\n}\ngraph end {\n}";

		log.info("EXECUTING IN THREAD");
		exp1.init(pcapPath, protocolGraphStr);
		exp1.call();
		TimeUnit.SECONDS.sleep(10);
		exp2.init(pcapPath, protocolGraphStr);
		exp2.call();
		log.info("FINISHED");
	}

	@RequestMapping(value = "/crun", method = RequestMethod.GET)
	public void runConcurrentExp()throws Exception {
		ExecutorService executors =  Executors.newFixedThreadPool(2);
		log.info("EXECUTING IN THREAD");
		executors.submit(exp1);
    executors.submit(exp2);
		log.info("FINISHED");
	}
}
