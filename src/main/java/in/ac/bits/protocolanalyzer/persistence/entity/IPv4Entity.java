package in.ac.bits.protocolanalyzer.persistence.entity;

import java.lang.String;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(
    indexName = "protocol",
    type = "IPv4",
    shards = 1,
    replicas = 0
)
public class IPv4Entity {
  private long packetId;

  private int totalLen;

  private long dstAddr;

  private short diffserv;

  private byte version;

  private int hdrChecksum;

  private int identification;

  private long srcAddr;

  private byte flags;

  private short fragOffset;

  private byte ihl;

  private String protocol;

  private short ttl;
}
