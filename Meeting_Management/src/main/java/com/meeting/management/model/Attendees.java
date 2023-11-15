package com.meeting.management.model;

import java.util.List;

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
@Document(collection = "Attendees")
public class Attendees {

	@Id
	private String id;

	@Field(name = "Dep_Name")
	private String deptName;
	
	@Field(name = "User Name")
	private String userName;
	
	@Field(name = "Group_User")
	private List<String> groupUsers;
	
	@Field(name = "Group_Name")
	private String groupName;

	@Field(name = "Is_Chairman")
	@Builder.Default
	private boolean isChairman = false;

	@Field(name = "Meeting_Id")
	private String meetingId;

	@Field(name = "extra")
	private String extra;

	@Field(name = "status")
	@Builder.Default
	private String status = "pending";

	@Field(name = "User Designation")
	private String userDesignation;

	@Field(name = "Meeting Priority")
	private String meetingPriority;

}
