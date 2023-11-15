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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "InboxInventory")
public class InboxInventory {
	
	@Id
	private String id;
	
	@Field(name="Meeting_Id")
	private String meetingId;
	@Field(name="Meeting_Title")
	private String meetingTitle;
	@Field(name="Receiving_Date")
	private LocalDateTime meetingRecivedDate;
	@Field(name="Start_Date")
	private String startDate;
	@Field(name="End_Date")
	private String endDate;
	@Field(name="Type")
	@Builder.Default
	private String type="Meeting";
	@Field(name="Chairman")
	private String chairman;
	@Field(name="Location")
	private String location;
	@Field(name="To Users")
	private List<String> toUsers;
	@Field(name="Status")
	@Builder.Default
	private String status= null;
	@Field(name="Meeting_Status")
	@Builder.Default
	private String meetingStatus= null;
}

