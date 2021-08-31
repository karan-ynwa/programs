package com.ericsson.eus.aps.dao.model;

import java.io.Serializable;

/**
 * 
 * FILE_NAME: CefCriteriaEvaluations.java
 * 
 * MODULE DESCRIPTION: US84027
 *
 * @author ekxrxax, Date: Sep 11, 2020 6:33:02 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class CefCriteriaEvaluations implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8633402482309902436L;

	private Integer criteriaEvalId;
	
	private String criteriaKey;
	
	
	private String criteriaValue;

	private String descr;



	public Integer getCriteriaEvalId() {
		return criteriaEvalId;
	}

	public void setCriteriaEvalId(Integer criteriaEvalId) {
		this.criteriaEvalId = criteriaEvalId;
	}

	public String getCriteriaKey() {
		return criteriaKey;
	}

	public void setCriteriaKey(String criteriaKey) {
		this.criteriaKey = criteriaKey;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	public String toString() {
		return "CefCriteriaEvaluations [criteriaEvalId=" + criteriaEvalId + ", criteriaKey=" + criteriaKey
				+ ", criteriaValue=" + criteriaValue + ", descr=" + descr + "]";
	}
	

}
