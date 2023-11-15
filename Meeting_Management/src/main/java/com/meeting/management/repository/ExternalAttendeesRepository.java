package com.meeting.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.ExternalAttendees;

@Repository
public interface ExternalAttendeesRepository extends MongoRepository<ExternalAttendees, String>{

	List<ExternalAttendees> findByMeetingId(String meetingId);

	Optional<ExternalAttendees> findByMeetingIdAndExternalAttendeesAndEmailId(String meetingId,
			String externalAttendees, String emailId);
	

}
