package in.ac.bits.protocolanalyzer.persistence.entity;

import java.io.Serializable;

import javax.persistence.Id;

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
@Document(indexName = "protocol", type = "ipv4")
public class IPv4Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

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
