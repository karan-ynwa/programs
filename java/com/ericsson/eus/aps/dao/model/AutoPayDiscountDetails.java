package com.ericsson.eus.aps.dao.model;

import java.io.Serializable;

/**
 * 
 * FILE_NAME: AutoPayDiscountDetails.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author esuspal, Date: Dec 7, 2020 11:50:50 AM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class AutoPayDiscountDetails implements Serializable{
	
	private static final long serialVersionUID = 5834644735290551049L;
	private String discountId;
	private String discountCode;
	private String adjType;
	private String adjCode;
	@Override
	public String toString() {
		return "AutoPayDiscountDetails [discountId=" + getDiscountId() + ", discountCode=" + getDiscountCode()
				+ ", adjType=" + getAdjType() + ", adjCode=" + getAdjCode() + "]";
	}
	
	/**
	 * @return the discountId
	 */
	public String getDiscountId() {
		return discountId;
	}

	/**
	 * @param discountId
	 *            the discountId to set
	 */
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	/**
	 * @return the discountCode
	 */
	public String getDiscountCode() {
		return discountCode;
	}
	
	/**
	 * @param discountCode
	 *            the discountCode to set
	 */
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}
	
	/**
	 * @return the adjType
	 */
	public String getAdjType() {
		return adjType;
	}
	
	/**
	 * @param adjType
	 *            the adjType to set
	 */
	public void setAdjType(String adjType) {
		this.adjType = adjType;
	}
	
	/**
	 * @return the adjCode
	 */
	public String getAdjCode() {
		return adjCode;
	}
	
	/**
	 * @param adjCode
	 *            the adjCode to set
	 */
	public void setAdjCode(String adjCode) {
		this.adjCode = adjCode;
	}

	
}
