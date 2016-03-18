/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ac.bits.protocolanalyzer.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import in.ac.bits.protocolanalyzer.mvc.model.StoredPacket;
import in.ac.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;
import in.ac.bits.protocolanalyzer.persistence.entity.NetworkAnalyzerEntity;
import in.ac.bits.protocolanalyzer.persistence.entity.PacketIdEntity;
import in.ac.bits.protocolanalyzer.persistence.entity.TransportAnalyzerEntity;
import in.ac.bits.protocolanalyzer.persistence.repository.LinkAnalyzerRepository;
import in.ac.bits.protocolanalyzer.persistence.repository.NetworkAnalyzerRepository;
import in.ac.bits.protocolanalyzer.persistence.repository.PacketIdRepository;
import in.ac.bits.protocolanalyzer.persistence.repository.TransportAnalyzerRepository;

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
            long packetId = pie.getPacketId();

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
