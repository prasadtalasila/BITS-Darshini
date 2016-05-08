package in.ac.bits.protocolanalyzer.analyzer.transport;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import in.ac.bits.protocolanalyzer.analyzer.CustomAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.PacketWrapper;
import in.ac.bits.protocolanalyzer.analyzer.event.PacketTypeDetectionEvent;
import in.ac.bits.protocolanalyzer.persistence.entity.UdpEntity;
import in.ac.bits.protocolanalyzer.persistence.repository.AnalysisRepository;
import in.ac.bits.protocolanalyzer.protocol.Protocol;
import in.ac.bits.protocolanalyzer.utils.Beautify;
import in.ac.bits.protocolanalyzer.utils.BitOperator;
import in.ac.bits.protocolanalyzer.utils.ByteOperator;
import java.lang.String;
import java.util.Arrays;
import org.pcap4j.packet.Packet;
import org.springframework.context.annotation.Scope;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UdpAnalyzer implements CustomAnalyzer {
  private byte[] udpHeader;

  private String indexName;

  private AnalysisRepository repository;

  private int startByte;

  private int endByte;

  private EventBus eventBus;

  public void configure(EventBus eventBus, AnalysisRepository repository, String sessionName) {
    this.eventBus = eventBus;
    this.eventBus.register(this);
    this.repository = repository;
    this.indexName = "protocol_" + sessionName;
  }

  private void setUdpHeader(PacketWrapper packetWrapper) {
    Packet packet = packetWrapper.getPacket();
    int startByte = packetWrapper.getStartByte();
    byte[] rawPacket = packet.getRawData();
    this.udpHeader = Arrays.copyOfRange(rawPacket, startByte, startByte + UdpHeader.TOTAL_HEADER_LENGTH);
  }

  public void setStartByte(PacketWrapper packetWrapper) {
    this.startByte = packetWrapper.getStartByte() + UdpHeader.TOTAL_HEADER_LENGTH;
  }

  public void setEndByte(PacketWrapper packetWrapper) {
    this.endByte = packetWrapper.getEndByte();
  }

  public void publishTypeDetectionEvent(String nextPacketType, int startByte, int endByte) {
    this.eventBus.post(new PacketTypeDetectionEvent(nextPacketType, startByte, endByte));
  }

  public int getSrcPort(byte[] udpHeader) {
    byte[] srcport = BitOperator.parse(udpHeader, UdpHeader.SRCPORT_START_BIT, UdpHeader.SRCPORT_END_BIT);
    int returnVar = ByteOperator.parseBytesint(srcport);
    return returnVar;
  }

  public int getDstPort(byte[] udpHeader) {
    byte[] dstport = BitOperator.parse(udpHeader, UdpHeader.DSTPORT_START_BIT, UdpHeader.DSTPORT_END_BIT);
    int returnVar = ByteOperator.parseBytesint(dstport);
    return returnVar;
  }

  public int getLength_(byte[] udpHeader) {
    byte[] length_ = BitOperator.parse(udpHeader, UdpHeader.LENGTH__START_BIT, UdpHeader.LENGTH__END_BIT);
    int returnVar = ByteOperator.parseBytesint(length_);
    return returnVar;
  }

  public String getChecksum(byte[] udpHeader) {
    byte[] checksum = BitOperator.parse(udpHeader, UdpHeader.CHECKSUM_START_BIT, UdpHeader.CHECKSUM_END_BIT);
    return Beautify.beautify(checksum, "hex");
  }

  @Subscribe
  public void analyze(PacketWrapper packetWrapper) {
    if (Protocol.get("UDP").equalsIgnoreCase(packetWrapper.getPacketType())) {
      setUdpHeader(packetWrapper);
      String nextPacketType = setNextProtocolType();
      setStartByte(packetWrapper);
      setEndByte(packetWrapper);
      publishTypeDetectionEvent(nextPacketType, startByte, endByte);
      UdpEntity entity = new UdpEntity();
      entity.setPacketId(packetWrapper.getPacketId());
      entity.setSrcPort(getSrcPort(udpHeader));
      entity.setChecksum(getChecksum(udpHeader));
      entity.setLength_(getLength_(udpHeader));
      entity.setDstPort(getDstPort(udpHeader));
      IndexQueryBuilder builder = new IndexQueryBuilder();
      IndexQuery query = builder.withIndexName(this.indexName).withType("udp").withId(String.valueOf(packetWrapper.getPacketId())).withObject(entity).build();
      repository.save(query);
    }
  }

  public String setNextProtocolType() {
    String nextHeaderType = "NO_CONDITIONAL_HEADER_FIELD";
    switch(nextHeaderType) {
      default: return Protocol.get("END_PROTOCOL");
    }
  }
}
