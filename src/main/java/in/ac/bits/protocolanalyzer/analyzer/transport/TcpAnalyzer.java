/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ac.bits.protocolanalyzer.analyzer.transport;

import java.util.Arrays;

import org.pcap4j.packet.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import in.ac.bits.protocolanalyzer.analyzer.CustomAnalyzer;
import in.ac.bits.protocolanalyzer.analyzer.PacketWrapper;
import in.ac.bits.protocolanalyzer.analyzer.event.PacketTypeDetectionEvent;
import in.ac.bits.protocolanalyzer.persistence.entity.TcpEntity;
import in.ac.bits.protocolanalyzer.persistence.repository.TcpRepository;
import in.ac.bits.protocolanalyzer.protocol.Protocol;
import in.ac.bits.protocolanalyzer.utils.BitOperator;
import in.ac.bits.protocolanalyzer.utils.ByteOperator;

/**
 *
 * @author amit
 * @author crygnus
 */
@Component
public class TcpAnalyzer implements CustomAnalyzer {

    public static final String PACKET_TYPE_OF_RELEVANCE = Protocol.TCP;

    @Autowired
    private TcpRepository tcpRepository;

    private EventBus eventBus;
    private byte[] tcpHeader;
    private int startByte;
    private int endByte;

    public void configure(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    /* Field extraction methods - Start */
    public int getSourcePort(byte[] tcpHeader) {
        byte[] soucePortBytes = Arrays.copyOf(tcpHeader, 2);
        return ByteOperator.parseBytes(soucePortBytes);
    }

    public int getDestinationPort(byte[] tcpHeader) {
        byte[] dstPortBytes = Arrays.copyOfRange(tcpHeader,
                TcpHeader.DESTINATION_PORT_START_BYTE,
                TcpHeader.DESTINATION_PORT_END_BYTE + 1);
        return ByteOperator.parseBytes(dstPortBytes);
    }

    public long getSequenceNumber(byte[] tcpHeader) {
        byte[] sequenceNoBytes = Arrays.copyOfRange(tcpHeader,
                TcpHeader.SEQUENCE_NUMBER_START_BYTE,
                TcpHeader.SEQUENCE_NUMBER_END_BYTE + 1);
        return ByteOperator.parseBytesLong(sequenceNoBytes);
    }

    public long getAckNumber(byte[] tcpHeader) {
        byte[] ackBytes = Arrays.copyOfRange(tcpHeader,
                TcpHeader.ACK_START_BYTE, TcpHeader.ACK_END_BYTE + 1);
        return ByteOperator.parseBytesLong(ackBytes);
    }

    public int getDataOffset(byte[] tcpHeader) {
        byte byteWithDataOffset = tcpHeader[TcpHeader.DATA_OFFSET_BYTE_INDEX];
        return BitOperator.getNibble(byteWithDataOffset, 0);
    }

    public boolean isCWRFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[TcpHeader.FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, TcpHeader.CWR_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isECEFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[TcpHeader.FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, TcpHeader.ECE_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isURGFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[TcpHeader.FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, TcpHeader.URG_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isACKFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[TcpHeader.FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, TcpHeader.ACK_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPSHFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[TcpHeader.FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, TcpHeader.PSH_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRSTFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[TcpHeader.FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, TcpHeader.RST_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSYNFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[TcpHeader.FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, TcpHeader.SYN_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFINFlagSet(byte[] tcpHeader) {
        byte flagByte = tcpHeader[TcpHeader.FLAGS_BYTE_INDEX];
        int flag = BitOperator.getBit(flagByte, TcpHeader.FIN_FLAG_BIT_INDEX);
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public int getWindowSize(byte[] tcpHeader) {
        byte[] windowSizeBytes = Arrays.copyOfRange(tcpHeader,
                TcpHeader.WINDOW_START_BYTE, TcpHeader.WINDOW_END_BYTE);
        return ByteOperator.parseBytes(windowSizeBytes);
    }

    public int getChecksum(byte[] tcpHeader) {
        byte[] checksumBytes = Arrays.copyOfRange(tcpHeader,
                TcpHeader.CHECKSUM_START_BYTE, TcpHeader.CHECKSUM_END_BYTE);
        return ByteOperator.parseBytes(checksumBytes);
    }

    public int getUrgentPointer(byte[] tcpHeader) {
        if (isURGFlagSet(tcpHeader)) {
            byte[] urgPointerBytes = Arrays.copyOfRange(tcpHeader,
                    TcpHeader.URGENT_PTR_START_BYTE,
                    TcpHeader.URGENT_PTR_END_BYTE + 1);
            return ByteOperator.parseBytes(urgPointerBytes);
        } else {
            return -1;
        }
    }
    /* Field extraction methods - End */

    private void setTcpHeader(PacketWrapper packetWrapper) {
        Packet packet = packetWrapper.getPacket();
        byte[] rawPacket = packet.getRawData();
        int startByte = packetWrapper.getStartByte();
        this.tcpHeader = Arrays.copyOfRange(rawPacket, startByte,
                startByte + TcpHeader.DEFAULT_HEADER_LENGTH_IN_BYTES + 1);
    }

    public void setStartByte(PacketWrapper packetWrapper) {
        int startByte = packetWrapper.getStartByte();
        this.startByte = startByte + TcpHeader.DEFAULT_HEADER_LENGTH_IN_BYTES;
    }

    public void setEndByte(PacketWrapper packetWrapper) {
        this.endByte = packetWrapper.getEndByte();
    }

    @Subscribe
    public void analyze(PacketWrapper packetWrapper) {
        if (PACKET_TYPE_OF_RELEVANCE
                .equalsIgnoreCase(packetWrapper.getPacketType())) {
            /* Do type detection first and publish the event */
            /* Set the tcp header */
            this.setTcpHeader(packetWrapper);
            /* Set start and end bytes */
            this.setStartByte(packetWrapper);
            this.setEndByte(packetWrapper);
            String nextProtocol = setNextProtocolType();
            publishTypeDetectionEvent(nextProtocol, this.startByte,
                    this.endByte);

            /*
             * Save to database
             */
            TcpEntity entity = new TcpEntity();

            entity.setSourcePort(getSourcePort(tcpHeader));
            entity.setDestinationPort(getDestinationPort(tcpHeader));
            entity.setSequenceNumber(getSequenceNumber(tcpHeader));
            entity.setAckNumber(getAckNumber(tcpHeader));
            entity.setDataOffset(getDataOffset(tcpHeader));
            entity.setCwrFlagSet(isCWRFlagSet(tcpHeader));
            entity.setEceFlagSet(isECEFlagSet(tcpHeader));
            entity.setUrgFlagSet(isURGFlagSet(tcpHeader));
            entity.setAckFlagSet(isACKFlagSet(tcpHeader));
            entity.setPshFlagSet(isPSHFlagSet(tcpHeader));
            entity.setRstFlagSet(isRSTFlagSet(tcpHeader));
            entity.setSynFlagSet(isSYNFlagSet(tcpHeader));
            entity.setFinFlagSet(isFINFlagSet(tcpHeader));
            entity.setWindowSize(getWindowSize(tcpHeader));
            entity.setChecksum(getChecksum(tcpHeader));
            entity.setUrgentPointer(getUrgentPointer(tcpHeader));
            entity.setNextProtocol(nextProtocol);

            entity.setPacketIdEntity(packetWrapper.getPacketIdEntity());

            tcpRepository.save(entity);
        }
    }

    public String setNextProtocolType() {

        int dstPortNo = getDestinationPort(tcpHeader);
        switch (dstPortNo) {
        case 80:
            return Protocol.HTTP;

        case 443:
            return Protocol.HTTPS;

        default:
            return Protocol.END_PROTOCOL;
        }
    }

    public void publishTypeDetectionEvent(String nextProtocol, int startByte,
            int endByte) {
        this.eventBus.post(
                new PacketTypeDetectionEvent(nextProtocol, startByte, endByte));
    }

    @Override
    public void setConditionHeader(String headerName) {
        // TODO Auto-generated method stub
    }
}
