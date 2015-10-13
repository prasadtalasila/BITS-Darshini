/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.mvc.controller;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.input.PcapFileReader;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author amit
 */
@Controller
@RequestMapping("/reader")
public class InputController {

	List<PacketWrapper> packets;

	@RequestMapping
	public ModelAndView reader() {
		PcapFileReader pcapFileReader = new PcapFileReader();
		packets = pcapFileReader.readFile();
		ModelAndView mav = new ModelAndView("packetData");
		if (packets != null && !packets.isEmpty()) {
			int listSize = packets.size();
			mav.addObject("packetCount", listSize);
			mav.addObject("packetList", packets);
		} else {
			mav.addObject("packetList", "No Packets retrived");
		}
		return mav;
	}
	
	
}
