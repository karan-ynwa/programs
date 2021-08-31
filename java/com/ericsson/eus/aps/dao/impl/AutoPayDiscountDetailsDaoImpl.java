package com.ericsson.eus.aps.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.IAutoPayDiscountDetailsDao;
import com.ericsson.eus.aps.dao.model.AutoPayDiscountDetails;

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
@Repository("autoPayDiscountDetailsDao")
public class AutoPayDiscountDetailsDaoImpl extends DaoBase implements IAutoPayDiscountDetailsDao {
	private static final String QUERY_GET_DISCOUNT_DETAILS = "SELECT DIS.DISCOUNT_ID AS DISCOUNT_ID,DIS.DISCOUNT_CODE AS DISCOUNT_CODE, RC.ADJ_TYPE, RC.ADJ_CODE "
			+ "FROM DSA_DISCOUNTS DIS, DSA_DISCOUNT_SERVICE_MAP DSM, DSA_RP_BONUS_SERVICES BNS, REASON_CODE RC, DSA_DISCOUNT_SERVICES  DSR "
			+ "WHERE DIS.DISCOUNT_ID = DSM.DISCOUNT_ID " + "AND DSM.DISCOUNT_SERVICE_VALUE = BNS.BONUS_ID "
			+ "AND BNS.REASON_CODE_ID = RC.REASON_CODE_ID  " + "AND DSR.SERVICE_ID = DSM.SERVICE_ID "
			+ "AND DSR.SERVICE_CODE = 'AUTOPAY_PROMO_SERVICE' " + "AND DIS.DISCOUNT_CODE=:discountCode";
	private final Logger logger = LogManager.getLogger(this.getClass());

	public static final String DISCOUNT_ID = "DISCOUNT_ID";
	public static final String DISCOUNT_CODE = "DISCOUNT_CODE";
	public static final String ADJ_TYPE = "ADJ_TYPE";
	public static final String ADJ_CODE = "ADJ_CODE";

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
	@Override
	@Cacheable("AutoPayDiscountDetails")
	public AutoPayDiscountDetails getAutoPayDiscountDetails(String discountCode) throws AutopayDaoException {

		if (logger.isDebugEnabled()) {
			logger.debug("executing " + QUERY_GET_DISCOUNT_DETAILS);
		}
		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("discountCode", discountCode);
		List<AutoPayDiscountDetails> list = doQuery(QUERY_GET_DISCOUNT_DETAILS, inParams,
				new AutoPayDiscountDetailsRowMapper());
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;

	}

	/**
	 * 
	 * FILE_NAME: AutoPayDiscountDetailsRowMapper.java
	 * 
	 * MODULE DESCRIPTION: TODO, US/F/D
	 *
	 * @author esuspal, Date: Dec 7, 2020 11:55:45 AM 2020
	 * 
	 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
	 *      Inc.Proprietary.
	 *
	 */
	public class AutoPayDiscountDetailsRowMapper implements RowMapper<AutoPayDiscountDetails> {
		@Override
		public AutoPayDiscountDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			AutoPayDiscountDetails object = new AutoPayDiscountDetails();
			object.setDiscountId(rs.getString(AutoPayDiscountDetailsDaoImpl.DISCOUNT_ID));
			object.setDiscountCode(rs.getString(AutoPayDiscountDetailsDaoImpl.DISCOUNT_CODE));
			object.setAdjCode(rs.getString(AutoPayDiscountDetailsDaoImpl.ADJ_CODE));
			object.setAdjType(rs.getString(AutoPayDiscountDetailsDaoImpl.ADJ_TYPE));
			if (logger.isDebugEnabled()) {
				logger.debug(object.toString());
			}
			return object;
		}

	}

	/**
	 * User: esuspal , Date: Dec 21, 2020 6:40:05 PM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number: 
	 *
	 */
	@Override
	@CacheEvict(value = "AutoPayDiscountDetails", allEntries = true)
	public void updateAutoPayDiscountDetails() {
		logger.debug("Clearing the cache for AutoPayDiscountDetails");
	}

}
