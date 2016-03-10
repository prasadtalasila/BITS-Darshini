/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;
import com.bits.protocolanalyzer.persistence.entity.PacketIdEntity;

/**
 *
 * @author amit
 */
public interface LinkAnalyzerRepository extends
        JpaRepository<LinkAnalyzerEntity, Long> {

    public LinkAnalyzerEntity findByPacketIdEntity(PacketIdEntity packetIdEntity);

}
