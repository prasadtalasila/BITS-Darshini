/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.analyzer.network;

import java.net.Inet4Address;
import org.pcap4j.packet.namednumber.IpNumber;
import org.pcap4j.packet.namednumber.IpVersion;

/**
 *
 * @author amit
 */
public abstract class NetworkAnalyzer {
	
	public abstract Inet4Address getSource();

	public abstract Inet4Address getDestination();
	
	public abstract IpVersion getVersion();
	
	public abstract IpNumber getIpNumber();
	
	public abstract int getHeaderLength();
	
	public abstract short getLength();
	
	public abstract int getId();
	
	public abstract short getHeaderChecksum();
	
}
