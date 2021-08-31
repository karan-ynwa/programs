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
import com.ericsson.eus.aps.common.exception.AutopayServiceException.FaultGlobalCode;
import com.ericsson.eus.aps.dao.ICefCriteriaDao;
import com.ericsson.eus.aps.dao.IDiscountDao;
import com.ericsson.eus.aps.dao.IVuRatePlanDao;
import com.ericsson.eus.aps.dao.model.CefCriteriaEvaluations;
import com.ericsson.eus.aps.dao.model.CefCriteriaKeyLists;
import com.ericsson.eus.aps.dao.model.DsaDiscounts;
import com.ericsson.eus.aps.dao.model.VuDsaEvaluations;
import com.ericsson.eus.aps.dao.model.VuRatePlan;
import com.ericsson.eus.aps.rest.service.CefService;
import com.ericsson.eus.cef.common.CefConstants;
import com.ericsson.eus.cef.domain.CriteriaData;
import com.ericsson.eus.cef.domain.CriteriaResult;
import com.ericsson.eus.cef.rule.CriteriaRule;
import com.ericsson.eus.cef.rule.EnrollmentStatusRule;
import com.ericsson.eus.cef.rule.factory.CriteriaRuleFactory;

@Service
public class CefServiceImpl implements CefService {
	
	private static final String VALIDATION_FAILED_TXT = "Validation Failed for ";
    private static final String ERROR_TXT = "An unexpected error occured";
    private final Logger logger = LogManager.getLogger(CefServiceImpl.class);

	@Autowired
	private IDiscountDao discountDao;
	@Autowired
	private ICefCriteriaDao cefCriteriaDao;
	@Autowired
	private IVuRatePlanDao vuRatePlanDao;

	private void populateRatePlanCodeList(Map<String, String> criteriaMap) throws NumberFormatException, AutopayDaoException {
		String ratePlanCodeKey = criteriaMap.get(CefConstants.RATE_PLAN_CODE_LIST);
		if(ratePlanCodeKey == null || ratePlanCodeKey.isEmpty()){
			return;
		}
		List<CefCriteriaKeyLists> ratePlanCodeList = cefCriteriaDao.getCefCriteriaKeyLists(Integer.parseInt(ratePlanCodeKey));
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for(CefCriteriaKeyLists ratePlanId:ratePlanCodeList){
			String ratePlanCode = getRatePlanCode(ratePlanId.getAttributeValue());
			if(i == 0){
				sb.append(ratePlanCode);
			} else {
				sb.append(CefConstants.COMMA_ELEMENT_SEPARATOR);
				sb.append(ratePlanCode);
			}
			i++;
		}
		logger.debug("RatePlan Code List : "+sb.toString());
		criteriaMap.replace(CefConstants.RATE_PLAN_CODE_LIST, sb.toString());
	}

	private String getRatePlanCode(String attributeValue) throws NumberFormatException, AutopayDaoException {
		VuRatePlan vuRatePlan = vuRatePlanDao.getRatePlan(Long.parseLong(attributeValue));
		if(vuRatePlan != null){
			return vuRatePlan.getRatePlanCode();
		}
		return null;
	}

