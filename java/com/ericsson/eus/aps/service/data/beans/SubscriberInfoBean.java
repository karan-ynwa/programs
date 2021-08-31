package com.ericsson.eus.aps.service.data.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * FILE_NAME: SubscriberInfoBean.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D: F1216
 *
 * @author esuspal, Date: Nov 23, 2020 8:37:58 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriberInfoBean {

	private String msisdn;
	private String apStatus;
	private String activationDate;
	private String apEnrollmentDate;
	private String rateplanCode;
	private String rateplanName;
	private String rateplanAmount;
	private String fixedAmount;
	private String rateplanExpirationDate;
	private String apPDOFToken;
	private String apPDOFLast4Digits;
	private String apPaymentDate;
	private String apTopupCycleTypeCD;
	private String apTopupCycleTypeCDName;

	private static final long serialVersionUID = 1L;
	private List<AutoPayDiscountPromoApplied> autoPayDiscountPromoApplied;
	private List<FeatureInfoBean> apFeatureInfo;
	private String apTotalFeatureAmount;
	/**
	 * User: ekxrxax , Date: Sep 16, 2020 1:48:05 PM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number: 
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "SubscriberInfoBean [autoPayDiscountPromoApplied=" + autoPayDiscountPromoApplied + ", msisdn=" + msisdn
				+ ", apStatus=" + apStatus + ",activationDate= " + activationDate + " , apEnrollmentDate = "
				+ apEnrollmentDate + ", rateplanCode=" + rateplanCode + ", rateplanName=" + rateplanName
				+ ", rateplanAmount=" + rateplanAmount + ", rateplanExpirationDate=" + rateplanExpirationDate
				+ ", apPDOFToken=" + apPDOFToken + ", apPDOFLast4Digits=" + apPDOFLast4Digits + ", apPaymentDate="
				+ apPaymentDate + ", apTopupCycleTypeCD=" + apTopupCycleTypeCD + ", apTopupCycleTypeCDName="
				+ apTopupCycleTypeCDName + ", apFeatureInfo=" + apFeatureInfo + ", apTotalFeatureAmount="
				+ apTotalFeatureAmount + ", fixedAmount=" + fixedAmount + "  ]";
	}

	/**
	 * @return the autoPayDiscountPromoApplied
	 */
	public List<AutoPayDiscountPromoApplied> getAutoPayDiscountPromoApplied() {
		return autoPayDiscountPromoApplied;
	}

	/**
	 * @param autoPayDiscountPromoApplied
	 *            the autoPayDiscountPromoApplied to set
	 */
	public void setAutoPayDiscountPromoApplied(List<AutoPayDiscountPromoApplied> autoPayDiscountPromoApplied) {
		this.autoPayDiscountPromoApplied = autoPayDiscountPromoApplied;
	}

	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * @param msisdn
	 *            the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return the apStatus
	 */
	public String getApStatus() {
		return apStatus;
	}

	/**
	 * @param apStatus
	 *            the apStatus to set
	 */
	public void setApStatus(String apStatus) {
		this.apStatus = apStatus;
	}

	/**
	 * @return the activationDate
	 */
	public String getActivationDate() {
		return activationDate;
	}

	/**
	 * @param activationDate
	 *            the activationDate to set
	 */
	public void setActivationDate(String activationDate) {
		this.activationDate = activationDate;
	}

	/**
	 * @return the apEnrollmentDate
	 */
	public String getApEnrollmentDate() {
		return apEnrollmentDate;
	}

	/**
	 * @param apEnrollmentDate
	 *            the apEnrollmentDate to set
	 */
	public void setApEnrollmentDate(String apEnrollmentDate) {
		this.apEnrollmentDate = apEnrollmentDate;
	}

	/**
	 * @return the rateplanCode
	 */
	public String getRateplanCode() {
		return rateplanCode;
	}

	/**
	 * @param rateplanCode
	 *            the rateplanCode to set
	 */
	public void setRateplanCode(String rateplanCode) {
		this.rateplanCode = rateplanCode;
	}

	/**
	 * @return the rateplanName
	 */
	public String getRateplanName() {
		return rateplanName;
	}

	/**
	 * @param rateplanName
	 *            the rateplanName to set
	 */
	public void setRateplanName(String rateplanName) {
		this.rateplanName = rateplanName;
	}

	/**
	 * @return the rateplanAmount
	 */
	public String getRateplanAmount() {
		return rateplanAmount;
	}

	/**
	 * @param rateplanAmount
	 *            the rateplanAmount to set
	 */
	public void setRateplanAmount(String rateplanAmount) {
		this.rateplanAmount = rateplanAmount;
	}

	/**
	 * @return the rateplanExpirationDate
	 */
	public String getRateplanExpirationDate() {
		return rateplanExpirationDate;
	}

	/**
	 * @param rateplanExpirationDate
	 *            the rateplanExpirationDate to set
	 */
	public void setRateplanExpirationDate(String rateplanExpirationDate) {
		this.rateplanExpirationDate = rateplanExpirationDate;
	}

	/**
	 * @return the apPDOFToken
	 */
	public String getApPDOFToken() {
		return apPDOFToken;
	}

	/**
	 * @param apPDOFToken
	 *            the apPDOFToken to set
	 */
	public void setApPDOFToken(String apPDOFToken) {
		this.apPDOFToken = apPDOFToken;
	}

	/**
	 * @return the apPDOFLast4Digits
	 */
	public String getApPDOFLast4Digits() {
		return apPDOFLast4Digits;
	}

	/**
	 * @param apPDOFLast4Digits
	 *            the apPDOFLast4Digits to set
	 */
	public void setApPDOFLast4Digits(String apPDOFLast4Digits) {
		this.apPDOFLast4Digits = apPDOFLast4Digits;
	}

	/**
	 * @return the apPaymentDate
	 */
	public String getApPaymentDate() {
		return apPaymentDate;
	}

	/**
	 * @param apPaymentDate
	 *            the apPaymentDate to set
	 */
	public void setApPaymentDate(String apPaymentDate) {
		this.apPaymentDate = apPaymentDate;
	}

	/**
	 * @return the apTopupCycleTypeCD
	 */
	public String getApTopupCycleTypeCD() {
		return apTopupCycleTypeCD;
	}

	/**
	 * @param apTopupCycleTypeCD
	 *            the apTopupCycleTypeCD to set
	 */
	public void setApTopupCycleTypeCD(String apTopupCycleTypeCD) {
		this.apTopupCycleTypeCD = apTopupCycleTypeCD;
	}

	/**
	 * @return the apTopupCycleTypeCDName
	 */
	public String getApTopupCycleTypeCDName() {
		return apTopupCycleTypeCDName;
	}

	/**
	 * @param apTopupCycleTypeCDName
	 *            the apTopupCycleTypeCDName to set
	 */
	public void setApTopupCycleTypeCDName(String apTopupCycleTypeCDName) {
		this.apTopupCycleTypeCDName = apTopupCycleTypeCDName;
	}

	/**
	 * @return the apFeatureInfo
	 */
	public List<FeatureInfoBean> getApFeatureInfo() {
		return apFeatureInfo;
	}

	/**
	 * @param apFeatureInfo
	 *            the apFeatureInfo to set
	 */
	public void setApFeatureInfo(List<FeatureInfoBean> apFeatureInfo) {
		this.apFeatureInfo = apFeatureInfo;
	}

	/**
	 * @return the apTotalFeatureAmount
	 */
	public String getApTotalFeatureAmount() {
		return apTotalFeatureAmount;
	}

	/**
	 * @param apTotalFeatureAmount
	 *            the apTotalFeatureAmount to set
	 */
	public void setApTotalFeatureAmount(String apTotalFeatureAmount) {
		this.apTotalFeatureAmount = apTotalFeatureAmount;
	}

	/**
	 * @return the fixedAmount
	 */
	public String getFixedAmount() {
		return fixedAmount;
	}

	/**
	 * @param fixedAmount
	 *            the fixedAmount to set
	 */
	public void setFixedAmount(String fixedAmount) {
		this.fixedAmount = fixedAmount;
	}
	
}
