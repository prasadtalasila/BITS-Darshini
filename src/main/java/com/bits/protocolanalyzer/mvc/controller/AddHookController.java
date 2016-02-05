package com.bits.protocolanalyzer.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bits.protocolanalyzer.analyzer.link.EthernetAnalyzer;
import com.bits.protocolanalyzer.analyzer.network.Ipv4Analyzer;
import com.bits.protocolanalyzer.analyzer.transport.TcpAnalyzer;
import com.bits.protocolanalyzer.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;

/**
 * 
 * @author crygnus
 *
 */

@Controller
@RequestMapping("/addHook")
public class AddHookController {

    @Autowired
    private EventBusFactory eventBusFactory;

    @RequestMapping("/ethernetHook")
    public ModelAndView addEthernetHook() {
        EventBus layerEventBus = eventBusFactory.getEventBus("layer_event_bus");
        layerEventBus.register(new EthernetAnalyzer());
        ModelAndView mav = new ModelAndView("addHook");
        mav.addObject("addHookMsg",
                "Hook for Ethernet analyzer is successfully added!");
        return mav;
    }

    @RequestMapping("/ipHook")
    public ModelAndView addIPHook() {
        EventBus layerEventBus = eventBusFactory.getEventBus("layer_event_bus");
        layerEventBus.register(new Ipv4Analyzer());
        ModelAndView mav = new ModelAndView("addHook");
        mav.addObject("addHookMsg",
                "Hook for IP analyzer is successfully added!");
        return mav;
    }

    @RequestMapping("/tcpHook")
    public ModelAndView addTCPHook() {
        EventBus layerEventBus = eventBusFactory.getEventBus("layer_event_bus");
        layerEventBus.register(new TcpAnalyzer());
        ModelAndView mav = new ModelAndView("addHook");
        mav.addObject("addHookMsg",
                "Hook for TCP analyzer is successfully added!");
        return mav;
    }
}
