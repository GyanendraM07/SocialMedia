package com.example.demo.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Demo {
	public static void main(String... s) 
	{
//		new Test(1,1 );
//		System.err.println(cacheCalendar.DATE);
//		System.err.println(cacheCalendar.getTime());
//		System.err.println(cacheCalendar.getTimeZone());
//		System.err.println(cacheCalendar.getTimeInMillis());
		 HashMap<Integer, String> hm=new HashMap<Integer, String>();
		 hm.put(1, "demo123");
		 hm.put(2, "demodemo");
		 hm.put(3,"demodemo123");
		 System.out.println(hm);
		Set keys=hm.entrySet();
		for(Object d:keys) {
			System.out.println(d); 
		}
	}

}
