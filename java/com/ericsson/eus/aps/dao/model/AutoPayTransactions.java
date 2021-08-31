package com.ericsson.eus.aps.dao.model;

import java.io.Serializable;

public class AutoPayTransactions implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4452923012394L;
	private String pdofLast4;
	private Integer recurringTopupCycleCode;
	private String comments;
	private String modifiedBy;
	private String transactionType;
	private String transactionTimeStamp;
	private String activationdate;
	private String enrollmentdate;
	private String enrollmentchannel;
	private String unenrollmentdate;
	private String rateplancode;
	private double promotionamount;
	private String nextautopaycycledate;

	public String getPdofLast4() {
		return pdofLast4;
	}

	public void setPdofLast4(String pdofLast4) {
		this.pdofLast4 = pdofLast4;
	}

	public Integer getRecurringTopupCycleCode() {
		return recurringTopupCycleCode;
	}

	public void setRecurringTopupCycleCode(Integer recurringTopupCycleCode) {
		this.recurringTopupCycleCode = recurringTopupCycleCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public String getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionTimeStamp() {
		return transactionTimeStamp;
	}

	public void setTransactionTimeStamp(String transactionTimeStamp) {
		this.transactionTimeStamp = transactionTimeStamp;
	}

	public String getActivationdate() {
		return activationdate;
	}

	public void setActivationdate(String activationdate) {
		this.activationdate = activationdate;
	}

	public String getEnrollmentdate() {
		return enrollmentdate;
	}

	public void setEnrollmentdate(String enrollmentdate) {
		this.enrollmentdate = enrollmentdate;
	}


	public String getEnrollmentchannel() {
		return enrollmentchannel;
	}


	public void setEnrollmentchannel(String enrollmentchannel) {
		this.enrollmentchannel = enrollmentchannel;
	}

	public String getUnenrollmentdate() {
		return unenrollmentdate;
	}

	public void setUnenrollmentdate(String unenrollmentdate) {
		this.unenrollmentdate = unenrollmentdate;
	}


	public String getRateplancode() {
		return rateplancode;
	}


	public void setRateplancode(String rateplancode) {
		this.rateplancode = rateplancode;
	}


	public double getPromotionamount() {
		return promotionamount;
	}


	public void setPromotionamount(double promotionamount) {
		this.promotionamount = promotionamount;
	}

	public String getNextautopaycycledate() {
		return nextautopaycycledate;
	}

	public void setNextautopaycycledate(String nextautopaycycledate) {
		this.nextautopaycycledate = nextautopaycycledate;
	}

	@Override
	public String toString() {
		return "AutoPayTransactions [pdofLast4= ****" + ", recurringTopupCycleCode=" + recurringTopupCycleCode
				+ ", comments=" + comments + ", modifiedBy=" + modifiedBy + ", transactionType=" + transactionType
				+ ", transactionTimeStamp=" + transactionTimeStamp + ", activationdate=" + activationdate
				+ ", enrollmentdate=" + enrollmentdate + ", enrollmentchannel=" + enrollmentchannel
				+ ", unenrollmentdate=" + unenrollmentdate + ", rateplancode=" + rateplancode + ", promotionamount="
				+ promotionamount + ", nextautopaycycledate=" + nextautopaycycledate + "]";
	}
}
