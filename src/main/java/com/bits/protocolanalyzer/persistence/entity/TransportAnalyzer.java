/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.persistence.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author amit
 */
@Entity
@Table(name="transport_analyzer")
public class TransportAnalyzer implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public Long getId() {
		return id;
	}
	
	@Column(name="packet_id")
	private long packetId;
	
	@Column(name="source_port")
	private int sourcePort;
	
	@Column(name="destination_port")
	private int destinationPort;
	
	@Column(name="ack_number")
	private long ackNumber;
	
	@Column(name="seq_number")
	private long seqNumber;

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TransportAnalyzer)) {
			return false;
		}
		TransportAnalyzer other = (TransportAnalyzer) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.bits.protocolanalyzer.entity.PcapPacket[ id=" + id + " ]";
	}
	
}