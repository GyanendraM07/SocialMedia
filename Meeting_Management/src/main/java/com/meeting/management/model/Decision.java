package com.meeting.management.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Decision")
public class Decision {
	
	@Id
	private String id;
	
	@Field(name="Agenda")
	private List<String> agenda;
	
	@Field(name="Meeting_Id")
	private String meetingId;
	
	@Field(name="Decision")
	private String decision;
	
	@Field(name="Discussion")
	private String discussion;
	
//	@Field(name="extra")
//	private String extra;
//	
//	@Field(name="status")
//	private String status;
	
//	@DBRef
//    private Collection<Agenda> agenda;
//	@DBRef(db="Agenda")
//    private List<Agenda> agenda;
	
}

