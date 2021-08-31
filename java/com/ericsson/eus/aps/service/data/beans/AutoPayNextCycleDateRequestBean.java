package com.ericsson.eus.aps.service.data.beans;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 
 * FILE_NAME: AutoPayNextCycleDateRequestBean.java
 * 
 * MODULE DESCRIPTION: F1221/US88638
 *
 * @author epinmon, Date: Jan 6, 2021 5:03:53 PM 2021
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class AutoPayNextCycleDateRequestBean extends BaseRequest {
	
	private static final long serialVersionUID = 7696477272743911489L;


	private Date supervisionExpiryDate;
	private Date accountRatePlanExpireDate;
	@Min(value = 1, message = "Account ID not available")
	private long accountId;
	@Valid
	@NotNull(message = "currentNextAutopayCycleDate not available")
	private Date currentNextAutopayCycleDate;
	/**
	 * @return the supervisionExpiryDate
	 */
	public Date getSupervisionExpiryDate() {
		return supervisionExpiryDate;
	}
	/**
	 * @param supervisionExpiryDate the supervisionExpiryDate to set
	 */
	public void setSupervisionExpiryDate(Date supervisionExpiryDate) {
		this.supervisionExpiryDate = supervisionExpiryDate;
	}
	/**
	 * @return the accountRatePlanExpireDate
	 */
	public Date getAccountRatePlanExpireDate() {
		return accountRatePlanExpireDate;
	}
	/**
	 * @param accountRatePlanExpireDate the accountRatePlanExpireDate to set
	 */
	public void setAccountRatePlanExpireDate(Date accountRatePlanExpireDate) {
		this.accountRatePlanExpireDate = accountRatePlanExpireDate;
	}
	
	/**
	 * @return the accountId
	 */
	public long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the currentNextAutopayCycleDate
	 */
	public Date getCurrentNextAutopayCycleDate() {
		return currentNextAutopayCycleDate;
	}
	
	/**
	 * @param currentNextAutopayCycleDate the currentNextAutopayCycleDate to set
	 */
	public void setCurrentNextAutopayCycleDate(Date currentNextAutopayCycleDate) {
		this.currentNextAutopayCycleDate = currentNextAutopayCycleDate;
	}

	@Override
	public String toString() {
		return "AutoPayNextCycleDateRequestBean [supervisionExpiryDate=" + supervisionExpiryDate
				+ ", accountRatePlanExpireDate=" + accountRatePlanExpireDate + ", accountId=" + accountId
				+ ", currentNextAutopayCycleDate=" + currentNextAutopayCycleDate + "]";
	}

}
