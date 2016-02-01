/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bits.protocolanalyzer.analyzer.PacketWrapper;
import com.bits.protocolanalyzer.analyzer.PcapAnalyzer;
import com.bits.protocolanalyzer.analyzer.TimeSeriesAnalyzer;
import com.bits.protocolanalyzer.input.PcapFileReader;
import com.bits.protocolanalyzer.repository.LinkAnalyzerRepository;
import com.bits.protocolanalyzer.repository.NetworkAnalyzerRepository;
import com.bits.protocolanalyzer.repository.TransportAnalyzerRepository;

/**
 *
 * @author amit
 */
@Controller
@RequestMapping("/reader")
public class InputController {

    @Autowired
    private PcapAnalyzer pcapAnalyzer;

    @Autowired
    private TimeSeriesAnalyzer timeSeriesAnalyzer;

    @Autowired
    private LinkAnalyzerRepository linkAnalyzerRepository;

    @Autowired
    private NetworkAnalyzerRepository networkAnalyzerRepository;

    @Autowired
    private TransportAnalyzerRepository transportAnalyzerRepository;

    List<PacketWrapper> packets;

    @RequestMapping
    public ModelAndView readFile() {
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

    @RequestMapping("/analysis")
    public ModelAndView analyzePackets() {
        ModelAndView mav = new ModelAndView("analyzeData");
        if (packets != null && !packets.isEmpty() && pcapAnalyzer != null) {
            pcapAnalyzer.setPackets(packets);
            pcapAnalyzer.analyzePackets();
            mav.addObject("packetList", "Packets retrived");
        } else {
            mav.addObject("packetList", "No Packets retrived");
        }
        return mav;
    }

    @RequestMapping("/stats-analysis")
    public ModelAndView analyzePcapStats() {
        ModelAndView mav = new ModelAndView("packetStats");
        timeSeriesAnalyzer.updateStats(packets);
        mav.addObject("meanTimeStamp",
                timeSeriesAnalyzer.getMeanTimeofArrival());
        mav.addObject("standardDeviation",
                timeSeriesAnalyzer.getStandardDeviation());
        return mav;
    }

}
