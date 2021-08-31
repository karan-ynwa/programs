package com.ericsson.eus.aps.common.exception;

/**
 * 
 * FILE_NAME: AutopayServiceException.java
 * 
 * MODULE DESCRIPTION: Custom Autopay Exception, US/F/D: US84030
 *
 * @author eihsnad, Date: Sep 10, 2020 2:55:18 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class AutopayServiceException extends RuntimeException {

	private static final long serialVersionUID = 7492737827046916786L;
	private Integer faultCode;
	private String faultText;
	private String serviceName;

	private FaultGlobalCode faultGlobalCode;

	/**
	 * 
	 * @param responseCode
	 * @param responseText
	 * @param serviceName
	 */
	public AutopayServiceException(int responseCode, String responseText, String serviceName) {
		super(responseText);
		this.faultCode = responseCode;
		this.faultText = responseText;
		this.serviceName = serviceName;
	}

	/**
	 * 
	 * @param faultGlobalCode
	 * @param faultText
	 */
	public AutopayServiceException(FaultGlobalCode faultGlobalCode, String faultText) {

		this.faultGlobalCode = faultGlobalCode;
		this.faultText = faultText;
		this.faultCode = Integer.valueOf(this.faultGlobalCode.getStatusCode());
	}

	/**
	 * 
	 * @param faultGlobalCode
	 * @param faultText
	 * @param serviceName
	 */
	public AutopayServiceException(FaultGlobalCode faultGlobalCode, String faultText, String serviceName) {
		super(faultText);
		this.faultGlobalCode = faultGlobalCode;
		this.faultText = faultText;
		this.faultCode = Integer.valueOf(this.faultGlobalCode.getStatusCode());
		this.serviceName = serviceName;
	}

	/**
	 * @param faultGlobalCode
	 * @param e
	 */
	public AutopayServiceException(FaultGlobalCode faultGlobalCode, Throwable e) {
		super(e);
		this.faultGlobalCode = faultGlobalCode;
		this.faultCode = Integer.valueOf(this.faultGlobalCode.getStatusCode());
		this.faultText = e.getMessage();// OTHER SERVICE ERROR
	}
	/**
	 * @return the faultCode
	 */
	public Integer getFaultCode() {
		return faultCode;
	}

	/**
	 * @param faultCode the faultCode to set
	 */
	public void setFaultCode(Integer faultCode) {
		this.faultCode = faultCode;
	}

	/**
	 * @return the faultText
	 */
	public String getFaultText() {
		return faultText;
	}

	/**
	 * @param faultText the faultText to set
	 */
	public void setFaultText(String faultText) {
		this.faultText = faultText;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return faultGlobalCode
	 */
	public FaultGlobalCode getFaultGlobalCode() {
		return faultGlobalCode;
	}

	public enum FaultGlobalCode {
		// custom plugin fault code 4XXX
		GLOBAL_PLUGIN_RUNTIME_FAILURE("4001"), 
		READ_TIMEOUT("4004"), 
		CONNECTION_TIMEOUT("4002"),
		CONNECTION_REFUSED("4002"), 
		MANDATORY_FIELD_MISSING("5016"),
		AUTOPAY_ALREADY_ENABLED("5016"),
		AUTOPAY_ALREADY_DISABLED("5016"),
		INTERNAL_FAILURE("4001"),
		APS_INVALID_MESSAGE_FORMAT("5016"), 
		LEGACY_RECURRING_ID_NOT_SUPPORTED("5016"), 
		CSI_NOT_AVAILABLE("4008"), 
		APS_PB_PARAM_FAILURE("5051"),
		APS_PB_TEMPORARY_CONNECTION_ERROR("5054"), 
		APS_PB_TEMPORARY_TIMED_OUT("5055"),
		APS_PB_INSUFFICIENT_BALANCE("5052"), 
		APS_PB_INSUFFICIENT_PERMISSION("5053"),
		INVALID_MSISDN("5002");

		private String statusCode;

		private FaultGlobalCode(String s) {
			statusCode = s;
		}

		public String getStatusCode() {
			return statusCode;
		}

		public static boolean contains(String statusCode) {
			for (FaultGlobalCode val : values()) {
				if (val.getStatusCode().equalsIgnoreCase(statusCode))
					return true;
			}
			return false;
		}
	}
}
