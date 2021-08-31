package com.ericsson.eus.aps.dao.model;

import java.io.Serializable;
/**
 * 
 * FILE_NAME: VuRatePlan.java
 * 
 * MODULE DESCRIPTION:rate plan bean, US84027
 *
 * @author ekxrxax, Date: Sep 11, 2020 6:33:02 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class VuRatePlan implements Serializable {

	private static final long serialVersionUID = -8633402482309902436L;
	
	private Long ratePlanId;
	
	private String ratePlanCode;
	
	private long apRecurringId;
	
	private String ratePlanName;

	public Long getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(Long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public String getRatePlanCode() {
		return ratePlanCode;
	}

	public void setRatePlanCode(String ratePlanCode) {
		this.ratePlanCode = ratePlanCode;
	}

	public long getApRecurringId() {
		return apRecurringId;
	}

	public void setApRecurringId(long apRecurringId) {
		this.apRecurringId = apRecurringId;
	}
	
	public String getRatePlanName() {
		return ratePlanName;
	}

	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}

	@Override
	public String toString() {
		return "VuRatePlan [ratePlanId=" + ratePlanId + ", ratePlanCode=" + ratePlanCode + ", apRecurringId="
				+ apRecurringId + ", ratePlanName=" + ratePlanName + "]";
	}

}
