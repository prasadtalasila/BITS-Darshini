package in.ac.bits.protocolanalyzer.analyzer.link;

public final class EthernetHeader {
  public static final int DST_ADDR_START_BIT = 0;

  public static final int DST_ADDR_START_BYTE = 0;

  public static final int DST_ADDR_END_BIT = 47;

  public static final int DST_ADDR_END_BYTE = 5;

  public static final int SRC_ADDR_START_BIT = 48;

  public static final int SRC_ADDR_START_BYTE = 6;

  public static final int SRC_ADDR_END_BIT = 95;

  public static final int SRC_ADDR_END_BYTE = 11;

  public static final int ETHERTYPE_START_BIT = 96;

  public static final int ETHERTYPE_START_BYTE = 12;

  public static final int ETHERTYPE_END_BIT = 111;

  public static final int ETHERTYPE_END_BYTE = 13;

  public static final int TOTAL_HEADER_LENGTH = 14;
}
