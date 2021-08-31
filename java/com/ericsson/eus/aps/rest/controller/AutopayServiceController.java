package com.ericsson.eus.aps.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException.FaultGlobalCode;
import com.ericsson.eus.aps.csi.service.PPSPBGetFeatures;
import com.ericsson.eus.aps.rest.common.RESTConstants;
import com.ericsson.eus.aps.rest.common.RESTConstants.ApsConstants;
import com.ericsson.eus.aps.rest.common.RESTConstants.CustomHeaders;
import com.ericsson.eus.aps.rest.common.RESTUtility;
import com.ericsson.eus.aps.rest.service.impl.AutopayServiceimpl;
import com.ericsson.eus.aps.service.data.beans.AutoPayEnrollRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayHistoryRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayNextCycleDateRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayNextCycleDateResponseBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayUnenrollRequestBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayUpdateRequestBean;
import com.ericsson.eus.aps.service.data.beans.BaseResponse;
import com.ericsson.eus.aps.service.data.validator.RequestValidator;

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
public class AutopayServiceController {
	private final Logger logger = LogManager.getLogger(AutopayServiceController.class);
	@Autowired
	private AutopayServiceimpl autopayService;

	@Autowired
	PPSPBGetFeatures ppspbGetFeatures;

	private static final String INTERNAL_ERROR_MSG = "An unexpected error occured";



	// F1217-US84042
	@PostMapping(value = "autopayUnenroll", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> autopayUnenroll(HttpServletRequest httpRequest,
			@RequestBody @Valid AutoPayUnenrollRequestBean request) {
		HttpHeaders header = new HttpHeaders();
		BaseResponse response = null;
		if (logger.isDebugEnabled()) {
			logger.debug(ApsConstants.REQUEST_MSG, request);
		}
		RequestValidator.validate(request);
		response = autopayService.autopayUnenroll(request);
		// Log the response
		if (logger.isDebugEnabled()) {
			logger.debug(ApsConstants.RESPONSE_MSG, response);
		}
		if (response == null) {
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, INTERNAL_ERROR_MSG);
		}
		header.add(CustomHeaders.RESPONSE_CODE, response.getResponseCode().toString());
		httpRequest.setAttribute(RESTConstants.RESPONSE_TEXT, response.getResponseText());
		return RESTUtility.createResponse(response, header, HttpStatus.OK);
	}

	/**
	 * User: eihsnad , Date: nov 9, 2020 9:26:25 PM 2020
	 *
	 * Purpose: Autopay History Request
	 *
	 * US/D/F Number: F1217-US84045
	 * 
	 * Return Type: BaseResponse
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws AutopayServiceException
	 */
	@PostMapping(value = "autoPayHistory", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> autopayHistory(HttpServletRequest httpRequest,
			@RequestBody @Valid AutoPayHistoryRequestBean request) {
		HttpHeaders header = new HttpHeaders();
		BaseResponse response = null;
		if (logger.isDebugEnabled()) {
			logger.debug(ApsConstants.REQUEST_MSG, request);
		}
		RequestValidator.validate(request);
		// return Success Response
		response = autopayService.getAutopayHistory(request);

		if (logger.isDebugEnabled()) {
			logger.debug(ApsConstants.RESPONSE_MSG, response);
		}
		header.add(CustomHeaders.RESPONSE_CODE, response.getResponseCode().toString());
		httpRequest.setAttribute(RESTConstants.RESPONSE_TEXT, response.getResponseText());
		return RESTUtility.createResponse(response, header, HttpStatus.OK);
	}

	/**
	 * User: eihsnad , Date: nov 11, 2020 19:26:32 PM 2020
	 *
	 * Purpose: Autopay Update Request
	 *
	 * US/D/F Number: F1217-US84043
	 * 
	 * Return Type: BaseResponse
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws AutopayServiceException
	 */
	@PostMapping(value = "autopayUpdate", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> autopayUpdate(HttpServletRequest httpRequest,
			@RequestBody @Valid AutoPayUpdateRequestBean request) {
		HttpHeaders header = new HttpHeaders();
		BaseResponse response = null;
		if (logger.isDebugEnabled()) {
			logger.debug(ApsConstants.REQUEST_MSG, request);
		}
		RequestValidator.validate(request);
		// return Response
		response = autopayService.autopayUpdate(request);
		if (logger.isDebugEnabled()) {
			logger.debug(ApsConstants.RESPONSE_MSG, response);
		}
		if (response == null) {
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, INTERNAL_ERROR_MSG);
		}
		header.add(CustomHeaders.RESPONSE_CODE, response.getResponseCode().toString());
		httpRequest.setAttribute(RESTConstants.RESPONSE_TEXT, response.getResponseText());
		
		return RESTUtility.createResponse(response, header, HttpStatus.OK);
	}

	/**
	 * 
	 * User: eiansrv , Date: Sep 30, 2020 1:53:12 PM 2020
	 *
	 * US/D/F Number: ATTPRE-84041 [F1217]
	 * 
	 * Return Type: ResponseEntity<BaseResponse>
	 *
	 * @param httpRequest
	 * @param request
	 * @return
	 */
	@PostMapping(value = "autoPayEnroll", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> autoPayEnroll(HttpServletRequest httpRequest,
			@RequestBody @Valid AutoPayEnrollRequestBean request) {
		HttpHeaders header = new HttpHeaders();
		BaseResponse response = null;
		RequestValidator.validate(request);
		if (logger.isDebugEnabled()) {
			logger.debug(ApsConstants.REQUEST_MSG, request);
		}

		response = autopayService.autopayEnroll(request);

		if (logger.isDebugEnabled()) {
			logger.debug(ApsConstants.RESPONSE_MSG, response);
		}

		if (response == null) {
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, INTERNAL_ERROR_MSG);
		}
		header.add(CustomHeaders.RESPONSE_CODE, response.getResponseCode().toString());
		httpRequest.setAttribute(RESTConstants.RESPONSE_TEXT, response.getResponseText());
		return RESTUtility.createResponse(response, header, HttpStatus.OK);
	}

	/**
	 * User: epinmon , Date: Jan 7, 2021 2:58:51 PM 2021
	 *
	 * Purpose: this method will calculate then validate and finally updates next
	 * auto pay cycle date.
	 *
	 * US/D/F Number:F1221/US88638
	 * 
	 * Return Type: ResponseEntity<BaseResponse>
	 *
	 * @param httpRequest
	 * @param request
	 * @return autoPayResponse nextCycleDate
	 */
	@PostMapping(value = "validateAndUpdateNextAutopayCycleDate", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> validateAndUpdateNextAutopayCycleDate(HttpServletRequest httpRequest,
			@RequestBody @Valid AutoPayNextCycleDateRequestBean request) {
		RequestValidator.validate(request);
		HttpHeaders header = new HttpHeaders();
		AutoPayNextCycleDateResponseBean autoPayResponse = (AutoPayNextCycleDateResponseBean) autopayService
				.validateAndUpdateNextAutopayCycleDate(request);
		if (autoPayResponse == null) {
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, INTERNAL_ERROR_MSG);
		}
		header.add(CustomHeaders.RESPONSE_CODE, autoPayResponse.getResponseCode().toString());
		httpRequest.setAttribute(RESTConstants.RESPONSE_TEXT, autoPayResponse.getResponseText());
		return RESTUtility.createResponse(autoPayResponse, header, HttpStatus.OK);
	}

}
