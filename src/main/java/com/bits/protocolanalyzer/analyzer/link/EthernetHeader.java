package com.bits.protocolanalyzer.analyzer.link;

import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;

import com.bits.protocolanalyzer.address.MacAddress;

/**
 * This class defines the properties of standard Ethernet-II Header as per
 * specified in P4 language
 * 
 * @author crygnus
 *
 */

public class EthernetHeader {

    public static final int DESTINATION_MAC_ADDR_START_BYTE = 0;
    public static final int DESTINATION_MAC_ADDR_END_BYTE = 5;

    public static final int SOURCE_MAC_ADDR_START_BYTE = 6;
    public static final int SOURCE_MAC_ADDR_END_BYTE = 11;

    public static final int ETHER_TYPE_START_BYTE = 12;
    public static final int ETHER_TYPE_END_BYTE = 13;

    public static final int HEADER_LENGTH_IN_BYTES = 14;

    public static MacAddress getSource(byte[] ethernetHeader) {
        byte[] sourceAddr = Arrays.copyOfRange(ethernetHeader,
                SOURCE_MAC_ADDR_START_BYTE, SOURCE_MAC_ADDR_END_BYTE + 1);

        MacAddress srcAddr = new MacAddress(Hex.encodeHexString(sourceAddr));
        return srcAddr;
    }

    public static MacAddress getDestination(byte[] ethernetHeader) {
        byte[] destinationAddr = Arrays.copyOfRange(ethernetHeader,
                DESTINATION_MAC_ADDR_START_BYTE,
                DESTINATION_MAC_ADDR_END_BYTE + 1);

        MacAddress dstAddr = new MacAddress(
                Hex.encodeHexString(destinationAddr));
        return dstAddr;
    }

    public static String getEtherType(byte[] ethernetHeader) {
        byte[] etherType = Arrays.copyOfRange(ethernetHeader,
                ETHER_TYPE_START_BYTE, ETHER_TYPE_END_BYTE + 1);

        return Hex.encodeHexString(etherType);
    }

}
