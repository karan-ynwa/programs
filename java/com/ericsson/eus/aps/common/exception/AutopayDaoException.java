package com.ericsson.eus.aps.common.exception;

 /**
 * 
 * FILE_NAME: AutopayDaoException.java
 * 
 * MODULE DESCRIPTION: Exception Handler for DAO, US84030
 *
 * @author eihsnad, Date: Sep 10, 2020 3:34:24 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public class AutopayDaoException extends Exception {
	private static final long serialVersionUID = -4057274727374698121L;
	/**
	 * 
	 * @param msg
	 * @param e
	 */
	public AutopayDaoException(String msg, Exception e) {
		super(msg, e);
	}

	public AutopayDaoException(String msg) {
		super(msg);
	}

}
