package com.ericsson.eus.aps.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException.FaultGlobalCode;
import com.ericsson.eus.aps.rest.common.RESTConstants;
import com.ericsson.eus.aps.rest.common.RESTConstants.ApsConstants;
import com.ericsson.eus.aps.rest.common.RESTConstants.CustomHeaders;
import com.ericsson.eus.aps.rest.common.RESTUtility;
import com.ericsson.eus.aps.rest.service.AutoPayDiscountService;
import com.ericsson.eus.aps.rest.service.AutoPayInfoService;
import com.ericsson.eus.aps.service.data.beans.AutoPayDiscountCalculationBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayDiscountPromoApplied;
import com.ericsson.eus.aps.service.data.beans.AutoPayInfoDataBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayInfoRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayInfoResponseBean;
import com.ericsson.eus.aps.service.data.beans.BaseResponse;
import com.ericsson.eus.aps.service.data.beans.FeatureInfo;
import com.ericsson.eus.aps.service.data.beans.MsisdnDetails;
import com.ericsson.eus.aps.service.data.validator.RequestValidator;
import com.ericsson.eus.aps.service.util.ApsUtil;
import com.ericsson.eus.cef.common.CefConstants;
import com.ericsson.eus.plcfg.plugin.util.PLCFGUtil;

