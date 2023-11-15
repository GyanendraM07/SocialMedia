package com.meeting.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.Groups;

@Repository
public interface GroupsRepository extends MongoRepository<Groups, String>{

	Optional<Groups> findByGroupName(String groupName);
	

}
