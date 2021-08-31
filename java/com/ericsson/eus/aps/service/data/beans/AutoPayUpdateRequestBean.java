package com.ericsson.eus.aps.service.data.beans;

import javax.validation.constraints.NotEmpty;

/**
 * 
 * FILE_NAME: AutoPayUpdateRequestBean.java
 * 
 * MODULE DESCRIPTION: Autopay Update Request Bean, US/F/D : US84043
 *
 * @author eihsnad, Date: Nov 12, 2020 7:51:13 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class AutoPayUpdateRequestBean extends BaseRequest {

	/**
	 * User: eihsnad
	 *
	 * Purpose: TODO, US/F/D long
	 */
	private static final long serialVersionUID = 8665615339259284254L;
	@NotEmpty(message = "AutoPayUpdateRequestBean's MSISDN has invalid value")
	private String msisdn;
	@NotEmpty(message = "AutoPayUpdateRequestBean's Description has invalid value")
	private String description;
	@NotEmpty(message = "AutoPayUpdateRequestBean's PdofToken has invalid value")
	private String pdofToken;
	private String pdofLast4;
	private String modifiedByUser;
	private String channelId;

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPdofToken() {
		return pdofToken;
	}

	public void setPdofToken(String pdofToken) {
		this.pdofToken = pdofToken;
	}

	public String getPdofLast4() {
		return pdofLast4;
	}

	public void setPdofLast4(String pdofLast4) {
		this.pdofLast4 = pdofLast4;
	}

	public String getModifiedByUser() {
		return modifiedByUser;
	}

	public void setModifiedByUser(String modifiedByUser) {
		this.modifiedByUser = modifiedByUser;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Override
	public String toString() {
		return "AutoPayUpdateRequestBean [msisdn=" + msisdn + ", description=" + description + ", pdofToken="
				+ pdofToken + ", pdofLast4=" + pdofLast4 + ", modifiedByUser=" + modifiedByUser + ", channelId="
				+ channelId + "]";
	}

}
