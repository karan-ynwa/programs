package com.ericsson.eus.aps.dao.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * FILE_NAME: VuAccountMembers.java
 * 
 * MODULE DESCRIPTION:account primary and member bean, US84027
 *
 * @author ekxrxax, Date: Sep 11, 2020 6:33:02 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class VuAccountMembers implements Serializable {

	
	private static final long serialVersionUID = 8790903401500422109L;
	private Long primaryAccountId; // INTEGER
	private String primaryMsisdn; // VARCHAR2
	private String primaryRatePlanCode; // VARCHAR2
	private String primaryRatePlanName; // VARCHAR2
	private String primaryDiscountCode;// VARCHAR2
	private Double primaryRatePlanAfAmount;
	private String primaryRatePlanStatus; // VARCHAR2
	private Date primaryRatePlanExpireDate; // DATE

	private String memberMsisdn; // VARCHAR2
	private String memberRatePlanCode; // VARCHAR2
	private String memberRatePlanName; // VARCHAR2
	private Double memberRatePlanAfAmount;
	private String memberRatePlanStatus; // VARCHAR2
	private Date memberRatePlanExpireDate; // DATE
	
	private Date invAcceptanceDate; // DATE

	public Long getPrimaryAccountId() {
		return primaryAccountId;
	}

	public void setPrimaryAccountId(Long primaryAccountId) {
		this.primaryAccountId = primaryAccountId;
	}

	public String getPrimaryMsisdn() {
		return primaryMsisdn;
	}

	public void setPrimaryMsisdn(String primaryMsisdn) {
		this.primaryMsisdn = primaryMsisdn;
	}

	public String getPrimaryRatePlanCode() {
		return primaryRatePlanCode;
	}

	public void setPrimaryRatePlanCode(String primaryRatePlanCode) {
		this.primaryRatePlanCode = primaryRatePlanCode;
	}

	public String getPrimaryRatePlanName() {
		return primaryRatePlanName;
	}

	public void setPrimaryRatePlanName(String primaryRatePlanName) {
		this.primaryRatePlanName = primaryRatePlanName;
	}

	public String getPrimaryDiscountCode() {
		return primaryDiscountCode;
	}

	public void setPrimaryDiscountCode(String primaryDiscountCode) {
		this.primaryDiscountCode = primaryDiscountCode;
	}

	public Double getPrimaryRatePlanAfAmount() {
		return primaryRatePlanAfAmount;
	}

	public void setPrimaryRatePlanAfAmount(Double primaryRatePlanAfAmount) {
		this.primaryRatePlanAfAmount = primaryRatePlanAfAmount;
	}

	public String getPrimaryRatePlanStatus() {
		return primaryRatePlanStatus;
	}

	public void setPrimaryRatePlanStatus(String primaryRatePlanStatus) {
		this.primaryRatePlanStatus = primaryRatePlanStatus;
	}

	public Date getPrimaryRatePlanExpireDate() {
		return primaryRatePlanExpireDate;
	}

	public void setPrimaryRatePlanExpireDate(Date primaryRatePlanExpireDate) {
		this.primaryRatePlanExpireDate = primaryRatePlanExpireDate;
	}

	public String getMemberMsisdn() {
		return memberMsisdn;
	}

	public void setMemberMsisdn(String memberMsisdn) {
		this.memberMsisdn = memberMsisdn;
	}

	public String getMemberRatePlanCode() {
		return memberRatePlanCode;
	}

	public void setMemberRatePlanCode(String memberRatePlanCode) {
		this.memberRatePlanCode = memberRatePlanCode;
	}

	public String getMemberRatePlanName() {
		return memberRatePlanName;
	}

	public void setMemberRatePlanName(String memberRatePlanName) {
		this.memberRatePlanName = memberRatePlanName;
	}

	public Double getMemberRatePlanAfAmount() {
		return memberRatePlanAfAmount;
	}

	public void setMemberRatePlanAfAmount(Double memberRatePlanAfAmount) {
		this.memberRatePlanAfAmount = memberRatePlanAfAmount;
	}

	public String getMemberRatePlanStatus() {
		return memberRatePlanStatus;
	}

	public void setMemberRatePlanStatus(String memberRatePlanStatus) {
		this.memberRatePlanStatus = memberRatePlanStatus;
	}

	public Date getMemberRatePlanExpireDate() {
		return memberRatePlanExpireDate;
	}

	public void setMemberRatePlanExpireDate(Date memberRatePlanExpireDate) {
		this.memberRatePlanExpireDate = memberRatePlanExpireDate;
	}

	public Date getInvAcceptanceDate() {
		return invAcceptanceDate;
	}

	public void setInvAcceptanceDate(Date invAcceptanceDate) {
		this.invAcceptanceDate = invAcceptanceDate;
	}

	@Override
	public String toString() {
		return "VuAccountMembers [primaryAccountId=" + primaryAccountId + ", primaryMsisdn=" + primaryMsisdn
				+ ", primaryRatePlanCode=" + primaryRatePlanCode + ", primaryRatePlanName=" + primaryRatePlanName
				+ ", primaryDiscountCode=" + primaryDiscountCode + ", primaryRatePlanAfAmount="
				+ primaryRatePlanAfAmount + ", primaryRatePlanStatus=" + primaryRatePlanStatus
				+ ", primaryRatePlanExpireDate=" + primaryRatePlanExpireDate + ", memberMsisdn=" + memberMsisdn
				+ ", memberRatePlanCode=" + memberRatePlanCode + ", memberRatePlanName=" + memberRatePlanName
				+ ", memberRatePlanAfAmount=" + memberRatePlanAfAmount + ", memberRatePlanStatus="
				+ memberRatePlanStatus + ", memberRatePlanExpireDate=" + memberRatePlanExpireDate
				+ ", invAcceptanceDate=" + invAcceptanceDate + "]";
	}
	
	
}
