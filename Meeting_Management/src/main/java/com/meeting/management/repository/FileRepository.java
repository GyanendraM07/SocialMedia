package com.meeting.management.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.File;
@Repository
public interface FileRepository extends MongoRepository<File, String>{
	List<File> findByMeetingId(String meeetingId);

}
