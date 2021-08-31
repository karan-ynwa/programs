package com.ericsson.eus.aps.dao.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * FILE_NAME: VuDsaEvaluations.java
 * 
 * MODULE DESCRIPTION:dsa evaluation bean, US84027
 *
 * @author ekxrxax, Date: Sep 11, 2020 6:33:02 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class VuDsaEvaluations implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9040074371901997375L;
	Integer discountId;
	String discountCode;
	Date discountEndDate;
	String discountName;
	Date discountStartDate;
	String discountType;
	Integer serviceId;
	String serviceCode;
	Integer criteriaEvalId;
	Integer offerId;
	String offerCode;
	String criteriaKey;
	String criteriaValue;
	
	Integer offerUnit;
	String bonusCode;
	Integer bonusUnits;
	String bonusUnitsType;
	String bonusServiceType;

	/**
	 * @return the bonusUnits
	 */
	public Integer getBonusUnits() {
		return bonusUnits;
	}

	/**
	 * @param bonusUnits the bonusUnits to set
	 */
	public void setBonusUnits(Integer bonusUnits) {
		this.bonusUnits = bonusUnits;
	}
	/**
	 * @return the bonusUnitsType
	 */
	public String getBonusUnitsType() {
		return bonusUnitsType;
	}

	/**
	 * @param bonusUnitsType the bonusUnitsType to set
	 */
	public void setBonusUnitsType(String bonusUnitsType) {
		this.bonusUnitsType = bonusUnitsType;
	}

	/**
	 * @return the bonusServiceType
	 */
	public String getBonusServiceType() {
		return bonusServiceType;
	}

	/**
	 * @param bonusServiceType the bonusServiceType to set
	 */
	public void setBonusServiceType(String bonusServiceType) {
		this.bonusServiceType = bonusServiceType;
	}

	public Integer getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Integer discountId) {
		this.discountId = discountId;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public Date getDiscountEndDate() {
		return discountEndDate;
	}

	public void setDiscountEndDate(Date discountEndDate) {
		this.discountEndDate = discountEndDate;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public Date getDiscountStartDate() {
		return discountStartDate;
	}

	public void setDiscountStartDate(Date discountStartDate) {
		this.discountStartDate = discountStartDate;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public Integer getCriteriaEvalId() {
		return criteriaEvalId;
	}

	public void setCriteriaEvalId(Integer criteriaEvalId) {
		this.criteriaEvalId = criteriaEvalId;
	}

	public Integer getOfferId() {
		return offerId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	public String getOfferCode() {
		return offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}


	public String getCriteriaKey() {
		return criteriaKey;
	}

	public void setCriteriaKey(String criteriaKey) {
		this.criteriaKey = criteriaKey;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Integer getOfferUnit() {
		return offerUnit;
	}

	public void setOfferUnit(Integer offerUnit) {
		this.offerUnit = offerUnit;
	}


	public String getBonusCode() {
		return bonusCode;
	}

	public void setBonusCode(String bonusCode) {
		this.bonusCode = bonusCode;
	}

	/**
	 * User: ekxrxax , Date: Oct 2, 2020 4:35:50 PM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number: 
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "VuDsaEvaluations [discountId=" + discountId + ", discountCode=" + discountCode + ", discountEndDate="
				+ discountEndDate + ", discountName=" + discountName + ", discountStartDate=" + discountStartDate
				+ ", discountType=" + discountType + ", serviceId=" + serviceId + ", serviceCode=" + serviceCode
				+ ", criteriaEvalId=" + criteriaEvalId + ", offerId=" + offerId + ", offerCode=" + offerCode
				+ ", criteriaKey=" + criteriaKey + ", criteriaValue=" + criteriaValue + ", offerUnit=" + offerUnit
				+ ", bonusCode=" + bonusCode + ", bonusUnits=" + bonusUnits + ", bonusUnitsType=" + bonusUnitsType
				+ ", bonusServiceType=" + bonusServiceType + "]";
	}

	
}
