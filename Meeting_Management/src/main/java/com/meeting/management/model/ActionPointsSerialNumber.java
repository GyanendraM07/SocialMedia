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
@Document(collection = "Action_Points_Serial_Number")
public class ActionPointsSerialNumber {
	
	@Id
	private String id;
	
	@Field(name="Count")
	private int count;
	
	@Field(name="Dir")
	private String dir;
	
	@Field(name="Serial No")
	private String serialNo;
	
	@Field(name="Task Id")
	private String taskId;
	
}

