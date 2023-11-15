package com.meeting.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.meeting.management.model.InboxInventory;

public interface InboxInventoryRepository  extends MongoRepository<InboxInventory, String>{

	List<InboxInventory> findByToUsersAndStatus(String userName, int i);

//	List<InboxInventory> findBytoUsersAndstatusOrtoUsersAndstatus(String userName, int i,String userName2, int i2);

	List<InboxInventory> findByToUsersAndStatusOrToUsersAndStatus(String userName,String status,String userName2,String status1);

	List<InboxInventory> findByMeetingId(String meetingId);

	Optional<InboxInventory> findByMeetingIdAndToUsersAndStatus(String meetingId, String userName, String string);
	
	

}
