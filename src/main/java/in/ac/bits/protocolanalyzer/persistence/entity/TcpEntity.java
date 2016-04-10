package in.ac.bits.protocolanalyzer.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(
    indexName = "protocol",
    type = "Tcp",
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

  private int checksum;

  private long ackNo;

  private int dstPort;

  private short flags;

  private byte dataOffset;
}
