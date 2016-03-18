/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ac.bits.protocolanalyzer.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ac.bits.protocolanalyzer.persistence.entity.NetworkAnalyzerEntity;
import in.ac.bits.protocolanalyzer.persistence.entity.PacketIdEntity;

/**
 *
 * @author amit
 */
public interface NetworkAnalyzerRepository extends
        JpaRepository<NetworkAnalyzerEntity, Long> {

    public NetworkAnalyzerEntity findByPacketIdEntity(
            PacketIdEntity packetIdEntity);

}
