package in.ac.bits.protocolanalyzer.persistence.entity;

import java.lang.String;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(
    indexName = "protocol",
    type = "ipv4",
    shards = 1,
    replicas = 0
)
public class IPv4Entity {
  private long packetId;

  private int totalLen;

  private String dstAddr;

  private short diffserv;

  private byte version;

  private String hdrChecksum;

  private int identification;

  private String srcAddr;

  private byte flags;

  private short fragOffset;

  private byte ihl;

  private String protocol;

  private short ttl;
}
