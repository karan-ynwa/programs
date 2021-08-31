package com.ericsson.eus.aps.rest.service.impl;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException.FaultGlobalCode;
import com.ericsson.eus.aps.dao.IAutoPayDiscountDetailsDao;
import com.ericsson.eus.aps.dao.IDiscountDao;
import com.ericsson.eus.aps.dao.model.AutoPayDiscountDetails;
import com.ericsson.eus.aps.dao.model.VuAutopayCriteria;
import com.ericsson.eus.aps.dao.model.VuDsaEvaluations;
import com.ericsson.eus.aps.rest.common.RESTConstants;
import com.ericsson.eus.aps.rest.common.RESTConstants.ApsConstants;
import com.ericsson.eus.aps.rest.service.ApInfoCefService;
import com.ericsson.eus.aps.rest.service.AutoPayDiscountService;
import com.ericsson.eus.aps.service.data.beans.AutoPayDiscountCalculationBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayDiscountPromoApplied;
import com.ericsson.eus.aps.service.util.ApsUtil;
import com.ericsson.eus.plcfg.plugin.util.PLCFGUtil;

/**
 * 
 * FILE_NAME: AutoPayInfoDiscountServiceImpl.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author esuspal, Date: Dec 9, 2020 4:08:23 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
@Service
public class AutoPayDiscountServiceImpl implements AutoPayDiscountService {

	@Autowired
	ApInfoCefService apInfoCefService;

	@Autowired
	private IDiscountDao discountDao;

	@Autowired
	private PLCFGUtil plcfgUtil;
	
	@Autowired
	private IAutoPayDiscountDetailsDao autoPayDiscountDetailsDaoImpl;

	private final Logger logger = LogManager.getLogger(AutoPayDiscountServiceImpl.class);
	private static final String DATABASE_ERROR = "Database error: ";
	private static final String ENTRY_FOR="Entry for "; 
	private static final String CEF_CRI_ORIGINAL_AUTOPAY_ENROLLMENT_END_DATE = "ORIGINAL_AUTOPAY_ENROLLMENT_END_DATE";
	private static final String CEF_CRI_ORIGINAL_AUTOPAY_ENROLLMENT_START_DATE = "ORIGINAL_AUTOPAY_ENROLLMENT_START_DATE";
	private static final String CEF_CRI_ACTIVATION_DATE_MAX = "ACTIVATION_DATE_MAX";
	private static final String CEF_CRI_ACTIVATION_DATE_MIN = "ACTIVATION_DATE_MIN";
	private static final String RECURRINGTOPUPCYCLECD_TYPE = "RECURRINGTOPUPCYCLECD_TYPE";
	private static final String STANDARD_AUTOPAY = "STANDARD_AUTOPAY";
	private static final String ENHANCED_AUTOPAY = "ENHANCED_AUTOPAY";
	
	boolean featureToggleChk = false;

	/**
	 * User: esuspal , Date: Dec 9, 2020 4:08:44 PM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 *
	 * @param apDataBean
	 * @return
	 * @throws AutopayDaoException
	 * @throws AutopayServiceException
	 */
	@Override
	public AutoPayDiscountPromoApplied getAutoPayInfoDiscount(
			AutoPayDiscountCalculationBean autoPayDiscountCalculationBean)
			throws AutopayServiceException, AutopayDaoException {
		AutoPayDiscountPromoApplied autoPayDiscountPromoApplied = getDiscount(autoPayDiscountCalculationBean);

		if (logger.isDebugEnabled()) {
			logger.debug("AutoPay autoPayDiscountPromoApplied:" + autoPayDiscountPromoApplied);
		}

		if (null != autoPayDiscountPromoApplied) {
			autoPayDiscountPromoApplied.setApPromoMsisdn(autoPayDiscountCalculationBean.getMsisdn());
		} else {
			autoPayDiscountPromoApplied = new AutoPayDiscountPromoApplied();
			autoPayDiscountPromoApplied.setApPromoAmount("0");
			autoPayDiscountPromoApplied.setApPromoMsisdn(autoPayDiscountCalculationBean.getMsisdn());
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"AutoPay autoPayDiscountPromoApplied after criteria evaluation:" + autoPayDiscountPromoApplied);
		}

		return autoPayDiscountPromoApplied;
	}

	/**
	 * User: esuspal , Date: Feb 10, 2021 12:38:48 PM 2021
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: AutoPayDiscountPromoApplied
	 *
	 * @param apDataBean
	 * @return
	 * @throws AutopayDaoException
	 * @throws AutopayServiceException
	 */
	private AutoPayDiscountPromoApplied getDiscount(AutoPayDiscountCalculationBean autoPayDiscountCalculationBean)
			throws AutopayDaoException, AutopayServiceException {
		AutoPayDiscountPromoApplied autoPayDiscountPromoApplied = null;
		String discountCode = null;
		logger.debug("inside getDiscount for autoPayDiscountCalculationBean "+autoPayDiscountCalculationBean);
		//Creating Discount Map 
		//Key1:ENHANCED_AUTOPAY, list1: enhanced discounts
		//Key2:STANDARD_AUTOPAY, list2:Standard discounts
		Map<String, List<VuDsaEvaluations>> discountMapList = getDiscountMapList(autoPayDiscountCalculationBean.getRatePlanId());
		logger.debug("inside getDiscount for discountMapList "+discountMapList);
			if(null!=discountMapList){
				//Extracting list of enhanced and standard discounts seperately
				List<VuDsaEvaluations> enhancedDiscountList = discountMapList.get(ENHANCED_AUTOPAY);
				List<VuDsaEvaluations> standardDiscountList = discountMapList.get(STANDARD_AUTOPAY);
				
				//For ML case, of the AO is having only standard discount, members cannot have enhanced discount.
				if (autoPayDiscountCalculationBean.isCalculateOnlyStandard() && null!=standardDiscountList) {
					//for checking only Standard discount
					discountCode = getStandardDiscount(standardDiscountList, autoPayDiscountCalculationBean);
					logger.debug("found Standard discount code =" + discountCode);
				} else 
					//for checking only Enhanced discount
				{
					// Start: Added below code for F107041.
					logger.debug("Before feature toggle :: AO AUTOPAY Satus  and MlAOEnrollmentStartdDate and featureToggleChk  "+autoPayDiscountCalculationBean.getMlAutopayStatus()
					+" : "+autoPayDiscountCalculationBean.getMlAOEnrollmentStartdDate()+" : "+featureToggleChk);
				
					logger.debug("Insisde processMemberForPrimary plcfgUtil "+plcfgUtil);
					
					featureToggleChk = plcfgUtil.isFeatureToggleEnabledFromConfigMap(ApsConstants.F107041);
				
					logger.debug("AO AUTOPAY Satus  and MlAOEnrollmentStartdDate and featureToggleChk , member type : "+autoPayDiscountCalculationBean.getMlAutopayStatus()
					+" : "+autoPayDiscountCalculationBean.getMlAOEnrollmentStartdDate()+" : "+featureToggleChk
					+autoPayDiscountCalculationBean.getMlType());
					//The AO is enrolled in AP (the plan is irrelevant)					
					//The member is on an eligible rate plan
					
					if(featureToggleChk && StringUtils.equalsIgnoreCase(ApsConstants.ML_MEMBER, autoPayDiscountCalculationBean.getMlType()) 
							&& null != autoPayDiscountCalculationBean.getMlAOEnrollmentStartdDate() 
							&& StringUtils.equalsIgnoreCase(ApsConstants.ACTIVE, autoPayDiscountCalculationBean.getMlAutopayStatus())) {
						discountCode = getEnhancedOrStndDiscount(autoPayDiscountCalculationBean, discountCode, enhancedDiscountList,
								standardDiscountList);
					} // End: F107041
					 else {
						discountCode = getEnrollEnhancedOrStndDiscount(autoPayDiscountCalculationBean, discountCode,
								enhancedDiscountList, standardDiscountList);
					}
					
				}
				if (null != discountCode) {
					autoPayDiscountPromoApplied = getAutoPayDiscountPromoApplied(autoPayDiscountCalculationBean, discountCode);
				}
		}
		return autoPayDiscountPromoApplied;
	}

	/**
	 * @param autoPayDiscountCalculationBean
	 * @param discountCode
	 * @param enhancedDiscountList
	 * @param standardDiscountList
	 * @return
	 * @throws AutopayDaoException
	 */
	private String getEnrollEnhancedOrStndDiscount(AutoPayDiscountCalculationBean autoPayDiscountCalculationBean,
			String discountCode, List<VuDsaEvaluations> enhancedDiscountList,
			List<VuDsaEvaluations> standardDiscountList) throws AutopayDaoException {
					
		discountCode = getEnhancedOrStndDiscount(autoPayDiscountCalculationBean, discountCode, enhancedDiscountList,
				standardDiscountList);
		return discountCode;
					}
	
	/**
	 * @param autoPayDiscountCalculationBean
	 * @param discountCode
	 * @param enhancedDiscountList
	 * @param standardDiscountList
	 * @return
	 * @throws AutopayDaoException
	 */
	private String getEnhancedOrStndDiscount(AutoPayDiscountCalculationBean autoPayDiscountCalculationBean,
			String discountCode, List<VuDsaEvaluations> enhancedDiscountList,
			List<VuDsaEvaluations> standardDiscountList) throws AutopayDaoException {
					if (null == discountCode  && null!=enhancedDiscountList) {
						logger.debug("Enhanced Criteria validation for Un-Enrolled sub ");
						discountCode = getEnhancedDiscount(enhancedDiscountList, autoPayDiscountCalculationBean);
			logger.debug("Enhanced discount code  "+discountCode);
					}
					//Then check for Standard discount
					if (null == discountCode && null!=standardDiscountList) {
						logger.debug("Standard Criteria validation for Un-Enrolled sub ");
						discountCode = getStandardDiscount(standardDiscountList, autoPayDiscountCalculationBean);
			logger.debug("Standard discount code  "+discountCode);
					}
		return discountCode;
				}

	/**
	 * User: esuspal , Date: Feb 28, 2021 10:10:13 PM 2021
	 *
	 * Purpose: return enhanced discount if all the criteria satisfied
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: String
	 *
	 * @param enhancedDiscountList
	 * @param autoPayDiscountCalculationBean
	 * @return
	 * @throws AutopayDaoException
	 */
	private String getEnhancedDiscount(List<VuDsaEvaluations> enhancedDiscountList,
			AutoPayDiscountCalculationBean autoPayDiscountCalculationBean) throws AutopayDaoException {
		List<String> enhancedDiscoundCodeList = new ArrayList<>();
		//If the Enrollment date is null ( ie. new Enroll Request or TPSPGetAccount request for non autopay subscriber)
		//Set the Enrollmentdate to current date.
		if (null == autoPayDiscountCalculationBean.getEnrollmentStartDate()) {
			autoPayDiscountCalculationBean.setEnrollmentStartDate(ApsUtil.getcurrentDate());
		}//looping through all the available enhanced discounts from the list
		for (VuDsaEvaluations vuDsaEvaluations : enhancedDiscountList) {
			boolean isCEnhancedriteriaSatisfied = true;
			//Criteria Map created based on standard criteria's ie. RECURRINGTOPUPCYCLECD_TYPE, CEF_CRI_ACTIVATION_DATE_MIN, CEF_CRI_ACTIVATION_DATE_MAX
			//, CEF_CRI_ACTIVATION_DATE_MAX, CEF_CRI_ACTIVATION_DATE_MIN
			Map<String, String> enhancedCriteriaMap = getEnhancedCriteriaMap(vuDsaEvaluations.getDiscountCode());
			for (Map.Entry<String, String> entry : enhancedCriteriaMap.entrySet()) {
				//check for the criteria against the subscriber details
				if (getCriteriaKeyCheck(entry.getKey(), entry.getValue(), autoPayDiscountCalculationBean) == false) {
					isCEnhancedriteriaSatisfied = false;
					break;
				}
			}

			if (isCEnhancedriteriaSatisfied) {
				enhancedDiscoundCodeList.add(vuDsaEvaluations.getDiscountCode());
			}
		}

		if (enhancedDiscoundCodeList.size() > 1) {
			logger.error("More than one enhanced discount code has same criteria: " + enhancedDiscoundCodeList.toString());
			if(featureToggleChk) {				
				return discountDao.getEarlierDateDisCdFrmMulplePromos(enhancedDiscoundCodeList, ApsConstants.ENHANCED);
			}
						return enhancedDiscoundCodeList.get(0);
		} else if (enhancedDiscoundCodeList.isEmpty()) {
			return null;
		}

		return enhancedDiscoundCodeList.get(0);
	}

	/**
	 * User: esuspal , Date: Feb 28, 2021 10:09:35 PM 2021
	 *
	 * Purpose: return standard discount if all the criteria satisfied
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: String
	 *
	 * @param standardDiscountList
	 * @param autoPayDiscountCalculationBean
	 * @return
	 * @throws AutopayDaoException
	 */
	private String getStandardDiscount(List<VuDsaEvaluations> standardDiscountList,
			AutoPayDiscountCalculationBean autoPayDiscountCalculationBean) throws AutopayDaoException {
		List<String> discoundCodeList = new ArrayList<>();

		for (VuDsaEvaluations vuDsaEvaluations : standardDiscountList) {
			boolean isCriteriaSatisfied = true;
			//Criteria Map created based on standard criteria's ie. RECURRINGTOPUPCYCLECD_TYPE, CEF_CRI_ACTIVATION_DATE_MIN, CEF_CRI_ACTIVATION_DATE_MAX
			Map<String, String> criteriaMap = getStandardCriteriaMap(vuDsaEvaluations.getDiscountCode());
			for (Map.Entry<String, String> entry : criteriaMap.entrySet()) {
				//Checking for the Criteria's based on standard Map populated in autoPayDiscountCalculationBean
				if (getCriteriaKeyCheck(entry.getKey(), entry.getValue(), autoPayDiscountCalculationBean) == false) {
					isCriteriaSatisfied = false;
					break;
				}
			}

			if (isCriteriaSatisfied) {
				discoundCodeList.add(vuDsaEvaluations.getDiscountCode());
			}
		}

		//Log error in case multiple standard discounts returned.
		if (discoundCodeList.size() > 1) {
			logger.error("More than one standard discount code has same criteria: " + discoundCodeList.toString());			
			if (featureToggleChk) {
				return discountDao.getEarlierDateDisCdFrmMulplePromos(discoundCodeList, ApsConstants.STANDARD);
			}

			return discoundCodeList.get(0);
		} else if (discoundCodeList.isEmpty()) {
			return null;
		}

		return discoundCodeList.get(0);
	}

	/**
	 * User: esuspal , Date: Feb 28, 2021 10:09:21 PM 2021
	 *
	 * Purpose: validate the criteria
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: boolean
	 *
	 * @param criteriaKey
	 * @param criteriaVaue
	 * @param autoPayDiscountCalculationBean
	 * @return
	 * @throws AutopayDaoException 
	 */
	private boolean getCriteriaKeyCheck(String criteriaKey, String criteriaVaue,
			AutoPayDiscountCalculationBean autoPayDiscountCalculationBean) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	
		logger.debug("Inside getCriteriaKeyCheck criteriaKey : and criteriaVaue and autoPayDiscountCalculationBean  "+
				criteriaKey+" :  "+criteriaVaue+" :  "+autoPayDiscountCalculationBean.getMsisdn());  
		
		logger.debug(
				"Inside getCriteriaKeyCheck criteriaKey :  and criteriaVaue : and criteriaVaue and featureToggleChk  and "
				+ " autoPayDiscountCalculationBean and ML Type : and MlAOEnrollmentStartdDate  and MlAutopayStatus  MLtype  "
						+ criteriaKey + " :  " + criteriaVaue + " :  " + featureToggleChk + " :  "
						+ autoPayDiscountCalculationBean.getMsisdn() + " :  "
						+ autoPayDiscountCalculationBean.getMlType() + " :  "
						+ autoPayDiscountCalculationBean.getMlAOEnrollmentStartdDate() + " :  "
						+ autoPayDiscountCalculationBean.getMlAutopayStatus()
						+autoPayDiscountCalculationBean.getMlType());

		if (featureToggleChk
				&& StringUtils.equalsIgnoreCase(ApsConstants.ML_MEMBER, autoPayDiscountCalculationBean.getMlType())
				&& null != autoPayDiscountCalculationBean.getMlAOEnrollmentStartdDate() && StringUtils
						.equalsIgnoreCase(ApsConstants.ACTIVE, autoPayDiscountCalculationBean.getMlAutopayStatus())) {
			if((criteriaKey.equals(CEF_CRI_ORIGINAL_AUTOPAY_ENROLLMENT_START_DATE)
					|| criteriaKey.equals(CEF_CRI_ORIGINAL_AUTOPAY_ENROLLMENT_END_DATE))) {
				return true;	
			}else {
				return getCriteriaKeyCheckResult(criteriaKey, criteriaVaue, autoPayDiscountCalculationBean, formatter);
			}
		}else {
			return getCriteriaKeyCheckResult(criteriaKey, criteriaVaue, autoPayDiscountCalculationBean, formatter);
		}
		
		
	}

	private boolean getCriteriaKeyCheckResult(String criteriaKey, String criteriaVaue,
			AutoPayDiscountCalculationBean autoPayDiscountCalculationBean, DateTimeFormatter formatter) {
		LocalDate localSubsActivationDate = null;
		LocalDate enrollmentDate = null;
	
		switch (criteriaKey) {
		case CEF_CRI_ACTIVATION_DATE_MIN:
			localSubsActivationDate = autoPayDiscountCalculationBean.getActivationStartDate().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate criValMinDate = LocalDate.parse(criteriaVaue, formatter);
			return localSubsActivationDate.isAfter(criValMinDate) || localSubsActivationDate.isEqual(criValMinDate);
		case CEF_CRI_ACTIVATION_DATE_MAX:
			localSubsActivationDate = autoPayDiscountCalculationBean.getActivationStartDate().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate criValMaxDate = LocalDate.parse(criteriaVaue, formatter);
			return localSubsActivationDate.isBefore(criValMaxDate) || localSubsActivationDate.isEqual(criValMaxDate);

		case CEF_CRI_ORIGINAL_AUTOPAY_ENROLLMENT_START_DATE:
			enrollmentDate = autoPayDiscountCalculationBean.getEnrollmentStartDate().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate criValEnrollMinDate = LocalDate.parse(criteriaVaue, formatter);
			return enrollmentDate.isAfter(criValEnrollMinDate) || enrollmentDate.isEqual(criValEnrollMinDate);
		case CEF_CRI_ORIGINAL_AUTOPAY_ENROLLMENT_END_DATE:
			enrollmentDate = autoPayDiscountCalculationBean.getEnrollmentStartDate().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate criValEnrollMaxDate = LocalDate.parse(criteriaVaue, formatter);
			return enrollmentDate.isBefore(criValEnrollMaxDate) || enrollmentDate.isEqual(criValEnrollMaxDate);
		case RECURRINGTOPUPCYCLECD_TYPE:
			return StringUtils.equals(autoPayDiscountCalculationBean.getTopUpCycleValue(), criteriaVaue);
		default:
			return false;
		}
	}

	/**
	 * User: esuspal , Date: Feb 28, 2021 10:09:09 PM 2021
	 *
	 * Purpose: Create Map for enhanced discount criteria
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: Map<String,String>
	 *
	 * @param discountCode
	 * @return
	 * @throws AutopayDaoException
	 */
	private Map<String, String> getEnhancedCriteriaMap(String discountCode) throws AutopayDaoException {
		List<VuAutopayCriteria> autopayCriterias = discountDao.getAutopayCriterias(discountCode);
		if(autopayCriterias.isEmpty()){
			logger.error(ENTRY_FOR+discountCode+" is missing from EBI_AUTOPAY_DISCOUNTS table. Incorrect business configuration.");
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, DATABASE_ERROR + 
					ENTRY_FOR+discountCode+" is missing from EBI_AUTOPAY_DISCOUNTS table");
		}
		Map<String, String> criteriaMap = new TreeMap<>();
		for (VuAutopayCriteria vuAutopayCriteria : autopayCriterias) {
			logger.debug(" vuAutopayCriteria "+vuAutopayCriteria.toString());
		
			if (vuAutopayCriteria.getCriteriaKey().equals(RECURRINGTOPUPCYCLECD_TYPE)) {
				if (null != vuAutopayCriteria.getCriteriaValue()) {
					criteriaMap.put(RECURRINGTOPUPCYCLECD_TYPE, vuAutopayCriteria.getCriteriaValue());
				}
			} else {
				if (null != vuAutopayCriteria.getActivationDateMin()) {
					criteriaMap.put(CEF_CRI_ACTIVATION_DATE_MIN, autopayCriterias.get(0).getActivationDateMin());
				}
				if (null != vuAutopayCriteria.getActivationDateMax()) {
					criteriaMap.put(CEF_CRI_ACTIVATION_DATE_MAX, autopayCriterias.get(0).getActivationDateMax());
				}

				if (null != autopayCriterias.get(0).getOriginalAutopayEnrollmentstartDate()) {
					criteriaMap.put(CEF_CRI_ORIGINAL_AUTOPAY_ENROLLMENT_START_DATE,
							autopayCriterias.get(0).getOriginalAutopayEnrollmentstartDate());
				}
				if (null != autopayCriterias.get(0).getOriginalAutopayEnrollmentEndDate()) {
					criteriaMap.put(CEF_CRI_ORIGINAL_AUTOPAY_ENROLLMENT_END_DATE,
							autopayCriterias.get(0).getOriginalAutopayEnrollmentEndDate());
				}
			}
			logger.debug(" criteriaMap Data :  "+criteriaMap.toString());
		}
		logger.debug(" final criteriaMap Data  :  "+criteriaMap.toString());
		return criteriaMap;
	}

	/**
	 * User: esuspal , Date: Feb 28, 2021 10:08:42 PM 2021
	 *
	 * Purpose: Create Map for standard discount criteria
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: Map<String,String>
	 *
	 * @param discountCode
	 * @return
	 * @throws AutopayDaoException
	 */
	private Map<String, String> getStandardCriteriaMap(String discountCode) throws AutopayDaoException {
		List<VuAutopayCriteria> autopayCriterias = discountDao.getAutopayCriterias(discountCode);
		if(autopayCriterias.isEmpty()){
			logger.error(ENTRY_FOR+discountCode+" is missing from EBI_AUTOPAY_DISCOUNTS table. Incorrect business configuration.");
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, DATABASE_ERROR + 
					ENTRY_FOR+discountCode+" is missing from EBI_AUTOPAY_DISCOUNTS table");
		}
		Map<String, String> criteriaMap = new TreeMap<>();
		for (VuAutopayCriteria vuAutopayCriteria : autopayCriterias) {
			logger.debug(" vuAutopayCriteria "+vuAutopayCriteria.toString());
			if (vuAutopayCriteria.getCriteriaKey().equals(RECURRINGTOPUPCYCLECD_TYPE)) {
				if (null != vuAutopayCriteria.getCriteriaValue()) {
					criteriaMap.put(RECURRINGTOPUPCYCLECD_TYPE, vuAutopayCriteria.getCriteriaValue());
				}
			} else {
				if (null != vuAutopayCriteria.getActivationDateMin()) {
					criteriaMap.put(CEF_CRI_ACTIVATION_DATE_MIN, autopayCriterias.get(0).getActivationDateMin());
				}
				if (null != vuAutopayCriteria.getActivationDateMax()) {
					criteriaMap.put(CEF_CRI_ACTIVATION_DATE_MAX, autopayCriterias.get(0).getActivationDateMax());
				}
			}
			logger.debug(" criteriaMap  data to set : "+criteriaMap.toString());
		}
		logger.debug(" final  criteriaMap data : "+criteriaMap.toString());
		return criteriaMap;
	}

	private Map<String, List<VuDsaEvaluations>> getDiscountMapList(String ratePlanId)
			throws NumberFormatException, AutopayDaoException {
		List<VuDsaEvaluations> dsaEvaluationList = discountDao.getDiscountByRatePlanId(Long.parseLong(ratePlanId));
		if (isEmpty(dsaEvaluationList)) {
			return null;
		}
		Map<String, List<VuDsaEvaluations>> discountMapList = dsaEvaluationList.stream()
				.collect(Collectors.groupingBy(VuDsaEvaluations::getDiscountType));

		return discountMapList;
	}

	/**
	 * User: esuspal , Date: Feb 28, 2021 10:08:17 PM 2021
	 *
	 * Purpose: set data for AutoPayDiscountPromoApplied
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: AutoPayDiscountPromoApplied
	 *
	 * @param bean
	 * @param discountCode
	 * @return
	 * @throws AutopayServiceException
	 */
	private AutoPayDiscountPromoApplied getAutoPayDiscountPromoApplied(AutoPayDiscountCalculationBean bean,
			String discountCode) throws AutopayServiceException {
		double promoAmount = 0.00;
		AutoPayDiscountPromoApplied autoPayDiscountPromoApplied = new AutoPayDiscountPromoApplied();
		logger.debug("Inside getAutoPayDiscountPromoApplied discountCode "+discountCode);
		try {
			List<VuDsaEvaluations> discountList = discountDao.getDiscount(discountCode);
			autoPayDiscountPromoApplied.setApPromoMsisdn(bean.getMsisdn());
			if (null != discountList && !discountList.isEmpty()) {
				logger.debug("discountList from vu_dsa_evaluations = "+discountList);
				promoAmount = discountList.get(0).getBonusUnits();
				autoPayDiscountPromoApplied.setDiscountCode(discountCode);
				autoPayDiscountPromoApplied.setDiscountType(discountList.get(0).getDiscountType());
				autoPayDiscountPromoApplied.setApPromoAmount(getApPromoAmount(promoAmount,
						discountList.get(0).getBonusUnitsType(), discountList.get(0).getBonusServiceType()));
				autoPayDiscountPromoApplied.setDiscountName(discountList.get(0).getDiscountName());

				AutoPayDiscountDetails autoPayDiscountDetails = autoPayDiscountDetailsDaoImpl
						.getAutoPayDiscountDetails(discountCode);

				if (null != autoPayDiscountDetails) {
					autoPayDiscountPromoApplied.setApPromoAdjCode(autoPayDiscountDetails.getAdjCode());
					autoPayDiscountPromoApplied.setApPromoAdjType(autoPayDiscountDetails.getAdjType());
				}
			}
			logger.debug("Returning autoPayDiscountPromoApplied",autoPayDiscountPromoApplied);
			return autoPayDiscountPromoApplied;

		} catch (AutopayDaoException e) {
			logger.error(e.getMessage());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, DATABASE_ERROR + e.getMessage());
		}
	}

	/**
	 * User: esuspal , Date: Feb 28, 2021 10:07:58 PM 2021
	 *
	 * Purpose: Calculate promo amount
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: String
	 *
	 * @param promoAmount
	 * @param bonusType
	 * @param serviceType
	 * @return
	 */
	private String getApPromoAmount(double promoAmount, String bonusType, String serviceType) {
		double calculatedPromoAmount = promoAmount;
		if (StringUtils.equals(serviceType, ApsConstants.MONEY)) {
			if (StringUtils.equals(bonusType, ApsConstants.DOLLARS)) {
				calculatedPromoAmount = promoAmount * 100;
			}
			calculatedPromoAmount = calculatedPromoAmount / 100;
		}
		return ApsUtil.roundBig(calculatedPromoAmount, RESTConstants.ApsConstants.PLACES).toString();
	}

	/**
	 * User: esuspal , Date: Feb 28, 2021 10:07:27 PM 2021
	 *
	 * Purpose: calculate discount for autopay enrollment
	 *
	 * US/D/F Number: F1216
	 *
	 * @param autoPayDiscountCalculationBean
	 * @return
	 * @throws AutopayServiceException
	 * @throws AutopayDaoException
	 */
	@Override
	public String getAutoPayEnrollDiscount(AutoPayDiscountCalculationBean autoPayDiscountCalculationBean)
			throws AutopayServiceException, AutopayDaoException {
		String discountCode = null;

		Map<String, List<VuDsaEvaluations>> discountMapList = getDiscountMapList(
				autoPayDiscountCalculationBean.getRatePlanId());
		if(null!=discountMapList){
			List<VuDsaEvaluations> enhancedDiscountList = discountMapList.get(ENHANCED_AUTOPAY);
			List<VuDsaEvaluations> standardDiscountList = discountMapList.get(STANDARD_AUTOPAY);
			
			if (null!=enhancedDiscountList) {
				discountCode = getEnhancedDiscount(enhancedDiscountList, autoPayDiscountCalculationBean);
			}
			//keeping enroll request call same, if not enhanced fallback to standard discount
			if (null == discountCode && null!=standardDiscountList) {
				discountCode = getStandardDiscount(standardDiscountList, autoPayDiscountCalculationBean);
			}
		}
		return discountCode;
	}
}
