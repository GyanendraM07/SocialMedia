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
@Document(collection = "Comment")
public class Comment {
	
	@Id
	private String id;
	
	@Field(name="Meeting_Id")
	private String meetingId;
	
	@Field(name="Comment")
	private String comment;
	
	@Field(name="User_From")
	private String userFrom;
	
	@Field(name="Status")
	private String status;

}
