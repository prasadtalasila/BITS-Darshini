package in.ac.bits.protocolanalyzer.persistence.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import in.ac.bits.protocolanalyzer.persistence.entity.IPv4Entity;

/**
 * Repository implementation to access {@link IPv4Entity}
 * 
 * @author crygnus
 *
 */
public interface IPv4Repository
        extends ElasticsearchRepository<IPv4Entity, String> {

    /**
     * Finds IPv4Entity corresponding to {@link PacketIdEntity} given.
     * 
     * @param packetIdEntity
     * @return {@link IPv4Entity}
     */
    public IPv4Entity findByPacketId(long packetId);

}
