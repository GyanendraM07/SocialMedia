package com.meeting.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.CalendarView;

@Repository
public interface CalendarViewRepository  extends MongoRepository<CalendarView, String>{

	List<CalendarView> findByToUsers(String username);

	List<CalendarView> findByToUsersAndStatusOrToUsersAndStatusOrToUsersAndStatus(String username, String string,
			String username2, String string2, String username3, String string3);

	Optional<CalendarView> findByMeetingIdAndToUsers(String meetingId, String initiator);

	List<CalendarView> findByMeetingId(String meetingId);

	List<CalendarView> findByToUsersAndMeetingStatusOrToUsersAndMeetingStatusOrToUsersAndMeetingStatus(String username,
			String string, String username2, String string2, String username3, String string3);

}
