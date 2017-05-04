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


@Controller
@RequestMapping("/test")
@Log4j
public class TestController {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	@Qualifier("concurrentExp")
	private Callable<Long> exp1;

	@RequestMapping(value = "/run", method = RequestMethod.GET)
	public void checkTwoExp()throws Exception {
		ExecutorService executors =  Executors.newFixedThreadPool(2);
		log.info("EXECUTING IN THREAD");
		executors.submit(exp1);
		log.info("FINISHED");
	}

}
