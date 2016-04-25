package in.ac.bits.protocolanalyzer.persistence.entity;

import java.lang.String;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(
        indexName = "protocol",
        type = "tcp"
    )
public class TcpEntity {
    @Id
  private long packetId;

  private int window;

  private long seqNo;

  private byte res;

  private int srcPort;

  private int urgentPtr;

  private String checksum;

  private long ackNo;

  private int dstPort;

  private String flags;

  private byte dataOffset;
}
