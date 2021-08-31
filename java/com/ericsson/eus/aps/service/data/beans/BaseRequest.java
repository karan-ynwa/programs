package com.ericsson.eus.aps.service.data.beans;

import java.io.Serializable;

import com.ericsson.eus.aps.common.exception.AutopayServiceException;
/**
 * 
 * FILE_NAME: BaseRequest.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Sep 11, 2020 3:11:55 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class BaseRequest implements IBaseRequest, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3654948123237203953L;



	/**
	 * validate
	 * 
	 * @throws DiscountServiceException
	 */
	@Override
	public void validate() {
		// just kept for future common params
	}


}
