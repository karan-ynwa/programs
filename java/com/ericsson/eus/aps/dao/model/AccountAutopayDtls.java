package com.ericsson.eus.aps.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * FILE_NAME: AccountAutopayDtls.java
 * 
 * MODULE DESCRIPTION: autopay details bean, US84027
 *
 * @author ekxrxax, Date: Sep 14, 2020 1:03:02 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class AccountAutopayDtls implements Serializable{
	
	private static final long serialVersionUID = 5834644735290551049L;
	private String accountId;
	private String autopayStatus;
	private Date enrollmentStartdDate;
	private Date lastUnenrollmentDate;
	private String recurringTopupCycleCDType;
	private String apCurrentdiscountCode;
	private Date nextAPCycleDate;
	private Date apDiscountAppliedDate;
	private String recurringTopupCycleCDValue;
	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	/**
	 * @return the autopayStatus
	 */
	public String getAutopayStatus() {
		return autopayStatus;
	}
	/**
	 * @param autopayStatus the autopayStatus to set
	 */
	public void setAutopayStatus(String autopayStatus) {
		this.autopayStatus = autopayStatus;
	}
	/**
	 * @return the enrollmentStartdDate
	 */
	public Date getEnrollmentStartdDate() {
		return enrollmentStartdDate;
	}
	/**
	 * @param enrollmentStartdDate the enrollmentStartdDate to set
	 */
	public void setEnrollmentStartdDate(Date enrollmentStartdDate) {
		this.enrollmentStartdDate = enrollmentStartdDate;
	}
	/**
	 * @return the lastUnenrollmentDate
	 */
	public Date getLastUnenrollmentDate() {
		return lastUnenrollmentDate;
	}
	/**
	 * @param LastUnenrollmentDate the LastUnenrollmentDate to set
	 */
	public void setLastUnenrollmentDate(Date lastUnenrollmentDate) {
		this.lastUnenrollmentDate = lastUnenrollmentDate;
	}
	/**
	 * @return the recurringTopupCycleCDType
	 */
	public String getRecurringTopupCycleCDType() {
		return recurringTopupCycleCDType;
	}
	/**
	 * @param recurringTopupCycleCDType the recurringTopupCycleCDType to set
	 */
	public void setRecurringTopupCycleCDType(String recurringTopupCycleCDType) {
		this.recurringTopupCycleCDType = recurringTopupCycleCDType;
	}
	
	
	/**
	 * @return the apCurrentdiscountCode
	 */
	public String getApCurrentdiscountCode() {
		return apCurrentdiscountCode;
	}
	/**
	 * @param apCurrentdiscountCode the apCurrentdiscountCode to set
	 */
	public void setApCurrentdiscountCode(String apCurrentdiscountCode) {
		this.apCurrentdiscountCode = apCurrentdiscountCode;
	}
	/**
	 * @return the nextAPCycleDate
	 */
	public Date getNextAPCycleDate() {
		return nextAPCycleDate;
	}
	/**
	 * @param nextAPCycleDate the nextAPCycleDate to set
	 */
	public void setNextAPCycleDate(Date nextAPCycleDate) {
		this.nextAPCycleDate = nextAPCycleDate;
	}

	/**
	 * @return the apDiscountAppliedDate
	 */
	public Date getApDiscountAppliedDate() {
		return apDiscountAppliedDate;
	}
	/**
	 * @param apDiscountAppliedDate the apDiscountAppliedDate to set
	 */
	public void setApDiscountAppliedDate(Date apDiscountAppliedDate) {
		this.apDiscountAppliedDate = apDiscountAppliedDate;
	}
	@Override
	public String toString() {
		return "AccountAutopayDtls [accountId=" + accountId + ", autopayStatus=" + autopayStatus
				+ ", enrollmentStartdDate=" + enrollmentStartdDate + ", lastUnenrollmentDate="
				+ lastUnenrollmentDate + ", recurringTopupCycleCDType=" + recurringTopupCycleCDType + ", apCurrentdiscountCode=" + apCurrentdiscountCode
				+ ", nextAPCycleDate=" + nextAPCycleDate + ", apDiscountAppliedDate=" + apDiscountAppliedDate + "]";
	}

	/**
	 * @return the recurringTopupCycleCDValue
	 */
	public String getRecurringTopupCycleCDValue() {
		return recurringTopupCycleCDValue;
	}

	/**
	 * @param recurringTopupCycleCDValue
	 *            the recurringTopupCycleCDValue to set
	 */
	public void setRecurringTopupCycleCDValue(String recurringTopupCycleCDValue) {
		this.recurringTopupCycleCDValue = recurringTopupCycleCDValue;
	}

	
}
