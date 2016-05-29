package in.ac.bits.protocolanalyzer.protocol;

import in.ac.bits.protocolanalyzer.analyzer.link.EthernetAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.network.IPv4Analyzer;
import in.ac.bits.protocolanalyzer.analyzer.network.IPv6Analyzer;
import in.ac.bits.protocolanalyzer.analyzer.transport.IcmpAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.transport.TcpAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.transport.UdpAnalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProtocolChecker {
  private static boolean defaultStatus = false;

  @Autowired
  private Protocol protocol;

  @Autowired
  private IPv4Analyzer ipv4analyzer;

  @Autowired
  private EthernetAnalyzer ethernetanalyzer;

  @Autowired
  private IPv6Analyzer ipv6analyzer;

  @Autowired
  private TcpAnalyzer tcpanalyzer;

  @Autowired
  private UdpAnalyzer udpanalyzer;

  @Autowired
  private IcmpAnalyzer icmpAnalyzer;

  public void checkNAdd() {
    protocol.defaultCustoms();
    if (!defaultStatus) {
      protocol.addCustomAnalyzer(ipv4analyzer, "IPv4", 2);
      protocol.addCustomAnalyzer(ethernetanalyzer, "Ethernet", 1);
      protocol.addCustomAnalyzer(ipv6analyzer, "IPv6", 2);
      protocol.addCustomAnalyzer(tcpanalyzer, "Tcp", 3);
      protocol.addCustomAnalyzer(udpanalyzer, "Udp", 3);
      protocol.addCustomAnalyzer(icmpAnalyzer, "Icmp", 3);
    }
  }
}
