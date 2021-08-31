package com.ericsson.eus.aps.dao;

import com.ericsson.eus.aps.common.exception.AutopayDaoException;
import com.ericsson.eus.aps.dao.model.EbiCacheConfigUpdateInfoRecord;

/**
 * 
 * FILE_NAME: IEbiCacheConfigDao.java
 * 
 * MODULE DESCRIPTION: Autopay Dao interface, US/F/D US92104
 *
 * @author eznraps, Date: Dec 04, 2020 03:33:32 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public interface IEbiCacheConfigDao {
	public EbiCacheConfigUpdateInfoRecord getEbiCacheConfigUpdateInfo() throws AutopayDaoException;
}
