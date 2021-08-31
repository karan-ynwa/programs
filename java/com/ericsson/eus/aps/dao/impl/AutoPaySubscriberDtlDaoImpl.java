package com.ericsson.eus.aps.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.model.AutoPaySubscriberDtl;
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
@Repository("autoPaySubscriberDtlDaoImpl")
public class AutoPaySubscriberDtlDaoImpl extends DaoBase {

	private static final String QUERY_GET_ACCOUNT = "SELECT " + "    VUS.MSISDN                       AS MSISDN, "
			+ "    VUS.RATE_PLAN_CODE               AS RATE_PLAN_CODE, "
			+ "    VUS.RATE_PLAN_TYPE               AS RATE_PLAN_TYPE, "
			+ "    VUS.RPL_MIRROR_TYPE              AS RPL_MIRROR_TYPE, "
			+ "    VUS.DISCOUNT_CODE                AS DISCOUNT_CODE, "
			+ "    VUS.MLT_LINE_TYPE                AS MLT_LINE_TYPE, "
			+ "    VUS.STATUS                       AS STATUS, "
			+ "    VUS.FEATURE_CODE_LIST            AS FEATURE_CODE_LIST, "
			+ "    VUS.ACCOUNT_ID                   AS ACCOUNT_ID, "
			+ "    VUS.RATE_PLAN_ID                 AS RATE_PLAN_ID, "
			+ "    VUS.ACTIVATE_DATE                AS ACTIVATE_DATE, "
			+ "    VUS.ACCOUNT_NO                   AS ACCOUNT_NO, "
			+ "    VUS.ACCT_RATE_PLAN_EXPIRE_DATE   AS ACCT_RATE_PLAN_EXPIRE_DATE, "
			+ "    VUS.RATE_PLAN_NAME               AS RATE_PLAN_NAME, "
			+ "    VUS.ACCT_MCRP_END_DATE 			AS ACCT_MCRP_END_DATE, "
			+ "    VUS.MCRP_NUM_CYCLES 				AS MCRP_NUM_CYCLES, "
			+ "    VUR.AF_AMOUNT                    AS AF_AMOUNT, "
			+ "    CHRG.FIXED_AMOUNT                AS FIXED_AMOUNT, "
			+ "    RPCM.RECURRINGTOPUPCYCLECD_TYPE   AS RECURRINGTOPUPCYCLECD_TYPE " + "FROM "
			+ "    VU_ACCOUNT_SUBSCRIBER_DTL       VUS, " + "    VU_RATE_PLAN_TIER               VUR, "
			+ "    VU_RATE_PLAN                    VRP, " + "    EBI_ACCOUNT_AP_PROCESS_CHARGE   CHRG, "
			+ "	   AGW_RATE_PLAN_CYCLE_CD_MAP  RPCM  WHERE "
			+ "    VUS.RATE_PLAN_ID = VUR.RATE_PLAN_ID " + "    AND VUS.MSISDN = :msisdn "
			+ "    AND VUR.ACTIVE_TIER_FLAG = 'Y' " + "    AND INIT_ACTIVATION_FLAG = 'Y' "
			+ "    AND VRP.RATE_PLAN_ID = VUR.RATE_PLAN_ID " + "    AND CHRG.ACCOUNT_ID (+) = VUS.ACCOUNT_ID"
			+ "	   AND VRP.RATE_PLAN_ID  = RPCM.rate_plan_id(+)";
	private final Logger logger = LogManager.getLogger(this.getClass());
	// Column Name

	public static final String MSISDN = "MSISDN";
	public static final String RPL_MIRROR_TYPE = "RPL_MIRROR_TYPE";
	public static final String RATE_PLAN_ID = "RATE_PLAN_ID";
	public static final String ACCT_RATE_PLAN_EXPIRE_DATE = "ACCT_RATE_PLAN_EXPIRE_DATE";
	public static final String AF_AMOUNT = "AF_AMOUNT";
	public static final String RATE_PLAN_NAME = "RATE_PLAN_NAME";
	public static final String ACTIVATE_DATE = "ACTIVATE_DATE";
	public static final String DISCOUNT_CODE = "DISCOUNT_CODE";
	public static final String MLT_LINE_TYPE = "MLT_LINE_TYPE";
	public static final String STATUS = "STATUS";
	public static final String FEATURE_CODE_LIST = "FEATURE_CODE_LIST";
	public static final String ACCOUNT_ID = "ACCOUNT_ID";
	public static final String ACCOUNT_NO = "ACCOUNT_NO";
	public static final String FIXED_AMOUNT = "FIXED_AMOUNT";
	public static final String RECURRINGTOPUPCYCLECD_TYPE = "RECURRINGTOPUPCYCLECD_TYPE";
	public static final String RATE_PLAN_CODE = "RATE_PLAN_CODE";
	public static final String RATE_PLAN_TYPE = "RATE_PLAN_TYPE";
	public static final String ACCT_MCRP_END_DATE = "ACCT_MCRP_END_DATE";
	public static final String MCRP_NUM_CYCLES = "MCRP_NUM_CYCLES";

