package com.ericsson.eus.aps.service.data.beans;

import java.util.Date;

public class AutoPayDiscountCalculationBean {

	private String ratePlanId;
	
	private boolean isCalculateOnlyStandard = false;
	
	private Date activationStartDate;
	
	private Date enrollmentStartDate;
	
	private String topUpCycleValue;
	
	private String apDiscountCode;
	
	private String msisdn;

	private String mlType;
	
	private Date mlAOEnrollmentStartdDate;
	private String mlAutopayStatus;
		

	public boolean isCalculateOnlyStandard() {
		return isCalculateOnlyStandard;
	}

	public void setCalculateOnlyStandard(boolean isCalculateOnlyStandard) {
		this.isCalculateOnlyStandard = isCalculateOnlyStandard;
	}

	public String getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(String ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public Date getActivationStartDate() {
		return activationStartDate;
	}

	public void setActivationStartDate(Date activationStartDate) {
		this.activationStartDate = activationStartDate;
	}


	public Date getEnrollmentStartDate() {
		return enrollmentStartDate;
	}

	public void setEnrollmentStartDate(Date enrollmentStartDate) {
		this.enrollmentStartDate = enrollmentStartDate;
	}

	public String getTopUpCycleValue() {
		return topUpCycleValue;
	}

	public void setTopUpCycleValue(String topUpCycleValue) {
		this.topUpCycleValue = topUpCycleValue;
	}

	public String getApDiscountCode() {
		return apDiscountCode;
	}

	public void setApDiscountCode(String apDiscountCode) {
		this.apDiscountCode = apDiscountCode;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getMlType() {
		return mlType;
	}

	public void setMlType(String mlType) {
		this.mlType = mlType;
	}

	public Date getMlAOEnrollmentStartdDate() {
		return mlAOEnrollmentStartdDate;
	}

	public void setMlAOEnrollmentStartdDate(Date mlAOEnrollmentStartdDate) {
		this.mlAOEnrollmentStartdDate = mlAOEnrollmentStartdDate;
	}

	public String getMlAutopayStatus() {
		return mlAutopayStatus;
	}

	public void setMlAutopayStatus(String mlAutopayStatus) {
		this.mlAutopayStatus = mlAutopayStatus;
	}

}
