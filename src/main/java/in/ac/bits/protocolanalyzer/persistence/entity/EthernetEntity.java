package in.ac.bits.protocolanalyzer.persistence.entity;

import java.lang.String;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(
    indexName = "protocol",
    type = "Ethernet",
    shards = 1,
    replicas = 0
)
public class EthernetEntity {
  private long packetId;

  private String ethertype;

  private long dst_addr;

  private long src_addr;
}
