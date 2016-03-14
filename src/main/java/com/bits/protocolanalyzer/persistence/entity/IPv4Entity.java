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
 * Corresponding entity class for ipv4 analysis. Corresponding table columns
 * include all the ipv4 header fields.
 * 
 * @author crygnus
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "ipv4_analysis")
public class IPv4Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="ipv4_entity_seq")
    @SequenceGenerator(name="ipv4_entity_seq", sequenceName="IPV4_ENTITY_SEQ")
    private Long id;

    @OneToOne
    private PacketIdEntity packetIdEntity;

    @Column(name = "version")
    private int version;

    @Column(name = "ihl")
    private int ihl;

    @Column(name = "total_length")
    private int totalLength;

    @Column(name = "identification")
    private int identification;

    @Column(name = "dont_frag")
    private boolean dontFragment;

    @Column(name = "more_frag")
    private boolean moreFragment;

    @Column(name = "frag_offset")
    private int fragmentOffset;

    @Column(name = "ttl")
    private int ttl;

    @Column(name = "next_protocol")
    private String nextProtocol;

    @Column(name = "header_checksum")
    private int checksum;

    @Column(name = "src_addr")
    private String sourceAddr;

    @Column(name = "dst_addr")
    private String destinationAddr;
}
