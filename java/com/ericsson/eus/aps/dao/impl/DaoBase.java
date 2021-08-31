package com.ericsson.eus.aps.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.ericsson.ebi.alarms.constants.AlarmConstants;
import com.ericsson.ebi.alarms.util.Alarms;
import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.impl.AutopayHistoryDaoImpl.AutoPayTransactionsRowMapper;
import com.ericsson.eus.aps.dao.model.AutoPayTransactions;
/**
 * 
 * FILE_NAME: DaoBase.java
 * 

 * MODULE DESCRIPTION: US84027
 *
 * @author ekxrxax, Date: Sep 11, 2020 3:44:31 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public abstract class DaoBase {

	@Autowired
	@Lazy
	private Alarms alarms;

	@Autowired
	@Lazy
	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final Log LOG = LogFactory.getLog(DaoBase.class);

	public void clearDBAlarm() {
		if (null!=Alarms.getInstance()) {
			Alarms.getInstance().clear(AlarmConstants.DB_UNAVAILABLE, 1);
		}
	}

	public void raiseDBAlarm(DataAccessException ex) {
			if (alarms != null && ex != null && (ex instanceof DataAccessResourceFailureException
					|| ex instanceof RecoverableDataAccessException || ex instanceof DataAccessException)) {
				Alarms.getInstance().raise(AlarmConstants.DB_UNAVAILABLE, 1);
			}
	}

	/**
	 * @return the jdbcTemplate
	 */
	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void handleError(DataAccessException e) throws AutopayDaoException {
		LOG.error(e);
		raiseDBAlarm(e);
		throw new AutopayDaoException(e.getMessage());
	}

	public <T> Object doQueryForObject(String query, MapSqlParameterSource params, RowMapper<T> rowMapper)
			throws AutopayDaoException {
		Object obj = null;
		try {
			obj = getJdbcTemplate().queryForObject(query, params, rowMapper);
			clearDBAlarm();
		} catch (DataAccessException e) {
			handleError(e);
		}
		return obj;

	}

	public <T> List<T> doQuery(String query, MapSqlParameterSource params, RowMapper<T> rowMapper)
			throws AutopayDaoException {
		List<T> list = null;
		try {
			list = getJdbcTemplate().query(query, params, rowMapper);
			clearDBAlarm();
		} catch (DataAccessException e) {
			handleError(e);
		}
		return list;

	}

	public void updateQuery(String query, MapSqlParameterSource params) throws AutopayDaoException {
		try {
			getJdbcTemplate().update(query, params);
			clearDBAlarm();
		} catch (DataAccessException e) {
			handleError(e);
		}
	}
	public <T> Object doQueryForObjectWithNullCheck(String query, MapSqlParameterSource params, RowMapper<T> rowMapper)
	{
		Object obj = null;
		try {
			obj = getJdbcTemplate().queryForObject(query, params, rowMapper);
			clearDBAlarm();
		} catch (DataAccessException e) {
			LOG.error(e);
			return null;
		}
		return obj;

	}
}
