package com.bits.protocolanalyzer.analyzer.transport;

import java.util.Arrays;

import com.bits.protocolanalyzer.utils.BitOperator;
import com.bits.protocolanalyzer.utils.ByteOperator;

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

    public static int getSoucePort(byte[] tcpHeader) {
        byte[] soucePortBytes = Arrays.copyOf(tcpHeader, 2);
        return ByteOperator.parseBytes(soucePortBytes);
    }

    public static int getDestinationPort(byte[] tcpHeader) {
        byte[] dstPortBytes = Arrays.copyOfRange(tcpHeader,
                DESTINATION_PORT_START_BYTE, DESTINATION_PORT_END_BYTE);
        return ByteOperator.parseBytes(dstPortBytes);
    }

    public static int getSequenceNumber(byte[] tcpHeader) {
        byte[] sequenceNoBytes = Arrays.copyOfRange(tcpHeader,
                SEQUENCE_NUMBER_START_BYTE, SEQUENCE_NUMBER_END_BYTE);
        return ByteOperator.parseBytes(sequenceNoBytes);
    }

    public static int getAckNumber(byte[] tcpHeader) {
        byte[] ackBytes = Arrays.copyOfRange(tcpHeader, ACK_START_BYTE,
                ACK_END_BYTE);
        return ByteOperator.parseBytes(ackBytes);
    }

    public static int getDataOffset(byte[] tcpHeader) {
        byte byteWithDataOffset = tcpHeader[DATA_OFFSET_BYTE_INDEX];
        return BitOperator.getNibble(byteWithDataOffset, 0);
    }

    public static boolean isCWRFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, CWR_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isECEFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, ECE_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isURGFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, URG_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isACKFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, ACK_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPSHFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, PSH_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isRSTFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, RST_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSYNFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, SYN_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isFINFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, FIN_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static int getWindowSize(byte[] tcpHeader) {
        byte[] windowSizeBytes = Arrays.copyOfRange(tcpHeader,
                WINDOW_START_BYTE, WINDOW_END_BYTE);
        return ByteOperator.parseBytes(windowSizeBytes);
    }

    public static int getChecksum(byte[] tcpHeader) {
        byte[] checksumBytes = Arrays.copyOfRange(tcpHeader,
                CHECKSUM_START_BYTE, CHECKSUM_END_BYTE);
        return ByteOperator.parseBytes(checksumBytes);
    }

    public static int getUrgentPointer(byte[] tcpHeader) {
        if (isURGFlagSet(tcpHeader)) {
            byte[] urgPointerBytes = Arrays.copyOfRange(tcpHeader,
                    URGENT_PTR_START_BYTE, URGENT_PTR_END_BYTE);
            return ByteOperator.parseBytes(urgPointerBytes);
        } else {
            return -1;
        }
    }

}
