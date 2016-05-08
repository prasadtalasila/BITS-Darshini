package in.ac.bits.protocolanalyzer.analyzer.network;

public final class IPv6Header {
  public static final int VERSION_START_BIT = 0;

  public static final int VERSION_START_BYTE = 0;

  public static final int VERSION_END_BIT = 3;

  public static final int VERSION_END_BYTE = 0;

  public static final int TRAFFICCLASS_START_BIT = 4;

  public static final int TRAFFICCLASS_START_BYTE = 0;

  public static final int TRAFFICCLASS_END_BIT = 11;

  public static final int TRAFFICCLASS_END_BYTE = 1;

  public static final int FLOWLABEL_START_BIT = 12;

  public static final int FLOWLABEL_START_BYTE = 1;

  public static final int FLOWLABEL_END_BIT = 31;

  public static final int FLOWLABEL_END_BYTE = 3;

  public static final int PAYLOADLEN_START_BIT = 32;

  public static final int PAYLOADLEN_START_BYTE = 4;

  public static final int PAYLOADLEN_END_BIT = 47;

  public static final int PAYLOADLEN_END_BYTE = 5;

  public static final int NEXTHDR_START_BIT = 48;

  public static final int NEXTHDR_START_BYTE = 6;

  public static final int NEXTHDR_END_BIT = 55;

  public static final int NEXTHDR_END_BYTE = 6;

  public static final int HOPLIMIT_START_BIT = 56;

  public static final int HOPLIMIT_START_BYTE = 7;

  public static final int HOPLIMIT_END_BIT = 63;

  public static final int HOPLIMIT_END_BYTE = 7;

  public static final int SRCADDR_START_BIT = 64;

  public static final int SRCADDR_START_BYTE = 8;

  public static final int SRCADDR_END_BIT = 191;

  public static final int SRCADDR_END_BYTE = 23;

  public static final int DSTADDR_START_BIT = 192;

  public static final int DSTADDR_START_BYTE = 24;

  public static final int DSTADDR_END_BIT = 319;

  public static final int DSTADDR_END_BYTE = 39;

  public static final int TOTAL_HEADER_LENGTH = 40;
}
