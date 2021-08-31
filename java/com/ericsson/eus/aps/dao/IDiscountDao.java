package com.ericsson.eus.aps.dao;

import java.util.List;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.model.DsaDiscounts;
import com.ericsson.eus.aps.dao.model.VuAutopayCriteria;
import com.ericsson.eus.aps.dao.model.VuDsaEvaluations;

public interface IDiscountDao {
	public List<VuDsaEvaluations> getDiscount(String discountCode) throws AutopayDaoException;

	public List<DsaDiscounts> getStandardDiscount() throws AutopayDaoException;

	public List<VuDsaEvaluations> getDiscountByRatePlanId(long ratePlanId) throws AutopayDaoException;
	
	public List<DsaDiscounts> getAutoPayEnhancedDiscount() throws AutopayDaoException;

	public void updateDiscount();

	public void updateStandardDiscount();

	public void updateDiscountByRatePlanId();

	public void updateAutoPayEnhancedDiscount();

	/**
	 * User: ekxrxax , Date: Feb 11, 2021 6:41:21 PM 2021
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: List<VuAutopayCriteria>
	 *
	 * @param discountCode
	 * @return
	 * @throws AutopayDaoException
	 */
	public List<VuAutopayCriteria> getAutopayCriterias(String discountCode)
			throws AutopayDaoException;		
	
	/**
	 * @author ezadhde , Date: July 26, 2021 6:41:21 PM 2021
	 * Return Type: String 
	 * @param enhancedDiscList
	 * @return
	 * @throws AutopayDaoException
	 */
	String getEarlierDateDisCdFrmMulplePromos(List<String> discountCodeList, String discountType) throws AutopayDaoException ;

	/**
	 * @author ezadhde , Date: July 26, 2021 6:41:21 PM 2021
	 * update Earlier Date Enhance Discount Code 
	 */
	void updateEarlierDateDisCountCode();

}
