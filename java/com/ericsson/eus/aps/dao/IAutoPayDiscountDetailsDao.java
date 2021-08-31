package com.ericsson.eus.aps.dao;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.model.AutoPayDiscountDetails;

/**
 * 
 * FILE_NAME: IAutoPayDiscountDetailsDao.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author esuspal, Date: Dec 21, 2020 6:38:57 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public interface IAutoPayDiscountDetailsDao {

	public AutoPayDiscountDetails getAutoPayDiscountDetails(String discountCode) throws AutopayDaoException;

	public void updateAutoPayDiscountDetails();
}
