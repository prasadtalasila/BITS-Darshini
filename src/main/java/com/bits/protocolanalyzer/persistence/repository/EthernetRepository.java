package com.bits.protocolanalyzer.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bits.protocolanalyzer.persistence.entity.EthernetEntity;
import com.bits.protocolanalyzer.persistence.entity.PacketIdEntity;

/**
 * Repository implementation to access {@link EthernetEntity}
 * 
 * @author crygnus
 *
 */

public interface EthernetRepository
        extends JpaRepository<EthernetEntity, Long> {

    /**
     * Finds EthernetEntity corresponding to {@link PacketIdEntity} given
     * 
     * @param packetIdEntity
     * @return {@link EthernetEntity}
     */
    public EthernetEntity findByPacketIdEntity(PacketIdEntity packetIdEntity);

}
