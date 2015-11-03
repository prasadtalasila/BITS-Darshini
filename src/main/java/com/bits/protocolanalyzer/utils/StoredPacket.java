/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.utils;

import com.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;
import com.bits.protocolanalyzer.persistence.entity.NetworkAnalyzerEntity;
import com.bits.protocolanalyzer.persistence.entity.TransportAnalyzerEntity;

/**
 *
 * @author amit
 */
public class StoredPacket {
	
	private int packetId;
	private LinkAnalyzerEntity linkAnalyzerEntity;
	private NetworkAnalyzerEntity networkAnalyzerEntity;
	private TransportAnalyzerEntity transportAnalyzerEntity;

	public int getPacketId() {
		return packetId;
	}

	public void setPacketId(int packetId) {
		this.packetId = packetId;
	}

	public LinkAnalyzerEntity getLinkAnalyzerEntity() {
		return linkAnalyzerEntity;
	}

	public void setLinkAnalyzerEntity(LinkAnalyzerEntity linkAnalyzerEntity) {
		this.linkAnalyzerEntity = linkAnalyzerEntity;
	}

	public NetworkAnalyzerEntity getNetworkAnalyzerEntity() {
		return networkAnalyzerEntity;
	}

	public void setNetworkAnalyzerEntity(NetworkAnalyzerEntity networkAnalyzerEntity) {
		this.networkAnalyzerEntity = networkAnalyzerEntity;
	}

	public TransportAnalyzerEntity getTransportAnalyzerEntity() {
		return transportAnalyzerEntity;
	}

	public void setTransportAnalyzerEntity(TransportAnalyzerEntity transportAnalyzerEntity) {
		this.transportAnalyzerEntity = transportAnalyzerEntity;
	}
	
	
	
}
