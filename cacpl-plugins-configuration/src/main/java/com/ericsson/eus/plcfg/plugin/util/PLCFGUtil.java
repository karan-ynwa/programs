package com.ericsson.eus.plcfg.plugin.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ericsson.eus.plcfg.plugin.LoadConfigMapProp;

/*
 * FILE_NAME:  CSIUtil.java
 * 
 * MODULE DESCRIPTION: This class is used to contains all utility.
 *
 * @author eznraps, Date 11:38:31 PM 2021
 * 
 * (c) COPYRIGHT 2021 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 * 
*/

public class PLCFGUtil {

	private static Log log = LogFactory.getLog(PLCFGUtil.class.getName());
	
	/**
	 * This method returns true or false based on conditions
	 * 
	 * User: eznraps , Date: Feb 5, 2021 11:48:57 PM 2021
	 *
	 * US/D/F Number: F96456 : US96674
	 * 
	 * @param featureCode
	 * @return boolean
	 *
	 */
	public boolean isFeatureToggleEnabledFromConfigMap(String featureCode) {
		boolean isFeatuereToggleEnabled;
		String featureToggleValue;
		featureToggleValue = LoadConfigMapProp.getInstance().getProp(featureCode);
		log.debug("Feature code from configMap :" + featureCode + ", featureToggleValue:" + featureToggleValue);
		switch (featureToggleValue.toUpperCase()) {
		case "ON":
			log.debug("Feature toggle is ON >>> Returning true");
			isFeatuereToggleEnabled = true;
			break;
		case "OFF":
			log.debug("Feature toggle is OFF >>> Returning false");
			isFeatuereToggleEnabled = false;
			break;
		default:
			log.debug("No favourable value for feature toggle >>> Returning false");
			isFeatuereToggleEnabled = false;
			break;
		}
		return isFeatuereToggleEnabled;
	}
}
