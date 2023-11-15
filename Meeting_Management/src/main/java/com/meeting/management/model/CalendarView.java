package com.meeting.management.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarView {
	
	@Id
	private String id;
	@Field(name="Meeting Id")
	private String meetingId;
	@Field(name="Meeting_Title")
	private String meetingTitle;
	@Field(name="Meeting_receivedDate")
	private LocalDateTime meetingReceivedDate;
	@Field(name="Meeting_startDate")
	private String startDate;
	@Field(name="Meeting_endDate")
	private String endDate;
	@Field(name="Type")
	@Builder.Default
	private String type="Meeting";
	@Field(name="To Users")
	private List<String> toUsers;
	@Field(name="Status")
	@Builder.Default
	private String status=null;
	@Field(name="Meeting_Status")
	@Builder.Default
	private String meetingStatus="Creating";
	

}
