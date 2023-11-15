package com.meeting.management.model;

import java.time.LocalDateTime;
import java.util.List;
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
@Document(collection = "Meeting")
public class Meeting {

	@Id
	private String id;
	@Field(name = "Meeting_Name")
	private String title;
	@Field(name = "Meeting_Id")
	private String meetingId;
	@Field(name = "Start_Date")
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private String startDate;
	@Field(name = "Duration")
	private int duration;
	@Field(name = "End_Date")
	private String endDate;
	@Field(name = "Meeting_createdDate")
	private LocalDateTime createdDate;
	@Field(name = "Meeting_Description")
	private String meetingDescription;
	@Field(name = "Initiator")
	private String initiator;
	@Field(name = "Chairman_Dept")
	private String chairmanDept;
	@Field(name = "Chairman")
	private String chairman;
	@Field(name = "Attending_Department")
	private List<String> attendingDepartment;
	@Field(name = "Location_Type")
	private String locationType;
	@Field(name = "Location_Url")
	private String locationUrl;
	@Field(name = "Location_place")
	private String locationPlace;
	@Field(name = "Proposed_Level")
	private String proposedLevel;
	// next step
	@Field(name = "Freeze_Agenda")
	@Builder.Default
	private Boolean freezeAgenda = false;
	@Field(name = "Chairman_Status")
	private String chairmanStatus;
	@Field(name = "Meeting_Priority")
	private String meetingPriority;
	@Field(name = "Repeat_Meeting")
	private String repeatMeeting;
	@Field(name = "Repeat_Month")
	private String repeatMonth;
	@Field(name = "Repeat_Week")
	private int repeatWeek;
	@Field(name = "Repeat_Day")
	private String repeatDay;
	@Field(name = "Count")
	private int count;
	@Field(name = "R-Rule")
	private String rRule;
	@Field(name = "Mom_Url")
	private String momURl;
	@Field(name = "Meeting_Status")
	@Builder.Default
	private String meetingStatus = "Creating";
	@Field(name = "PreApproved_Meeting")
	@Builder.Default
	private Boolean approveMeeting =false;
	@Field(name = "PreApproved_Agenda")
	@Builder.Default
	private Boolean approveAgenda =false;
	@Field(name = "Role Name")
	private String roleName;
	@Field(name = "Meeting_RelatedTo")
	private String meetingRelatedto;

}