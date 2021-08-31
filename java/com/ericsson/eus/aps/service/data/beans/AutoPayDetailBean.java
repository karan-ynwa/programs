package com.ericsson.eus.aps.service.data.beans;

import java.io.Serializable;
import java.util.Date;

public class AutoPayDetailBean implements Serializable {

	/**
	 * User: eihsnad
	 *
	 */
	private static final long serialVersionUID = -212186936994922274L;
	private String msisdn;
	private String pdofLast4;
	private long recurringtopupcyclecdType;
	private Date enrollmentDate;
	private String accountNo;
	private String ratePlanCode;
	private String ratePlanName;
	private String apDiscountCode;
	private Date nextAutopayCycleDate;
	private Date activationDate;
	private long bonusUnits;

	public long getBonusUnits() {
		return bonusUnits;
	}

	public void setBonusUnits(long bonusUnits) {
		this.bonusUnits = bonusUnits;
	}

	public String getPdofLast4() {
		return pdofLast4;
	}

	public void setPdofLast4(String pdofLast4) {
		this.pdofLast4 = pdofLast4;
	}

	public long getRecurringtopupcyclecdType() {
		return recurringtopupcyclecdType;
	}

	public void setRecurringtopupcyclecdType(long recurringtopupcyclecdType) {
		this.recurringtopupcyclecdType = recurringtopupcyclecdType;
	}

	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getRatePlanCode() {
		return ratePlanCode;
	}

	public void setRatePlanCode(String ratePlanCode) {
		this.ratePlanCode = ratePlanCode;
	}

	public String getRatePlanName() {
		return ratePlanName;
	}

	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}

	public String getApDiscountCode() {
		return apDiscountCode;
	}

	public void setApDiscountCode(String apDiscountCode) {
		this.apDiscountCode = apDiscountCode;
	}

	public java.util.Date getNextAutopayCycleDate() {
		return nextAutopayCycleDate;
	}

	public void setNextAutopayCycleDate(java.util.Date nextAutopayCycleDate) {
		this.nextAutopayCycleDate = nextAutopayCycleDate;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	@Override
	public String toString() {
		return "AutoPayDetailBean [msisdn=" + msisdn + ", pdofLast4=" + pdofLast4 + ", recurringtopupcyclecdType="
				+ recurringtopupcyclecdType + ", enrollmentDate=" + enrollmentDate + ", accountNo=" + accountNo
				+ ", ratePlanCode=" + ratePlanCode + ", ratePlanName=" + ratePlanName + ", apDiscountCode="
				+ apDiscountCode + ", nextAutopayCycleDate=" + nextAutopayCycleDate + ", activationDate="
				+ activationDate + ", bonusUnits=" + bonusUnits + "]";
	}
}
