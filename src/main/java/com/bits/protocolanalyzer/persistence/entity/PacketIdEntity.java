/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.persistence.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.ReturnInsert;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author amit
 * @author crygnus
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "packet_id")
public class PacketIdEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "packet_id_seq")
    @SequenceGenerator(name = "packet_id_seq", sequenceName = "PACKET_ID_SEQ")
    private Long id;

    @Column(name = "packet_id", columnDefinition = "serial")
    @Basic
    @ReturnInsert(returnOnly = true)
    private long packetId;

}
