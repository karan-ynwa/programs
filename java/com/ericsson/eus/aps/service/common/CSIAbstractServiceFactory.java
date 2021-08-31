package com.ericsson.eus.aps.service.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ericsson.eus.aps.service.config.CSIPluginConfig;
import com.ericsson.eus.csi.exception.CSIServiceException;
import com.ericsson.eus.csi.service.CSIPlugin;
import com.ericsson.eus.csi.service.CSIPluginService;
import com.ericsson.eus.csi.util.CSIConfig;

/**
 * 
 * FILE_NAME: CSIAbstractServiceFactory.java
 * 
 * MODULE DESCRIPTION: Create CSI-plugin Instance, US84030
 *
 * @author eihsnad, Date: Sep 10, 2020 2:13:32 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class CSIAbstractServiceFactory {
	private CSIAbstractServiceFactory() {

	}

	private static CSIPluginService instance = null;
	private static Log log = LogFactory.getLog(CSIAbstractServiceFactory.class);

	/**
	 * User: eihsnad , Date: Sep 10, 2020 2:15:24 PM 2020
	 * Purpose: get CSI instance
	 * US/D/F Number: US84030
	 * Return Type: CSIConfig
	 * @param props
	 * @return CSIPluginService
	 * @throws CSIServiceException
	 */
	public static CSIPluginService initCSIService(CSIPluginConfig config) throws CSIServiceException {
		if (instance == null) {
			instance = CSIPlugin.getInstance(getPluginConfig(config));
			if (log.isDebugEnabled()) {
				log.debug("CSIServiceImpl instance created");
			}
		}

		return instance;
	}

	/**
	 * User: eihsnad , Date: Sep 10, 2020 2:15:24 PM 2020
	 * Purpose: create instance using properties
	 * US/D/F Number:US84030
	 * Return Type: CSIConfig
	 * @param props
	 * @return CSIConfig
	 */
	private static CSIConfig getPluginConfig(CSIPluginConfig config) {
		final Boolean enableCXFLogging = config.getEnableLogging() == null
				? Boolean.FALSE
				: config.getEnableLogging();
		final Boolean enableXSLTTransformation = config.getEnableXstTransformation() == null ? Boolean.TRUE
				: config.getEnableXstTransformation();
		return com.ericsson.eus.csi.util.CSIConfig.valueOf(
				config.getMaxTotalConnections(), config.getMaxConnectionsPerHost(), config.getConnectionPoolTimeout(),
				config.getConnectionTimeout(), config.getReadTimeout(), config.getEndpointAddress(), null,
				config.getIoThreadCount(), config.getUsername(), config.getPassword(), enableCXFLogging,
				enableXSLTTransformation, config.getToggleMap());
	}
}
