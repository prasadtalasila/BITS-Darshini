package in.ac.bits.protocolanalyzer.persistence.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * Corresponding entity class for ipv4 analysis. Corresponding table columns
 * include all the ipv4 header fields.
 * 
 * @author crygnus
 *
 */
@Getter
@Setter
@Document(indexName = "protocol", type = "ipv4", shards=1, replicas=0)
public class IPv4Entity {

    private long packetId;

    private int version;

    private int ihl;

    private int totalLength;

    private int identification;

    private boolean dontFragment;

    private boolean moreFragment;

    private int fragmentOffset;

    private int ttl;

    private String nextProtocol;

    private int checksum;

    private String sourceAddr;

    private String destinationAddr;
}
