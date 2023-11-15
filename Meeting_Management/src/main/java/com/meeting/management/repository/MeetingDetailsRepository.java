package com.meeting.management.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.MeetingDetails;

@Repository
public interface MeetingDetailsRepository extends MongoRepository<MeetingDetails, String>{

	

}