package com.ericsson.eus.aps.service.data.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * FILE_NAME: AutoPayNextCycleDateResponseBean.java
 * 
 * MODULE DESCRIPTION: F1221/US88638
 *
 * @author epinmon, Date: Jan 6, 2021 5:03:53 PM 2021
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class AutoPayNextCycleDateResponseBean extends BaseResponse {

	private static final long serialVersionUID = -581912281808132137L;
	
	private Date nextAutopayCycleDate;
	private boolean hasNextAutopayCycleDateUpdated = false;

	/**
	 * @return the nextAutopayCycleDate
	 */
	public Date getNextAutopayCycleDate() {
		return nextAutopayCycleDate;
	}

	/**
	 * @param nextAutopayCycleDate the nextAutopayCycleDate to set
	 */
	public void setNextAutopayCycleDate(Date nextAutopayCycleDate) {
		this.nextAutopayCycleDate = nextAutopayCycleDate;
	}

	/**
	 * @return the hasNextAutopayCycleDateUpdated
	 */
	@JsonProperty("hasNextAutopayCycleDateUpdated")
	public boolean hasNextAutopayCycleDateUpdated() {
		return hasNextAutopayCycleDateUpdated;
	}

	/**
	 * @param hasNextAutopayCycleDateUpdated the hasNextAutopayCycleDateUpdated to
	 *                                       set
	 */
	public void setHasNextAutopayCycleDateUpdated(boolean hasNextAutopayCycleDateUpdated) {
		this.hasNextAutopayCycleDateUpdated = hasNextAutopayCycleDateUpdated;
	}


	@Override
	public String toString() {
		return "AutoPayNextCycleDateResponseBean [nextAutopayCycleDate=" + nextAutopayCycleDate
				+ ", hasNextAutopayCycleDateUpdated=" + hasNextAutopayCycleDateUpdated + "]";
	}

}
