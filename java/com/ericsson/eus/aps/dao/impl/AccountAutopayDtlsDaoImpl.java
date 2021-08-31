package com.ericsson.eus.aps.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ericsson.eus.aps.dao.IAccountAutopayDtlsDao;
import com.ericsson.eus.aps.dao.model.AccountAutopayDtls;
import com.ericsson.eus.aps.service.util.ApsUtil;

/**
 *
 * FILE_NAME: VuAccountAutopayDtlsDaoImpl.java
 *
 * MODULE DESCRIPTION: to fecth autopay details, US84027
 *
 * @author ekxrxax, Date: Sep 14, 2020 1:16:53 PM 2020
 *
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
@Repository("accountAutopayDtlsDao")
public class AccountAutopayDtlsDaoImpl extends DaoBase implements IAccountAutopayDtlsDao {
	private static final String QUERY_GET_AUTOPAY_DTLS = "SELECT * FROM EBI_ACCOUNT_AUTOPAY AP, EBI_AP_RECURRING_TOPUP_CYCLE_CD_TYPE CYCL WHERE AP.RECURRINGTOPUPCYCLECD_TYPE = CYCL.RECURRINGTOPUPCYCLECD_TYPE AND ACCOUNT_ID = :accountId";
	private final Logger logger = LogManager.getLogger(this.getClass());

	public static final String ACCOUNT_ID = "ACCOUNT_ID";
	public static final String AUTOPAY_STATUS = "AUTOPAY_STATUS";
	public static final String ENROLLMENT_START_DATE = "ENROLLMENT_START_DATE";
	public static final String LAST_UNENROLLMENT_DATE = "LAST_UNENROLLMENT_DATE";
	public static final String RECURRINGTOPUPCYCLECD_TYPE = "RECURRINGTOPUPCYCLECD_TYPE";
	public static final String AP_CURRENT_DISCOUNT_CODE = "AP_CURRENT_DISCOUNT_CODE";
	public static final String NEXT_AUTOPAY_CYCLE_DATE = "NEXT_AUTOPAY_CYCLE_DATE";
	public static final String AP_DISCOUNT_APPLIED_DATE = "AP_DISCOUNT_APPLIED_DATE";
	public static final String RECURRINGTOPUPCYCLECD_NAME = "RECURRINGTOPUPCYCLECD_NAME";

	@Override
	public AccountAutopayDtls getAutoPayDtls(String accountId) {

		if (logger.isDebugEnabled()) {
			logger.debug("executing " + QUERY_GET_AUTOPAY_DTLS);
		}
		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("accountId", accountId);

		return (AccountAutopayDtls) doQueryForObjectWithNullCheck(QUERY_GET_AUTOPAY_DTLS, inParams,
				new AccountAutopayDtlsRowMapper());

	}

	/**
	 * User: ekxrxax , Date: Sep 14, 2020 2:23:00 PM 2020
	 *
	 * Purpose: mapping db autopay data to bean
	 *
	 * US/D/F Number: US84027
	 *
	 * @param rs
	 * @param rowNum
	 * @return
	 * @throws SQLException
	 */
	public class AccountAutopayDtlsRowMapper implements RowMapper<AccountAutopayDtls> {
		@Override
		public AccountAutopayDtls mapRow(ResultSet rs, int rowNum) throws SQLException {
			AccountAutopayDtls object = new AccountAutopayDtls();
			object.setAccountId(rs.getString(AccountAutopayDtlsDaoImpl.ACCOUNT_ID));
			object.setAutopayStatus(rs.getString(AccountAutopayDtlsDaoImpl.AUTOPAY_STATUS));
			object.setApCurrentdiscountCode(rs.getString(AccountAutopayDtlsDaoImpl.AP_CURRENT_DISCOUNT_CODE));
			object.setEnrollmentStartdDate(
					ApsUtil.getUtilDate(rs.getTimestamp(AccountAutopayDtlsDaoImpl.ENROLLMENT_START_DATE)));

			object.setNextAPCycleDate(
					ApsUtil.getUtilDate(rs.getTimestamp(AccountAutopayDtlsDaoImpl.NEXT_AUTOPAY_CYCLE_DATE)));
			object.setLastUnenrollmentDate(
					ApsUtil.getUtilDate(rs.getTimestamp(AccountAutopayDtlsDaoImpl.LAST_UNENROLLMENT_DATE)));
			object.setRecurringTopupCycleCDType(rs.getString(AccountAutopayDtlsDaoImpl.RECURRINGTOPUPCYCLECD_TYPE));
			object.setRecurringTopupCycleCDValue(rs.getString(AccountAutopayDtlsDaoImpl.RECURRINGTOPUPCYCLECD_NAME));
			object.setApDiscountAppliedDate(
					ApsUtil.getUtilDate(rs.getTimestamp(AccountAutopayDtlsDaoImpl.AP_DISCOUNT_APPLIED_DATE)));
			if (logger.isDebugEnabled()) {
				logger.debug(object.toString());
			}
			return object;
		}

	}

}
