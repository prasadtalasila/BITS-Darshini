package in.ac.bits.protocolanalyzer.analyzer;

import com.google.common.eventbus.EventBus;

import in.ac.bits.protocolanalyzer.analyzer.event.PacketTypeDetectionEvent;
import in.ac.bits.protocolanalyzer.protocol.Protocol;

/**
 * Defines a set of methods that any custom analyzer must implement in order to
 * be compatible with existing cellular architecture.
 * <p>
 * Note that a custom analyzer can have many more methods than those defined
 * here (e.g. field extraction methods).
 * </p>
 * 
 * @author crygnus
 *
 */
public interface CustomAnalyzer {

    /**
     * Corresponding eventbus must be supplied by the cell in which this custom
     * analyzer is to be attached. The job of this method is to register this
     * custom analyzer on this eventbus.
     * 
     * @param {@link
     *            EventBus}
     */
    public void configure(EventBus eventBus);

    /**
     * Sets the start byte in the relevant byte range for the next analyzer
     * 
     * @param {@link
     *            PacketWrapper}
     * 
     */
    public void setStartByte(PacketWrapper packetWrapper);

    /**
     * Sets the end byte in the relevant byte range for the next analyzer
     * 
     * @param {@link
     *            PacketWrapper}
     */
    public void setEndByte(PacketWrapper packetWrapper);

    /**
     * Sets the next protocol type from one of the types of the {@link Protocol}
     * class
     * 
     * @return {@link String} protocolType
     */
    public String setNextProtocolType();

    /**
     * Posts the {@link PacketTypeDetectionEvent} on the eventbus for the
     * corresponding analyzer cell
     * 
     * @param nextProtocolType
     * @param startByte
     * @param endByte
     */
    public void publishTypeDetectionEvent(String nextProtocolType,
            int startByte, int endByte);

    /**
     * Analyzes the packet to detect the nextprotocoltype and persist header
     * fields in the database
     * 
     * @param packetWrapper
     */
    public void analyze(PacketWrapper packetWrapper);

    /**
     * Sets the header field as CONDITIONAL_HEADER_FIELD which decides next
     * protocol as its payload
     * 
     * @param headerName
     */
    public void setConditionHeader(String headerName);
}
