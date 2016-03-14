package com.bits.protocolanalyzer.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Corresponding entity class for tcp analysis. Corresponding table columns are
 * all the tcp header fields.
 * 
 * @author crygnus
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "tcp_analysis")
public class TcpEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="tcp_entity_seq")
    @SequenceGenerator(name="tcp_entity_seq", sequenceName="TCP_ENTITY_SEQ")
    private Long id;

    @OneToOne
    private PacketIdEntity packetIdEntity;

    @Column(name = "src_port")
    private int sourcePort;

    @Column(name = "dst_port")
    private int destinationPort;

    @Column(name = "sequence_no")
    private long sequenceNumber;

    @Column(name = "ack_no")
    private long ackNumber;

    @Column(name = "data_offset")
    private int dataOffset;

    @Column(name = "cwr_flag")
    private boolean cwrFlagSet;

    @Column(name = "ece_flag")
    private boolean eceFlagSet;

    @Column(name = "urg_flag")
    private boolean urgFlagSet;

    @Column(name = "ack_flag")
    private boolean ackFlagSet;

    @Column(name = "psh_flag")
    private boolean pshFlagSet;

    @Column(name = "rst_flag")
    private boolean rstFlagSet;

    @Column(name = "syn_flag")
    private boolean synFlagSet;

    @Column(name = "fin_flag")
    private boolean finFlagSet;

    @Column(name = "window_size")
    private int windowSize;

    @Column(name = "checksum")
    private int checksum;

    @Column(name = "urg_pointer")
    private int urgentPointer;

    @Column(name = "next_protocol")
    private String nextProtocol;
}
