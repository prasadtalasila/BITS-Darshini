package in.ac.bits.protocolanalyzer.persistence.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import in.ac.bits.protocolanalyzer.persistence.entity.TcpEntity;

/**
 * Repository implementation to access {@link TcpEntity}
 * 
 * @author crygnus
 *
 */
public interface TcpRepository
        extends ElasticsearchRepository<TcpEntity, String> {

    /**
     * Finds TcpEntity corresponding to {@link PacketIdEntity} given.
     * 
     * @param packetIdEntity
     * @return {@link TcpEntity}
     */
    public TcpEntity findByPacketId(long packetId);

}
