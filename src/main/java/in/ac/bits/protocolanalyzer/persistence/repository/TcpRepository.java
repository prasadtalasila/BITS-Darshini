package in.ac.bits.protocolanalyzer.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ac.bits.protocolanalyzer.persistence.entity.PacketIdEntity;
import in.ac.bits.protocolanalyzer.persistence.entity.TcpEntity;

/**
 * Repository implementation to access {@link TcpEntity}
 * 
 * @author crygnus
 *
 */
public interface TcpRepository extends JpaRepository<TcpEntity, Long> {

    /**
     * Finds TcpEntity corresponding to {@link PacketIdEntity} given.
     * 
     * @param packetIdEntity
     * @return {@link TcpEntity}
     */
    public TcpEntity findByPacketIdEntity(PacketIdEntity packetIdEntity);

}
