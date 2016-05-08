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
    type = "udp"
)
public class UdpEntity {
  @Id
  private long packetId;

  private int srcPort;

  private String checksum;

  private int length_;

  private int dstPort;
}
