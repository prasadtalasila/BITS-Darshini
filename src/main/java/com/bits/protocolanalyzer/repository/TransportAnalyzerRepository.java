/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bits.protocolanalyzer.persistence.entity.PacketIdEntity;
import com.bits.protocolanalyzer.persistence.entity.TransportAnalyzerEntity;

/**
 *
 * @author amit
 */
public interface TransportAnalyzerRepository extends
        JpaRepository<TransportAnalyzerEntity, Long> {

    public TransportAnalyzerEntity findByPacketIdEntity(
            PacketIdEntity packetIdEntity);

}