	/**
	 * @param ratePlanCode
	 * @return
	 * @throws AutopayDaoException
	 * @throws AmountDueDaoException
	 */
	public AutoPaySubscriberDtl getAccountDtls(String msisdn) throws AutopayDaoException {
		if (logger.isDebugEnabled()) {
			logger.debug("executing " + QUERY_GET_ACCOUNT);
		}
		MapSqlParameterSource inParamsAp = new MapSqlParameterSource();
		inParamsAp.addValue("msisdn", msisdn);
		return (AutoPaySubscriberDtl) doQueryForObject(QUERY_GET_ACCOUNT, inParamsAp,
				new AutoPaySubscriberDtlRowMapper());
	}

	public class AutoPaySubscriberDtlRowMapper implements RowMapper<AutoPaySubscriberDtl> {

		@Override
		public AutoPaySubscriberDtl mapRow(ResultSet resultSet, int rowNumber) {
			AutoPaySubscriberDtl autoPaySubscriberDtl = new AutoPaySubscriberDtl();
			try {

				autoPaySubscriberDtl.setMsisdn(resultSet.getString(AutoPaySubscriberDtlDaoImpl.MSISDN));
				autoPaySubscriberDtl.setRatePlanCode(resultSet.getString(AutoPaySubscriberDtlDaoImpl.RATE_PLAN_CODE));
				autoPaySubscriberDtl.setDiscountCode(resultSet.getString(AutoPaySubscriberDtlDaoImpl.DISCOUNT_CODE));
				autoPaySubscriberDtl.setRplMirrorType(resultSet.getString(AutoPaySubscriberDtlDaoImpl.RPL_MIRROR_TYPE));
				autoPaySubscriberDtl.setMltLineType(resultSet.getString(AutoPaySubscriberDtlDaoImpl.MLT_LINE_TYPE));
				autoPaySubscriberDtl.setStatus(resultSet.getString(AutoPaySubscriberDtlDaoImpl.STATUS));
				autoPaySubscriberDtl
						.setFeatureCodeList(resultSet.getString(AutoPaySubscriberDtlDaoImpl.FEATURE_CODE_LIST));
				autoPaySubscriberDtl.setAccountId(resultSet.getString(AutoPaySubscriberDtlDaoImpl.ACCOUNT_ID));
				autoPaySubscriberDtl.setRatePlanId(resultSet.getInt(AutoPaySubscriberDtlDaoImpl.RATE_PLAN_ID));
				autoPaySubscriberDtl.setAccountRatePlanExpireDate(ApsUtil
						.getUtilDate(resultSet.getTimestamp(AutoPaySubscriberDtlDaoImpl.ACCT_RATE_PLAN_EXPIRE_DATE)));
				autoPaySubscriberDtl.setAfAmount(resultSet.getString(AF_AMOUNT));
				autoPaySubscriberDtl.setRatePlanName(resultSet.getString(RATE_PLAN_NAME));
				autoPaySubscriberDtl.setActivateDate(
						ApsUtil.getUtilDate(resultSet.getTimestamp(AutoPaySubscriberDtlDaoImpl.ACTIVATE_DATE)));
				autoPaySubscriberDtl.setAccountNumber(resultSet.getString(AutoPaySubscriberDtlDaoImpl.ACCOUNT_NO));
				autoPaySubscriberDtl.setFixedAmount(resultSet.getString(FIXED_AMOUNT));
				autoPaySubscriberDtl.setTopUpCycleCD(resultSet.getString(RECURRINGTOPUPCYCLECD_TYPE));
				autoPaySubscriberDtl.setAcctMCRPEndDate(
						ApsUtil.getUtilDate(resultSet.getTimestamp(AutoPaySubscriberDtlDaoImpl.ACCT_MCRP_END_DATE)));
				autoPaySubscriberDtl.setMcrpNoOfCycles(AutoPaySubscriberDtlDaoImpl.MCRP_NUM_CYCLES);
				if (logger.isDebugEnabled()) {
					logger.debug(autoPaySubscriberDtl.toString());
				}
			} catch (SQLException e) {
				logger.error("Catch SQLException " + e);
				logger.error("Exception Details " + e.fillInStackTrace());
			}
			return autoPaySubscriberDtl;
		}

	}
}
