package com.meeting.management.model;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection="Task")
public class Task {
	@Id
	private String id;
	
	@Field(name="Meeting_Id")
	private String meetingId;
	
	@Field(name="Decision")
	private String decision;
	
	@Field(name="Decision_Id")
	private String decisionId;
	
	@Field(name="Reference")
	private String reference;
	
	@Field(name="Subject")
	private String subject;
	
	@Field(name="On_Behalf_Of")
	private String onBehalfOf;
	
	@Field(name="Due_Date")
	private Date DueDate;
	
	@Field(name="Description")
	private String description;
	
	@Field(name="Dep_User Name")
	private String deptUserName;
	
	@Field(name="Internal_User")
	private String internalUser;
	
	@Field(name="External_User")
	private String externalUser;
	
	@Field(name="Status")
	@Builder.Default
	private String status="Pending";
	
	@Field(name="File_Attachment_Url")
	private String fileUrl;
	
	@Field(name="Memo_Meeting_Url")
	private String memoMeetingUrl;
	
	@Field(name="Priority")
	private String priority;
	
	@Field(name="Favorites")
	private String favorites;
	
	@Field(name="From_User")
	private String fromUser;
	
	@Field(name="To_User")
	private String toUser;


}
