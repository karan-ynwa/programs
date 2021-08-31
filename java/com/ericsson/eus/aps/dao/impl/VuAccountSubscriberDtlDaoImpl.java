package com.ericsson.eus.aps.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.model.VuAccountSubscriberDtl;
import com.ericsson.eus.aps.service.util.ApsUtil;

/**
 * 
 * FILE_NAME: VuAccountSubscriberDtlDaoImpl.java
 * 
 * MODULE DESCRIPTION: to fetch subscriber details, US84027
 *
 * @author ekxrxax, Date: Sep 11, 2020 6:37:57 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
@Repository("vuAccountSubscriberDtlDao")
public class VuAccountSubscriberDtlDaoImpl extends DaoBase {

	private static final String QUERY_GET_ACCOUNT = "SELECT * FROM VU_ACCOUNT_SUBSCRIBER_DTL VUS, VU_RATE_PLAN_TIER VUR WHERE VUS.RATE_PLAN_ID = VUR.RATE_PLAN_ID AND VUS.MSISDN = :msisdn AND VUR.ACTIVE_TIER_FLAG = 'Y' AND INIT_ACTIVATION_FLAG = 'Y'";
	private final Logger logger = LogManager.getLogger(this.getClass());
	// Column Name

	public static final String MSISDN = "MSISDN";
	public static final String RATE_PLAN_CODE = "RATE_PLAN_CODE";
	public static final String RATE_PLAN_TYPE = "RATE_PLAN_TYPE";
	public static final String RPL_MIRROR_TYPE = "RPL_MIRROR_TYPE";
	public static final String DISCOUNT_CODE = "DISCOUNT_CODE";
	public static final String MLT_LINE_TYPE = "MLT_LINE_TYPE";
	public static final String STATUS = "STATUS";
	public static final String FEATURE_CODE_LIST = "FEATURE_CODE_LIST";
	public static final String ACCOUNT_ID = "ACCOUNT_ID";
	public static final String RATE_PLAN_ID = "RATE_PLAN_ID";
	public static final String ACCT_RATE_PLAN_EXPIRE_DATE = "ACCT_RATE_PLAN_EXPIRE_DATE";
	public static final String AF_AMOUNT = "AF_AMOUNT";
	public static final String RATE_PLAN_NAME = "RATE_PLAN_NAME";
	public static final String ACTIVATE_DATE = "ACTIVATE_DATE";

	public static final String ACCOUNT_NO = "ACCOUNT_NO";
	// ATTPRE-96159
	public static final String MCRP_NUM_CYCLES = "MCRP_NUM_CYCLES";
	public static final String ACCT_MCRP_END_DATE = "ACCT_MCRP_END_DATE";

	/**
	 * @param ratePlanCode
	 * @return
	 * @throws AutopayDaoException
	 * @throws AmountDueDaoException
	 */
	public VuAccountSubscriberDtl getAccountDtls(String msisdn) throws AutopayDaoException {
		if (logger.isDebugEnabled()) {
			logger.debug("executing " + QUERY_GET_ACCOUNT);
		}
		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("msisdn", msisdn);
		return (VuAccountSubscriberDtl) doQueryForObject(QUERY_GET_ACCOUNT, inParams,
				new VuAccountSubscriberDtlRowMapper());
	}

	public class VuAccountSubscriberDtlRowMapper implements RowMapper<VuAccountSubscriberDtl> {

		@Override
		public VuAccountSubscriberDtl mapRow(ResultSet resultSet, int rowNumber) {
			VuAccountSubscriberDtl object = new VuAccountSubscriberDtl();
			try {

				object.setMsisdn(resultSet.getString(VuAccountSubscriberDtlDaoImpl.MSISDN));
				object.setRatePlanCode(resultSet.getString(VuAccountSubscriberDtlDaoImpl.RATE_PLAN_CODE));
				object.setDiscountCode(resultSet.getString(VuAccountSubscriberDtlDaoImpl.DISCOUNT_CODE));
				object.setRplMirrorType(resultSet.getString(VuAccountSubscriberDtlDaoImpl.RPL_MIRROR_TYPE));
				object.setMltLineType(resultSet.getString(VuAccountSubscriberDtlDaoImpl.MLT_LINE_TYPE));
				object.setStatus(resultSet.getString(VuAccountSubscriberDtlDaoImpl.STATUS));
				object.setFeatureCodeList(resultSet.getString(VuAccountSubscriberDtlDaoImpl.FEATURE_CODE_LIST));
				object.setAccountId(resultSet.getString(VuAccountSubscriberDtlDaoImpl.ACCOUNT_ID));
				object.setRatePlanId(resultSet.getInt(VuAccountSubscriberDtlDaoImpl.RATE_PLAN_ID));
				object.setAccountRatePlanExpireDate(ApsUtil
						.getUtilDate(resultSet.getDate(VuAccountSubscriberDtlDaoImpl.ACCT_RATE_PLAN_EXPIRE_DATE)));
				object.setAfAmount(resultSet.getString(AF_AMOUNT));
				object.setRatePlanName(resultSet.getString(RATE_PLAN_NAME));
				object.setActivateDate(
						ApsUtil.getUtilDate(resultSet.getDate(VuAccountSubscriberDtlDaoImpl.ACTIVATE_DATE)));
				object.setAccountNumber(resultSet.getString(VuAccountSubscriberDtlDaoImpl.ACCOUNT_NO));
				// ATTPRE-96159
				object.setMcrpNumCycles(resultSet.getInt(VuAccountSubscriberDtlDaoImpl.MCRP_NUM_CYCLES));
				object.setAcctMCRPEndDate(resultSet.getDate(VuAccountSubscriberDtlDaoImpl.ACCT_MCRP_END_DATE));
				if (logger.isDebugEnabled()) {
					logger.debug(object.toString());
				}
			} catch (SQLException e) {
				logger.error("Catch SQLException " + e);
				logger.error("Exception Details " + e.fillInStackTrace());
			}
			return object;
		}

	}
}
