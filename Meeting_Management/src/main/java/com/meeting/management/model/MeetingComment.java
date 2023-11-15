package com.meeting.management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "Meeting_Comment")
public class MeetingComment {
	
	@Id
	private String id;
	
	@Field(name="Comment")
	private String comment;
	
	@Field(name="Dept From")
	private String deptFrom;
	
	@Field(name="Dept To")
	private String deptTo;
	
	@Field(name="extra")
	private String extra;
	
	@Field(name="Meeting id")
	private String meetingId;
	
	@Field(name="status")
	private String status;
	
	@Field(name="Update Status")
	private Boolean updateStatus;
	
	@Field(name="User From")
	private String userFrom;
	
	@Field(name="User To")
	private String userTo;
	
}