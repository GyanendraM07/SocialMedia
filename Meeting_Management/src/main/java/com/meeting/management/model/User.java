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
@Document(collection = "Keycloak User")
public class User {
	
	@Id
	private String id;
	
	@Field(name="Department_Name")
	private String deptName;
	
	@Field(name="User Name")
	private String userName;
	
	@Field(name="User_Designation")
	private String userDesignation;

	

}
