package com.ericsson.eus.aps.dao.model;

 /**
 * 
 * FILE_NAME: FeatToggleDtls.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Sep 18, 2020 5:04:41 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class FeatToggleDtls {
	private String featTogStatus;

	/**
	 * User: ekxrxax , Date: Sep 18, 2020 5:06:23 PM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number: 
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "FeatToggleDtls [featTogStatus=" + featTogStatus + "]";
	}

	/**
	 * @return the featTogStatus
	 */
	public String getFeatTogStatus() {
		return featTogStatus;
	}

	/**
	 * @param featTogStatus the featTogStatus to set
	 */
	public void setFeatTogStatus(String featTogStatus) {
		this.featTogStatus = featTogStatus;
	}
}
