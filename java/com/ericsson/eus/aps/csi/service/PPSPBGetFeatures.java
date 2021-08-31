package com.ericsson.eus.aps.csi.service;

import com.ericsson.eus.csi.beans.InquirePrepaidFeaturesResponse;

/**
 * 
 * FILE_NAME: PPSPBGetFeatures.java
 * 
 * MODULE DESCRIPTION: Interface for Get Features form CSI , US84030
 *
 * @author eihsnad, Date: Sep 11, 2020 7:36:10 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public interface PPSPBGetFeatures {
	/**
	 * Extract Features from payment broker based on msisdn
	 * 
	 * @param msisdn
	 * @param mode
	 * @param originatingRepresentativeId
	 * @param conversationId
	 * @return InquirePrepaidFeaturesResponse
	 */
	public InquirePrepaidFeaturesResponse inquiredPPSBGetFeatures(String msisdn, String mode,
			String originatingRepresentativeId,
			String conversationId);
}
