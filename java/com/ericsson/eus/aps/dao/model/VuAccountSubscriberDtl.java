package com.ericsson.eus.aps.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * FILE_NAME: VuAccountSubscriberDtl.java
 * 
 * MODULE DESCRIPTION: subscriber details bean, US84027
 *
 * @author ekxrxax, Date: Sep 11, 2020 6:29:48 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class VuAccountSubscriberDtl implements Serializable{

	private static final long serialVersionUID = 861687177769254467L;
	private String msisdn; // VARCHAR2
	private String ratePlanCode; // VARCHAR2
	private String status; // VARCHAR2 Not Null
     
     // ML Changes
	private String rplMirrorType; // VARCHAR2

	private String mltLineType;
	private String discountCode;// VARCHAR2 // F1164
	private String featureCodeList;
	private String accountId;
	private Date accountRatePlanExpireDate;
	private long ratePlanId;
	private String ratePlanName;
	private String afAmount;
	private Date activateDate;
	private String accountNumber;
	// Multi-Month type rate plan
	private long mcrpNumCycles;
	private Date AcctMCRPEndDate;
	
	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getRatePlanCode() {
		return ratePlanCode;
	}

	public void setRatePlanCode(String ratePlanCode) {
		this.ratePlanCode = ratePlanCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRplMirrorType() {
		return rplMirrorType;
	}

	public void setRplMirrorType(String rplMirrorType) {
		this.rplMirrorType = rplMirrorType;
	}


	public String getMltLineType() {
		return mltLineType;
	}

	public void setMltLineType(String mltLineType) {
		this.mltLineType = mltLineType;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}
	
	public Date getAccountRatePlanExpireDate() {
		return accountRatePlanExpireDate;
	}

	public void setAccountRatePlanExpireDate(Date accountRatePlanExpireDate) {
		this.accountRatePlanExpireDate = accountRatePlanExpireDate;
	}

	public long getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public long getMcrpNumCycles() {
		return mcrpNumCycles;
	}

	public void setMcrpNumCycles(long mcrpNumCycles) {
		this.mcrpNumCycles = mcrpNumCycles;
	}

	public Date getAcctMCRPEndDate() {
		return AcctMCRPEndDate;
	}

	public void setAcctMCRPEndDate(Date acctMCRPEndDate) {
		AcctMCRPEndDate = acctMCRPEndDate;
	}

	@Override
	public String toString() {
		return "VuAccountSubscriberDtl [msisdn=" + msisdn + ", ratePlanCode=" + ratePlanCode + ", status=" + status
				+ ", rplMirrorType=" + rplMirrorType + ", mltLineType=" + mltLineType + ", discountCode=" + discountCode
				+ ", featureCodeList=" + featureCodeList + ", accountId=" + accountId + ", accountRatePlanExpireDate="
				+ accountRatePlanExpireDate + ", ratePlanId=" + ratePlanId + ", ratePlanName=" + ratePlanName
				+ ", afAmount=" + afAmount + ", activateDate=" + activateDate + ", accountNumber=" + accountNumber
				+ ", mcrpNumCycles=" + mcrpNumCycles + ", AcctMCRPEndDate=" + AcctMCRPEndDate + "]";
	}
	/**
	 * @return the featureCodeList
	 */
	public String getFeatureCodeList() {
		return featureCodeList;
	}

	/**
	 * @param featureCodeList the featureCodeList to set
	 */
	public void setFeatureCodeList(String featureCodeList) {
		this.featureCodeList = featureCodeList;
	}

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
	 * @return the ratePlanName
	 */
	public String getRatePlanName() {
		return ratePlanName;
	}

	/**
	 * @param ratePlanName
	 *            the ratePlanName to set
	 */
	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}

	/**
	 * @return the afAmount
	 */
	public String getAfAmount() {
		return afAmount;
	}

	/**
	 * @param afAmount
	 *            the afAmount to set
	 */
	public void setAfAmount(String afAmount) {
		this.afAmount = afAmount;
	}

	public  Date getActivateDate() {
		return activateDate;
	}

	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
