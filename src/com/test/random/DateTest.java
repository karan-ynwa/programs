package com.test.random;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

 /**
 * 
 * FILE_NAME: DateTest.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Feb 9, 2021 7:36:18 PM 2021
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class DateTest {

	/**
	 * User: ekxrxax , Date: Feb 9, 2021 7:36:18 PM 2021
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: void
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
		String actMinDate = simpleDateFormat.format(getCurrentDate());
		System.out.println(actMinDate);
	}

	/**
	 * User: ekxrxax , Date: Feb 10, 2021 6:58:27 PM 2021
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: CharSequence
	 *
	 * @return
	 */
	private static Date getCurrentDate() {
		Date currentDate = new java.util.Date();
		TimeZone tz = TimeZone.getTimeZone("US/Pacific");
		boolean isDSTtoday = tz.inDaylightTime(currentDate);
		if (isDSTtoday) {
			int sec = tz.getDSTSavings() / 1000;// for no. of seconds
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			cal.add(Calendar.SECOND, sec);
			currentDate = cal.getTime();
		}
		
		return currentDate;
	}
	
}
