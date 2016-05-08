package in.ac.bits.protocolanalyzer.persistence.entity;

import java.lang.String;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(
    indexName = "protocol",
    type = "ipv4"
)
public class IPv4Entity {
  @Id
  private long packetId;

  private String totalLen;

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
