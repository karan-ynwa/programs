package com.ericsson.eus.aps.service.data.beans;

import javax.validation.constraints.NotEmpty;

import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.service.util.ApsUtil;

/**
 * 
 * FILE_NAME: AutoPayHistoryRequestBean.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author eihsnad, Date: Nov 10, 2020 11:50:15 AM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class AutoPayHistoryRequestBean extends BaseRequest {

	/**
	 * User: eihsnad
	 *
	 * Purpose: Request bean for history, US/F/D : US84045
	 */
	private static final long serialVersionUID = 5096872135146524710L;
	@NotEmpty(message = "AutoPayHistoryRequestBean's MSISDN has invalid value")
	private String msisdn;
	@NotEmpty(message = "AutoPayHistoryRequestBean's StartDate has invalid value")
	private String startdate;
	@NotEmpty(message = "AutoPayHistoryRequestBean's EndDate has invalid value")
	private String enddate;
	private Integer numberOfRecords;
	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	/**
	 * @return the numberOfRecords
	 */
	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}

	/**
	 * @param numberOfRecords the numberOfRecords to set
	 */
	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	/**
	 * User: ekxrxax , Date: Dec 17, 2020 5:15:40 PM 2020
	 *
	 * Purpose: ToString Method
	 *
	 * US/D/F Number: 
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "AutoPayHistoryRequestBean [msisdn=" + msisdn + ", startdate="
				+ startdate + ", enddate=" + enddate + ", numberOfRecords="
				+ numberOfRecords + "]";
	}

}
