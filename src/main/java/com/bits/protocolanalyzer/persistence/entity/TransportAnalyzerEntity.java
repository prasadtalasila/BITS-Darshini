/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.persistence.entity;

import java.io.Serializable;

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
 *
 * @author amit
 * @author crygnus
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "transport_analyzer")
public class TransportAnalyzerEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="transport_entity_seq")
    @SequenceGenerator(name="transport_entity_seq", sequenceName="TRANSPORT_ENTITY_SEQ")
    private Long id;

    @OneToOne
    private PacketIdEntity packetIdEntity;

}