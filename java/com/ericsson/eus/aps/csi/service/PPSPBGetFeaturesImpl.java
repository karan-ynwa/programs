package com.ericsson.eus.aps.csi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.ebi.alarms.constants.AlarmConstants;
import com.ericsson.ebi.alarms.util.Alarms;
import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException.FaultGlobalCode;
import com.ericsson.eus.aps.rest.common.RESTConstants.ApsConstants;
import com.ericsson.eus.aps.service.common.AutopayServiceAgent;
import com.ericsson.eus.aps.service.util.ApsUtil;
import com.ericsson.eus.csi.beans.InquirePrepaidFeaturesRequest;
import com.ericsson.eus.csi.beans.InquirePrepaidFeaturesResponse;
import com.ericsson.eus.csi.domain.Mode;
import com.ericsson.eus.csi.exception.CSIServiceApplicationException;
import com.ericsson.eus.csi.exception.CSIServiceException;
import com.ericsson.eus.csi.exception.CSIServiceFaultDetail;
import com.ericsson.eus.csi.util.CSIConstants.ExceptionType;

/**
 * 
 * FILE_NAME: PPSPBGetFeaturesImpl.java
 * 
 * MODULE DESCRIPTION: Service implementation of PPSPBGetFeatures , US84030
 *
 * @author eihsnad, Date: Sep 11, 2020 7:52:27 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
@Service
public class PPSPBGetFeaturesImpl implements PPSPBGetFeatures {
	private final Logger logger = LogManager.getLogger(PPSPBGetFeaturesImpl.class);

	@Autowired
	AutopayServiceAgent autopayServiceAgent;

	@Override
	public InquirePrepaidFeaturesResponse inquiredPPSBGetFeatures(String msisdn, String mode,
			String originatingRepresentativeId,
			String conversationId) {
		InquirePrepaidFeaturesResponse featureResponse = null;
		try {
				if (autopayServiceAgent.getCSIServiceInstance() != null) {
					InquirePrepaidFeaturesRequest inquirePrepaidFeaturesRequest = createInquirePrepaidFeaturesRequest(
							msisdn, mode, originatingRepresentativeId, conversationId);
					featureResponse = autopayServiceAgent.getCSIServiceInstance()
							.inquirePrepaidFeatures(inquirePrepaidFeaturesRequest);
				Alarms.getInstance().clear(AlarmConstants.ALARM_PB_UNAVAILABLE, 1);
					if (logger.isDebugEnabled()) {
						logger.debug("Received InquirePrepaidFeaturesResponse from CSIService");
					}
				}else {
					String errorDesc = "Internal Server Error  CSI-Plugin Instance return NULL";
					AutopayServiceException apsExp = new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE,
							ApsConstants.FAULT_CODE_CLIENT, "CSI-Plugin Instance is NULL");
					logger.error(msisdn, errorDesc, ExceptionType.CSIPLUGIN, apsExp);
				Alarms.getInstance().raise(AlarmConstants.ALARM_PB_UNAVAILABLE, 1);
					throw apsExp;
				}
			
			} catch (CSIServiceException csiServiceExp) {
				String errorDescription = "Caught CSIServiceEception. " + csiServiceExp.getMessage();
				AutopayServiceException apsExp = new AutopayServiceException(ApsUtil.mapToAPSFaultCode(csiServiceExp.getFaultCode()),
						ApsUtil.mapToErrorDescription(csiServiceExp.getFaultCode()), "CSIServiceException ->InquirePrepaidFeaturesRequest");
				logger.error(msisdn, errorDescription, ExceptionType.CSIPLUGIN,
						apsExp);
				if (CSIServiceException.FaultCode.REMOTE_COMMUNICATION_ERROR.equals(csiServiceExp.getFaultCode())
						|| CSIServiceException.FaultCode.OTHER_SERVICE_ERROR.equals(csiServiceExp.getFaultCode())
						|| CSIServiceException.FaultCode.CONFIGURATION_ERROR.equals(csiServiceExp.getFaultCode())) {
				Alarms.getInstance().raise(AlarmConstants.ALARM_PB_UNAVAILABLE, 1);
				} else {
				// for Other Fault code
				Alarms.getInstance().clear(AlarmConstants.ALARM_PB_UNAVAILABLE, 1);
				}
			throw apsExp;
		} catch (CSIServiceApplicationException csiServiceAppExp) {
			String errorDescription = "Caught CSIServiceApplicationException. " + csiServiceAppExp.getMessage();
			CSIServiceFaultDetail faultDetail = csiServiceAppExp.getCsiServiceFaultDetail();
			FaultGlobalCode faultCode = ApsUtil.mapToEBIFaultCode(faultDetail.getClientFaultCode(), false);
			AutopayServiceException apsExp = new AutopayServiceException(faultCode,
					ApsUtil.mapToErrorDescription(faultCode),
					"CSIServiceApplicationException ->InquirePrepaidFeaturesRequest");
			logger.error(msisdn, errorDescription, ExceptionType.CSIPLUGIN, apsExp);
			Alarms.getInstance().clear(AlarmConstants.ALARM_PB_UNAVAILABLE, 1);
			throw apsExp;
		}
		return featureResponse;
	}

	public InquirePrepaidFeaturesRequest createInquirePrepaidFeaturesRequest(String msisdn, String mode,
			String originatingRepresentativeId, String conversationId) {
		String errorDesc = "Invalid Mode Type PBGetFeatures: " + mode;
		InquirePrepaidFeaturesRequest inquirePrepaidFeaturesRequest = new InquirePrepaidFeaturesRequest();

		if (mode.equalsIgnoreCase(ApsConstants.Mode.A.getMode())) {
			inquirePrepaidFeaturesRequest.setMode(Mode.A);
		} else if (mode.equalsIgnoreCase(ApsConstants.Mode.C.getMode())) {

			inquirePrepaidFeaturesRequest.setMode(Mode.C);
		} else if (mode.equalsIgnoreCase(ApsConstants.Mode.E.getMode())) {

			inquirePrepaidFeaturesRequest.setMode(Mode.E);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug(errorDesc);
			}
			AutopayServiceException apsExcp = new AutopayServiceException(FaultGlobalCode.APS_INVALID_MESSAGE_FORMAT, errorDesc, "InquirePrepaidFeaturesRequest");
			logger.error(inquirePrepaidFeaturesRequest.getSubscriberNumber(),errorDesc, ExceptionType.CSIPLUGIN, apsExcp);
			throw apsExcp;
		}
		inquirePrepaidFeaturesRequest.setSubscriberNumber(msisdn);
		inquirePrepaidFeaturesRequest.setConversationId(conversationId);
		inquirePrepaidFeaturesRequest.setOriginatingRepresentativeId(originatingRepresentativeId);
		return inquirePrepaidFeaturesRequest;
	}
}
