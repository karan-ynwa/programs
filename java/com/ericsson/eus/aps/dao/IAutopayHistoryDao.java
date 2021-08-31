/**
 * 
 */
package com.ericsson.eus.aps.dao;

import java.util.List;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.model.AutoPayTransactions;

/**
 * @author ekxrxax
 *
 */
public interface IAutopayHistoryDao {
	public List<AutoPayTransactions> getAutoPayHistory(String msisdn,String startDate,String endDate,String numberOfRecords) throws AutopayDaoException;
}
