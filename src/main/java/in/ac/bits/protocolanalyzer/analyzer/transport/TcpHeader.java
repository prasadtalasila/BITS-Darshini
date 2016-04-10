package in.ac.bits.protocolanalyzer.analyzer.transport;

public final class TcpHeader {
  public static final int SRCPORT_START_BIT = 0;

  public static final int SRCPORT_START_BYTE = 0;

  public static final int SRCPORT_END_BIT = 15;

  public static final int SRCPORT_END_BYTE = 1;

  public static final int DSTPORT_START_BIT = 16;

  public static final int DSTPORT_START_BYTE = 2;

  public static final int DSTPORT_END_BIT = 31;

  public static final int DSTPORT_END_BYTE = 3;

  public static final int SEQNO_START_BIT = 32;

  public static final int SEQNO_START_BYTE = 4;

  public static final int SEQNO_END_BIT = 63;

  public static final int SEQNO_END_BYTE = 7;

  public static final int ACKNO_START_BIT = 64;

  public static final int ACKNO_START_BYTE = 8;

  public static final int ACKNO_END_BIT = 95;

  public static final int ACKNO_END_BYTE = 11;

  public static final int DATAOFFSET_START_BIT = 96;

  public static final int DATAOFFSET_START_BYTE = 12;

  public static final int DATAOFFSET_END_BIT = 99;

  public static final int DATAOFFSET_END_BYTE = 12;

  public static final int RES_START_BIT = 100;

  public static final int RES_START_BYTE = 12;

  public static final int RES_END_BIT = 103;

  public static final int RES_END_BYTE = 12;

  public static final int FLAGS_START_BIT = 104;

  public static final int FLAGS_START_BYTE = 13;

  public static final int FLAGS_END_BIT = 111;

  public static final int FLAGS_END_BYTE = 13;

  public static final int WINDOW_START_BIT = 112;

  public static final int WINDOW_START_BYTE = 14;

  public static final int WINDOW_END_BIT = 127;

  public static final int WINDOW_END_BYTE = 15;

  public static final int CHECKSUM_START_BIT = 128;

  public static final int CHECKSUM_START_BYTE = 16;

  public static final int CHECKSUM_END_BIT = 143;

  public static final int CHECKSUM_END_BYTE = 17;

  public static final int URGENTPTR_START_BIT = 144;

  public static final int URGENTPTR_START_BYTE = 18;

  public static final int URGENTPTR_END_BIT = 159;

  public static final int URGENTPTR_END_BYTE = 19;

  public static final int TOTAL_HEADER_LENGTH = 20;
}
