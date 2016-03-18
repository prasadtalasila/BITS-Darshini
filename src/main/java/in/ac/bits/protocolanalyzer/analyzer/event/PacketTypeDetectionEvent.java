package in.ac.bits.protocolanalyzer.analyzer.event;

import lombok.Getter;

@Getter
public class PacketTypeDetectionEvent {

    private String nextPacketType;
    private int startByte;
    private int endByte;

    public PacketTypeDetectionEvent(String nextPacketType, int startByte,
            int endByte) {
        this.nextPacketType = nextPacketType;
        this.startByte = startByte;
        this.endByte = endByte;
    }
}
