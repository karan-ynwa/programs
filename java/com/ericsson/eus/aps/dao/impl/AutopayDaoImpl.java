package com.ericsson.eus.aps.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.IAutopayDao;
import com.ericsson.eus.aps.dao.model.AccountAutopayDtls;
import com.ericsson.eus.aps.rest.common.RESTConstants.ApsConstants;
import com.ericsson.eus.aps.service.data.beans.AutoPayEnrollRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayUnenrollRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayUpdateRequestBean;


/**
 * 
 * FILE_NAME: IAutopayDaoImpl.java
 * 
 * MODULE DESCRIPTION: Autopay DAO implementation , US/F/D US84042
 *
 * @author eihsnad, Date: Sep 30, 2020 2:01:14 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
@Repository("autopayDao")
public class AutopayDaoImpl extends DaoBase implements IAutopayDao {

	/**
	 * User: eihsnad , Date: Sep 30, 2020 2:01:59 PM 2020
	 *
	 * Purpose: perform Autopay unenroll
	 *
	 * US/D/F Number:US84042
	 *
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());
	private static final String PROC_AUTOPAY_CALL = "{call ebi_services_pkg.SET_AUTOPAY_DATA(?,?,?,?,?,?,?,?,?,?)}";
	private static final String UPDATE_ACTION = "update";
	private static final String ENROLL_ACTION = "enroll";
	private static final String SOFT_UNENROLL_ACTION = "softunenroll";
	private static final String HARD_UNENROLL_ACTION = "hardunenroll";
	private static final String UPDATE_NEXT_CYCLE_DATE_ACTION = "updateNextCycleDate";
	

	/**
	 * User: eznraps , Date: Nov 23, 2020 1:26:44 PM 2020
	 *
	 * Purpose: perform AutopayUnEnroll
	 *
	 * US/D/F Number:ATTPRE:80979
	 *
	 */
	@Override
	public void autopayUnenroll(final AutoPayUnenrollRequestBean request, AccountAutopayDtls autopayDtls,
			boolean isHardUnenrollType) throws AutopayDaoException {
		if (logger.isDebugEnabled()) {
			logger.debug(ApsConstants.QUERY_EXECUTING_MSG + PROC_AUTOPAY_CALL);
		}
		try (Connection con = getJdbcTemplate().getJdbcTemplate().getDataSource().getConnection()) {
			try (CallableStatement callStatement = con.prepareCall(PROC_AUTOPAY_CALL)) {
				callStatement.setLong(1, request.getAccountId());
				callStatement.setString(2, null);
				if (isHardUnenrollType) {
					callStatement.setString(3, ApsConstants.INACTIVE_STATUS);
				} else {
					callStatement.setString(3, ApsConstants.SOFT_INACTIVE_STATUS);
				}
				callStatement.setDate(4, null);
				callStatement.setString(5, null);
				callStatement.setDate(6, null);
				callStatement.setString(7, null);
				callStatement.setString(8, null);
				callStatement.setString(9, null);
				if (isHardUnenrollType) {
					callStatement.setString(10, HARD_UNENROLL_ACTION);
				} else {
				callStatement.setString(10, SOFT_UNENROLL_ACTION);
				}
				callStatement.executeUpdate();
			}
		}
		catch (SQLException e) {
			logger.error(e);
			throw new AutopayDaoException(e.getMessage(), e);
		}
	}
	
	/**
	 * User: eiansrv , Date: Oct 01, 2020 1:26:44 PM 2020
	 *
	 * Purpose: perform Autopay Enroll
	 *
	 * US/D/F Number:US84041
	 *
	 */
	@Override
	public void autopayEnroll(final String accountId, AutoPayEnrollRequestBean request, long apRecurringId, Date nextAutoPayCycleDate, String apDiscountCode) throws AutopayDaoException {
		if (logger.isDebugEnabled()) {
			logger.debug(ApsConstants.QUERY_EXECUTING_MSG + PROC_AUTOPAY_CALL);
		}
		
		try (Connection con = getJdbcTemplate().getJdbcTemplate().getDataSource().getConnection();
				CallableStatement callStatement = con.prepareCall(PROC_AUTOPAY_CALL)) {
			callStatement.setLong(1, Long.parseLong(accountId));
			callStatement.setLong(2, apRecurringId);
			callStatement.setString(3, ApsConstants.ACTIVE);
			callStatement.setDate(4, new java.sql.Date(nextAutoPayCycleDate.getTime()));
			callStatement.setString(5, apDiscountCode);
			if(apDiscountCode == null) {
				callStatement.setDate(6, null);
			}else {
				callStatement.setDate(6, new java.sql.Date(new Date().getTime()));
			}
			callStatement.setString(7, request.getPdofToken());
			callStatement.setString(8, request.getPdofLast4());
			callStatement.setString(9, request.getDescription());
			callStatement.setString(10, ENROLL_ACTION);
			callStatement.executeUpdate();
		}
		catch (SQLException e) {
			logger.error(e);
			throw new AutopayDaoException(e.getMessage(), e);
		}
	}

	/**
	 * User: eihsnad , Date: Nov 12, 2020 8:17:34 PM 2020
	 *
	 * Purpose: Autopay Update DAO
	 *
	 * US/D/F Number: US84043
	 *
	 * @param accountId
	 * @param request
	 * @throws AutopayDaoException
	 */
	@Override
	public void autopayUpdate(String accountId, AutoPayUpdateRequestBean request) throws AutopayDaoException {
		if (logger.isDebugEnabled()) {
			logger.debug("executing AutoPay Update Procedure " + PROC_AUTOPAY_CALL);
		}
		try (Connection con = getJdbcTemplate().getJdbcTemplate().getDataSource().getConnection(); 
			CallableStatement callStatement = con.prepareCall(PROC_AUTOPAY_CALL)) {
				callStatement.setLong(1, Long.parseLong(accountId));
				callStatement.setLong(2, 0L);
				callStatement.setString(3, null);
				callStatement.setDate(4, null);
				callStatement.setString(5, null);
				callStatement.setDate(6, null);
				callStatement.setString(7, request.getPdofToken());
				callStatement.setString(8, request.getPdofLast4());
			callStatement.setString(9, request.getDescription());
			callStatement.setString(10, UPDATE_ACTION);
				callStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new AutopayDaoException(e.getMessage(), e);
		}
	}
	
	
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
	@Override
	public void autopayUpdateNextCycleDate(long accountId, Date nextAutoPayCycleDate) throws AutopayDaoException {
		if (logger.isDebugEnabled()) {
			logger.debug("executing AutoPay Update Procedure " + PROC_AUTOPAY_CALL);
		}
		try (Connection con = getJdbcTemplate().getJdbcTemplate().getDataSource().getConnection(); 
			CallableStatement callStatement = con.prepareCall(PROC_AUTOPAY_CALL)) {
			callStatement.setLong(1, accountId);
				callStatement.setLong(2, 0L);
				callStatement.setString(3, null);
				callStatement.setDate(4, new java.sql.Date(nextAutoPayCycleDate.getTime()));
				callStatement.setString(5, null);
				callStatement.setDate(6, null);
				callStatement.setString(7, null);
				callStatement.setString(8, null);
			callStatement.setString(9, null);
			callStatement.setString(10, UPDATE_NEXT_CYCLE_DATE_ACTION);
				callStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new AutopayDaoException(e.getMessage(), e);
		}
	}

}
