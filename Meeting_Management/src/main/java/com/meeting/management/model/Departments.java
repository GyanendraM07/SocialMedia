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
@Document(collection = "Departments")
public class Departments {
	
	@Id
	private String Id;
	@Field(name="dept_name")
	private String deptName;
	@Field(name="dept_coord_role")
	private String deptCoordRole;
	@Field(name="dept_display_name")
	private String deptDisplayName;

}
