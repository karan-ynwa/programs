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
public class AutoPaySubscriberDtl implements Serializable{

	private static final long serialVersionUID = 861687177769254467L;
     // ML Changes
	private String rplMirrorType; // VARCHAR2
	private String mltLineType;
	private String discountCode;// VARCHAR2 // F1164
	private String featureCodeList;
	private String accountId;
	private Date accountRatePlanExpireDate;
	private long ratePlanId;
	private Date activateDate;
	private String accountNumber;
	private String topUpCycleCD;
	private String msisdn; // VARCHAR2
	private String ratePlanCode; // VARCHAR2
	private String status; // VARCHAR2 Not Null
	private String ratePlanName;
	private String afAmount;
	private String fixedAmount;
	private Date acctMCRPEndDate;
	private String mcrpNoOfCycles;
	

	@Override
	public String toString() {
		return "VuAccountSubscriberDtl [msisdn=" + getMsisdn() + ", ratePlanCode=" + getRatePlanCode() + ", status="
				+ getStatus() + ", rplMirrorType=" + getRplMirrorType() + ", mltLineType=" + getMltLineType()
				+ ", discountCode=" + getDiscountCode() + ", featureCodeList=" + getFeatureCodeList() + ", accountId="
				+ getAccountId() + ", accountRatePlanExpireDate=" + getAccountRatePlanExpireDate() + ", ratePlanId="
				+ getRatePlanId() + ", activateDate=" + getActivateDate() + ", accountNumber=" + getAccountNumber()
				+ ", fixedAmount=" + getFixedAmount() + ", acctMCRPEndDate=" + getAcctMCRPEndDate()
				+ ", mcrpNoOfCycles=" + getMcrpNoOfCycles() + "]";
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the rplMirrorType
	 */
	public String getRplMirrorType() {
		return rplMirrorType;
	}

	/**
	 * @return the mltLineType
	 */
	public String getMltLineType() {
		return mltLineType;
	}

	/**
	 * @return the discountCode
	 */
	public String getDiscountCode() {
		return discountCode;
	}

	/**
	 * @return the featureCodeList
	 */
	public String getFeatureCodeList() {
		return featureCodeList;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @return the accountRatePlanExpireDate
	 */
	public Date getAccountRatePlanExpireDate() {
		return accountRatePlanExpireDate;
	}

	/**
	 * @return the ratePlanId
	 */
	public long getRatePlanId() {
		return ratePlanId;
	}

	/**
	 * @return the activateDate
	 */
	public Date getActivateDate() {
		return activateDate;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @return the topUpCycleCD
	 */
	public String getTopUpCycleCD() {
		return topUpCycleCD;
	}

	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * @return the ratePlanCode
	 */
	public String getRatePlanCode() {
		return ratePlanCode;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the ratePlanName
	 */
	public String getRatePlanName() {
		return ratePlanName;
	}

	/**
	 * @return the afAmount
	 */
	public String getAfAmount() {
		return afAmount;
	}

	/**
	 * @return the fixedAmount
	 */
	public String getFixedAmount() {
		return fixedAmount;
	}

	/**
	 * @param rplMirrorType
	 *            the rplMirrorType to set
	 */
	public void setRplMirrorType(String rplMirrorType) {
		this.rplMirrorType = rplMirrorType;
	}

	/**
	 * @param mltLineType
	 *            the mltLineType to set
	 */
	public void setMltLineType(String mltLineType) {
		this.mltLineType = mltLineType;
	}

	/**
	 * @param discountCode
	 *            the discountCode to set
	 */
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	/**
	 * @param featureCodeList
	 *            the featureCodeList to set
	 */
	public void setFeatureCodeList(String featureCodeList) {
		this.featureCodeList = featureCodeList;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @param accountRatePlanExpireDate
	 *            the accountRatePlanExpireDate to set
	 */
	public void setAccountRatePlanExpireDate(Date accountRatePlanExpireDate) {
		this.accountRatePlanExpireDate = accountRatePlanExpireDate;
	}

	/**
	 * @param ratePlanId
	 *            the ratePlanId to set
	 */
	public void setRatePlanId(long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	/**
	 * @param activateDate
	 *            the activateDate to set
	 */
	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @param topUpCycleCD
	 *            the topUpCycleCD to set
	 */
	public void setTopUpCycleCD(String topUpCycleCD) {
		this.topUpCycleCD = topUpCycleCD;
	}

	/**
	 * @param msisdn
	 *            the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @param ratePlanCode
	 *            the ratePlanCode to set
	 */
	public void setRatePlanCode(String ratePlanCode) {
		this.ratePlanCode = ratePlanCode;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param ratePlanName
	 *            the ratePlanName to set
	 */
	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}

	/**
	 * @param afAmount
	 *            the afAmount to set
	 */
	public void setAfAmount(String afAmount) {
		this.afAmount = afAmount;
	}

	/**
	 * @param fixedAmount
	 *            the fixedAmount to set
	 */
	public void setFixedAmount(String fixedAmount) {
		this.fixedAmount = fixedAmount;
	}

	/**
	 * @return the acctMCRPEndDate
	 */
	public Date getAcctMCRPEndDate() {
		return acctMCRPEndDate;
	}

	/**
	 * @param acctMCRPEndDate
	 *            the acctMCRPEndDate to set
	 */
	public void setAcctMCRPEndDate(Date acctMCRPEndDate) {
		this.acctMCRPEndDate = acctMCRPEndDate;
	}

	/**
	 * @return the mcrpNoOfCycles
	 */
	public String getMcrpNoOfCycles() {
		return mcrpNoOfCycles;
	}

	/**
	 * @param mcrpNoOfCycles
	 *            the mcrpNoOfCycles to set
	 */
	public void setMcrpNoOfCycles(String mcrpNoOfCycles) {
		this.mcrpNoOfCycles = mcrpNoOfCycles;
	}
	
}
