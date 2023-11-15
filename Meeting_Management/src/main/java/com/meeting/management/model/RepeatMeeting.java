package com.meeting.management.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.meeting.management.exception.CustomException;
import com.meeting.management.repository.MeetingRepository;

@Service
public class RepeatMeeting {
	int month;
	int day;

	@Autowired
	MeetingRepository meetingRepository;
	@Autowired
	RepeatCalendar repeatCalendar;

	public void repeatMeeting(Meeting meeting, int hour, int min, int sec,int monthofyear,int year,int weekofMonth,int dayofWeek,int dayofMonth) {
		System.err.println("reahed in Repeat mode");
		if (meeting.getRepeatMeeting().equals("Annually")) {
			System.err.println("rinside Anuuually");
			month = repeatCalendar.getmonth(meeting.getRepeatMonth());
			day = repeatCalendar.getday(meeting.getRepeatDay());
			System.out.println(month+""+day);
			System.out.println("inside the lopp");
			for (int i = 1; i <= meeting.getCount(); i++) {
				System.err.println("rinside loop of Anuuually");
				Meeting meetingi = new Meeting();
				Calendar cacheCalendar = Calendar.getInstance();
				cacheCalendar.add(Calendar.YEAR, i);
				cacheCalendar.set(Calendar.MONTH, month);
				cacheCalendar.set(Calendar.WEEK_OF_MONTH, meeting.getRepeatWeek() - 4);
				cacheCalendar.set(Calendar.DAY_OF_WEEK, day);
				cacheCalendar.set(Calendar.HOUR, hour);
				cacheCalendar.set(Calendar.MINUTE, min);
				cacheCalendar.set(Calendar.SECOND, sec);
				DateTime now = new DateTime(cacheCalendar);
				DateTime demo = now.plusHours(meeting.getDuration());
				meetingi.setStartDate(now.toString());
				meetingi.setEndDate(demo.toString());
				meetingi.setDuration(meeting.getDuration());
				meetingi.setMeetingDescription(meeting.getMeetingDescription());
				meetingi.setInitiator(meeting.getInitiator());
				meetingi.setChairman(meeting.getChairman());
				meetingi.setChairmanDept(meeting.getChairmanDept());
				meetingi.setCreatedDate(LocalDateTime.now());
				meetingi.setTitle(meeting.getTitle());
				meetingi.setMeetingId(meeting.getId());
				meetingi.setLocationType(meeting.getLocationType());
				meetingi.setLocationPlace(meeting.getLocationPlace());
				meetingi.setLocationUrl(meeting.getLocationUrl());
				meetingi.setProposedLevel(meeting.getProposedLevel());
				meetingi.setRepeatMeeting(meeting.getRepeatMeeting());
				meetingi.setRepeatMonth(meeting.getRepeatMonth());
				meetingi.setRepeatWeek(meeting.getRepeatWeek());
				meetingi.setRepeatDay(meeting.getRepeatDay());
				meetingi.setMeetingStatus(meeting.getMeetingStatus());
				meetingi.setApproveAgenda(meeting.getApproveAgenda());
				meetingi.setApproveMeeting(meeting.getApproveMeeting());
				Meeting meeting2 = meetingRepository.save(meetingi);
				System.err.println("Meeting 2nd" + meeting2);
			}
			System.err.println("outside loop of Anuuually");
		}
		else if(meeting.getRepeatMeeting().equals("Monthly")) {
			System.err.println("inside the loop Monthly");
			int y=0;
			day=repeatCalendar.getday(meeting.getRepeatDay());
			for (int i = 1; i <= meeting.getCount(); i++) {
				monthofyear += 1;
				if(monthofyear==13){
					monthofyear=1;
					y +=1; 
					}
				Meeting meetingi = new Meeting();
				Calendar cacheCalendar = Calendar.getInstance();
				cacheCalendar.add(Calendar.YEAR, y);
				cacheCalendar.set(Calendar.MONTH, monthofyear);
				System.err.println(monthofyear);
				cacheCalendar.set(Calendar.WEEK_OF_MONTH, meeting.getRepeatWeek() - 4);
				System.err.println(meeting.getRepeatWeek() - 4);
				cacheCalendar.set(Calendar.DAY_OF_WEEK, day);
				cacheCalendar.set(Calendar.HOUR, hour);
				cacheCalendar.set(Calendar.MINUTE, min);
				cacheCalendar.set(Calendar.SECOND, sec);
				DateTime now = new DateTime(cacheCalendar);
				DateTime demo = now.plusHours(meeting.getDuration());
				meetingi.setStartDate(now.toString());
				meetingi.setEndDate(demo.toString());
				meetingi.setDuration(meeting.getDuration());
				meetingi.setMeetingDescription(meeting.getMeetingDescription());
				meetingi.setInitiator(meeting.getInitiator());
				meetingi.setChairman(meeting.getChairman());
				meetingi.setChairmanDept(meeting.getChairmanDept());
				meetingi.setCreatedDate(LocalDateTime.now());
				meetingi.setTitle(meeting.getTitle());
				meetingi.setMeetingId(meeting.getId());
				meetingi.setLocationType(meeting.getLocationType());
				meetingi.setLocationPlace(meeting.getLocationPlace());
				meetingi.setLocationUrl(meeting.getLocationUrl());
				meetingi.setProposedLevel(meeting.getProposedLevel());
				meetingi.setRepeatMeeting(meeting.getRepeatMeeting());
				meetingi.setRepeatMonth(meeting.getRepeatMonth());
				meetingi.setRepeatWeek(meeting.getRepeatWeek());
				meetingi.setRepeatDay(meeting.getRepeatDay());
				meetingi.setMeetingStatus(meeting.getMeetingStatus());
				meetingi.setApproveAgenda(meeting.getApproveAgenda());
				meetingi.setApproveMeeting(meeting.getApproveMeeting());
				Meeting meeting2 = meetingRepository.save(meetingi);
				System.err.println("Meeting 2nd" + meeting2);
			}
		}
		
		else if(meeting.getRepeatMeeting().equals("Weekly")) {
//			month=repeatCalendar.getmonth(meeting.getRepeatMeeting());
			day=repeatCalendar.getday(meeting.getRepeatDay());
			for (int i = 1; i <= meeting.getCount(); i++) {
				int dow =+ 7;
				Meeting meetingi = new Meeting();
				Calendar cacheCalendar = Calendar.getInstance();
				cacheCalendar.add(Calendar.YEAR, year);
				cacheCalendar.set(Calendar.MONTH, monthofyear);
				cacheCalendar.set(Calendar.WEEK_OF_MONTH, weekofMonth- 4+i);
				cacheCalendar.set(Calendar.DAY_OF_WEEK, dow);
				cacheCalendar.set(Calendar.HOUR, hour);
				cacheCalendar.set(Calendar.MINUTE, min);
				cacheCalendar.set(Calendar.SECOND, sec);
				DateTime now = new DateTime(cacheCalendar);
				DateTime demo = now.plusHours(meeting.getDuration());
				meetingi.setStartDate(now.toString());
				meetingi.setEndDate(demo.toString());
				meetingi.setDuration(meeting.getDuration());
				meetingi.setMeetingDescription(meeting.getMeetingDescription());
				meetingi.setInitiator(meeting.getInitiator());
				meetingi.setChairman(meeting.getChairman());
				meetingi.setChairmanDept(meeting.getChairmanDept());
				meetingi.setCreatedDate(LocalDateTime.now());
				meetingi.setTitle(meeting.getTitle());
				meetingi.setMeetingId(meeting.getId());
				meetingi.setLocationType(meeting.getLocationType());
				meetingi.setLocationPlace(meeting.getLocationPlace());
				meetingi.setLocationUrl(meeting.getLocationUrl());
				meetingi.setProposedLevel(meeting.getProposedLevel());
				meetingi.setRepeatMeeting(meeting.getRepeatMeeting());
				meetingi.setRepeatMonth(meeting.getRepeatMonth());
				meetingi.setRepeatWeek(meeting.getRepeatWeek());
				meetingi.setRepeatDay(meeting.getRepeatDay());
				meetingi.setMeetingStatus(meeting.getMeetingStatus());
				meetingi.setApproveAgenda(meeting.getApproveAgenda());
				meetingi.setApproveMeeting(meeting.getApproveMeeting());
				Meeting meeting2 = meetingRepository.save(meetingi);
				System.err.println("Meeting 2nd" + meeting2);
			}
		}
		else {
//			month=repeatCalendar.getmonth(meeting.getRepeatMeeting());
//			day=repeatCalendar.getday(meeting.getRepeatDay());
			for (int i = 1; i <= meeting.getCount(); i++) {
				Meeting meetingi = new Meeting();
				Calendar cacheCalendar = Calendar.getInstance();
				cacheCalendar.add(Calendar.YEAR, 0);
				cacheCalendar.set(Calendar.MONTH, monthofyear);
				cacheCalendar.set(Calendar.WEEK_OF_MONTH, weekofMonth- 4);
				cacheCalendar.set(Calendar.DAY_OF_WEEK, day);
				cacheCalendar.set(Calendar.HOUR, hour);
				cacheCalendar.set(Calendar.MINUTE, min);
				cacheCalendar.set(Calendar.SECOND, sec);
				DateTime now = new DateTime(cacheCalendar);
				DateTime demo = now.plusHours(meeting.getDuration());
				meetingi.setStartDate(now.toString());
				meetingi.setEndDate(demo.toString());
				meetingi.setDuration(meeting.getDuration());
				meetingi.setMeetingDescription(meeting.getMeetingDescription());
				meetingi.setInitiator(meeting.getInitiator());
				meetingi.setChairman(meeting.getChairman());
				meetingi.setChairmanDept(meeting.getChairmanDept());
				meetingi.setCreatedDate(LocalDateTime.now());
				meetingi.setTitle(meeting.getTitle());
				meetingi.setMeetingId(meeting.getId());
				meetingi.setLocationType(meeting.getLocationType());
				meetingi.setLocationPlace(meeting.getLocationPlace());
				meetingi.setLocationUrl(meeting.getLocationUrl());
				meetingi.setProposedLevel(meeting.getProposedLevel());
				meetingi.setRepeatMeeting(meeting.getRepeatMeeting());
				meetingi.setRepeatMonth(meeting.getRepeatMonth());
				meetingi.setRepeatWeek(meeting.getRepeatWeek());
				meetingi.setRepeatDay(meeting.getRepeatDay());
				meetingi.setMeetingStatus(meeting.getMeetingStatus());
				meetingi.setApproveAgenda(meeting.getApproveAgenda());
				meetingi.setApproveMeeting(meeting.getApproveMeeting());
				Meeting meeting2 = meetingRepository.save(meetingi);
				System.err.println("Meeting 2nd" + meeting2);
			}
		}
	}
}
