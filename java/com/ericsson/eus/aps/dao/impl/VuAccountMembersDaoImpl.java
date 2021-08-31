package com.ericsson.eus.aps.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.model.VuAccountMembers;

/**
 * 
 * FILE_NAME: VuAccountMembersDaoImpl.java
 * 
 * MODULE DESCRIPTION: to fetch ML members, US84027
 *
 * @author ekxrxax, Date: Sep 11, 2020 6:33:02 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
@Repository("vuAccountMembersDao")
public class VuAccountMembersDaoImpl extends DaoBase{
	private static final String QUERY_GET_MEMBERS = "SELECT * FROM VU_ACCOUNT_MEMBERS WHERE PRIMARY_MSISDN = :primaryMsisdn  ORDER BY INV_ACCEPTANCE_DATE ASC";
	private final Logger logger = LogManager.getLogger(this.getClass());
	// Column Name

	public static final String PRIMARY_ACCOUNT_ID = "PRIMARY_ACCOUNT_ID";
	public static final String PRIMARY_MSISDN = "PRIMARY_MSISDN";
	public static final String PRIMARY_RATE_PLAN_STATUS = "PRIMARY_RATE_PLAN_STATUS";
	public static final String PRIMARY_RATE_PLAN_CODE = "PRIMARY_RATE_PLAN_CODE";
	public static final String PRIMARY_RATE_PLAN_NAME = "PRIMARY_RATE_PLAN_NAME";
	public static final String PRIMARY_DISCOUNT_CODE = "PRIMARY_DISCOUNT_CODE";

	public static final String PRIMARY_RATE_PLAN_AF_AMOUNT = "PRIMARY_RATE_PLAN_AF_AMOUNT";
	public static final String PRIMARY_RATE_PLAN_EXPIRE_DATE = "PRIMARY_RATE_PLAN_EXPIRE_DATE";
	public static final String MEMBER_MSISDN = "MEMBER_MSISDN";
	public static final String MEMBER_RATE_PLAN_STATUS = "MEMBER_RATE_PLAN_STATUS";
	public static final String MEMBER_RATE_PLAN_CODE = "MEMBER_RATE_PLAN_CODE";
	public static final String MEMBER_RATE_PLAN_NAME = "MEMBER_RATE_PLAN_NAME";

	public static final String MEMBER_RATE_PLAN_EXPIRE_DATE = "MEMBER_RATE_PLAN_EXPIRE_DATE";
	public static final String MEMBER_RATE_PLAN_AF_AMOUNT = "MEMBER_RATE_PLAN_AF_AMOUNT";
	public static final String INV_ACCEPTANCE_DATE = "INV_ACCEPTANCE_DATE";

	public List<VuAccountMembers> getSortedMemberDetails(String primaryMsisdn) throws AutopayDaoException
	{
		if (logger.isDebugEnabled()) {
			logger.debug("executing " + QUERY_GET_MEMBERS);
		}
		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("primaryMsisdn", primaryMsisdn);
		return doQuery(QUERY_GET_MEMBERS, inParams,
				new VuAccountMembersRowMapper());
	}
	

	public class VuAccountMembersRowMapper implements RowMapper<VuAccountMembers> {

		@Override
		public VuAccountMembers mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

			VuAccountMembers object = new VuAccountMembers();
			object.setPrimaryAccountId(resultSet.getLong(VuAccountMembersDaoImpl.PRIMARY_ACCOUNT_ID));
			object.setPrimaryMsisdn(resultSet.getString(VuAccountMembersDaoImpl.PRIMARY_MSISDN));
			object.setPrimaryRatePlanStatus(resultSet.getString(VuAccountMembersDaoImpl.PRIMARY_RATE_PLAN_STATUS));
			object.setPrimaryRatePlanCode(resultSet.getString(VuAccountMembersDaoImpl.PRIMARY_RATE_PLAN_CODE));
			object.setPrimaryRatePlanName(resultSet.getString(VuAccountMembersDaoImpl.PRIMARY_RATE_PLAN_NAME));
			object.setPrimaryDiscountCode(resultSet.getString(VuAccountMembersDaoImpl.PRIMARY_DISCOUNT_CODE));
			object.setPrimaryRatePlanAfAmount(resultSet.getDouble(VuAccountMembersDaoImpl.PRIMARY_RATE_PLAN_AF_AMOUNT));
			object.setPrimaryRatePlanExpireDate(
					resultSet.getDate(VuAccountMembersDaoImpl.PRIMARY_RATE_PLAN_EXPIRE_DATE));
			object.setMemberMsisdn(resultSet.getString(VuAccountMembersDaoImpl.MEMBER_MSISDN));
			object.setMemberRatePlanStatus(resultSet.getString(VuAccountMembersDaoImpl.MEMBER_RATE_PLAN_STATUS));
			object.setMemberRatePlanCode(resultSet.getString(VuAccountMembersDaoImpl.MEMBER_RATE_PLAN_CODE));
			object.setMemberRatePlanName(resultSet.getString(VuAccountMembersDaoImpl.MEMBER_RATE_PLAN_NAME));
			object.setMemberRatePlanExpireDate(resultSet.getDate(VuAccountMembersDaoImpl.MEMBER_RATE_PLAN_EXPIRE_DATE));
			object.setMemberRatePlanAfAmount(resultSet.getDouble(VuAccountMembersDaoImpl.MEMBER_RATE_PLAN_AF_AMOUNT));
			object.setInvAcceptanceDate(resultSet.getDate(VuAccountMembersDaoImpl.INV_ACCEPTANCE_DATE));
			if (logger.isDebugEnabled()) {
				logger.debug(object.toString());
			}
			return object;
		}

	}

}
