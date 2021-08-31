package com.ericsson.eus.aps.service.data.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * FILE_NAME: SubscriberInfoBean.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D: F1216
 *
 * @author esuspal, Date: Nov 23, 2020 8:37:58 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeatureInfoBean {

	private String featureDesc;
	private String featureCode;
	private String featureCost;

	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return "FeatureInfo [featureDesc=" + getFeatureDesc() + ",featureCode=" + getFeatureCode() + ",featureCost= "
				+ getFeatureCost() + "]";
	}

	/**
	 * @return the featureDesc
	 */
	public String getFeatureDesc() {
		return featureDesc;
	}

	/**
	 * @param featureDesc
	 *            the featureDesc to set
	 */
	public void setFeatureDesc(String featureDesc) {
		this.featureDesc = featureDesc;
	}

	/**
	 * @return the featureCode
	 */
	public String getFeatureCode() {
		return featureCode;
	}

	/**
	 * @param featureCode
	 *            the featureCode to set
	 */
	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}

	/**
	 * @return the featureCost
	 */
	public String getFeatureCost() {
		return featureCost;
	}

	/**
	 * @param featureCost
	 *            the featureCost to set
	 */
	public void setFeatureCost(String featureCost) {
		this.featureCost = featureCost;
	}

	

}
