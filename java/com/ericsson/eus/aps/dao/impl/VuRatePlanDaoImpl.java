package com.ericsson.eus.aps.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.IVuRatePlanDao;
import com.ericsson.eus.aps.dao.model.VuRatePlan;

/**
 * 
 * FILE_NAME: VuRatePlanDaoImpl.java
 * 
 * MODULE DESCRIPTION: to fetch rate plan details, US84027
 *
 * @author ekxrxax, Date: Sep 11, 2020 6:37:57 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */

@Repository("vuRatePlanDao")
public class VuRatePlanDaoImpl extends DaoBase implements IVuRatePlanDao {
	private static final String QUERY_GET_RATE_PLAN = "SELECT a.*,b.RECURRINGTOPUPCYCLECD_TYPE from (SELECT * FROM VU_RATE_PLAN vu WHERE vu.RATE_PLAN_ID = :ratePlanId) a inner join agw_rate_plan_cycle_cd_map b on a.rate_plan_id = b.rate_plan_id";
	private final Logger logger = LogManager.getLogger(this.getClass());
	// Column Name

	public static final String RATE_PLAN_ID = "RATE_PLAN_ID";
	public static final String RATE_PLAN_CODE = "RATE_PLAN_CODE";
	public static final String AP_RECURRING_ID = "RECURRINGTOPUPCYCLECD_TYPE";
	public static final String RATE_PLAN_NAME = "RATE_PLAN_NAME";

	/**
	 * Get EBI_CACHE_CONFIG_UPDATE_INFO
	 * 
	 * @param msisdn
	 * @return
	 * @throws DiscountDaoException 
	 * @throws BEADaoException
	 */
	@Override
	@Cacheable("VuRatePlan")
	public VuRatePlan getRatePlan(Long ratePlanId) throws AutopayDaoException
	// throws GatewayDaoException
	{
		if (logger.isDebugEnabled()) {
			logger.debug("executing " + QUERY_GET_RATE_PLAN);
		}
		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("ratePlanId", ratePlanId);
		return (VuRatePlan) doQueryForObject(QUERY_GET_RATE_PLAN, inParams,
				new VuRatePlanRecordRowMapper());
	}
	
	@Override
	@CacheEvict(value = "VuRatePlan", allEntries = true)
	public void updateRatePlan() {
		logger.debug("Clearing the cache for VuRatePlan");
		// Do nothing here , just needs to invalidate the cache
	}
	
	public class VuRatePlanRecordRowMapper implements RowMapper<VuRatePlan> {

		@Override
		public VuRatePlan mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

			VuRatePlan object = new VuRatePlan();
			object.setRatePlanId(resultSet.getLong(VuRatePlanDaoImpl.RATE_PLAN_ID));
			object.setRatePlanCode(resultSet.getString(VuRatePlanDaoImpl.RATE_PLAN_CODE));
			object.setApRecurringId(resultSet.getLong(VuRatePlanDaoImpl.AP_RECURRING_ID));
			object.setRatePlanName(resultSet.getString(VuRatePlanDaoImpl.RATE_PLAN_NAME));
			if (logger.isDebugEnabled()) {
				logger.debug(object.toString());
			}
			return object;
		}

	}

}
