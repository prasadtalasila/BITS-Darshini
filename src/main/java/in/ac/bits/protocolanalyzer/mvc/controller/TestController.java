package in.ac.bits.protocolanalyzer.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

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

import in.ac.bits.protocolanalyzer.mvc.model.Experiment;




@Controller
@RequestMapping("/test")
@Log4j
public class TestController {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	@Qualifier("Experiment")
	private Experiment exp1;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String runExp(@RequestParam("protocolGraphPath") String protocolGraphPath, @RequestParam("pcapPath") String pcapPath)throws IOException {
		
		String protocolGraphStr = null;
		//Exception handlers for missing file / access issues / empty file for p4 graph
		try {
			protocolGraphStr = new String(Files.readAllBytes(Paths.get(protocolGraphPath)));
	    }
	    catch(NoSuchFileException noSuchFileException) {
	    	return (new JSONObject()).put("packetCount", 0).put("status", "failure").put("comment", "No such .p4 File").toString();
	    } 
		catch(AccessDeniedException accessDeniedException) {
	    	return (new JSONObject()).put("packetCount", 0).put("status", "failure").put("comment", "Access to file Denied").toString();
		}
		if(protocolGraphStr.equals("") || protocolGraphStr==null) {
	    	return (new JSONObject()).put("packetCount", 0).put("status", "failure").put("comment", "Empty file").toString();
		}
		
		//Exception handlers for missing file / access issues / empty file for .pcap file
		
		File pcapFile = new File(pcapPath);
		if(!(pcapFile.isFile())) {
	    	return (new JSONObject()).put("packetCount", 0).put("status", "failure").put("comment", "No such .pcap File").toString();
		}
		if(pcapFile.length()==0) {
	    	return (new JSONObject()).put("packetCount", 0).put("status", "failure").put("comment", "Empty .pcap file").toString();
		}
		if(!pcapFile.canRead()) {
	    	return (new JSONObject()).put("packetCount", 0).put("status", "failure").put("comment", "Access to file Denied").toString();
		}
		
		//Exception handlers for invalid contents in p4 graph or .pcap file
		
		String experimentResults = null;
		try {
			exp1.init(pcapPath, protocolGraphStr);
			experimentResults = exp1.analyze();
		}
		catch(Exception e) {
	    	return (new JSONObject()).put("packetCount", 0).put("status", "failure").put("comment", "Invalid Contents in file(s)").toString();
		}
		
		return experimentResults;
	}
}
