package com.meeting.management.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.meeting.management.model.Agenda;
import com.meeting.management.model.Attendees;
import com.meeting.management.model.Comment;
import com.meeting.management.model.Decision;
import com.meeting.management.model.ExternalAttendees;
import com.meeting.management.model.Meeting;
import com.meeting.management.model.Task;
import com.meeting.management.model.User;
import com.meeting.management.repository.AgendaRepository;
import com.meeting.management.repository.UserRepository;
import com.meeting.management.service.MeetingService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class MeetingController {

//	@Value("${minio.rest-url}")
//	private String baseUrl;
//
//	@Value("${minio.rest-port}")
//	private String port;

	@Autowired
	private MeetingService meetingService;

	// For meeting distribution
//	@GetMapping("/getAllMeeting")
//	public ResponseEntity<HashMap<String, Object>> getAgendas(@RequestHeader String roleName,
//			@RequestHeader String userName, @RequestHeader String department) {
//		HashMap<String, Object> hash = meetingService.distribute(roleName, userName, department);
//		return ResponseEntity.ok(hash);
//	}

	// For Department
	@GetMapping("/getDepartment")
	public ResponseEntity<HashMap<String, Object>> getDepartment() {
		return meetingService.department();
	}

	// For Department USerName
	@GetMapping("/getUserName")
	public ResponseEntity<HashMap<String, Object>> getUserName(@RequestHeader String deptName) {
		return meetingService.Users(deptName);
	}

	// getCoordUser
	@GetMapping("/getCoordUser")
	public ResponseEntity<HashMap<String, Object>> getCoordUser(@RequestHeader String deptName) {
		return meetingService.getCoordUser(deptName);
	}

	@GetMapping("/getGroups")
	public ResponseEntity<HashMap<String, Object>> getGroups() {
		return meetingService.groupsName();
	}

	@GetMapping("/getGroupUsers")
	public ResponseEntity<HashMap<String, Object>> getGroupUsers(@RequestHeader String groupName) {
		return meetingService.groupUsers(groupName);
	}

	// Create meeting
	@PostMapping("/create")
	public ResponseEntity<HashMap<String, Object>> createMeeting1(@RequestBody Meeting meeting,
			HttpServletRequest request) {
		String roleName = request.getHeader("roleName");
		return meetingService.createMeeting(meeting, roleName);
	}

	// Add Agenda To Meeting
	@PostMapping("/addAgenda")
	public ResponseEntity<HashMap<String, Object>> addAgenda(@RequestBody Agenda agenda,
			@RequestHeader String meetingId) {
		return meetingService.addAgenda(agenda, meetingId);
	}

	// Get Agenda Of Meeting
	@GetMapping("/getmeetingAgenda")
	public ResponseEntity<HashMap<String, Object>> getmeetingAgenda(@RequestHeader String meetingId) {
		return meetingService.getAgendaOfMeeting(meetingId);
	}

	// Get Meeting Department
	@GetMapping("/getMeetingDepartment")
	public ResponseEntity<HashMap<String, Object>> getMeetingDepartment(@RequestHeader String meetingId) {
		return meetingService.meetingDepartment(meetingId);
	}

	// get users from the deptName
	@GetMapping("/getMeetingUser")
	public ResponseEntity<HashMap<String, Object>> getMeetingUser(@RequestHeader String deptName) {
		return meetingService.getMeetingUser(deptName);
	}

	// Add attendees for meeting
	@PostMapping("/addAttendees")
	public ResponseEntity<HashMap<String, Object>> addAttendees(@RequestHeader String meetingId,
			@RequestBody Attendees attendee) throws Exception {
		return meetingService.addAttendee(attendee, meetingId);
	}

	// get Attendees by meeting id
	@GetMapping("/getAttendees")
	public ResponseEntity<HashMap<String, Object>> getAttendee(@RequestHeader String meetingId) {
		return meetingService.getAttendees(meetingId);
	}

	// Add external Attendee
	@PostMapping("/addExternalAttendee")
	public ResponseEntity<HashMap<String, Object>> addExternalAttendees(@RequestHeader String meetingId,
			@RequestBody ExternalAttendees attendee) {
		return  meetingService.addExternalAttendee(meetingId, attendee);
	}

	// get Attendees by meeting id
	@GetMapping("/getExternalAttendee")
	public ResponseEntity<HashMap<String, Object>> getExternalAttendee(@RequestHeader String meetingId) {
		return meetingService.getExternalAttendee(meetingId);
	}

	// Final meeting submit or send to chairman
	@PostMapping("/sendToChairman")
	public ResponseEntity<HashMap<String, Object>> sendToChairman(@RequestHeader String meetingId) {
		return meetingService.chairmanInbox(meetingId);
	}

	// Users inbox data
	@GetMapping("/getInboxData")
	public ResponseEntity<HashMap<String, Object>> inbox(@RequestHeader String userName,
			@RequestHeader String roleName) {
		return meetingService.inboxData(userName, roleName);
	}

	@GetMapping("/getCalendar")
	public ResponseEntity<HashMap<String, Object>> getCalendar(@RequestHeader String userName) {
		System.err.println("reached");
		return meetingService.getCalendar(userName);
	}

	// Get Meeting by Id and roleName
	@GetMapping("/getMeetingById")
	public ResponseEntity<HashMap<String, Object>> getMeetingById(@RequestHeader String meetingId) {
		return meetingService.getMeetingById(meetingId);
	}

	// meeting Approved by chairman
	@PostMapping("/approve")
	public ResponseEntity<HashMap<String, Object>> approvedMeetingByChairman(@RequestHeader String userName,
			@RequestHeader String meetingPriority, @RequestHeader String meetingId, @RequestBody Comment comment) {
		return meetingService.approveMeeting(userName, meetingPriority, meetingId, comment);
		
	}

	// meeting Reject by chairman
	@PostMapping("/reject")
	public ResponseEntity<HashMap<String, Object>> rejectMeetingByChairman(@RequestHeader String userName,
			@RequestHeader String meetingId, @RequestBody Comment comment) {
		return meetingService.rejectMeeting(userName, meetingId, comment);
	}

	// Freeze agenda
	@PutMapping("/freezeAgenda")
	public ResponseEntity<HashMap<String, Object>> freezeAgenda(@RequestHeader String meetingId) {
		return meetingService.freezeAgenda(meetingId);
	}

	@GetMapping("/getUserAttedee")
	public ResponseEntity<HashMap<String, Object>> getUserAttendees(@RequestHeader String meetingid,
			@RequestHeader String deptUserName) {
		return meetingService.getUserAttendees(meetingid, deptUserName);
	}

	// Attending user options or Attendees options
	@PostMapping("/userAcceptMeeting")
	public ResponseEntity<HashMap<String, Object>> acceptMeeting(@RequestHeader String meetingid,@RequestHeader String deptUserName) {			
		return meetingService.userAcceptMeeting(meetingid, deptUserName);
	}

	// userOption RejectMeeting
	@PostMapping("/userRejectMeeting")
	public ResponseEntity<HashMap<String, Object>> rejectMeeting(@RequestHeader String meetingid,@RequestHeader String deptUserName, @RequestBody Comment comment) {		
		return meetingService.userRejectMeeting(meetingid, deptUserName, comment);
	}

	// userOption forwardMeeting
	@PostMapping("/userForwardMeeting")
	public ResponseEntity<HashMap<String, Object>> forwardMeeting(@RequestHeader String meetingid,@RequestHeader String deptUserName, @RequestHeader String meetingPriority,
			@RequestHeader String forwardUserDept, @RequestHeader String forwardUser, @RequestBody Comment comment) {
		return meetingService.userForwardMeeting(meetingid, deptUserName, meetingPriority,forwardUserDept, forwardUser, comment);
	}

	// Initiator Options
	// RescheduledMeeting
	@PostMapping("/rescheduledMeeting")
	public ResponseEntity<HashMap<String, Object>> rescheduledMeeting(@RequestHeader String meetingId,
			@RequestBody Meeting meeting, @RequestHeader String comment) {
		return meetingService.rescheduledMeeting(meetingId, meeting, comment);
	}

	// Initiator Options
	// CancleMeeting
	@PostMapping("/cancelMeeting")
	public ResponseEntity<HashMap<String, Object>> cancelMeeting(@RequestHeader String meetingId) {
		return meetingService.cancelMeeting(meetingId);
	}

	// Add DEcision On Agenda
	@PostMapping("/decision")
	public ResponseEntity<HashMap<String, Object>> decisionOnAgenda(@RequestBody Decision decision) {
		return meetingService.decisionOnAgenda(decision);
	}

	// getDecision2
	@GetMapping("/Getdecision")
	public ResponseEntity<HashMap<String, Object>> GetDecision(@RequestHeader String meetingId) {
		return meetingService.GetDecision(meetingId);
	}

	// Assign Task on Agenda
	@PostMapping("/task")
	public ResponseEntity<HashMap<String, Object>> actionTask(@RequestHeader String decisionId,
			@RequestBody Task task) {
		return meetingService.AssignTask(decisionId, task);
	}

	// getTask
	@GetMapping("/getTask")
	public ResponseEntity<HashMap<String, Object>> getTask(@RequestHeader String meetingId) {
		return meetingService.getTask(meetingId);
	}

	// AttachDocuments on AssignedTask
	@PostMapping("/attachDocument")
	public ResponseEntity<HashMap<String, Object>> attachDocument(HttpServletRequest request,
			@RequestHeader String userName, @RequestHeader String meetingId, @RequestHeader String decisionId,
			@RequestBody MultipartFile file) {
		String clippedToken;
		try {
			String token = (String) request.getHeader("Authorization");
			clippedToken = token.replace("Bearer ", "");
		} catch (Exception e) {
			e.printStackTrace();
			HashMap<String, Object> json1 = new HashMap<String, Object>();
			json1.put("status", "Keycloak Exception");
			json1.put("message", "keycloak is not working");
			return ResponseEntity.ok(json1);
		}
		return meetingService.attachDocument(clippedToken, userName, meetingId, decisionId, file);
	}

	// Get Document url
	@GetMapping("/getDocument")
	public ResponseEntity<?> getDocument(@RequestHeader String meetingId) {
		return meetingService.getDocument(meetingId);
	}

	// Generate meetingMemo
	@PostMapping("/meetingMemo")
	public ResponseEntity<HashMap<String, Object>> meetingMemo(HttpServletRequest request,
			@RequestHeader String meetingId,@RequestHeader List<String> Attended) throws IOException{
			String token = request.getHeader("Authorization");
			String clippedToken = token.replace("Bearer ", "");
			return meetingService.meetingMemo(clippedToken, meetingId,Attended);
	}

	// getMeetingMemoUrl
	@GetMapping("/getmeetingMemo")
	public ResponseEntity<?> getMeetingMemo(@RequestHeader String meetingId) {
		return meetingService.getMeetingMemo(meetingId);
	}

	// view TaskDetails
	@GetMapping("/taskDetails")
	public ResponseEntity<HashMap<String, Object>> taskDetails(@RequestHeader String meetingId) {
		HashMap<String, Object> json = meetingService.openTaskDetails(meetingId);
		return ResponseEntity.ok(json);
	}

	// task forward to Another user
	@PostMapping("/assignFurther")
	public ResponseEntity<HashMap<String, Object>> assignFurther(@RequestHeader String meetingId,
			@RequestBody Task task1) {
		HashMap<String, Object> json = meetingService.taskAssignFurther(meetingId, task1);
		return ResponseEntity.ok(json);
	}

	// NextSteps

	// delete meeting by Id
	@DeleteMapping("/deleteMeeting")
	public ResponseEntity<HashMap<String, Object>> deleteMeeting(@RequestHeader String meetingId) {
		return meetingService.deleteMeeting(meetingId);

	}

	// getAllComment
	@GetMapping("getAllComment")
	public ResponseEntity<?> getComment(@RequestHeader String meetingId) {
		HashMap<String, Object> json = meetingService.getComment(meetingId);
		return ResponseEntity.ok(json);
	}

	// Delete Attendees
//	@DeleteMapping("/deleteAttendee")
//	public ResponseEntity<HashMap<String, Object>> deleteAttendees(@RequestBody List<String> meetingId)
//			throws Exception {
//		return meetingService.deleteAttendee(meetingId);
//	}

	// Edit Agenda
	@Autowired
	private AgendaRepository agendaRepository;

	@SuppressWarnings("unchecked")
	@PutMapping("/editAgenda")
	public ResponseEntity<HashMap<String, Object>> editAgenda(@RequestHeader String id,
			@RequestBody Agenda updateAgenda) throws Exception {
		Optional<Agenda> agenda = agendaRepository.findById(id);
		HashMap<String, Object> json = new HashMap<String, Object>();
		if (agenda.isPresent()) {
			agenda.get().setAgenda(updateAgenda.getAgenda());
			agenda.get().setDescription(updateAgenda.getDescription());
			agenda.get().setStatus(updateAgenda.getStatus());
			agenda.get().setRequestedBy(updateAgenda.getRequestedBy());
			Agenda updatedAgenda = agendaRepository.save(agenda.get());
			json.put("status", HttpStatus.OK);
			json.put("data", updatedAgenda);
		} else {
			json.put("status", HttpStatus.NOT_FOUND);
			json.put("message", "Id Does Not Exist");
		}
		return ResponseEntity.ok(json);
	}

	// get Agenda by meeting id
//	@SuppressWarnings("unchecked")
//	@GetMapping("/getAgenda")
//	public ResponseEntity<JSONObject> getAgenda(@RequestHeader String meetingId) {
//		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
//		JSONObject json = new JSONObject();
//		if (meeting.isPresent()) {
//			json.put("status", HttpStatus.OK);
//			json.put("data", agendaRepository.findByMeetingIdAndStatusNot(meetingId, "Deleted"));
//		} else {
//			json.put("status", HttpStatus.NOT_FOUND);
//			json.put("message", "Id Does Not Exist");
//		}
//		return ResponseEntity.ok(json);
//	}

	// Delete Agenda
//	@DeleteMapping("/deleteAgenda")
//	public ResponseEntity<HashMap<String, Object>> deleteAgenda(@RequestBody List<String> meetingId) throws Exception {
//		return meetingService.deleteAgenda(meetingId);
////		return ResponseEntity.ok(json);
//	}

	// confirm agenda
	@PostMapping("/confirmAgenda")
	public ResponseEntity<HashMap<String, Object>> confirmAgenda(@RequestBody List<String> meetingId) {
		HashMap<String, Object> json = meetingService.confirmAgenda(meetingId);
		return ResponseEntity.ok(json);
	}

	// meetingInvitation
	@PostMapping("/meetingInvite")
	public ResponseEntity<HashMap<String, Object>> meetingInvitation(HttpServletRequest request,
			@RequestHeader String meetingId) throws IOException {
		String clippedToken;
		try {
			String token = request.getHeader("Authorization");
			clippedToken = token.replace("Bearer ", "");
		} catch (Exception e) {
			e.printStackTrace();
			HashMap<String, Object> json = new HashMap<String, Object>();
			json.put("status", HttpStatus.BAD_REQUEST);
			json.put("message", "keycloak is not working");
			return ResponseEntity.ok(json);
		}
		HashMap<String, Object> json = meetingService.meetingInvitation(meetingId, clippedToken);
		return ResponseEntity.ok(json);

	}

	@Autowired
	UserRepository userRepository;

	@SuppressWarnings("unchecked")
	@PostMapping("/addingUsers")
	public ResponseEntity<HashMap<String, Object>> addUsers(@RequestBody User user) {
		userRepository.save(user);
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("status", HttpStatus.OK);
		return ResponseEntity.ok(json);
	}
}
