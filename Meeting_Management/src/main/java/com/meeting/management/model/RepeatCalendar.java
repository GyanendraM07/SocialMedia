package com.meeting.management.model;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.meeting.management.exception.CustomException;
@Service
public class RepeatCalendar {
	int month;
	int day;
  public int getmonth(String Month) {
		switch (Month) {
		case "January" :month=1;
		break;
		case "February" :month=2;
      break;
		case "March" :month=3;
      break;
		case "April" :month=4;
      break;
		case "May" :month=5;
      break;
		case "June" :month=6;
      break;
		case "July" :month=7;
      break;
		case "August" :month=8;
      break;
		case "September" :month=9;
      break;
		case "October" :month=10;
      break;
		case "November" :month=11;
      break;
		case "December" :month=12;
      break;
		default: new CustomException("pls Select a Right Month",HttpStatus.BAD_REQUEST);
      break;
  }
	return month;
	  
  }
  public  int getday(String Day) {
	  switch(Day){
		case "Monday" : day=2;
		break;
		case "Tuesday" : day=3;
		break;
		case "Wednesday" : day=4;
		break;
		case "Thursday" : day=5;
		break;
		case "Friday" : day=6;
		break;
		case "Saturday" : day=7;
		break;
		case "Sunday" : day=1;
		break;
		default: new CustomException("pls Select a Right day",HttpStatus.BAD_REQUEST);
      break;
		
	}
	  return day;
  }
}
