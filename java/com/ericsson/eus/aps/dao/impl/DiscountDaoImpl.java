package com.ericsson.eus.aps.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.IDiscountDao;
import com.ericsson.eus.aps.dao.model.DsaDiscounts;
import com.ericsson.eus.aps.dao.model.VuAutopayCriteria;
import com.ericsson.eus.aps.dao.model.VuDsaEvaluations;
import com.ericsson.eus.aps.rest.common.RESTConstants.ApsConstants;
import com.ericsson.eus.cef.common.CefConstants;

/**
 * 
 * FILE_NAME: DiscountDaoImpl.java
 * 

 * MODULE DESCRIPTION: to fetch discount data, US84027
 *
 * @author ekxrxax, Date: Sep 11, 2020 3:44:31 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
@Repository("discountDao")
public class DiscountDaoImpl extends DaoBase implements IDiscountDao {
	private static final String QUERY_GET_DISCOUNT_MAP = "SELECT  DISCOUNT_ID,"
			+ "DISCOUNT_CODE, DISCOUNT_END_DATE, DISCOUNT_NAME,"
			+ " DISCOUNT_START_DATE, DISCOUNT_TYPE, CRITERIA_EVAL_ID, CRITERIA_KEY, CRITERIA_VALUE, SERVICE_ID, SERVICE_CODE, "
			+ " OFFER_ID, OFFER_CODE, OFFER_UNITS,BONUS_UNITS,BONUS_CODE,BONUS_UNIT_TYPE,BONUS_SERVICE_TYPE FROM VU_DSA_EVALUATIONS WHERE DISCOUNT_CODE = :discountCode";
	private static final String QUERY_DISCOUNT_MAP_STANDARD = "SELECT * FROM DSA_DISCOUNTS WHERE DISCOUNT_TYPE = :discountType"; // F1214 : US11465
	private static final String QUERY_DISCOUNT_RATE_PLAN_ID = "select dsa.* from VU_DSA_EVALUATIONS dsa, CEF_CRITERIA_EVALUATIONS ctreval where ctreval.CRITERIA_EVAL_ID in("+
			" select ctreval.CRITERIA_EVAL_ID from CEF_CRITERIA_EVALUATIONS ctreval,CEF_CRITERIA_KEY_LISTS keylist  where  TO_CHAR(keylist.KEY_LIST_ID) = ctreval.CRITERIA_VALUE"+ 
			" and keylist.attribute_value=:ratePlanId and  ctreval.CRITERIA_KEY='RATE_PLAN_CODE_LIST' and  ctreval.CRITERIA_KEY = keylist.CRITERIA_KEY)"+ 
			" and dsa.CRITERIA_KEY = ctreval.CRITERIA_KEY and dsa.CRITERIA_EVAL_ID=ctreval.CRITERIA_EVAL_ID and DISCOUNT_TYPE in('STANDARD_AUTOPAY', 'ENHANCED_AUTOPAY') AND dsa.criteria_key ='RATE_PLAN_CODE_LIST'";
	private static final String QUERY_AUTO_PAY_DISCOUNT = "SELECT * FROM DSA_DISCOUNTS WHERE DISCOUNT_TYPE = :discountType";
	private final Logger logger = LogManager.getLogger(DiscountDaoImpl.class);
	private static final String QUERY_GET_AUTOPAY_CRITERIA= "SELECT * FROM VU_AUTOPAY_CRITERIA where DISCOUNT_CODE= :discountCode";
	// Column Name
	
	private static final String GET_DISCOUNT_CODE = "SELECT t.discount_code FROM dsa_discounts t, ebi_autopay_discounts o "
			+ "WHERE o.discount_id = t.discount_id AND t.discount_code in (:discountCodeList) ORDER BY ";
	private static final String ORG_AP_ENROLLMENT_START_DATE = "o.original_autopay_enrollment_start_date ";
	private static final String ACTIVATION_DTAE_MIN ="o.activation_date_min ";
	private static final String EARLIER_DATE = "FETCH FIRST 1 ROWS ONLY ";

		public static final String DISCOUNT_ID = "DISCOUNT_ID";
	public static final String DISCOUNT_CODE = "DISCOUNT_CODE";
	public static final String DISCOUNT_END_DATE = "DISCOUNT_END_DATE";
	public static final String DISCOUNT_NAME = "DISCOUNT_NAME";
	public static final String DISCOUNT_START_DATE = "DISCOUNT_START_DATE";
	public static final String DISCOUNT_TYPE = "DISCOUNT_TYPE";
	public static final String CRITERIA_EVAL_ID = "CRITERIA_EVAL_ID";
	public static final String CRITERIA_KEY = "CRITERIA_KEY";
	public static final String CRITERIA_VALUE = "CRITERIA_VALUE";
	public static final String SERVICE_ID = "SERVICE_ID";
	public static final String SERVICE_CODE = "SERVICE_CODE";
	public static final String OFFER_ID = "OFFER_ID";
	public static final String OFFER_CODE = "OFFER_CODE";
	public static final String OFFER_UNITS = "OFFER_UNITS";
	public static final String BONUS_CODE = "BONUS_CODE";
	public static final String BONUS_UNITS = "BONUS_UNITS";
	public static final String BONUS_UNIT_TYPE = "BONUS_UNIT_TYPE";
	public static final String BONUS_SERVICE_TYPE = "BONUS_SERVICE_TYPE";

	private static final String DISCOUNT_TYPE_PARAM = "discountType";
	private static final String ACTIVATION_DATE_MAX="ACTIVATION_DATE_MAX";
	private static final String ACTIVATION_DATE_MIN="ACTIVATION_DATE_MIN";
	private static final String ORIGINAL_AUTOPAY_ENROLLMENT_END_DATE="ORIGINAL_AUTOPAY_ENROLLMENT_END_DATE";
	private static final String ORIGINAL_AUTOPAY_ENROLLMENT_START_DATE="ORIGINAL_AUTOPAY_ENROLLMENT_START_DATE";
	
	private static final String DISCOUNT_CODE_LIST = "discountCodeList";

	/**
	 * @throws DiscountDaoException 
	 * @throws AutopayDaoException 
	 */
	@Override 
	@Cacheable("VuDsaEvaluations")
	public List<VuDsaEvaluations> getDiscount(String discountCode) throws AutopayDaoException
	// throws GatewayDaoException
	{
		if (logger.isDebugEnabled()) {
			logger.debug("executing " + QUERY_GET_DISCOUNT_MAP);
		}

		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("discountCode", discountCode);
		return doQuery(QUERY_GET_DISCOUNT_MAP, inParams, new VuDsaEvaluationsRowMapper());
	}
	
	@Override
	@Cacheable(value="DiscountByRatePlanId",key="#ratePlanId")
	public List<VuDsaEvaluations> getDiscountByRatePlanId(long ratePlanId) throws AutopayDaoException{
		if (logger.isDebugEnabled()) {
			logger.debug("Executing Query " + QUERY_DISCOUNT_RATE_PLAN_ID);
		}
		
		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("ratePlanId", String.valueOf(ratePlanId));
		return doQuery(QUERY_DISCOUNT_RATE_PLAN_ID, inParams, new VuDsaEvaluationsRowMapper());

	}

	@Override
	@Cacheable(value="VuAutopayCriteria")
	public List<VuAutopayCriteria> getAutopayCriterias(String discountCode) throws AutopayDaoException{
		if (logger.isDebugEnabled()) {
			logger.debug("executing " + QUERY_GET_AUTOPAY_CRITERIA+" discountCode "+discountCode);
		}
		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("discountCode", discountCode);
		return doQuery(QUERY_GET_AUTOPAY_CRITERIA,inParams,new VuAutopayCriteriaRowMapper() );
	}
	public class VuAutopayCriteriaRowMapper implements RowMapper<VuAutopayCriteria>{

		/**
		 * User: ekxrxax , Date: Feb 8, 2021 7:03:46 PM 2021
		 *
		 * Purpose: map vu autopay criteria
		 *
		 * US/D/F Number:US96224
		 *
		 * @param rs
		 * @param rowNum
		 * @return
		 * @throws SQLException
		 */
		@Override
		public VuAutopayCriteria mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
			VuAutopayCriteria object= new VuAutopayCriteria();
			object.setDiscountId(resultSet.getInt(DISCOUNT_ID));
			object.setDiscountCode(resultSet.getString(DISCOUNT_CODE));
			object.setCriteriaKey(resultSet.getString(CRITERIA_KEY));
			object.setCriteriaValue(resultSet.getString(CRITERIA_VALUE));
			object.setActivationDateMax(resultSet.getString(ACTIVATION_DATE_MAX));
			object.setActivationDateMin(resultSet.getString(ACTIVATION_DATE_MIN));
			object.setOriginalAutopayEnrollmentEndDate(resultSet.getString(ORIGINAL_AUTOPAY_ENROLLMENT_END_DATE));
			object.setOriginalAutopayEnrollmentstartDate(resultSet.getString(ORIGINAL_AUTOPAY_ENROLLMENT_START_DATE));
			return object;
		}
		
	}
	
	@Override
	@CacheEvict(value= "DiscountByRatePlanId", allEntries = true)
	public void updateDiscountByRatePlanId() {
		logger.debug("Clearing the cache for DiscountByRatePlanId");
		// Do nothing here , just needs to invalidate the cache
	}

	public class VuDsaEvaluationsRowMapper implements RowMapper<VuDsaEvaluations> {

		@Override
		public VuDsaEvaluations mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

			VuDsaEvaluations object = new VuDsaEvaluations();
			object.setDiscountId(resultSet.getInt(DISCOUNT_ID));
			object.setDiscountCode(resultSet.getString(DISCOUNT_CODE));
			object.setDiscountStartDate(resultSet.getDate(DISCOUNT_START_DATE));
			object.setDiscountEndDate(resultSet.getDate(DISCOUNT_END_DATE));
			object.setDiscountName(resultSet.getString(DISCOUNT_NAME));
			object.setDiscountType(resultSet.getString(DISCOUNT_TYPE));
			object.setOfferCode(resultSet.getString(OFFER_CODE));
			object.setOfferId(resultSet.getInt(OFFER_ID));
			object.setServiceCode(resultSet.getString(SERVICE_CODE));
			object.setOfferId(resultSet.getInt(OFFER_ID));
			object.setCriteriaEvalId(resultSet.getInt(CRITERIA_EVAL_ID));
			object.setCriteriaKey(resultSet.getString(CRITERIA_KEY));
			object.setCriteriaValue(resultSet.getString(CRITERIA_VALUE));
			object.setOfferUnit(resultSet.getInt(OFFER_UNITS));
			object.setBonusCode(resultSet.getString(BONUS_CODE));
			object.setBonusServiceType(resultSet.getString(BONUS_SERVICE_TYPE));
			object.setBonusUnits(resultSet.getInt(BONUS_UNITS));
			object.setBonusUnitsType(resultSet.getString(BONUS_UNIT_TYPE));
			if (logger.isDebugEnabled()) {
				logger.debug(object.toString());
			}
			return object;
		}

	}
	
	/**
	 * @throws AutopayDaoException 
	 * @throws DiscountDaoException 
	 */
	@Override 
	@Cacheable("DsaDiscounts")
	public List<DsaDiscounts> getStandardDiscount() throws AutopayDaoException 
	// throws GatewayDaoException
	{
		if (logger.isDebugEnabled()) {
			logger.debug("executing getStandardDiscount" + QUERY_DISCOUNT_MAP_STANDARD);
		}

		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue(DISCOUNT_TYPE_PARAM, CefConstants.STANDARD_AUTOPAY);
		return doQuery(QUERY_DISCOUNT_MAP_STANDARD, inParams, new DsaDiscountsRowMapper());
	}
	
	@Override
	@CacheEvict(value= "VuDsaEvaluations", allEntries = true)
	public void updateDiscount() {
		logger.debug("Clearing the cache for DsaDiscounts");
		// Do nothing here , just needs to invalidate the cache
	}
	
	@Override
	@CacheEvict(value= "DsaDiscounts", allEntries = true)
	public void updateStandardDiscount() {
		logger.debug("Clearing the cache for DsaStansardDiscounts");
		// Do nothing here , just needs to invalidate the cache
	}
	

	@Override
	@Cacheable("ApInfoEnhancedDsaDiscounts")
	public List<DsaDiscounts> getAutoPayEnhancedDiscount() throws AutopayDaoException {
		if (logger.isDebugEnabled()) {
			logger.debug("executing getAutoPayEnhancedDiscount" + QUERY_AUTO_PAY_DISCOUNT);
		}

		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue(DISCOUNT_TYPE_PARAM, CefConstants.ENHANCED_AUTOPAY);
		return doQuery(QUERY_AUTO_PAY_DISCOUNT, inParams, new DsaDiscountsRowMapper());
	}
	
	@Override
	@CacheEvict(value = "ApInfoEnhancedDsaDiscounts", allEntries = true)
	public void updateAutoPayEnhancedDiscount() {
		logger.debug("Clearing the cache for AutoPayEnhancedDiscount");
		// Do nothing here , just needs to invalidate the cache
	}

	public class DsaDiscountsRowMapper implements RowMapper<DsaDiscounts> {

		@Override
		public DsaDiscounts mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

			DsaDiscounts object = new DsaDiscounts();
			object.setDiscountId(resultSet.getInt(DISCOUNT_ID));
			object.setDiscountCode(resultSet.getString(DISCOUNT_CODE));
			object.setDiscountStartDate(resultSet.getDate(DISCOUNT_START_DATE));
			object.setDiscountEndDate(resultSet.getDate(DISCOUNT_END_DATE));
			object.setDiscountName(resultSet.getString(DISCOUNT_NAME));
			object.setDiscountType(resultSet.getString(DISCOUNT_TYPE));
			object.setCriteriaEvalId(resultSet.getInt(CRITERIA_EVAL_ID));
			if (logger.isDebugEnabled()) {
				logger.debug(object.toString());
			}
			return object;
		}

	}
	
	/**
	 * This method is used to fetch the earlier date discount code records i.e : standard/enhanced
	 * from eligible rate plan lists.
	 */
	@Override
	@Cacheable("EarlierDateDisCountCode")
	public String getEarlierDateDisCdFrmMulplePromos(List<String> discountCodeList, String discountType)
			throws AutopayDaoException {
		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue(DISCOUNT_CODE_LIST, discountCodeList);
		List<String> list = null;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(GET_DISCOUNT_CODE);
		if (StringUtils.equalsIgnoreCase(ApsConstants.ENHANCED, discountType)) {
			stringBuilder.append(ORG_AP_ENROLLMENT_START_DATE).append(EARLIER_DATE);
			logger.debug("Enhanced Query "+stringBuilder.toString() + " To Fetch earlier original_autopay_enrollment_start_date one enhanced ");
			list = doQuery(stringBuilder.toString(), inParams,(resultSet, rownumber) -> resultSet.getString(1));
		} else if (StringUtils.equalsIgnoreCase(ApsConstants.STANDARD, discountType)) {
			stringBuilder.append(ACTIVATION_DTAE_MIN).append(EARLIER_DATE);
			logger.debug("Standard Query "+stringBuilder.toString() +"  To Fetch earlier activation_date_min one standard ");
			list = doQuery(stringBuilder.toString(), inParams,(resultSet, rownumber) -> resultSet.getString(1));
		}
		String discountCode = null;
		if (list != null && !list.isEmpty()) {
			discountCode = list.get(0);
			if (logger.isDebugEnabled()) {
				logger.debug("discount code from multiple discount code list: " + discountCode);
			}
		}
		return discountCode;
	}

	@Override
	@CacheEvict(value = "EarlierDateDisCountCode", allEntries = true)
	public void updateEarlierDateDisCountCode() {
		logger.debug("Clearing the cache for EarlierDateDisCountCode");
		// Do nothing here , just needs to invalidate the cache
	}
	
}
