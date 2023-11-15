package com.meeting.management.model;

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
@Document(collection = "Users")
public class Users {
	
	@Id
	private String Id;
	@Field (name="dept_name")
	private String deptName;
	@Field(name="dept_role")
	private String deptRole;
	@Field(name="dept_username")
	private String deptUserName;
	@Field(name="dept_display_name")
	private String deptDisplayName;
	@Field("Dept_Role_display_name")
	private String deptRoleDisplayName;
	@Field(name="dept_display_username")
	private String deptDisplayUserName;
	@Field(name="user_Desgnation")
	private String userDesignation;

}
