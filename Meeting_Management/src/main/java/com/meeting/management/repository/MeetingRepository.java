package com.meeting.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.Attendees;
import com.meeting.management.model.Mapping;
import com.meeting.management.model.Meeting;

@Repository
public interface MeetingRepository extends MongoRepository<Meeting, String>{
	@Query("{'attendees.depUserName': ?0}")
	List<Meeting> findByDepUserName(String depUserName);
	@Query("{'agenda.agenda': ?0}")
	List<Meeting> findByAgenda(String agenda );

	List<Meeting> findByRoleName(String roleName);

	List<Meeting> findByChairman(String roleName);

	Optional<Meeting> findByIdAndRoleName(String id, String roleName);
	
	Optional<Meeting> findById(String meetingId);

	List<Meeting> findByRoleNameAndMeetingStatusNotOrChairmanAndMeetingStatusNot(String roleName, String string, String userName,
			String string2);

	List<Meeting> findByChairmanAndMeetingStatusOrRoleNameAndMeetingStatus(String userName, String string, String roleName,
			String string2);

	List<Meeting> findByChairmanAndMeetingStatusOrRoleNameAndMeetingStatusOrRoleNameAndMeetingStatusOrRoleNameAndMeetingStatus(String userName,
			String string, String roleName, String string2, String roleName2, String string3, String roleName3,
			String string4);

	List<Meeting> findByChairmanAndMeetingStatusOrInitiatorAndMeetingStatusOrInitiatorAndMeetingStatusOrInitiatorAndMeetingStatus(String user,String status,String user1,String status1,String user2,String status2,String user3,String status3);
	List<Meeting> findByMeetingId(String meetingId);
	


	
	
	
	
	
	
	
	

	



}
