package in.ac.bits.protocolanalyzer.persistence.entity;

import java.lang.String;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(
    indexName = "protocol",
    type = "ethernet",
    shards = 1,
    replicas = 0
)
public class EthernetEntity {
  private long packetId;

  private String ethertype;

  private String dst_addr;

  private String src_addr;
}
