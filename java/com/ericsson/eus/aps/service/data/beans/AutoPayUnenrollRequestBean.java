package com.ericsson.eus.aps.service.data.beans;

import javax.validation.constraints.NotEmpty;

/**
 * 
 * FILE_NAME: AutoPayUnenrollRequestBean.java
 * 
 * MODULE DESCRIPTION: Request bean for Autopay Unenroll, US/F/D US84042
 *
 * @author eihsnad, Date: Sep 30, 2020 1:26:00 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class AutoPayUnenrollRequestBean extends BaseRequest {
	/**
	 * User: eihsnad
	 *
	 * Purpose: bean properties, US/F/D US84042 long
	 */
	private static final long serialVersionUID = -2849614208275442383L;
	@NotEmpty(message = "AutoPayUnenrollRequestBean's MSISDN has invalid value")
	private String msisdn;
	@NotEmpty(message = "AutoPayUnenrollRequestBean's COMMENT has invalid value")
	private String comment;
	private Long accountId;
	private boolean hardUnenroll;
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isHardUnenroll() {
		return hardUnenroll;
	}

	public void setHardUnenroll(boolean hardUnenroll) {
		this.hardUnenroll = hardUnenroll;
	}

	@Override
	public String toString() {
		return "AutoPayUnenrollRequestBean [msisdn=" + msisdn + ", comment=" + comment + ", accountId=" + accountId
				+ ", hardUnenroll=" + hardUnenroll + "]";
	}
}
