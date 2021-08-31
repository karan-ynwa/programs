package com.ericsson.eus.aps.service.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.ericsson.ebi.alarms.constants.AlarmConstants;
import com.ericsson.ebi.alarms.util.Alarms;
import com.ericsson.ebi.alarms.util.Alarms.AppNames;
import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException;
import com.ericsson.eus.aps.common.exception.AutopayServiceException.FaultGlobalCode;
import com.ericsson.eus.aps.dao.impl.EbiCacheConfigDaoImpl;
import com.ericsson.eus.aps.rest.common.RESTConstants.ApsConstants;
import com.ericsson.eus.aps.service.config.CSIPluginConfig;
import com.ericsson.eus.csi.exception.CSIServiceException;
import com.ericsson.eus.csi.service.CSIPluginService;

/**
 * 
 * FILE_NAME: AutopayServiceAgent.java
 * 
 * MODULE DESCRIPTION: Create Agent For Autopay Service, US84030
 *
 * @author eihsnad, Date: Sep 10, 2020 2:50:00 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
@Component("autopayserviceagent")
public class AutopayServiceAgent {
	private final Logger logger = LogManager.getLogger(AutopayServiceAgent.class);

	@Autowired
	private CSIPluginConfig csiPluginConfig;
	
	@Autowired
	private EbiCacheConfigDaoImpl ebiCacheConfigDao;
	
	private CSIPluginService csiPluginService;
	
	@PostConstruct
	public void init() {
		logger.debug("call @PostConstruct or init method of AutopayServiceAgent");
		try {
			csiPluginService = CSIAbstractServiceFactory.initCSIService(csiPluginConfig);
			ApsConfig.setEbiCacheConfigUpdateInfoRecordLocal(
					ebiCacheConfigDao.getEbiCacheConfigUpdateInfo());
			Alarms.getInstance(getAppID(), getHostAddress(), getLog(), AppNames.APS);
		} catch (DataAccessException ex) {
			logger.error(ex.getMessage());
			Alarms.getInstance().raise(AlarmConstants.DB_UNAVAILABLE, 1);
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, "Database error: " + ex.getMessage());
		} catch (CSIServiceException  e) {
			logger.error(e.getMessage());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, "Database error: " + e.getMessage());
		} catch (AutopayDaoException e) {
			logger.error(e.getMessage());
			throw new AutopayServiceException(FaultGlobalCode.INTERNAL_FAILURE, "Database error in Autopay: " + e.getMessage());
		}
		
		Alarms.getInstance().clear(AlarmConstants.DB_UNAVAILABLE, 1);
	}

	public CSIPluginService getCSIServiceInstance() {
		return csiPluginService;
	}

	public void destroyParam() {
		if (csiPluginService != null)
			csiPluginService.shutdown();
	}

	protected String getHostAddress() {
		String ipAddress = null;
		try {
			ipAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ipAddress = ApsConstants.DEFAULT_HOST_IP;
		}

		return ipAddress;
	}

	protected String getHostName() {
		String hostName = null;
		try {
			hostName = java.net.InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			hostName = ApsConstants.DEFAULT_HOST_NAME;
		}
		return hostName;
	}

	protected String getAppID() {
		return getHostName();
	}

	protected Logger getLog() {
		return LogManager.getLogger(ApsConstants.ALARM_LOGGER_NAME);
	}
}
