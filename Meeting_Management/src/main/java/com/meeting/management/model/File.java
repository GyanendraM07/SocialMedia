package com.meeting.management.model;

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
@Document
public class File {
	@Id
	private String id;
	@Field(name="Meeting_Id")
	private String meetingId;
	@Field(name="File_title")
	private String filetitle;
	@Field(name="Decison_id")
	private String decisonId;
	@Field(name="File_url")
	private String fileUrl;

}
