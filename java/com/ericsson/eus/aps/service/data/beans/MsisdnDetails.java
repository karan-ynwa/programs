package com.ericsson.eus.aps.service.data.beans;

/**
 * 
 * FILE_NAME: AutoPayPromoRequestBean.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Sep 11, 2020 3:08:01 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class MsisdnDetails {

	/**
	 * User: ekxrxax
	 *
	 * Purpose: TODO, US/F/D
	 * long
	 */
	private static final long serialVersionUID = 1L;
	private String msisdn;
	private String mlType;
	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}
	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	/**
	 * @return the mlType
	 */
	public String getMlType() {
		return mlType;
	}
	/**
	 * @param mlType the mlType to set
	 */
	public void setMlType(String mlType) {
		this.mlType = mlType;
	}

	/**
	 * User: ekxrxax , Date: Sep 16, 2020 12:47:13 AM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number: 
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "AutoPayInfoRequestBean [msisdn=" + msisdn + ", mlType=" + mlType + "]";
	}
	
}
