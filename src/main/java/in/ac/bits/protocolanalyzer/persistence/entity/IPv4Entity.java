package in.ac.bits.protocolanalyzer.persistence.entity;

import java.lang.String;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IPv4Entity {
    @Id
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
