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
    type = "ipv6"
)
public class IPv6Entity {
  @Id
  private long packetId;

  private int flowLabel;

  private String dstAddr;

  private String nextHdr;

  private byte version;

  private short hopLimit;

  private int payloadLen;

  private short trafficClass;

  private String srcAddr;
}
