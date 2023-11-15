package com.meeting.management.model;

import java.util.Date;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mongodb.lang.NonNull;

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
@Document(collection = "meeting_details")
public class MeetingDetails {
	
	@Id
	private String id;
	
	@Field
	@NonNull
	private String meetingName;
	
	@Field
	@NonNull
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private Date date;
	
	@Field
	private String duration;
	
	@Field
	private String description;
	
	@Field(name="is recursive")
	private boolean recursive;
	
	@Field
	private String intiator;
	
	@Field
	private String chairmanDepartment;
	
	@Field
	private String chairman;
	
	@Field
	private String attendingDepartment;
	
	@Field
	private String ExternalAttendeness;
	
	@Field
	private String location;
	
	@Field
	private String level;
	

}
