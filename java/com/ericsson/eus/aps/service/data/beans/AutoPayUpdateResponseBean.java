package com.ericsson.eus.aps.service.data.beans;

 /**
 * 
 * FILE_NAME: AutoPayUpdateResponseBean.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author eihsnad, Date: Nov 12, 2020 8:06:41 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class AutoPayUpdateResponseBean extends BaseResponse {

	/**
	 * User: eihsnad
	 *
	 * Purpose: Autopay Update Response Bean, US/F/D US84043
	 */
	private static final long serialVersionUID = 6346192050002410918L;
	private Long msisdn;
	
	private AutoPayDetailBean detailBean;

	public AutoPayDetailBean getDetailBean() {
		return detailBean;
	}

	public void setDetailBean(AutoPayDetailBean detailBean) {
		this.detailBean = detailBean;
	}

	public Long getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(Long msisdn) {
		this.msisdn = msisdn;
	}

	@Override
	public String toString() {
		return "AutoPayUpdateResponseBean [msisdn=" + msisdn + ", detailBean=" + detailBean + "]";
	}

}
