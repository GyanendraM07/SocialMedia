package com.meeting.management.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExternalAttendees {
	
	@Id
	private String Id;
	@Field(name="Meeting_Id")
	private String meetingId;
	@Field(name="External_Attendees")
	private String externalAttendees;
	@Field(name="Email_Id")
	private  String emailId;
	@Field(name="Mobile_No")
	private  String mobileNo;
	@Field(name="Status")
	@Builder.Default
	private String status="Pending";
	

}
