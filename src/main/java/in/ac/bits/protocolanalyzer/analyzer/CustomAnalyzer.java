package in.ac.bits.protocolanalyzer.analyzer;

import com.google.common.eventbus.EventBus;

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
     * @param {@link EventBus}
     */
    public void configure(EventBus eventBus);

    /**
     * 
     * @param packetWrapper
     */
    public void setStartByte(PacketWrapper packetWrapper);

    public void setEndByte(PacketWrapper packetWrapper);

    public String setNextProtocolType();

    public void publishTypeDetectionEvent(String nextProtocolType,
            int startByte, int endByte);
}
