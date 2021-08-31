package com.ericsson.eus.aps.rest.service;

import com.ericsson.eus.cef.domain.CriteriaData;
import com.ericsson.eus.cef.domain.CriteriaResult;

public interface CefService {
		
	public CriteriaResult validateCriteriaStandard(CriteriaData criteriaData, String msisdn);
	
	public CriteriaResult validateCriteriaEnhanced(CriteriaData criteriaData, String msisdn, String discountCode);
	
}
