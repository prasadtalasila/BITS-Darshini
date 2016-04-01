package in.ac.bits.protocolanalyzer.persistence.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * Corresponding entity class for ethernet analysis. Corresponding table columns
 * are source_addr, destination_addr and ether_type
 * 
 * @author crygnus
 *
 */

@Getter
@Setter
@Document(indexName = "protocol", type = "ethernet", shards=1, replicas=0)
public class EthernetEntity {

    private long packetId;

    private String sourceAddr;

    private String dstAddr;

    private String etherType;

}