	private Map<String, String> getCriteriaMap(String msisdn, String discountCode) throws AutopayDaoException {
		Map<String, String> criteriaMap = new TreeMap<>();

		// Get discount details and criteria key, value details
		List<VuDsaEvaluations> dsaDiscountsList = discountDao.getDiscount(discountCode);

        // we already get the criteria details above, and criteria key is same for all records. This step is not needed
		Integer evalId = dsaDiscountsList.get(0).getCriteriaEvalId();
		logger.debug("Msisdn :"+msisdn+" DiscountCode:"+discountCode +" Eval Id :"+evalId);
		
		// List of Criteria key and Value, this is not needed
		List<CefCriteriaEvaluations> criteriaList = cefCriteriaDao.getCriteriaEvalId(evalId);

		// Map created for Criteria key and value
		for(CefCriteriaEvaluations criteria:criteriaList){
			logger.debug("Populating criteriaMap with Key:"+criteria.getCriteriaKey() 
					+" Value:"+criteria.getCriteriaValue());
			criteriaMap.put(criteria.getCriteriaKey(), criteria.getCriteriaValue());
		}	
		return criteriaMap;
	}
	// F81681 : US11465
	/**
	 * This method is used to validate the Criteria for Enhanced Flow
	 */
	@Override
	public CriteriaResult validateCriteriaEnhanced(CriteriaData criteriaData,String msisdn, String discountCode) {
		logger.debug("evaluating enhanced criteira");
		CriteriaResult result = null;
		Map<String, String> criteriaMap;
		try {

			// Map created for Criteria key and value
		 criteriaMap = getCriteriaMap(msisdn,discountCode); 
		
			// Rateplans name "A,B,C" replaced with the RateplancodeList key in Map.
			populateRatePlanCodeList(criteriaMap);
			logger.debug("validating criterias");
		} catch (NumberFormatException | AutopayDaoException e) {
			logger.error(e.getMessage());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, ERROR_TXT);
		}
		//Using rule factory to get the set of rules object for the given criteria set
		Set<CriteriaRule> ruleSet= CriteriaRuleFactory.getCriteriaRules(criteriaMap.keySet());
		for(CriteriaRule rule : ruleSet){
			//validate criteria for Enhanced and in case of failure validate for Standard
			logger.debug("evaluating rule: "+rule.toString());
			result = rule.validate(criteriaData, criteriaMap);
			//F81681 : US84027 : exiting the criteria validation as not enrolled in AutoPay
			
			//THis below line means it is not enhanced, above comment is stating something else.
			if (!result.isStatus() && rule instanceof EnrollmentStatusRule) {
				return result;
			}
			//F81681 : US84027 : This Flow is executed if Enhanced Criteria Fails
			if(!result.isStatus())
			{
				
				//Here we are validating it against all the standard discount codes in DSA_DISCOUNTS
				return validateCriteriaStandard(criteriaData, msisdn);
			}
		}
		return result;
	}

	// F81681 : US84027
	/**
	 * This method is used to validate the Criteria for Standard Flow
	 */
	@Override
	public CriteriaResult validateCriteriaStandard(CriteriaData criteriaData, String msisdn) {
		logger.debug("evaluating Standard criteira");
		CriteriaResult result = null;

		Map<String, String> criteriaMap = null;
		try {
			List<DsaDiscounts> dsaDiscountsList = discountDao.getStandardDiscount();
			for (DsaDiscounts dsaDiscount : dsaDiscountsList) {
				criteriaMap = getCriteriaMap(msisdn, dsaDiscount.getDiscountCode());
				populateRatePlanCodeList(criteriaMap);
				// Using rule factory to get the set of rules object for the
				// given criteria set
				Set<CriteriaRule> ruleSet = CriteriaRuleFactory.getCriteriaRules(criteriaMap.keySet());
				for (CriteriaRule rule : ruleSet) {
					// validate criteria and break loop on failure
					result = rule.validate(criteriaData, criteriaMap);
					if (!result.isStatus() && rule instanceof EnrollmentStatusRule) {
						return result;
					}
					if (!result.isStatus()) {
						logger.debug(VALIDATION_FAILED_TXT + result.getCriteriaCode() + "\n" + result);
						break;
					}	
				}
				if (result!=null && result.isStatus()) {
					result.setStandardDiscountCode(dsaDiscount.getDiscountCode());
					break;
				}
			}

		} catch (NumberFormatException | AutopayDaoException e) {
			logger.error(e.getMessage());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, ERROR_TXT);
		}
		return logAndReturnResult(result);
		
	}


	/**
	 * @param result
	 */
	private CriteriaResult logAndReturnResult(CriteriaResult result) {
		if (result!=null && !result.isStatus()) {
			logger.debug(VALIDATION_FAILED_TXT + result.getCriteriaCode() + "\n" + result);
			return result;
		}
		return result;
	}
}
