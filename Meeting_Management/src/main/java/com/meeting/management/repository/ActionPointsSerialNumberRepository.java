package com.meeting.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.ActionPointsSerialNumber;

@Repository
public interface ActionPointsSerialNumberRepository extends MongoRepository<ActionPointsSerialNumber, String> {

}
