package com.meeting.management.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

public class Test {
	
	 
	static Calendar cacheCalendar=Calendar.getInstance();
	Test(int year, int month) throws ParseException {
//		
//		cacheCalendar.add(Calendar.YEAR,1);
//	    cacheCalendar.set(Calendar.MONTH, month);
//	    cacheCalendar.set(Calendar.WEEK_OF_MONTH, 1-4);
//	    cacheCalendar.set(Calendar.DAY_OF_WEEK, 1);
//	    System.out.println(cacheCalendar.getTime());
//	    cacheCalendar.set(Calendar.HOUR, 5);
//	    cacheCalendar.set(Calendar.MINUTE,25);
//	    cacheCalendar.set(Calendar.MINUTE,25);
//	    DateTime date=new DateTime(cacheCalendar.getTime());
//	    date.toDate();
////		LocalDateTime demo = dateTime.plusHours(meeting.getDuration());
//	    String pattern = "MM-dd-yyyy";
//	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//	    Date date1 = new SimpleDateFormat("MM-dd-yyyy").parse(date.toString());
//	    System.out.println(date1);
		Calendar cal=Calendar.getInstance();
		cal.get(Calendar.WEEK_OF_MONTH);
		Calendar calendar = Calendar.getInstance();
		calendar.set(2022, 12, 8);
		int weekofMonth=calendar.get(Calendar.WEEK_OF_MONTH);
	    System.out.println(weekofMonth);
	}

	
	public static void main(String... s) throws ParseException
	{
		new Test(1,1 );
//		System.err.println(cacheCalendar.DATE);
//		System.err.println(cacheCalendar.getTime());
//		System.err.println(cacheCalendar.getTimeZone());
//		System.err.println(cacheCalendar.getTimeInMillis());
		
	}

}
