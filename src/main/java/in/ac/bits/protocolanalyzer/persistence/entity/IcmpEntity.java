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
    type = "icmp"
)
public class IcmpEntity {
  @Id
  private long packetId;

  private String hdrChecksum;

  private short type_;

  private short code;
}
