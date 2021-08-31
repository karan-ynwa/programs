package com.ericsson.eus.aps.dao;

import java.util.Date;

import com.ericsson.eus.aps.dao.model.AccountAutopayDtls;


/**
 *
 * FILE_NAME: IAccountAutopayDtlsDao.java
 *
 * MODULE DESCRIPTION: Account Autopay Dao interface
 *
 * @author eornshi, Date: May 20, 2021 1:59:32 PM 2021
 *
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public interface IAccountAutopayDtlsDao {

	public AccountAutopayDtls getAutoPayDtls(String accountId);

}
