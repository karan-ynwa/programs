package com.ericsson.eus.aps.rest.service;

import java.util.List;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.service.data.beans.AutoPayInfoDataBean;
import com.ericsson.eus.aps.service.data.beans.AutoPayInfoResponseBean;
import com.ericsson.eus.aps.service.data.beans.FeatureInfo;
import com.ericsson.eus.aps.service.data.beans.MsisdnDetails;

/**
 * 
 * FILE_NAME: AutopayService.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Sep 14, 2020 3:30:37 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public interface AutoPayInfoService {


	public AutoPayInfoDataBean getAutopayData(AutoPayInfoDataBean apDataBean, String msisdn);

	public List<FeatureInfo> getFeatureInfoList(AutoPayInfoDataBean apDataBean, String msisdn);

	public AutoPayInfoDataBean getAutoPayEventTracker(AutoPayInfoDataBean apDataBean) throws AutopayDaoException;

	public AutoPayInfoResponseBean getAutoPayInfoResponseBean(List<MsisdnDetails> msisdnDetails,
			AutoPayInfoDataBean apDataBean);
}
