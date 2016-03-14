package com.bits.protocolanalyzer.analyzer.transport;

public class TcpHeader {

    public static final int DEFAULT_HEADER_LENGTH_IN_BYTES = 20;

    public static final int SOURCE_PORT_START_BYTE = 0;
    public static final int SOURCE_PORT_END_BYTE = 1;

    public static final int DESTINATION_PORT_START_BYTE = 2;
    public static final int DESTINATION_PORT_END_BYTE = 3;

    public static final int SEQUENCE_NUMBER_START_BYTE = 4;
    public static final int SEQUENCE_NUMBER_END_BYTE = 7;

    public static final int ACK_START_BYTE = 8;
    public static final int ACK_END_BYTE = 11;

    public static final int DATA_OFFSET_BYTE_INDEX = 12;

    public static final int FLAGS_BYTE_INDEX = 13;

    public static final int CWR_FLAG_BIT_INDEX = 0;
    public static final int ECE_FLAG_BIT_INDEX = 1;
    public static final int URG_FLAG_BIT_INDEX = 2;
    public static final int ACK_FLAG_BIT_INDEX = 3;

    public static final int PSH_FLAG_BIT_INDEX = 4;
    public static final int RST_FLAG_BIT_INDEX = 5;
    public static final int SYN_FLAG_BIT_INDEX = 6;
    public static final int FIN_FLAG_BIT_INDEX = 7;

    public static final int WINDOW_START_BYTE = 14;
    public static final int WINDOW_END_BYTE = 15;

    public static final int CHECKSUM_START_BYTE = 16;
    public static final int CHECKSUM_END_BYTE = 17;

    public static final int URGENT_PTR_START_BYTE = 18;
    public static final int URGENT_PTR_END_BYTE = 19;

}
