package com.ericsson.eus.aps.service.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 
 * FILE_NAME: CsiPluginConfig.java
 * 
 * MODULE DESCRIPTION: CSI-plugin Properties, US84030
 *
 * @author eihsnad, Date: Sep 10, 2020 4:05:09 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
@Configuration
@PropertySource("classpath:ext/application.properties")
@PropertySource(value = "file:./data/ext/application.properties", ignoreResourceNotFound = true)
public class CSIPluginConfig {
	@Value("${csi.total.connections}")
	private String maxTotalConnections;

	@Value("${csi.maxconn.perhost}")
	private String maxConnectionsPerHost;

	@Value("${csi.connection.poolTimeOut}")
	private String connectionPoolTimeout; // connection time to live (in MS)

	@Value("${csi.connection.timeout}")
	private String connectionTimeout; // retry duration before connection establishes (in MS)

	@Value("${csi.read.timeout}")
	private String readTimeout; // socket time out while it reads a stream (in MS)

	@Value("${csi.endpoint.address}")
	private String endpointAddress;

	@Value("${csi.thread.counts}")
	private String ioThreadCount;

	@Value("${csi.enable.logging}")
	private Boolean enableLogging = Boolean.FALSE;

	@Value("${csi.username}")
	private String username;

	@Value("${csi.password}")
	private String password;

	@Value("${csi.enable.xstTransformation}")
	private Boolean enableXstTransformation;

	@Value("${csi.sales.channel}")
	private String saleChannel;
	private Map<String, String> toggleMap;
	private Boolean useAsynch = Boolean.TRUE;

	public String getMaxTotalConnections() {
		return maxTotalConnections;
	}

	public void setMaxTotalConnections(String maxTotalConnections) {
		this.maxTotalConnections = maxTotalConnections;
	}

	public String getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}

	public void setMaxConnectionsPerHost(String maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	public String getConnectionPoolTimeout() {
		return connectionPoolTimeout;
	}

	public void setConnectionPoolTimeout(String connectionPoolTimeout) {
		this.connectionPoolTimeout = connectionPoolTimeout;
	}

	public String getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(String connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public String getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(String readTimeout) {
		this.readTimeout = readTimeout;
	}

	public String getEndpointAddress() {
		return endpointAddress;
	}

	public void setEndpointAddress(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	public String getIoThreadCount() {
		return ioThreadCount;
	}

	public void setIoThreadCount(String ioThreadCount) {
		this.ioThreadCount = ioThreadCount;
	}

	public Boolean getEnableLogging() {
		return enableLogging;
	}

	public void setEnableLogging(Boolean enableLogging) {
		this.enableLogging = enableLogging;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnableXstTransformation() {
		return enableXstTransformation;
	}

	public void setEnableXstTransformation(Boolean enableXstTransformation) {
		this.enableXstTransformation = enableXstTransformation;
	}

	public String getSaleChannel() {
		return saleChannel;
	}

	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}

	public Boolean getUseAsynch() {
		return useAsynch;
	}

	public void setUseAsynch(Boolean useAsynch) {
		this.useAsynch = useAsynch;
	}

	/**
	 * User: ekxrxax , Date: Oct 2, 2020 6:31:45 PM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number: 
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "CSIPluginConfig [maxTotalConnections=" + maxTotalConnections + ", maxConnectionsPerHost="
				+ maxConnectionsPerHost + ", connectionPoolTimeout=" + connectionPoolTimeout + ", connectionTimeout="
				+ connectionTimeout + ", readTimeout=" + readTimeout + ", endpointAddress=" + endpointAddress
				+ ", ioThreadCount=" + ioThreadCount + ", enableLogging=" + enableLogging + ", username=" + username
				+ ", password=" + password + ", enableXstTransformation=" + enableXstTransformation + ", saleChannel="
				+ saleChannel + ", toggleMap=" + toggleMap + ", useAsynch=" + useAsynch + "]";
	}

	/**
	 * @return the toggleMap
	 */
	public Map<String, String> getToggleMap() {
		return toggleMap;
	}

	/**
	 * @param toggleMap the toggleMap to set
	 */
	public void setToggleMap(Map<String, String> toggleMap) {
		this.toggleMap = toggleMap;
	}
}
