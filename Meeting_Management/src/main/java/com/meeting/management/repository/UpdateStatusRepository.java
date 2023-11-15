package com.meeting.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.UpdateStatus;

@Repository
public interface UpdateStatusRepository extends MongoRepository<UpdateStatus, String>{

}
