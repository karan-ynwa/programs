package com.ericsson.eus.aps.dao.model;

import java.util.Date;
/**
 * 
 * FILE_NAME: DsaDiscounts.java
 * 
 * MODULE DESCRIPTION:Dsa Discounts bean, US84027
 *
 * @author ekxrxax, Date: Sep 11, 2020 6:33:02 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class DsaDiscounts {

	Integer discountId;
	String discountCode;
	Date discountEndDate;
	String discountName;
	Date discountStartDate;
	String discountType;
	Integer criteriaEvalId;
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
	@Override
	public String toString() {
		return "DSADiscounts [discountId=" + discountId + ", discountCode=" + discountCode + ", discountEndDate="
				+ discountEndDate + ", discountName=" + discountName + ", discountStartDate=" + discountStartDate
				+ ", discountType=" + discountType + ", criteriaEvalId=" + criteriaEvalId + "]";
	}
	
}