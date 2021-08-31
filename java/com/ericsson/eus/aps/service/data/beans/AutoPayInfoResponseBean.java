package com.ericsson.eus.aps.service.data.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * FILE_NAME: AutoPayResponseBean.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Sep 15, 2020 1:03:30 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AutoPayInfoResponseBean extends BaseResponse{

	/**
	 * User: ekxrxax
	 *
	 * Purpose: TODO, US/F/D
	 * long
	 */
	private static final long serialVersionUID = 1L;
	private SubscriberInfoBean apSubscriberInfo;
	/**
	 * User: ekxrxax , Date: Sep 16, 2020 1:48:05 PM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number: 
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "AutoPayResponseBean [apSubscriberInfo=" + apSubscriberInfo + "]";
	}

	/**
	 * @return the apSubscriberInfo
	 */
	public SubscriberInfoBean getApSubscriberInfo() {
		return apSubscriberInfo;
	}

	/**
	 * @param apSubscriberInfo
	 *            the apSubscriberInfo to set
	 */
	public void setApSubscriberInfo(SubscriberInfoBean apSubscriberInfo) {
		this.apSubscriberInfo = apSubscriberInfo;
	}
	
}
