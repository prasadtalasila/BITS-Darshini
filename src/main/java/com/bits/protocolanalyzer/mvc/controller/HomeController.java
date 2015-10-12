/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.mvc.controller;

import com.bits.protocolanalyzer.input.PcapFileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import org.pcap4j.packet.Packet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author amit
 */
@Controller
@RequestMapping("/")
public class HomeController {

	@RequestMapping
	public ModelAndView home() {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		URL[] urls = ((URLClassLoader)cl).getURLs();
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("paths", urls);
		return mav;
	}

	@RequestMapping("reader")
	public ModelAndView reader() {
		PcapFileReader pcapFileReader = new PcapFileReader();
		List<Packet> packets = pcapFileReader.readFile();
		ModelAndView mav = new ModelAndView("packetCount");
		if (packets != null && !packets.isEmpty()) {
			mav.addObject("count", packets.size());
		} else {
			mav.addObject("count", "No Packets retrived");
		}
		return mav;
	}

}