/**
 * 
 * FILE_NAME: AutopayServiceController.java
 * 
 * MODULE DESCRIPTION: Controller for Autopay request, ATTPRE-84028
 *
 * @author eihsnad, Date: Sep 7, 2020 12:11:41 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
@RestController
public class AutoPayInfoServiceController {
	private final Logger logger = LogManager.getLogger(AutoPayInfoServiceController.class);
	@Autowired
	private AutoPayInfoService autopayService;
	
	@Autowired
	private PLCFGUtil plcfgUtil;
	

	@Autowired
	private AutoPayDiscountService autoPayInfoDiscountService;

	private static final String INTERNAL_ERROR_MSG = "An unexpected error occured";
		
	boolean featureToggleChk = false;

	/**
	 * 
	 * User: ekxrxax , Date: Sep 9, 2020 2:23:36 PM 2020
	 *
	 * Purpose: calculate Autopay Promo
	 *
	 * US/D/F Number: US84027
	 * 
	 * Return Type: ResponseEntity<BaseResponse>
	 *
	 * @param httpRequest
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAutoPayInfo", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> getAutoPayInfo(HttpServletRequest httpServletRequest,
			@RequestBody AutoPayInfoRequestBean request) {
		HttpHeaders header = new HttpHeaders();
		BaseResponse autoPayInfoResponse = null;
		try {
			RequestValidator.validate(request);

			if (logger.isDebugEnabled()) {
				logger.debug("Request:" + (request != null ? request.toString() : "NULL"));
			}

			autoPayInfoResponse = processReq(request);

			if (logger.isDebugEnabled()) {
				logger.debug("Response:" + (autoPayInfoResponse != null ? autoPayInfoResponse.toString() : "NULL"));
			}

			if (autoPayInfoResponse == null) {
				throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, INTERNAL_ERROR_MSG);
			}
			header.add(CustomHeaders.RESPONSE_CODE, autoPayInfoResponse.getResponseCode().toString());
			httpServletRequest.setAttribute(RESTConstants.RESPONSE_TEXT, autoPayInfoResponse.getResponseText());
			return RESTUtility.createResponse(autoPayInfoResponse, header, HttpStatus.OK);
		} catch (AutopayServiceException e) {
			header.add(CustomHeaders.RESPONSE_CODE, String.valueOf(e.getFaultCode()));
			httpServletRequest.setAttribute(RESTConstants.RESPONSE_TEXT, e.getFaultText());
			autoPayInfoResponse = ApsUtil.buildErrorResponse(e);
			return RESTUtility.createResponse(autoPayInfoResponse, header, HttpStatus.OK);
		}
	}

	/**
	 * User: esuspal , Date: Dec 21, 2020 6:28:13 PM 2020
	 *
	 * Purpose: Process request for the requested msisdn and mltype
	 *
	 * US/D/F Number: F1216
	 * 
	 * Return Type: BaseResponse
	 *
	 * @param request
	 * @return
	 * @throws AutopayServiceException
	 */
	private BaseResponse processReq(AutoPayInfoRequestBean request) throws AutopayServiceException {
		AutoPayInfoResponseBean response = null;

		try {
			List<MsisdnDetails> msisdnDetails = request.getMsisdnList();
			if (logger.isDebugEnabled()) {
				logger.debug("msisdnDetails:=" + msisdnDetails);
			}
			AutoPayInfoDataBean apDataBean = null;
			if (msisdnDetails.size() > 1) {
				apDataBean = processMultilineReq(msisdnDetails);
			} else if (StringUtils.isEmpty(msisdnDetails.get(0).getMlType())
					|| StringUtils.equals(msisdnDetails.get(0).getMlType(), ApsConstants.ML_PRIMARY)) {
				apDataBean = processGBMReq(msisdnDetails);
			} else {
				/**
				 * Process only multiline member
				 */
				apDataBean = processMultilineMemberReq(msisdnDetails);
			}

			response = autopayService.getAutoPayInfoResponseBean(msisdnDetails, apDataBean);

		} catch (AutopayServiceException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, "Application Error: " + e.getMessage());
		}
		return response;
	}

	/**
	 * User: esuspal , Date: Dec 21, 2020 6:28:55 PM 2020
	 *
	 * Purpose: Process request for mltype PRIMARY and MEMBER
	 *
	 * US/D/F Number: F1216
	 * 
	 * Return Type: AutoPayInfoDataBean
	 *
	 * @param msisdnDetails
	 * @return
	 * @throws AutopayServiceException
	 * @throws AutopayDaoException
	 */
	private AutoPayInfoDataBean processMultilineReq(List<MsisdnDetails> msisdnDetails)
			throws AutopayServiceException, AutopayDaoException {
		AutoPayInfoDataBean apDataBean = new AutoPayInfoDataBean();
		for (MsisdnDetails msisdn : msisdnDetails) {
			if (StringUtils.equalsIgnoreCase(msisdn.getMlType(), ApsConstants.ML_PRIMARY)) {
				apDataBean.setMlType(ApsConstants.ML_PRIMARY);
				apDataBean = autopayService.getAutopayData(apDataBean, msisdn.getMsisdn());
				AutoPayDiscountPromoApplied autoPayDiscountPromoApplied = autoPayInfoDiscountService
						.getAutoPayInfoDiscount(getAutoPayDiscountCalculationBean(apDataBean));
				if (null != autoPayDiscountPromoApplied) {
					List<AutoPayDiscountPromoApplied> autoPayDiscountPromoAppliedList = new ArrayList<AutoPayDiscountPromoApplied>();
					autoPayDiscountPromoApplied.setMlType(ApsConstants.ML_PRIMARY);
					autoPayDiscountPromoAppliedList.add(autoPayDiscountPromoApplied);
					apDataBean.setAutoPayDiscountPromoApplied(autoPayDiscountPromoAppliedList);
				}

				List<FeatureInfo> featureInfoList = autopayService.getFeatureInfoList(apDataBean, msisdn.getMsisdn());
				apDataBean.setFeatureInfoList(featureInfoList);

				apDataBean = autopayService.getAutoPayEventTracker(apDataBean);
			}
		}

		processMemberForPrimary(msisdnDetails, apDataBean);

		return apDataBean;

	}

	/**
	 * User: esuspal , Date: Dec 21, 2020 6:29:44 PM 2020
	 *
	 * Purpose: Process request for MEMBER for PRIMARY
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: void
	 *
	 * @param msisdnDetails
	 * @param apDataBean
	 * @throws AutopayServiceException
	 * @throws AutopayDaoException
	 */
	private void processMemberForPrimary(List<MsisdnDetails> msisdnDetails, AutoPayInfoDataBean apDataBean)
			throws AutopayServiceException, AutopayDaoException {
		if (null == apDataBean.getAutoPayDiscountPromoApplied() || StringUtils.equals(
				apDataBean.getAutoPayDiscountPromoApplied().get(0).getDiscountType(), CefConstants.STANDARD_AUTOPAY)) {
			apDataBean.setCalculateOnlyStandard(true);
		}
		for (MsisdnDetails msisdn : msisdnDetails) {
			AutoPayInfoDataBean apMemberDataBean = new AutoPayInfoDataBean();
			if (StringUtils.equalsIgnoreCase(msisdn.getMlType(), ApsConstants.ML_MEMBER)) {

				apMemberDataBean = autopayService.getAutopayData(apMemberDataBean, msisdn.getMsisdn());
				apMemberDataBean.setMlType(ApsConstants.ML_MEMBER);
				apMemberDataBean.setCalculateOnlyStandard(apDataBean.isCalculateOnlyStandard());
				
				AutoPayDiscountPromoApplied autoPayDiscountPromoApplied = null;
				// Start: Added below code for F107041.
				// Feature toggle on send to mlMember details else AO details.
				autoPayDiscountPromoApplied = getAutoPayDiscountPromoData(apDataBean, msisdn, apMemberDataBean);
			
				if (null != autoPayDiscountPromoApplied) {
					autoPayDiscountPromoApplied.setMlType(ApsConstants.ML_MEMBER);
					autoPayDiscountPromoApplied.setApPromoMsisdn(apMemberDataBean.getMsisdn());
					if (null == apDataBean.getAutoPayDiscountPromoApplied()) {
						apDataBean.setAutoPayDiscountPromoApplied(new ArrayList<>());
					}
					apDataBean.getAutoPayDiscountPromoApplied().add(autoPayDiscountPromoApplied);
				}
			}
		}

	}

	private AutoPayDiscountPromoApplied getAutoPayDiscountPromoData(AutoPayInfoDataBean apDataBean,
			MsisdnDetails msisdn, AutoPayInfoDataBean apMemberDataBean) throws AutopayDaoException {
		AutoPayDiscountPromoApplied autoPayDiscountPromoApplied;
		
		
		logger.debug("Insisde processMemberForPrimary plcfgUtil "+plcfgUtil);
		
		featureToggleChk = plcfgUtil.isFeatureToggleEnabledFromConfigMap(ApsConstants.F107041);
	
		logger.debug("Insisde processMemberForPrimary msisdn {} and featureToggleChk {} "
				+ "and MemberDataBean Information {} and Account Owner Information {} ",msisdn , featureToggleChk
				,apMemberDataBean,apDataBean.toString());
		
		if (featureToggleChk) {
			// Set AO - AP enrollment
			if (null != apDataBean.getApDetails()) {
				apMemberDataBean
						.setMlAOEnrollmentStartdDate(null != apDataBean.getApDetails().getEnrollmentStartdDate()
								? apDataBean.getApDetails().getEnrollmentStartdDate()
								: null);
				apMemberDataBean.setMlAutopayStatus(apDataBean.getApDetails().getAutopayStatus());
			} 
			// Set AO - AP status
			
			apMemberDataBean.setMlAOmsisdn(apDataBean.getMsisdn());
			logger.debug(" MemberDataBean value : {} ",apMemberDataBean.toString());
			
			autoPayDiscountPromoApplied = autoPayInfoDiscountService
					.getAutoPayInfoDiscount(getAutoPayDiscountCalculationBean(apMemberDataBean));
		} // End: F107041
		else {
			autoPayDiscountPromoApplied = autoPayInfoDiscountService
					.getAutoPayInfoDiscount(getAutoPayDiscountCalculationBean(apDataBean));
		}
		return autoPayDiscountPromoApplied;
	}

	/**
	 * User: esuspal , Date: Dec 21, 2020 6:30:08 PM 2020
	 *
	 * Purpose: Process request for GBM and mltype PRIMARY without member
	 *
	 * US/D/F Number: F1216
	 * 
	 * Return Type: AutoPayInfoDataBean
	 *
	 * @param msisdnDetails
	 * @return
	 * @throws AutopayServiceException
	 * @throws AutopayDaoException
	 */
	private AutoPayInfoDataBean processGBMReq(List<MsisdnDetails> msisdnDetails)
			throws AutopayServiceException, AutopayDaoException {
		AutoPayInfoDataBean apDataBean = new AutoPayInfoDataBean();
		for (MsisdnDetails msisdn : msisdnDetails) {
			apDataBean = autopayService.getAutopayData(apDataBean, msisdn.getMsisdn());
			/**
			 * Getting ap promo/discount details after evaluating the criteria
			 */
			AutoPayDiscountPromoApplied autoPayDiscountPromoApplied = autoPayInfoDiscountService
					.getAutoPayInfoDiscount(getAutoPayDiscountCalculationBean(apDataBean));
			if (null != autoPayDiscountPromoApplied) {
				List<AutoPayDiscountPromoApplied> autoPayDiscountPromoAppliedList = new ArrayList<AutoPayDiscountPromoApplied>();
				autoPayDiscountPromoAppliedList.add(autoPayDiscountPromoApplied);
				apDataBean.setAutoPayDiscountPromoApplied(autoPayDiscountPromoAppliedList);
			}

			List<FeatureInfo> featureInfoList = autopayService.getFeatureInfoList(apDataBean, msisdn.getMsisdn());
			apDataBean.setFeatureInfoList(featureInfoList);
			apDataBean = autopayService.getAutoPayEventTracker(apDataBean);

		}

		return apDataBean;
	}

	/**
	 * User: esuspal , Date: Dec 21, 2020 6:30:40 PM 2020
	 *
	 * Purpose: Process request for mltype MEMBER only
	 *
	 * US/D/F Number: F1216
	 * 
	 * Return Type: AutoPayInfoDataBean
	 *
	 * @param msisdnDetails
	 * @return
	 * @throws AutopayServiceException
	 * @throws AutopayDaoException
	 */
	private AutoPayInfoDataBean processMultilineMemberReq(List<MsisdnDetails> msisdnDetails)
			throws AutopayServiceException, AutopayDaoException {
		AutoPayInfoDataBean apDataBean = new AutoPayInfoDataBean();
		apDataBean.setMlType(ApsConstants.ML_MEMBER);
		for (MsisdnDetails msisdn : msisdnDetails) {
			apDataBean = autopayService.getAutopayData(apDataBean, msisdn.getMsisdn());

			List<FeatureInfo> featureInfoList = autopayService.getFeatureInfoList(apDataBean, msisdn.getMsisdn());
			apDataBean.setFeatureInfoList(featureInfoList);
			apDataBean = autopayService.getAutoPayEventTracker(apDataBean);

		}

		return apDataBean;
	}
	
	private AutoPayDiscountCalculationBean getAutoPayDiscountCalculationBean(AutoPayInfoDataBean bean) {
		AutoPayDiscountCalculationBean autoPayDiscountCalculationBean = new AutoPayDiscountCalculationBean();
		autoPayDiscountCalculationBean.setActivationStartDate(bean.getAutoPaySubscriberDtl().getActivateDate());
		autoPayDiscountCalculationBean.setMsisdn(bean.getAutoPaySubscriberDtl().getMsisdn());
		autoPayDiscountCalculationBean.setRatePlanId(String.valueOf(bean.getAutoPaySubscriberDtl().getRatePlanId()));
		if (null != bean.getApDetails() && StringUtils.isNotBlank(bean.getApDetails().getAccountId())) {
			autoPayDiscountCalculationBean.setEnrollmentStartDate(bean.getApDetails().getEnrollmentStartdDate());
			autoPayDiscountCalculationBean.setTopUpCycleValue(bean.getApDetails().getRecurringTopupCycleCDType());
			autoPayDiscountCalculationBean.setApDiscountCode(bean.getApDetails().getApCurrentdiscountCode());
		} else {
			autoPayDiscountCalculationBean.setTopUpCycleValue(bean.getAutoPaySubscriberDtl().getTopUpCycleCD());
		}
		autoPayDiscountCalculationBean.setCalculateOnlyStandard(bean.isCalculateOnlyStandard());
		// Start: Added below code for F107041.
		if (featureToggleChk && StringUtils.equalsIgnoreCase(ApsConstants.ML_MEMBER, bean.getMlType())
				&& null != bean.getMlAOEnrollmentStartdDate() && null != bean.getMlAutopayStatus()) {
			autoPayDiscountCalculationBean.setMlAOEnrollmentStartdDate(bean.getMlAOEnrollmentStartdDate());
			autoPayDiscountCalculationBean.setMlAutopayStatus(bean.getMlAutopayStatus());
			autoPayDiscountCalculationBean.setMsisdn(bean.getMlAOmsisdn());
			autoPayDiscountCalculationBean.setCalculateOnlyStandard(false);
			autoPayDiscountCalculationBean.setMlType(bean.getMlType());

		}
		// End: F107041
		return autoPayDiscountCalculationBean;
	}

}
