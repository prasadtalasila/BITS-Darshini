package in.ac.bits.protocolanalyzer.analyzer.link;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import in.ac.bits.protocolanalyzer.analyzer.CustomAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.PacketWrapper;
import in.ac.bits.protocolanalyzer.analyzer.event.PacketTypeDetectionEvent;
import in.ac.bits.protocolanalyzer.persistence.entity.EthernetEntity;
import in.ac.bits.protocolanalyzer.persistence.repository.AnalysisRepository;
import in.ac.bits.protocolanalyzer.protocol.Protocol;
import in.ac.bits.protocolanalyzer.utils.BitOperator;
import in.ac.bits.protocolanalyzer.utils.ByteOperator;
import java.lang.String;
import java.util.Arrays;
import org.apache.commons.codec.binary.Hex;
import org.pcap4j.packet.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class EthernetAnalyzer implements CustomAnalyzer {
  private static final String PACKET_TYPE_OF_RELEVANCE = Protocol.ETHERNET;

  private byte[] ethernetHeader;

  @Autowired
  private AnalysisRepository repository;

  private int startByte;

  private int endByte;

  private EventBus eventBus;

  public void configure(EventBus eventBus) {
    this.eventBus = eventBus;
    this.eventBus.register(this);
  }

  private void setEthernetHeader(PacketWrapper packetWrapper) {
    Packet packet = packetWrapper.getPacket();
    int startByte = packetWrapper.getStartByte();
    byte[] rawPacket = packet.getRawData();
    this.ethernetHeader = Arrays.copyOfRange(rawPacket, startByte, startByte + EthernetHeader.TOTAL_HEADER_LENGTH);
  }

  public void setStartByte(PacketWrapper packetWrapper) {
    this.startByte = packetWrapper.getStartByte() + EthernetHeader.TOTAL_HEADER_LENGTH;
  }

  public void setEndByte(PacketWrapper packetWrapper) {
    this.endByte = packetWrapper.getEndByte();
  }

  public void publishTypeDetectionEvent(String nextPacketType, int startByte, int endByte) {
    this.eventBus.post(new PacketTypeDetectionEvent(nextPacketType, startByte, endByte));
  }

  public long getDst_addr(byte[] ethernetHeader) {
    byte[] dst_addr = BitOperator.parse(ethernetHeader, EthernetHeader.DST_ADDR_START_BIT, EthernetHeader.DST_ADDR_END_BIT);
    long returnVar = ByteOperator.parseByteslong(dst_addr);
    return returnVar;
  }

  public long getSrc_addr(byte[] ethernetHeader) {
    byte[] src_addr = BitOperator.parse(ethernetHeader, EthernetHeader.SRC_ADDR_START_BIT, EthernetHeader.SRC_ADDR_END_BIT);
    long returnVar = ByteOperator.parseByteslong(src_addr);
    return returnVar;
  }

  public String getEthertype(byte[] ethernetHeader) {
    byte[] ethertype = BitOperator.parse(ethernetHeader, EthernetHeader.ETHERTYPE_START_BIT, EthernetHeader.ETHERTYPE_END_BIT);
    return Hex.encodeHexString(ethertype);
  }

  @Subscribe
  public void analyze(PacketWrapper packetWrapper) {
    if (PACKET_TYPE_OF_RELEVANCE.equalsIgnoreCase(packetWrapper.getPacketType())) {
      setEthernetHeader(packetWrapper);
      String nextPacketType = setNextProtocolType();
      setStartByte(packetWrapper);
      setEndByte(packetWrapper);
      publishTypeDetectionEvent(nextPacketType, startByte, endByte);
      EthernetEntity entity = new EthernetEntity();
      entity.setPacketId(packetWrapper.getPacketId());
      entity.setEthertype(getEthertype(ethernetHeader));
      entity.setDst_addr(getDst_addr(ethernetHeader));
      entity.setSrc_addr(getSrc_addr(ethernetHeader));
      IndexQuery query = new IndexQuery();
      query.setObject(entity);
      repository.save(query);
    }
  }

  public String setNextProtocolType() {
    String nextHeaderType = getEthertype(this.ethernetHeader);
    switch(nextHeaderType) {
      case "0800": return Protocol.IPV4;
      default: return Protocol.END_PROTOCOL;
    }
  }
}
