package com.ericsson.eus.aps.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.IAutopayHistoryDao;
import com.ericsson.eus.aps.dao.model.AutoPayTransactions;

@Repository("autopayHistoryDao")
public class AutopayHistoryDaoImpl extends DaoBase implements IAutopayHistoryDao {
	private static final String QUERY_GET_AP_HISTORY = "select * from (SELECT ap.PDOF_LAST4, ap.RECURRINGTOPUPCYCLECD_TYPE, ap.COMMENTS, ap.MODIFIED_BY, ap.TRANSACTION_TYPE, TO_CHAR(ap.TRANSACTION_TIME_STAMP,'dd-MON-yyyy HH:MI:SS') as TRANSACTION_TIME_STAMP, ap.ACTIVATION_DATE, ap.ENROLLMENT_DATE, ap.ENROLLMENT_CHANNEL, ap.UNENROLLMENT_DATE, ap.RATE_PLAN_CODE, ap.PROMOTION_AMOUNT, ap.NEXT_AUTOPAY_CYCLE_DATE FROM VU_TRANSACTION ap WHERE MSISDN = :msisdn AND TRANSACTION_AUTOPAY_ID is not null AND ap.TRANSACTION_TIME_STAMP >= :startDate AND ap.TRANSACTION_TIME_STAMP < :endDate ORDER BY ap.TRANSACTION_TIME_STAMP desc ) where  rownum <= :numberOfRecords";
	private final Logger logger = LogManager.getLogger(this.getClass());
	public static final String PDOF_LAST4 = "PDOF_LAST4";
	public static final String RECURRINGTOPUPCYCLECD_TYPE = "RECURRINGTOPUPCYCLECD_TYPE";
	public static final String COMMENTS = "COMMENTS";
	public static final String MODIFIED_BY = "MODIFIED_BY";
	public static final String TRANSACTION_TYPE = "TRANSACTION_TYPE";
	public static final String TRANSACTION_TIME_STAMP = "TRANSACTION_TIME_STAMP";
	public static final String ACTIVATION_DATE = "ACTIVATION_DATE";
	public static final String ENROLLMENT_DATE = "ENROLLMENT_DATE";
	public static final String ENROLLMENT_CHANNEL = "ENROLLMENT_CHANNEL";
	public static final String UNENROLLMENT_DATE = "UNENROLLMENT_DATE";
	public static final String RATE_PLAN_CODE = "RATE_PLAN_CODE";
	public static final String PROMOTION_AMOUNT = "PROMOTION_AMOUNT";
	public static final String NEXT_AUTOPAY_CYCLE_DATE = "NEXT_AUTOPAY_CYCLE_DATE";

	SimpleDateFormat dateFormator = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public List<AutoPayTransactions> getAutoPayHistory(String msisdn,String startDate,String endDate,String numberOfRecords) throws AutopayDaoException {
		if (logger.isDebugEnabled()) {
			logger.debug("executing " + QUERY_GET_AP_HISTORY);
		}
		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("msisdn",msisdn);
		inParams.addValue("startDate", startDate);
		inParams.addValue("endDate", endDate);
		inParams.addValue("numberOfRecords", numberOfRecords);
		return doQuery(QUERY_GET_AP_HISTORY,inParams,new AutoPayTransactionsRowMapper());		
	}

	public class AutoPayTransactionsRowMapper implements RowMapper<AutoPayTransactions>	{

		@Override
		public AutoPayTransactions mapRow(ResultSet rs, int rowNum) throws SQLException {
			AutoPayTransactions object= new AutoPayTransactions();
			object.setPdofLast4(rs.getString(AutopayHistoryDaoImpl.PDOF_LAST4));
			object.setRecurringTopupCycleCode(rs.getInt(AutopayHistoryDaoImpl.RECURRINGTOPUPCYCLECD_TYPE));
			object.setComments(rs.getString(AutopayHistoryDaoImpl.COMMENTS));
			object.setModifiedBy(rs.getString(AutopayHistoryDaoImpl.MODIFIED_BY));
			object.setTransactionType(rs.getString(AutopayHistoryDaoImpl.TRANSACTION_TYPE));
			object.setTransactionTimeStamp(rs.getString(AutopayHistoryDaoImpl.TRANSACTION_TIME_STAMP));
			object.setActivationdate(dateConvert(rs.getDate(AutopayHistoryDaoImpl.ACTIVATION_DATE)));
			object.setEnrollmentdate(dateConvert(rs.getDate(AutopayHistoryDaoImpl.ENROLLMENT_DATE)));
			object.setEnrollmentchannel(rs.getString(AutopayHistoryDaoImpl.ENROLLMENT_CHANNEL));
			object.setUnenrollmentdate(dateConvert(rs.getDate(AutopayHistoryDaoImpl.UNENROLLMENT_DATE)));
			object.setRateplancode(rs.getString(AutopayHistoryDaoImpl.RATE_PLAN_CODE));
			object.setPromotionamount(rs.getDouble(AutopayHistoryDaoImpl.PROMOTION_AMOUNT));
			object.setNextautopaycycledate(dateConvert(rs.getDate(AutopayHistoryDaoImpl.NEXT_AUTOPAY_CYCLE_DATE)));
			return object;
		}

		/**
		 * User: eihsnad , Date: Mar 2, 2021 12:34:25 PM 2021
		 *
		 * Purpose: Convert date to specific
		 *
		 * US/D/F Number:
		 * 
		 * Return Type: String
		 *
		 * @param date
		 * @return
		 */
		private String dateConvert(Date date) {
			if (date != null)
				return dateFormator.format(date);
			return null;
		}
	}
}
