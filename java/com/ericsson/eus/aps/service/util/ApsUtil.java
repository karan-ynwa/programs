package com.ericsson.eus.aps.service.util;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException.FaultGlobalCode;
import com.ericsson.eus.aps.rest.common.RESTConstants.ApsConstants;
import com.ericsson.eus.aps.service.data.beans.BaseResponse;
import com.ericsson.eus.cef.common.CefConstants;
import com.ericsson.eus.csi.domain.ClientFaultCode;
import com.ericsson.eus.csi.exception.CSIServiceException;


/**
 * 
 * FILE_NAME: ApsUtil.java
 * 

 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Sep 11, 2020 3:44:31 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class ApsUtil {
	private static final String SYSTEM_ISSUE_MESSAGE = "We seem to be experiencing system issues, please try again in 10 minutes";
	private static Logger logger = LogManager.getLogger(ApsUtil.class);
	private static Map<FaultGlobalCode, String> errorDescriptionMapper = new HashMap<FaultGlobalCode, String>();

	static {
		errorDescriptionMapper.put(FaultGlobalCode.APS_PB_PARAM_FAILURE,
				"We seem to be experiencing system issues, please call 611 from your prepaid phone for further assistance.");

		errorDescriptionMapper.put(FaultGlobalCode.APS_PB_INSUFFICIENT_PERMISSION,
				SYSTEM_ISSUE_MESSAGE);

		errorDescriptionMapper.put(FaultGlobalCode.APS_PB_INSUFFICIENT_BALANCE,
				"your account balance is insufficient to purchase this feature package please add money to your account and try again");

		errorDescriptionMapper.put(FaultGlobalCode.APS_PB_TEMPORARY_CONNECTION_ERROR,
				SYSTEM_ISSUE_MESSAGE);

		errorDescriptionMapper.put(FaultGlobalCode.APS_PB_TEMPORARY_TIMED_OUT,
				SYSTEM_ISSUE_MESSAGE);
	}


	private ApsUtil() {
		throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, "Utility class");
	}

	/**

	 * User: ekxrxax , Date: Sep 11, 2020 3:44:41 PM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: void
	 *
	 * @param msisdn
	 * @param string
	 */
	public static void validateMandatoryField(Object field, String fieldName) {


		String errorMsg = String.format(ApsConstants.INVALID_PARAM_VALUE,
				fieldName);

		if (field == null) {
			logger.error(errorMsg);
			throw new AutopayServiceException(
					FaultGlobalCode.MANDATORY_FIELD_MISSING, errorMsg);
		}
		if (field instanceof String && ((String) field).trim().length() == 0) {
			logger.error(errorMsg);
			throw new AutopayServiceException(
					FaultGlobalCode.MANDATORY_FIELD_MISSING, errorMsg);
		}

		if (field instanceof List<?> && ((List<?>) field).size() == 0) {
			logger.error(errorMsg);
			throw new AutopayServiceException(FaultGlobalCode.MANDATORY_FIELD_MISSING, errorMsg);
		}
	
	}

	/**
	 * User: ekxrxax , Date: Sep 15, 2020 2:04:27 PM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: BigDecimal
	 *
	 * @param promoAmount
	 * @param places
	 * @return
	 */
	public static BigDecimal roundBig(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	 
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.CEILING);
	    return bd;
	}

	public static String roundDouble(String value) {
		if (NumberUtils.isParsable(value)) {
			return String.format("%.2f", new Double(value));
		}
		return null;
	}

	public static FaultGlobalCode mapToAPSFaultCode(CSIServiceException.FaultCode faultCode) {
		switch (faultCode) {
		case CONFIGURATION_ERROR:
			return FaultGlobalCode.GLOBAL_PLUGIN_RUNTIME_FAILURE;
		case REQUEST_PARAMETER_ERROR:
			return FaultGlobalCode.APS_INVALID_MESSAGE_FORMAT;
		case RESPONSE_PARAMETER_ERROR:
		case OTHER_SERVICE_ERROR:
		case REMOTE_COMMUNICATION_ERROR:
			return FaultGlobalCode.CSI_NOT_AVAILABLE;
		default:
			return FaultGlobalCode.CSI_NOT_AVAILABLE;
		}
	}

	public static String mapToErrorDescription(CSIServiceException.FaultCode faultCode) {
		switch (faultCode) {
		case CONFIGURATION_ERROR:
			return "CSI Configuration Error";
		case RESPONSE_PARAMETER_ERROR:
			return "Invalid Response recieved from CSI";
		case OTHER_SERVICE_ERROR:
			return "Other Service Error";
		case REQUEST_PARAMETER_ERROR:
			return "Invalid Request";
		case REMOTE_COMMUNICATION_ERROR:
			return "Backend Unavailable";
		default:
			return "Backend Unavailable";
		}
	}

	/**
	 * Helper Method to map the 10 digit faultcode from CSIService to corresponding
	 * EBIFault Codes
	 * 
	 * @param faultCodeAndMessage
	 * @param isUpdatePrepaidFeatures
	 * @return ebiFaultCode
	 */
	public static FaultGlobalCode mapToEBIFaultCode(ClientFaultCode csiFaultCode, boolean isUpdatePrepaidFeatures) {
		if (csiFaultCode != null) {
			switch (csiFaultCode) {
			case FAULT_20000000001:
			case FAULT_20000000002:
			case FAULT_20000000003:
			case FAULT_20000000005:
			case FAULT_30000659001:
			case FAULT_30000659002:
			case FAULT_30000659003:
			case FAULT_30000659009:
			case FAULT_30000659025:
			case FAULT_30000671002:
			case FAULT_30002272002:
			case FAULT_50000000187:
			case FAULT_40000000001:
			case FAULT_40000000002:
			case FAULT_30002273002:
			case FAULT_30000109002:
			case FAULT_30002288002:
			case FAULT_30002098002:
			case FAULT_30002281009:
			case FAULT_30002282009:
			case FAULT_30002283009:
			case FAULT_30002280002:
			case FAULT_50000000016:
			case FAULT_10000000005:
			case FAULT_10000000002:
			case FAULT_50000000005:
			case FAULT_50000000006:
			case FAULT_50000000026:
			case FAULT_50000000027:
			case FAULT_50000000131:
			case FAULT_50000000049:

				return FaultGlobalCode.APS_PB_PARAM_FAILURE;

			case FAULT_20000000014:
				return FaultGlobalCode.APS_PB_TEMPORARY_CONNECTION_ERROR;

			// if the request being performed is UpdatePrepaidFeatures, return error code
			// 5054 if not return 5051
			case FAULT_20000000006:
				if (isUpdatePrepaidFeatures) {
					return FaultGlobalCode.APS_PB_TEMPORARY_TIMED_OUT;
				} else {
					return FaultGlobalCode.APS_PB_PARAM_FAILURE;
				}

			case FAULT_50000000186:
				return FaultGlobalCode.APS_PB_INSUFFICIENT_BALANCE;

			default:
				return FaultGlobalCode.APS_PB_PARAM_FAILURE;
			}
		}
		return null;

	}

	public static String mapToErrorDescription(FaultGlobalCode faultGlobalCode) {
		return errorDescriptionMapper.get(faultGlobalCode);
	}

	/**
	 * User: ekxrxax , Date: Sep 15, 2020 1:47:01 PM 2020
	 *
	 * Purpose: error response
	 *
	 * US/D/F Number: US84027
	 * 
	 * Return Type: BaseResponse
	 *
	 * @param e
	 * @return
	 */
	public static BaseResponse buildErrorResponse(AutopayServiceException e) {
		BaseResponse response;
		response = new BaseResponse();
		response.setResponseCode(e.getFaultCode());
		response.setResponseText(e.getFaultText());
		logger.error(e.getFaultText());
		logger.debug(e.toString());
		return response;
	}

	/**
	 * User: esuspal , Date: Dec 5, 2020 10:15:46 PM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: String
	 *
	 * @param date
	 * @return
	 */
	public static String formatDate(java.util.Date date) {

		return date != null ? new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(date) : null;
	}

	public static Date getUtilDate(java.sql.Date date) {
		if (null != date) {
			return new java.util.Date(date.getTime());
		}

		return null;
	}

	public static Date getUtilDate(java.sql.Timestamp date) {
		if (null != date) {
			return new java.util.Date(date.getTime());
		}

		return null;
	}

	public static String getCurrentDateTime() {
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss.S");
		Date dateobj = new Date();
		String currentDateTime = df.format(dateobj);
		return currentDateTime;
	}
	
	public static Date getcurrentDate() {
		Date currentDate = new java.util.Date();
		TimeZone tz = TimeZone.getTimeZone(CefConstants.TIMEZONE);
		boolean isDSTtoday = tz.inDaylightTime(currentDate);
		if (isDSTtoday) {
			int sec = tz.getDSTSavings() / 1000;// for no. of seconds
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			cal.add(Calendar.SECOND, sec);
			currentDate = cal.getTime();
		}
		
		return currentDate;
	}

}
