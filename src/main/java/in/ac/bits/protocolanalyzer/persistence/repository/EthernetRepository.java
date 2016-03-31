package in.ac.bits.protocolanalyzer.persistence.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import in.ac.bits.protocolanalyzer.persistence.entity.EthernetEntity;

/**
 * Repository implementation to access {@link EthernetEntity}
 * 
 * @author crygnus
 *
 */

public interface EthernetRepository
        extends ElasticsearchRepository<EthernetEntity, String> {

    /**
     * Finds EthernetEntity corresponding to {@link PacketIdEntity} given
     * 
     * @param packetIdEntity
     * @return {@link EthernetEntity}
     */
    public EthernetEntity findByPacketId(long packetId);

}
