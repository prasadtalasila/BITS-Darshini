/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;
import com.bits.protocolanalyzer.persistence.entity.NetworkAnalyzerEntity;
import com.bits.protocolanalyzer.persistence.entity.PacketIdEntity;
import com.bits.protocolanalyzer.persistence.entity.TransportAnalyzerEntity;
import com.bits.protocolanalyzer.repository.LinkAnalyzerRepository;
import com.bits.protocolanalyzer.repository.NetworkAnalyzerRepository;
import com.bits.protocolanalyzer.repository.PacketIdRepository;
import com.bits.protocolanalyzer.repository.TransportAnalyzerRepository;
import com.bits.protocolanalyzer.utils.StoredPacket;

/**
 *
 * @author amit
 */
@Controller
@RequestMapping("/stored_view")
public class StoredViewController {

    @Autowired
    private PacketIdRepository packetIdRepository;

    @Autowired
    private LinkAnalyzerRepository linkAnalyzerRepository;

    @Autowired
    private NetworkAnalyzerRepository networkAnalyzerRepository;

    @Autowired
    private TransportAnalyzerRepository transportAnalyzerRepository;

    @RequestMapping
    public ModelAndView viewStoredPackets() {
        ModelAndView mav = new ModelAndView("viewStored");
        List<StoredPacket> storedPackets = getStoredPackets();
        mav.addObject("listSize", storedPackets.size());
        mav.addObject("packetList", storedPackets);
        return mav;
    }

    private List<StoredPacket> getStoredPackets() {
        List<StoredPacket> storedPackets = new ArrayList<>();

        List<PacketIdEntity> packets = packetIdRepository.findAll();
        for (PacketIdEntity pie : packets) {
            StoredPacket sp = new StoredPacket();
            int packetId = pie.getPacketId();

            LinkAnalyzerEntity lae = linkAnalyzerRepository
                    .findByPacketIdEntity(pie);
            NetworkAnalyzerEntity nae = networkAnalyzerRepository
                    .findByPacketIdEntity(pie);
            TransportAnalyzerEntity tae = transportAnalyzerRepository
                    .findByPacketIdEntity(pie);

            sp.setPacketId(packetId);
            sp.setLinkAnalyzerEntity(lae);
            sp.setNetworkAnalyzerEntity(nae);
            sp.setTransportAnalyzerEntity(tae);
            storedPackets.add(sp);
        }
        return storedPackets;
    }
}
