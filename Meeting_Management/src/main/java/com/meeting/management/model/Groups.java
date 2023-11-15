package com.meeting.management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Groups")
public class Groups {
     
	@Id
	private String Id;
	@Field(name="Group_Name")
	private String groupName;
	@Field(name="Group_Users")
	private List<String> groupUsers;
}
