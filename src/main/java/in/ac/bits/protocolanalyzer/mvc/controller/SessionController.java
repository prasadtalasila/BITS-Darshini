/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ac.bits.protocolanalyzer.mvc.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import in.ac.bits.protocolanalyzer.analyzer.Session;
import in.ac.bits.protocolanalyzer.protocol.Protocol;
import in.ac.bits.protocolanalyzer.protocol.ProtocolGraphParser;

/**
 *
 * @author crygnus
 */
@Controller
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private WebApplicationContext context;

    private Session session;

    private ProtocolGraphParser graphParser;

    @Autowired
    private Protocol protocol;

    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    public @ResponseBody String analyze(
            @RequestParam("graph") String protocolGraphStr) {
        System.out.println("Graph string parsed = \n" + protocolGraphStr);
        // Initializing session and protocol
        init();
        graphParser = context.getBean(ProtocolGraphParser.class);
        graphParser.configureSession(session, protocolGraphStr);
        System.out.println("Successfully completed session configuration!!");
        /*
         * later to be replaced by session.startExperiment() call return value
         */
        long readCount = 0;
        /* long readCount = session.startExperiment(); */
        JSONObject response = new JSONObject();
        response.put("status", "success");
        response.put("pktCount", readCount);
        return response.toString();
    }

    /*
     * later this method can be converted to an API.
     */
    private void init() {
        this.session = context.getBean(Session.class);
        session.init("session_name");
        System.out.println("Session init complete!!");
        protocol.init();
        System.out.println(
                "Successfully completed init method in session controller!!");
    }
}
