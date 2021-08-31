package com.ericsson.eus.aps.dao.model;

import java.io.Serializable;

/**
 * 
 * FILE_NAME: AutoPayEventTracker.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author esuspal, Date: Dec 7, 2020 11:50:50 AM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class AutoPayEventTracker implements Serializable{
	
	private static final long serialVersionUID = 5834644735290551049L;
	private String accountId;
	private String eventTrackerCode;
	private String eventTrackerValue;
	@Override
	public String toString() {
		return "AutoPayEventTracker [accountId=" + getAccountId() + ", eventTrackerCode=" + getEventTrackerCode()
				+ ", eventTrackerValue=" + getEventTrackerValue() + "]";
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the eventTrackerCode
	 */
	public String getEventTrackerCode() {
		return eventTrackerCode;
	}

	/**
	 * @param eventTrackerCode
	 *            the eventTrackerCode to set
	 */
	public void setEventTrackerCode(String eventTrackerCode) {
		this.eventTrackerCode = eventTrackerCode;
	}

	/**
	 * @return the eventTrackerValue
	 */
	public String getEventTrackerValue() {
		return eventTrackerValue;
	}

	/**
	 * @param eventTrackerValue
	 *            the eventTrackerValue to set
	 */
	public void setEventTrackerValue(String eventTrackerValue) {
		this.eventTrackerValue = eventTrackerValue;
	}

	
}
