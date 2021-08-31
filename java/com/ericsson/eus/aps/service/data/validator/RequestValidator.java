package com.ericsson.eus.aps.service.data.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException.FaultGlobalCode;
import com.ericsson.eus.aps.service.data.beans.IBaseRequest;

/**
 * 
 * FILE_NAME: RequestValidator.java
 * 
 * MODULE DESCRIPTION: F1217
 *
 * @author ekxrxax, Date: Sep 11, 2020 5:38:11 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class RequestValidator {
	private RequestValidator() {
		//do nothing
	}
	private static Logger logger = LogManager.getLogger(RequestValidator.class);
	/**
	 * User: ekxrxax , Date: Sep 11, 2020 5:38:59 PM 2020
	 *
	 * US/D/F Number:F1217
	 * 
	 * Return Type: void
	 *
	 * @param request
	 * @throws AutopayServiceException 
	 */
	public static void validate(IBaseRequest request) {
		if(request == null)	{
			logger.error("Invalid Request. Request is NULL");
			throw new AutopayServiceException(FaultGlobalCode.MANDATORY_FIELD_MISSING,"Invalid request[NULL]");
		}
		else{
			request.validate();
		}
	}

}
