package com.ericsson.eus.aps.rest.service;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.service.data.beans.AutoPayDiscountCalculationBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayDiscountPromoApplied;
import com.ericsson.eus.aps.service.data.beans.AutoPayInfoDataBean;

/**
 * 
 * FILE_NAME: AutoPayInfoDiscountService.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author esuspal, Date: Dec 9, 2020 8:55:45 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public interface AutoPayDiscountService {


	public AutoPayDiscountPromoApplied getAutoPayInfoDiscount(AutoPayDiscountCalculationBean autoPayDiscountCalculationBean)
			throws AutopayServiceException, AutopayDaoException;
	
	public String getAutoPayEnrollDiscount(AutoPayDiscountCalculationBean autoPayDiscountCalculationBean)
			throws AutopayServiceException, AutopayDaoException;

}
