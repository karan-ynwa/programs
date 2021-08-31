package com.ericsson.eus.aps.service.data.beans;

import java.io.Serializable;

import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.service.util.ApsUtil;

/**
 * 
 * FILE_NAME: BaseResponse.java
 * 
 * MODULE DESCRIPTION: Base response class, ATTPRE-84028
 *
 * @author eihsnad, Date: Sep 7, 2020 12:34:12 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class BaseResponse implements Serializable {

	/**
	 * User: eihsnad
	 *
	 * Purpose: generate serial id , ATTPRE-84028
	 */
	private static final long serialVersionUID = 834957765316331129L;
	protected Integer responseCode;
	protected String responseText;


	/**
	 * Gets the value of the responseCode property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getResponseCode() {
		return responseCode;
	}
	/**
	 * Sets the value of the responseCode property.
	 * 
	 * @param value allowed object is {@link Integer }
	 * 
	 */
	public void setResponseCode(Integer value) {
		this.responseCode = value;
	}

	/**
	 * Gets the value of the responseText property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getResponseText() {
		return responseText;
	}

	/**
	 * Sets the value of the responseText property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setResponseText(String value) {
		this.responseText = value;
	}

	/**
	 * validate
	 * 
	 * @throws AmountDueServiceException
	 */
	public void validate() {
		ApsUtil.validateMandatoryField(getResponseCode(), "Response Code");
	}

	@Override
	public String toString() {
		return "BaseResponse [responseCode=" + responseCode + ", responseText=" + responseText + "]";
	}

}
