package com.meeting.management.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.Agenda;

@Repository
public interface AgendaRepository extends MongoRepository<Agenda, String>{

	List<Agenda> findByMeetingId(String meetingId);

	List<Agenda> findByMeetingIdAndStatusNot(String meetingId, String string);

	List<Agenda> findByMeetingIdAndStatus(String meetingId, String string);
	
	

}
