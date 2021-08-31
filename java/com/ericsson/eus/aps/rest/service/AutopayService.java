package com.ericsson.eus.aps.rest.service;

import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.service.data.beans.AutoPayNextCycleDateRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayUpdateRequestBean;
import com.ericsson.eus.aps.service.data.beans.BaseResponse;

/**
 * 
 * FILE_NAME: AutopayService.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Sep 14, 2020 3:30:37 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public interface AutopayService {

	public BaseResponse autopayUpdate(AutoPayUpdateRequestBean request);

	/**
	 * User: epinmon , Date: Jan 6, 2021 1:29:17 PM 2021
	 *
	 * Purpose: this method will calculate then validate and finally updates next
	 * auto pay cycle date.
	 *
	 * US/D/F Number: F1221/US88638
	 *
	 * @param request
	 * @return response
	 */
	BaseResponse validateAndUpdateNextAutopayCycleDate(AutoPayNextCycleDateRequestBean request)
			throws AutopayServiceException;
}
