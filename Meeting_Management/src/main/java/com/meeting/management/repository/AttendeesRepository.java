package com.meeting.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.Attendees;
import com.meeting.management.model.Meeting;

@Repository
public interface AttendeesRepository extends MongoRepository<Attendees, String>{
	@Query("{'meeting.chairman': ?0}")
	List<Attendees> findByChairman(String chairman);

	List<Attendees> findByMeetingId(String meetingId);

	Optional<Attendees> findByMeetingIdAndUserName(String meetingId, String deptUserName);

	Optional<Attendees> findByMeetingIdAndUserNameAndIsChairman(String meetingId, String deptUserName,boolean isChairman);

	List<Attendees> findByMeetingIdAndStatusNot(String meetingId, String string);
	
	@Query(fields = "{'meetingId' : 1}")
	List<Attendees> findByUserNameAndIsChairmanAndStatus(String userName, boolean isChairman, String string2);

	List<Attendees> findByMeetingIdAndIsChairman(String meetingId, boolean isChairman);

	Optional<Attendees> findByMeetingIdAndUserNameAndStatus(String meetingId, String i, String string);

	List<Attendees> findByMeetingIdAndStatus(String meetingId, String string);

	List<Attendees> findAllByIdAndUserName(List<String> meetingId);

//	Attendees  findBydeptUserName(String deptUserName);
//	List<Attendees> findBymeetingIdAndisChairman(String meetingId, String string);
	
	
	
	
	

	
	 

}
