package com.ericsson.eus.aps.service.data.beans;

import java.util.List;

import com.ericsson.eus.aps.service.util.ApsUtil;

/**
 * 
 * FILE_NAME: AutoPayPromoRequestBean.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Sep 11, 2020 3:08:01 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class AutoPayInfoRequestBean extends BaseRequest {

	/**
	 * User: ekxrxax
	 *
	 * Purpose: TODO, US/F/D long
	 */
	private static final long serialVersionUID = 1L;
	private List<MsisdnDetails> msisdnDetails;

	/**
	 * validate AmountDueRequestRequestBean
	 * 
	 * @throws AmountDueServiceException
	 */
	@Override
	public void validate() {

		super.validate();
		ApsUtil.validateMandatoryField(msisdnDetails, "AutoPayPromoRequestBean's MSISDNLIST");

		for (MsisdnDetails msisdnDetail : msisdnDetails) {
			ApsUtil.validateMandatoryField(msisdnDetail.getMsisdn(), "AutoPayPromoDetailsBean's MSISDN");
		}

	}

	/**
	 * User: ekxrxax , Date: Sep 16, 2020 12:47:13 AM 2020
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "AutoPayPromoRequestBean [msisdnList=" + msisdnDetails + "]";
	}

	/**
	 * @return the msisdnList
	 */
	public List<MsisdnDetails> getMsisdnList() {
		return msisdnDetails;
	}

	/**
	 * @param msisdnDetails
	 *            the msisdnList to set
	 */
	public void setMsisdnList(List<MsisdnDetails> msisdnDetails) {
		this.msisdnDetails = msisdnDetails;
	}

}
