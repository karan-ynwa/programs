package com.ericsson.eus.aps.service.data.beans;

import java.util.Date;
import java.util.List;

import com.ericsson.eus.aps.dao.model.AccountAutopayDtls;
import com.ericsson.eus.aps.dao.model.AutoPayEventTracker;
import com.ericsson.eus.aps.dao.model.AutoPaySubscriberDtl;

/**
 * 
 * FILE_NAME: AutoPayDataBean.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Sep 14, 2020 4:15:19 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class AutoPayInfoDataBean {
	private String msisdn;
	private String discountCode;
	private String mlType;
	private AutoPaySubscriberDtl autoPaySubscriberDtl;
	private AccountAutopayDtls apDetails;
	private boolean isCalculateOnlyStandard = false;
	private List<AutoPayDiscountPromoApplied> autoPayDiscountPromoApplied;
	private List<AutoPayEventTracker> autoPayEventTrackerList;
	private List<FeatureInfo> featureInfoList;
	private Date mlAOEnrollmentStartdDate;
	private String mlAutopayStatus;
	private String mlAOmsisdn;

	/**
	 * User: ekxrxax , Date: Sep 16, 2020 2:24:44 AM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "AutoPayInfoDataBean [msisdn=" + msisdn + ", discountCode=" + discountCode + ", mlType=" + mlType
				+ ", autoPaySubscriberDtl=" + autoPaySubscriberDtl + ", apDetails=" + apDetails
				+ ", isCalculateOnlyStandard=" + isCalculateOnlyStandard + ", autoPayDiscountPromoApplied="
				+ autoPayDiscountPromoApplied + ", autoPayEventTrackerList=" + autoPayEventTrackerList
				+ ", featureInfoList=" + featureInfoList + ", mlAOEnrollmentStartdDate=" + mlAOEnrollmentStartdDate
				+ ", mlAutopayStatus=" + mlAutopayStatus + ", mlAOmsisdn=" + mlAOmsisdn + "]";
	}

	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}
	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	/**
	 * @return the discountCode
	 */
	public String getDiscountCode() {
		return discountCode;
	}
	/**
	 * @param discountCode the discountCode to set
	 */
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}



	/**
	 * @return the apDetails
	 */
	public AccountAutopayDtls getApDetails() {
		return apDetails;
	}

	/**
	 * @param apDetails
	 *            the apDetails to set
	 */
	public void setApDetails(AccountAutopayDtls apDetails) {
		this.apDetails = apDetails;
	}

	/**
	 * @return the autoPayDiscountPromoApplied
	 */
	public List<AutoPayDiscountPromoApplied> getAutoPayDiscountPromoApplied() {
		return autoPayDiscountPromoApplied;
	}

	/**
	 * @param autoPayDiscountPromoApplied
	 *            the autoPayDiscountPromoApplied to set
	 */
	public void setAutoPayDiscountPromoApplied(List<AutoPayDiscountPromoApplied> autoPayDiscountPromoApplied) {
		this.autoPayDiscountPromoApplied = autoPayDiscountPromoApplied;
	}

	/**
	 * @return the autoPayEventTrackerList
	 */
	public List<AutoPayEventTracker> getAutoPayEventTrackerList() {
		return autoPayEventTrackerList;
	}

	/**
	 * @param autoPayEventTrackerList
	 *            the autoPayEventTrackerList to set
	 */
	public void setAutoPayEventTrackerList(List<AutoPayEventTracker> autoPayEventTrackerList) {
		this.autoPayEventTrackerList = autoPayEventTrackerList;
	}

	/**
	 * @return the featureInfoList
	 */
	public List<FeatureInfo> getFeatureInfoList() {
		return featureInfoList;
	}

	/**
	 * @param featureInfoList
	 *            the featureInfoList to set
	 */
	public void setFeatureInfoList(List<FeatureInfo> featureInfoList) {
		this.featureInfoList = featureInfoList;
	}

	/**
	 * @return the mlType
	 */
	public String getMlType() {
		return mlType;
	}

	/**
	 * @param mlType
	 *            the mlType to set
	 */
	public void setMlType(String mlType) {
		this.mlType = mlType;
	}

	/**
	 * @return the isCalculateOnlyStandard
	 */
	public boolean isCalculateOnlyStandard() {
		return isCalculateOnlyStandard;
	}

	/**
	 * @param isCalculateOnlyStandard
	 *            the isCalculateOnlyStandard to set
	 */
	public void setCalculateOnlyStandard(boolean isCalculateOnlyStandard) {
		this.isCalculateOnlyStandard = isCalculateOnlyStandard;
	}

	/**
	 * @return the autoPaySubscriberDtl
	 */
	public AutoPaySubscriberDtl getAutoPaySubscriberDtl() {
		return autoPaySubscriberDtl;
	}

	/**
	 * @param autoPaySubscriberDtl
	 *            the autoPaySubscriberDtl to set
	 */
	public void setAutoPaySubscriberDtl(AutoPaySubscriberDtl autoPaySubscriberDtl) {
		this.autoPaySubscriberDtl = autoPaySubscriberDtl;
	}
	
	/**
	 * @return the mlAOEnrollmentStartdDate
	 */
	public Date getMlAOEnrollmentStartdDate() {
		return mlAOEnrollmentStartdDate;
	}

	/**
	 * @param mlAOEnrollmentStartdDate the mlAOEnrollmentStartdDate to set
	 */
	public void setMlAOEnrollmentStartdDate(Date mlAOEnrollmentStartdDate) {
		this.mlAOEnrollmentStartdDate = mlAOEnrollmentStartdDate;
	}

	/**
	 * @return the mlAutopayStatus
	 */
	public String getMlAutopayStatus() {
		return mlAutopayStatus;
	}

	/**
	 * @param mlAutopayStatus the mlAutopayStatus to set
	 */
	public void setMlAutopayStatus(String mlAutopayStatus) {
		this.mlAutopayStatus = mlAutopayStatus;
	}

	/**
	 * @return the mlAOmsisdn
	 */
	public String getMlAOmsisdn() {
		return mlAOmsisdn;
	}

	/**
	 * @param mlAOmsisdn the mlAOmsisdn to set
	 */
	public void setMlAOmsisdn(String mlAOmsisdn) {
		this.mlAOmsisdn = mlAOmsisdn;
	}
	
}
