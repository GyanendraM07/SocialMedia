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
@Document(collection = "Agenda")
public class Agenda {
	@Id
	private String id;
	@Field(name="Meeting Id")
	private String meetingId;
	@Field(name="Agenda")
	private String agenda;
	@Field(name="Attach_Document_Url")
	private List<String> Url;
	@Field(name="extra")
	private String extra;
	@Field(name="Requested By")
	private String requestedBy;
	@Field(name="Description")
	private String description;
	@Field(name="status")
	@Builder.Default
	private String status="Pending";
	@Field(name="Suggested Agenda")
	private String suggestedAgenda;
	
}
