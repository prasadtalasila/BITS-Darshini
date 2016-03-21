package in.ac.bits.protocolanalyzer.analyzer.link;

import java.util.HashSet;
import java.util.Set;

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

    public static Set<String> HEADER_FIELDS_SET = new HashSet<String>();

    public static void addHeaderField(String name) {
        HEADER_FIELDS_SET.add(name);
    }

}
