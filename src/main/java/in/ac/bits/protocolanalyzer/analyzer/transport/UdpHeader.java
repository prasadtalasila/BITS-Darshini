package in.ac.bits.protocolanalyzer.analyzer.transport;

public final class UdpHeader {
  public static final int SRCPORT_START_BIT = 0;

  public static final int SRCPORT_START_BYTE = 0;

  public static final int SRCPORT_END_BIT = 15;

  public static final int SRCPORT_END_BYTE = 1;

  public static final int DSTPORT_START_BIT = 16;

  public static final int DSTPORT_START_BYTE = 2;

  public static final int DSTPORT_END_BIT = 31;

  public static final int DSTPORT_END_BYTE = 3;

  public static final int LENGTH__START_BIT = 32;

  public static final int LENGTH__START_BYTE = 4;

  public static final int LENGTH__END_BIT = 47;

  public static final int LENGTH__END_BYTE = 5;

  public static final int CHECKSUM_START_BIT = 48;

  public static final int CHECKSUM_START_BYTE = 6;

  public static final int CHECKSUM_END_BIT = 63;

  public static final int CHECKSUM_END_BYTE = 7;

  public static final int TOTAL_HEADER_LENGTH = 8;
}
