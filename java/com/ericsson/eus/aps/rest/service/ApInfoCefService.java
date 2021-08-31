package com.ericsson.eus.aps.rest.service;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.cef.domain.CriteriaData;
import com.ericsson.eus.cef.domain.CriteriaResult;

public interface ApInfoCefService {
		
	public CriteriaResult validateCriteria(CriteriaData criteriaData, String discountCode)
			throws AutopayServiceException, AutopayDaoException;
	
}
