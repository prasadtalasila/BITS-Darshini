package in.ac.bits.protocolanalyzer.analyzer.network;

public class IPv4Header {

    public static final int DEFAULT_HEADER_LENTH_IN_BYTES = 20;

    public static final int IP_VERSION = 4;
    public static final int IP_VERSION_START_BIT = 0;
    public static final int IP_VERSION_BITS = 1;

    public static final int IHL_START_BIT = 4;
    public static final int IHL_BITS = 4;

    public static final int DIFFSERV_START_BIT = 8;
    public static final int DIFFSERV_BITS = 8;

    public static final int TOTAL_LENGTH_START_BYTE = 2;
    public static final int TOTAL_LENGTH_END_BYTE = 3;

    public static final int IDENTIFICATION_START_BYTE = 4;
    public static final int IDENTIFICATION_END_BYTE = 5;

    public static final int FLAGS_START_BIT = 48;
    public static final int FLAGS_BITS = 3;

    public static final int FRAGMENT_OFFSET_START_BIT = 51;
    public static final int FRAGMENT_OFFSET_BITS = 13;

    public static final int TTL_BYTE = 8;

    public static final int PROTOCOL_BYTE = 9;

    public static final int HEADER_CHECKSUM_START_BYTE = 10;
    public static final int HEADER_CHECKSUM_END_BYTE = 11;

    public static final int SOURCE_IP_START_BYTE = 12;
    public static final int SOURCE_IP_END_BYTE = 15;

    public static final int DESTINATION_IP_START_BYTE = 16;
    public static final int DESTINATION_IP_END_BYTE = 19;

}
