package in.ac.bits.protocolanalyzer.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ac.bits.protocolanalyzer.persistence.entity.IPv4Entity;
import in.ac.bits.protocolanalyzer.persistence.entity.PacketIdEntity;

/**
 * Repository implementation to access {@link IPv4Entity}
 * 
 * @author crygnus
 *
 */
public interface IPv4Repository extends JpaRepository<IPv4Entity, Long> {

    /**
     * Finds IPv4Entity corresponding to {@link PacketIdEntity} given.
     * 
     * @param packetIdEntity
     * @return {@link IPv4Entity}
     */
    public IPv4Entity findByPacketIdEntity(PacketIdEntity packetIdEntity);

}
