package com.ericsson.eus.aps.service.data.beans;

import java.util.List;

/**
 * 
 * FILE_NAME: AutoPayHistoryResponseBean.java
 * 
 * MODULE DESCRIPTION: Autopay Histpory Response Bean, US91272
 *
 * @author ekxrxax, Date: Nov 10, 2020 2:38:01 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class AutoPayHistoryResponseBean extends BaseResponse {

	/**
	 * User: eihsnad
	 *
	 */
	private static final long serialVersionUID = -6338481689784284540L;
	private List<ApsHistory> apHistory;

	public List<ApsHistory> getApHistory() {
		return apHistory;
	}

	public void setApHistoryList(List<ApsHistory> apHistory) {
		this.apHistory = apHistory;
	}

	@Override
	public String toString() {
		return "AutoPayHistoryResponseBean [autoPayHistoryResponseList=" + apHistory
				+ ", responseCode=" + responseCode + ", responseText=" + responseText + "]";
	}

}
