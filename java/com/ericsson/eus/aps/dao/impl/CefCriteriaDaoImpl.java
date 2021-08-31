package com.ericsson.eus.aps.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.ICefCriteriaDao;
import com.ericsson.eus.aps.dao.model.CefCriteriaEvaluations;
import com.ericsson.eus.aps.dao.model.CefCriteriaKeyLists;

/**
 * 
 * FILE_NAME: CefCriteriaDaoImpl.java
 * 

 * MODULE DESCRIPTION: to fetch cef criterias, US84027
 *
 * @author ekxrxax, Date: Sep 11, 2020 3:44:31 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
@Repository("cefCriteriaDao")
public class CefCriteriaDaoImpl extends DaoBase implements ICefCriteriaDao {
	private static final String QUERY_CEF_CRITERIA_EVALUATIONS = "SELECT * FROM CEF_CRITERIA_EVALUATIONS WHERE"
			+ " CRITERIA_EVAL_ID = :criteriaEvalId";
	
	private static final String QUERY_CEF_CRITERIA_KEY_LISTS = "SELECT * FROM CEF_CRITERIA_KEY_LISTS WHERE CRITERIA_KEY = 'RATE_PLAN_CODE_LIST' "
			+ " AND KEY_LIST_ID = :keyListId";
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	// Column Name

	public static final String CRITERIA_EVAL_ID = "CRITERIA_EVAL_ID";
	public static final String CRITERIA_KEY = "CRITERIA_KEY";
	public static final String CRITERIA_VALUE = "CRITERIA_VALUE";
	public static final String CRITERIA_EVAL_DESCR = "DESCR";
	
	public static final String KEY_LIST_ID = "KEY_LIST_ID";
	public static final String CRITERIA_KEY_LIST_KEY = "CRITERIA_KEY";
	public static final String ATTRIBUTE_NAME = "ATTRIBUTE_NAME";
	public static final String ATTRIBUTE_VALUE = "ATTRIBUTE_VALUE";
	public static final String CRITERIA_KEY_LIST_DESCR = "DESCR";

	@Override 
	@Cacheable("CefCriteriaEvaluations")
	public List<CefCriteriaEvaluations> getCriteriaEvalId(Integer criteriaEvalId) throws AutopayDaoException {

		if (logger.isDebugEnabled()) {
			logger.debug("Executing query " + QUERY_CEF_CRITERIA_EVALUATIONS);
		}

		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("criteriaEvalId", criteriaEvalId);
		return doQuery(QUERY_CEF_CRITERIA_EVALUATIONS, inParams, new CefCriteriaEvaluationsRowMapper());

	}
	
	@Override
	@CacheEvict(value = "CefCriteriaEvaluations", allEntries = true)
	public void updateCriteriaEvalId() {
		logger.debug("Clearing the cache for CefCriteriaEvaluations");
	}
	
	@Override
	@Cacheable("CefCriteriaEvalutionsKey")
	public List<CefCriteriaKeyLists> getCefCriteriaKeyLists(Integer keyListId) throws  AutopayDaoException{
		if (logger.isDebugEnabled()) {
			logger.debug("executing " + QUERY_CEF_CRITERIA_KEY_LISTS);
		}

		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("keyListId", keyListId);
		return doQuery(QUERY_CEF_CRITERIA_KEY_LISTS, inParams, new CefCriteriaKeyListsRowMapper());
	}
	
	@Override
	@CacheEvict(value = "CefCriteriaEvalutionsKey", allEntries = true)
	public void updateCefCriteriaKeyLists() {
		logger.debug("Clearing the cache for CefCriteriaEvalutionsKey");
	}
	
	public class CefCriteriaEvaluationsRowMapper implements RowMapper<CefCriteriaEvaluations> {

		@Override
		public CefCriteriaEvaluations mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

			CefCriteriaEvaluations object = new CefCriteriaEvaluations();
			object.setCriteriaEvalId(resultSet.getInt(CRITERIA_EVAL_ID));
			object.setCriteriaKey(resultSet.getString(CRITERIA_KEY));
			object.setCriteriaValue(resultSet.getString(CRITERIA_VALUE));
			object.setDescr(resultSet.getString(CRITERIA_EVAL_DESCR));

			if (logger.isDebugEnabled()) {
				logger.debug(object.toString());
			}
			return object;
		}

	}
	
	public class CefCriteriaKeyListsRowMapper implements RowMapper<CefCriteriaKeyLists> {

		@Override
		public CefCriteriaKeyLists mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
			
			CefCriteriaKeyLists object = new CefCriteriaKeyLists();
			object.setKeyListId(resultSet.getInt(KEY_LIST_ID));
			object.setCriteriaKey(resultSet.getString(CRITERIA_KEY_LIST_KEY));
			object.setAttributeName(resultSet.getString(ATTRIBUTE_NAME));
			object.setAttributeValue(resultSet.getString(ATTRIBUTE_VALUE));
			object.setDescr(resultSet.getString(CRITERIA_KEY_LIST_DESCR));

			if (logger.isDebugEnabled()) {
				logger.debug(object.toString());
			}
			return object;
		}

	}

}
