package com.ericsson.eus.aps.dao.model;

import java.io.Serializable;

 /**
 * 
 * FILE_NAME: VuAutopayCriteria.java
 * 
 * MODULE DESCRIPTION: Model for AutopayCriteria View, US/F/D: US96224
 *
 * @author ekxrxax, Date: Feb 8, 2021 6:31:15 PM 2021
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class VuAutopayCriteria implements Serializable{

	/**
	 * User: ekxrxax
	 *
	 * Purpose: TODO, US/F/D
	 * long
	 */
	private static final long serialVersionUID = 3360834263674426934L;
	Integer discountId;
	String discountCode;
	String criteriaKey;
	String criteriaValue;
	String activationDateMax;
	String activationDateMin;
	String originalAutopayEnrollmentstartDate;
	String originalAutopayEnrollmentEndDate;
	/**
	 * @return the discountId
	 */
	public Integer getDiscountId() {
		return discountId;
	}
	/**
	 * @param discountId the discountId to set
	 */
	public void setDiscountId(Integer discountId) {
		this.discountId = discountId;
	}
	/**
	 * @return the discountCode
	 */
	public String getDiscountCode() {
		return discountCode;
	}
	/**
	 * @param discountCode the discountCode to set
	 */
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}
	/**
	 * @return the criteriaKey
	 */
	public String getCriteriaKey() {
		return criteriaKey;
	}
	/**
	 * @param criteriaKey the criteriaKey to set
	 */
	public void setCriteriaKey(String criteriaKey) {
		this.criteriaKey = criteriaKey;
	}
	/**
	 * @return the criteriaValue
	 */
	public String getCriteriaValue() {
		return criteriaValue;
	}
	/**
	 * @param criteriaValue the criteriaValue to set
	 */
	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}
	/**
	 * @return the activationDateMax
	 */
	public String getActivationDateMax() {
		return activationDateMax;
	}
	/**
	 * @param activationDateMax the activationDateMax to set
	 */
	public void setActivationDateMax(String activationDateMax) {
		this.activationDateMax = activationDateMax;
	}
	/**
	 * @return the activationDateMin
	 */
	public String getActivationDateMin() {
		return activationDateMin;
	}
	/**
	 * @param activationDateMin the activationDateMin to set
	 */
	public void setActivationDateMin(String activationDateMin) {
		this.activationDateMin = activationDateMin;
	}
	/**
	 * @return the originalAutopayEnrollmentstartDate
	 */
	public String getOriginalAutopayEnrollmentstartDate() {
		return originalAutopayEnrollmentstartDate;
	}
	/**
	 * @param originalAutopayEnrollmentstartDate the originalAutopayEnrollmentstartDate to set
	 */
	public void setOriginalAutopayEnrollmentstartDate(
			String originalAutopayEnrollmentstartDate) {
		this.originalAutopayEnrollmentstartDate = originalAutopayEnrollmentstartDate;
	}
	/**
	 * @return the originalAutopayEnrollmentEndDate
	 */
	public String getOriginalAutopayEnrollmentEndDate() {
		return originalAutopayEnrollmentEndDate;
	}
	/**
	 * @param originalAutopayEnrollmentEndDate the originalAutopayEnrollmentEndDate to set
	 */
	public void setOriginalAutopayEnrollmentEndDate(
			String originalAutopayEnrollmentEndDate) {
		this.originalAutopayEnrollmentEndDate = originalAutopayEnrollmentEndDate;
	}
	
	@Override
	public String toString() {
		return "VuAutopayCriteria [discountId=" + discountId + ", discountCode=" + discountCode + ", criteriaKey="
				+ criteriaKey + ", criteriaValue=" + criteriaValue + ", activationDateMax=" + activationDateMax
				+ ", activationDateMin=" + activationDateMin + ", originalAutopayEnrollmentstartDate="
				+ originalAutopayEnrollmentstartDate + ", originalAutopayEnrollmentEndDate="
				+ originalAutopayEnrollmentEndDate + "]";
	}
	
}
