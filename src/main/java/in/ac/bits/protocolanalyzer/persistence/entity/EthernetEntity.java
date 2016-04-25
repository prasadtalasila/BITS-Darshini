package in.ac.bits.protocolanalyzer.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;

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
