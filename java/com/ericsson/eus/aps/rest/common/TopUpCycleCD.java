package com.ericsson.eus.aps.rest.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * FILE_NAME: TopUpCycleCD.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author esuspal, Date: Feb 11, 2021 1:23:43 PM 2021
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson
 *      Inc.Proprietary.
 *
 */
public enum TopUpCycleCD {
	AMOUNT_DUE_ON_RATEPLAN_EXPIRY(9), AMOUNT_DUE_ON_BALANCE_EXPIRY(8), FIXED_AMT_ON_RATEPLAN_EXPIRY(
			7), FIXED_AMT_ON_BALANCE_EXPIRY(6);

	private int topUpCycleCDVal;

	private TopUpCycleCD(int topUpCycleCDVal) {
		this.topUpCycleCDVal = topUpCycleCDVal;
	}

	private static final Map<String, TopUpCycleCD> nameToValueMap = new HashMap<String, TopUpCycleCD>();

	static {
		for (TopUpCycleCD value : EnumSet.allOf(TopUpCycleCD.class)) {
			nameToValueMap.put(String.valueOf(value.topUpCycleCDVal), value);
		}
	}

	public static boolean isValid(String topUpCycleCDVal) {
		if (null != nameToValueMap.get(topUpCycleCDVal)) {
			return true;
		}

		return false;

	}

	public static boolean is7(String topUpCycleCDVal) {
		if (null != nameToValueMap.get(topUpCycleCDVal)
				&& (Integer.parseInt(topUpCycleCDVal) == FIXED_AMT_ON_RATEPLAN_EXPIRY.topUpCycleCDVal)) {
			return true;
		}

		return false;

	}

	public static boolean isMcrpTopCycleCD(String topUpCycleCDVal) {
		if (null != nameToValueMap.get(topUpCycleCDVal)
				&& (Integer.parseInt(topUpCycleCDVal) == FIXED_AMT_ON_RATEPLAN_EXPIRY.topUpCycleCDVal
						|| Integer.parseInt(topUpCycleCDVal) == AMOUNT_DUE_ON_RATEPLAN_EXPIRY.topUpCycleCDVal)) {
			return true;
		}

		return false;

	}
}
