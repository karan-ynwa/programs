package com.ericsson.eus.aps.dao;

import java.util.Date;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.model.AccountAutopayDtls;
import com.ericsson.eus.aps.service.data.beans.AutoPayEnrollRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayUnenrollRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayUpdateRequestBean;


/**
 * 
 * FILE_NAME: IAutopayDao.java
 * 
 * MODULE DESCRIPTION: Autopay Dao interface, US/F/D US84042
 *
 * @author eihsnad, Date: Sep 30, 2020 1:59:32 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public interface IAutopayDao {

	// [F1217] US90619
	public void autopayUnenroll(final AutoPayUnenrollRequestBean request, AccountAutopayDtls autopayDtls,
			boolean isHardUnenrollType) throws AutopayDaoException;

	//[F1217] US84041
	public void autopayEnroll(final String accountId, AutoPayEnrollRequestBean request, long apRecurringId, Date nextAutoPayCycleDate, String apDiscountCode) throws AutopayDaoException;

	// [F1217]-US84043
	public void autopayUpdate(final String accountId, AutoPayUpdateRequestBean request) throws AutopayDaoException;

	/**
	 * User: epinmon , Date: Jan 8, 2021 6:03:26 PM 2021
	 *
	 * Purpose: This method updates nextAutoPayCycleDate in ebi_account_autopay
	 * table.
	 *
	 * US/D/F Number:F1221/US88638
	 * 
	 * Return Type: void
	 *
	 * @param accountId
	 * @param nextAutoPayCycleDate
	 * @throws AutopayDaoException
	 */
	void autopayUpdateNextCycleDate(long accountId, Date nextAutoPayCycleDate) throws AutopayDaoException;

}
