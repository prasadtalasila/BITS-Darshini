/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.entity;

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
@Table(name="network_analyzer")
public class NetworkAnalyzer implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="source")
	private byte[] source;
	
	@Column(name="destination")
	private byte[] destination;
	
	@Column(name="packet_id")
	private long packetId;
	
	@Column(name="version")
	private int version;
	
	@Column(name="header_length")
	private int headerLength;
	
	@Column(name="packet_length")
	private int packetLength;
	
	@Column(name="flag")
	private int flag;
	
	@Column(name="checksum")
	private int checksum;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getPacketId() {
		return packetId;
	}

	public void setPacketId(long packetId) {
		this.packetId = packetId;
	}

	public byte[] getSource() {
		return source;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	public byte[] getDestination() {
		return destination;
	}

	public void setDestination(byte[] destination) {
		this.destination = destination;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getHeaderLength() {
		return headerLength;
	}

	public void setHeaderLength(int headerLength) {
		this.headerLength = headerLength;
	}

	public int getPacketLength() {
		return packetLength;
	}

	public void setPacketLength(int packetLength) {
		this.packetLength = packetLength;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getChecksum() {
		return checksum;
	}

	public void setChecksum(int checksum) {
		this.checksum = checksum;
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
		if (!(object instanceof NetworkAnalyzer)) {
			return false;
		}
		NetworkAnalyzer other = (NetworkAnalyzer) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.bits.protocolanalyzer.entity.NetworkAnalyzer[ id=" + id + " ]";
	}
	
}
