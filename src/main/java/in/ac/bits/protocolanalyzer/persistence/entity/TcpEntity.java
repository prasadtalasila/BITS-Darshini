package in.ac.bits.protocolanalyzer.persistence.entity;

import java.lang.String;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(
    indexName = "protocol",
    type = "tcp",
    shards = 1,
    replicas = 0
)
public class TcpEntity {
  private long packetId;

  private int window;

  private long seqNo;

  private byte res;

  private int srcPort;

  private int urgentPtr;

  private String checksum;

  private long ackNo;

  private int dstPort;

  private String flags;

  private byte dataOffset;
}
