package com.bits.protocolanalyzer.analyzer.network;

import java.util.Arrays;

import com.bits.protocolanalyzer.utils.BitOperator;
import com.bits.protocolanalyzer.utils.ByteOperator;

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

    public static int getIhl(byte[] ipHeader) {
        return BitOperator.getValue(ipHeader[0], IHL_START_BIT, IHL_BITS);
    }

    public static int getTotalLength(byte[] ipHeader) {
        byte[] totalLength = Arrays.copyOfRange(ipHeader,
                TOTAL_LENGTH_START_BYTE, TOTAL_LENGTH_END_BYTE + 1);
        return ByteOperator.parseBytes(totalLength);
    }

    public static int getIdentification(byte[] ipHeader) {
        byte[] identification = Arrays.copyOfRange(ipHeader,
                IDENTIFICATION_START_BYTE, IDENTIFICATION_END_BYTE + 1);
        return ByteOperator.parseBytes(identification);
    }

    public static int getDontFragmentBit(byte[] ipHeader) {
        byte byteWithFlags = ipHeader[6];
        int[] byteWithFlagsBits = BitOperator.getBits(byteWithFlags);
        return byteWithFlagsBits[1];
    }

    public static int getMoreFragmentBit(byte[] ipHeader) {
        byte byteWithFlags = ipHeader[6];
        int[] byteWithFlagsBits = BitOperator.getBits(byteWithFlags);
        return byteWithFlagsBits[2];
    }

    public static int getFragmentOffset(byte[] ipHeader) {
        byte[] bytesWithFragmentOffset = new byte[2];
        bytesWithFragmentOffset[0] = ipHeader[6];
        bytesWithFragmentOffset[1] = ipHeader[7];
        int fragmentOffset = ByteOperator.parseBytes(bytesWithFragmentOffset);
        /* To neglect 3 flag bits */
        return fragmentOffset & 0x1FFF;
    }

    public static int getTTL(byte[] ipHeader) {
        byte[] ttlByte = new byte[1];
        ttlByte[0] = ipHeader[TTL_BYTE];
        return ByteOperator.parseBytes(ttlByte);
    }

    public static int getNextProtocol(byte[] ipHeader) {
        byte[] protocolByte = new byte[1];
        protocolByte[0] = ipHeader[PROTOCOL_BYTE];
        int protocolInt = ByteOperator.parseBytes(protocolByte);
        return protocolInt;
    }

    public static int getHeaderChecksum(byte[] ipHeader) {
        byte[] headerChecksumBytes = Arrays.copyOfRange(ipHeader,
                HEADER_CHECKSUM_START_BYTE, HEADER_CHECKSUM_END_BYTE + 1);
        return ByteOperator.parseBytes(headerChecksumBytes);
    }

    public static byte[] getSouceAddress(byte[] ipHeader) {

        byte[] sourceAddressBytes = Arrays.copyOfRange(ipHeader,
                SOURCE_IP_START_BYTE, SOURCE_IP_END_BYTE + 1);
        /*
         * IPv4Address sourceAddress = new IPv4Address(sourceAddressBytes);
         * return sourceAddress.toString();
         */
        return sourceAddressBytes;
    }

    public static byte[] getDestinationAddress(byte[] ipHeader) {

        byte[] destinationAddressBytes = Arrays.copyOfRange(ipHeader,
                DESTINATION_IP_START_BYTE, DESTINATION_IP_END_BYTE + 1);
        /*
         * IPv4Address destAddress = new IPv4Address(destinationAddressBytes);
         * return destAddress.toString();
         */
        return destinationAddressBytes;
    }

}
