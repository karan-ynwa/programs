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
import com.ericsson.eus.aps.dao.model.AutoPayEventTracker;

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
@Repository("autoPayEventTrackerDao")
public class AutoPayEventTrackerDaoImpl extends DaoBase {
	private static final String QUERY_GET_AUTOPAY_EVENT_TRACKER = "SELECT * FROM EBI_AUTOPAY_EVENT_TRACKER WHERE ACCOUNT_ID = :accountId";
	private final Logger logger = LogManager.getLogger(this.getClass());

	public static final String ACCOUNT_ID = "ACCOUNT_ID";
	public static final String EVENT_TRACKER_CODE = "EVENT_TRACKER_CODE";
	public static final String EVENT_TRACKER_VALUE = "EVENT_TRACKER_VALUE";
	


	/**
	 * User: esuspal , Date: Dec 7, 2020 11:57:43 AM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: List<AutoPayEventTracker>
	 *
	 * @param accountId
	 * @return
	 * @throws AutopayDaoException
	 */
	public List<AutoPayEventTracker> getAutoPayEventTracker(String accountId) throws AutopayDaoException {

		if (logger.isDebugEnabled()) {
			logger.debug("executing " + QUERY_GET_AUTOPAY_EVENT_TRACKER);
		}
		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("accountId", accountId);
		List<AutoPayEventTracker> list = doQuery(QUERY_GET_AUTOPAY_EVENT_TRACKER, inParams,
				new AutoPayEventTrackerRowMapper());
		return list;

	}

	/**
	 * 
	 * FILE_NAME: AutoPayEventTrackerDaoImpl.java
	 * 
	 * MODULE DESCRIPTION: TODO, US/F/D
	 *
	 * @author esuspal, Date: Dec 7, 2020 11:55:45 AM 2020
	 * 
	 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
	 *      Inc.Proprietary.
	 *
	 */
	public class AutoPayEventTrackerRowMapper implements RowMapper<AutoPayEventTracker> {
		@Override
		public AutoPayEventTracker mapRow(ResultSet rs, int rowNum) throws SQLException {
			AutoPayEventTracker object = new AutoPayEventTracker();
			object.setAccountId(rs.getString(AutoPayEventTrackerDaoImpl.ACCOUNT_ID));
			object.setEventTrackerCode(rs.getString(AutoPayEventTrackerDaoImpl.EVENT_TRACKER_CODE));
			object.setEventTrackerValue(rs.getString(AutoPayEventTrackerDaoImpl.EVENT_TRACKER_VALUE));
			if (logger.isDebugEnabled()) {
				logger.debug(object.toString());
			}
			return object;
		}

	}

}
