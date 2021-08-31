package com.ericsson.eus.aps.rest.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException.FaultGlobalCode;
import com.ericsson.eus.aps.rest.common.RESTConstants.CustomHeaders;
import com.ericsson.eus.aps.rest.controller.AutopayServiceController;
import com.ericsson.eus.aps.service.data.beans.BaseResponse;
import com.ericsson.eus.aps.service.util.ApsUtil;

@ControllerAdvice(basePackageClasses = {AutopayServiceController.class})
public class APSExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value = {AutopayServiceException.class})
	public ResponseEntity<BaseResponse> handleAutoPayServiceException(AutopayServiceException ex, HttpServletRequest httpRequest) {
		HttpHeaders header = new HttpHeaders();
		header.add(CustomHeaders.RESPONSE_CODE, String.valueOf(ex.getFaultCode()));
		httpRequest.setAttribute(RESTConstants.RESPONSE_TEXT, ex.getFaultText());
		BaseResponse response = ApsUtil.buildErrorResponse(ex);
		return RESTUtility.createResponse(response, header, HttpStatus.OK);
	}
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<BaseResponse> handleException(Exception ex, HttpServletRequest httpRequest) {
		HttpHeaders header = new HttpHeaders();
		header.add(CustomHeaders.RESPONSE_CODE, FaultGlobalCode.MANDATORY_FIELD_MISSING.getStatusCode());
		httpRequest.setAttribute(RESTConstants.RESPONSE_TEXT, ex.getMessage());
		BaseResponse response = new BaseResponse();
		response.setResponseText(ex.getMessage());
		response.setResponseCode(Integer.valueOf(FaultGlobalCode.INTERNAL_FAILURE.getStatusCode()));
		return RESTUtility.createResponse(response, header, HttpStatus.OK);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		HttpHeaders header = new HttpHeaders();
		BaseResponse response = new BaseResponse();
		response.setResponseText(ex.getBindingResult().getFieldError().getDefaultMessage());
		response.setResponseCode(Integer.valueOf(FaultGlobalCode.MANDATORY_FIELD_MISSING.getStatusCode()));
		header.add(CustomHeaders.RESPONSE_CODE, FaultGlobalCode.MANDATORY_FIELD_MISSING.getStatusCode());
		return new ResponseEntity<>(response, header, HttpStatus.BAD_REQUEST);
	}
	
	
	
}
