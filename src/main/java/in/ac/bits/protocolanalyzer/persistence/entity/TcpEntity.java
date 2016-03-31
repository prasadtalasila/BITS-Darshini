package in.ac.bits.protocolanalyzer.persistence.entity;

import java.io.Serializable;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * Corresponding entity class for tcp analysis. Corresponding table columns are
 * all the tcp header fields.
 * 
 * @author crygnus
 *
 */
@Getter
@Setter
@Document(indexName = "protocol", type = "tcp")
public class TcpEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private long packetId;

    private int sourcePort;

    private int destinationPort;

    private long sequenceNumber;

    private long ackNumber;

    private int dataOffset;

    private boolean cwrFlagSet;

    private boolean eceFlagSet;

    private boolean urgFlagSet;

    private boolean ackFlagSet;

    private boolean pshFlagSet;

    private boolean rstFlagSet;

    private boolean synFlagSet;

    private boolean finFlagSet;

    private int windowSize;

    private int checksum;

    private int urgentPointer;

    private String nextProtocol;
}
