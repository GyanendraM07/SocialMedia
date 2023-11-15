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
@Document(collection = "Update_Status")
public class UpdateStatus {
	
	@Id
	private String id;
	
	@Field(name="Comment")
	private String comment;
	
	@Field(name="Content id value")
	private String contentIdValue;
	
	@Field(name="Content Name")
	private String contentName;
	
	@Field(name="Content Type")
	private String contentType;
	
	@Field(name="Dept From")
	private String deptFrom;
	
	@Field(name="Dept To")
	private String deptTo;
	
	@Field(name="extra")
	private String extra;
	
	@Field(name="extra_m")
	private String extra_m;
	
	@Field(name="Fwd Status")
	private Boolean fwdStatus;
	
	@Field(name="Status")
	private String status;
	
	@Field(name="Task id")
	private String taskId;
	
	@Field(name="Updated By")
	private String updatedBy;
	
	@Field(name="User From")
	private String userFrom;
	
	@Field(name="User To")
	private String userTo;
	
	
}