package com.ericsson.eus.aps.dao.model;

import java.io.Serializable;
/**
 * 
 * FILE_NAME: CefCriteriaKeyLists.java
 * 
 * MODULE DESCRIPTION: US84027
 *
 * @author ekxrxax, Date: Sep 11, 2020 6:33:02 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class CefCriteriaKeyLists implements Serializable {


	private static final long serialVersionUID = -8633402482309902436L;
	
	private Integer keyListId;
	
	private String criteriaKey;
	
	private String attributeName;
	
	private String attributeValue;

	private String descr;


	public Integer getKeyListId() {
		return keyListId;
	}

	public void setKeyListId(Integer keyListId) {
		this.keyListId = keyListId;
	}

	public String getCriteriaKey() {
		return criteriaKey;
	}

	public void setCriteriaKey(String criteriaKey) {
		this.criteriaKey = criteriaKey;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	public String toString() {
		return "CefCriteriaKeyLists [keyListId=" + keyListId + ", criteriaKey=" + criteriaKey + ", attributeName="
				+ attributeName + ", attributeValue=" + attributeValue + ", descr=" + descr + "]";
	}

	
}
