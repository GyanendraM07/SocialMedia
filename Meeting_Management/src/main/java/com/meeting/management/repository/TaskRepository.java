package com.meeting.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.meeting.management.model.Task;

public interface TaskRepository extends MongoRepository<Task, String>{
//	Optional<Task> findBymeetingId(String meetingId);
	Optional<Task> findBydecisionId(String decisonId);
	List<Task> findBydecision(String decision);
	List<Task> findBymeetingId(String meetingId);

}
