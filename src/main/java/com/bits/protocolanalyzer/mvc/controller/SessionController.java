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
import com.bits.protocolanalyzer.analyzer.Session;
import com.bits.protocolanalyzer.analyzer.TimeSeriesAnalyzer;
import com.bits.protocolanalyzer.input.PcapFileReader;
import com.bits.protocolanalyzer.utils.EventBusFactory;

/**
 *
 * @author crygnus
 */

@Controller
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private TimeSeriesAnalyzer timeSeriesAnalyzer;

    @Autowired
    private EventBusFactory factory;

    @Autowired
    private Session session;

    @Autowired
    private PcapFileReader pcapFileReader;

    List<PacketWrapper> packets;

    @RequestMapping
    public ModelAndView getSession() {
        ModelAndView mav = new ModelAndView("sessionPage");
        return mav;
    }

    @RequestMapping("/new")
    public ModelAndView setNewSession() {
        session.configureSession("new_session", factory);
        ModelAndView mav = new ModelAndView();
        mav.addObject("sessionName", session.getSessionName());
        return mav;
    }

    @RequestMapping("/default")
    public ModelAndView setDefaultSession() {
        session.configureSession("default_session", factory);
        session.setDefault();
        ModelAndView mav = new ModelAndView("packetData");
        mav.addObject("sessionName", session.getSessionName());
        readFile(mav, session);
        return mav;
    }

    public void readFile(ModelAndView mav, Session session) {
        long packetCount = pcapFileReader.readFile();
        if (packetCount != 0) {
            mav.addObject("packetCount", String.valueOf(packetCount));
        } else {
            mav.addObject("packetCount", "No Packets retrived");
        }
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

    @RequestMapping("/addCaptureFilter")
    public void addCaptureFilter(Session session) {
        /*
         * Yet to decide how to do it
         */
    }

    @RequestMapping("/validate")
    public boolean validateSession(Session session) {

        /*
         * Validation logic based on determining whether given graph is a
         * subgraph of the "big-graph" of protocols.
         */

        return false;
    }

}
