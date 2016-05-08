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
    type = "ethernet"
)
public class EthernetEntity {
  @Id
  private long packetId;

  private String ethertype;

  private String dst_addr;

  private String src_addr;
}
