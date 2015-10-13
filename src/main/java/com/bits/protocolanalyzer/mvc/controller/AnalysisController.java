/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author amit
 */
@Controller
@RequestMapping("/analyze")
public class AnalysisController {

	@RequestMapping
	public ModelAndView analyzePackets() {
//		PcapAnalyzer pcapAnalyzer = new PcapAnalyzer(packets);
//		pcapAnalyzer.analyzePackets();
		ModelAndView mav = new ModelAndView("analyzeData");
		return mav;
	}

}
