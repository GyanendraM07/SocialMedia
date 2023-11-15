package com.meeting.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.meeting.management.model.Decision;

@Repository
public interface DecisionRepository extends MongoRepository<Decision, String>{

	List<Decision> findByAgenda(String agenda);
	Optional<Decision> findById(String decisionId);
	List<Decision> findByMeetingId(String meetingId);

}
