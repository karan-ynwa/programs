package com.ericsson.eus.aps.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ericsson.eus.aps.dao.model.AutoPayTransactions;

/**
 * User: eiansrv , Date: Nov 12, 2020 3:01:30 PM 2020
 *
 * Purpose: Date utility
 *
 * US/D/F Number:US84041
 *
 */

public class DateUtils {
	private static final Logger logger = LogManager.getLogger(DateUtils.class);
	private DateUtils(){}

	public static Date addDays(Date date, int days)
	  {
	      Calendar cal = Calendar.getInstance();
	      cal.setTime(date);
	      cal.add(Calendar.DATE, days); //minus number would decrement the days     
	      return cal.getTime();
	  }
	public static String convertDateToDBFormat(Date date, boolean endate) {
		 
	    SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
	 
		if (endate) {
			date = addDays(date,1);
		} 
	    return format2.format(date);
	}
	public static Date getDate(String dateToConvert) {
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		   Date date=null;
		   try {
			date = format1.parse(dateToConvert);
		} catch (ParseException e) {
			logger.error(e.getMessage()+"ERROR converting Date to dd-MMM-yy Format");
			return null;
		}
		
		return date;
	}
	public static int getDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
		return dayOfMonth;
	}
}
