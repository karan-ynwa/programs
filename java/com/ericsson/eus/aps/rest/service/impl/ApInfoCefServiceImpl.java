package com.ericsson.eus.aps.rest.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.dao.ICefCriteriaDao;
import com.ericsson.eus.aps.dao.IDiscountDao;
import com.ericsson.eus.aps.dao.IVuRatePlanDao;
import com.ericsson.eus.aps.dao.model.CefCriteriaKeyLists;
import com.ericsson.eus.aps.dao.model.VuDsaEvaluations;
import com.ericsson.eus.aps.dao.model.VuRatePlan;
import com.ericsson.eus.aps.rest.service.ApInfoCefService;
import com.ericsson.eus.cef.common.CefConstants;
import com.ericsson.eus.cef.domain.CriteriaData;
import com.ericsson.eus.cef.domain.CriteriaResult;
import com.ericsson.eus.cef.rule.CriteriaRule;
import com.ericsson.eus.cef.rule.factory.CriteriaRuleFactory;

@Service
public class ApInfoCefServiceImpl implements ApInfoCefService {

	private final Logger logger = LogManager.getLogger(ApInfoCefServiceImpl.class);

	@Autowired
	private IDiscountDao apDiscountDao;
	@Autowired
	private ICefCriteriaDao apCefCriteriaDao;
	@Autowired
	private IVuRatePlanDao apVuRatePlanDao;

	private void populateApRatePlanCodeList(Map<String, String> criteriaMap)
			throws NumberFormatException, AutopayDaoException {
		String ratePlanCodeKey = criteriaMap.get(CefConstants.RATE_PLAN_CODE_LIST);
		if (ratePlanCodeKey == null || ratePlanCodeKey.isEmpty()) {
			return;
		}
		List<CefCriteriaKeyLists> ratePlanCodeList = apCefCriteriaDao
				.getCefCriteriaKeyLists(Integer.parseInt(ratePlanCodeKey));
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (CefCriteriaKeyLists cefRatePlan : ratePlanCodeList) {
			String ratePlanCode = getRatePlanCode(cefRatePlan.getAttributeValue());
			if (i == 0) {
				sb.append(ratePlanCode);
			} else {
				sb.append(CefConstants.COMMA_ELEMENT_SEPARATOR);
				sb.append(ratePlanCode);
			}
			i++;
		}
		logger.debug("RatePlan Code List : " + sb.toString());
		criteriaMap.replace(CefConstants.RATE_PLAN_CODE_LIST, sb.toString());
	}

	private String getRatePlanCode(String attributeValue) throws NumberFormatException, AutopayDaoException {
		VuRatePlan vuRatePlan = apVuRatePlanDao.getRatePlan(Long.parseLong(attributeValue));
		if (vuRatePlan != null) {
			return vuRatePlan.getRatePlanCode();
		}
		return null;
	}

	private Map<String, String> getCriteriaMap(String discountCode) throws AutopayDaoException {
		Map<String, String> criteriaMap = new TreeMap<>();

		List<VuDsaEvaluations> dsaDiscountsList = apDiscountDao.getDiscount(discountCode);

		for (VuDsaEvaluations criteria : dsaDiscountsList) {
			criteriaMap.put(criteria.getCriteriaKey(), criteria.getCriteriaValue());
		}
		return criteriaMap;
	}

	@Override
	public CriteriaResult validateCriteria(CriteriaData criteriaData, String discountCode)
			throws AutopayServiceException, AutopayDaoException {
		if (logger.isDebugEnabled()) {
			logger.debug("evaluating criteira");
		}
		CriteriaResult result = null;
		Map<String, String> criteriaMap;

		criteriaMap = getCriteriaMap(discountCode);

		// Rateplans name "A,B,C" replaced with the RateplancodeList key in Map.
		populateApRatePlanCodeList(criteriaMap);
		if (logger.isDebugEnabled()) {
			logger.debug("criteriaMap: " + criteriaMap);
		}
		Set<CriteriaRule> ruleSet = CriteriaRuleFactory.getCriteriaRules(criteriaMap.keySet());
		for (CriteriaRule criteriaRule : ruleSet) {
			if (logger.isDebugEnabled()) {
				logger.debug("evaluating rule: " + criteriaRule.toString());
			}
			result = criteriaRule.validate(criteriaData, criteriaMap);
			if (!result.isStatus()) {
				return result;
			}
		}
		return result;
	}
}
