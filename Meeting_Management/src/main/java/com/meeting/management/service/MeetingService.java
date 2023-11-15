package com.meeting.management.service;

import java.io.ByteArrayInputStream;
import com.meeting.management.model.Users;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.meeting.management.model.Groups;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.meeting.management.exception.CustomException;
import com.meeting.management.model.Agenda;
import com.meeting.management.model.Attendees;
import com.meeting.management.model.CalendarView;
import com.meeting.management.model.Comment;
import com.meeting.management.model.Decision;
import com.meeting.management.model.Departments;
import com.meeting.management.model.ExternalAttendees;
import com.meeting.management.model.File;
import com.meeting.management.model.InboxInventory;
import com.meeting.management.model.Meeting;
import com.meeting.management.model.RepeatMeeting;
import com.meeting.management.model.Task;
import com.meeting.management.repository.AgendaRepository;
import com.meeting.management.repository.AttendeesRepository;
import com.meeting.management.repository.CalendarViewRepository;
import com.meeting.management.repository.CommentRepository;
import com.meeting.management.repository.DecisionRepository;
import com.meeting.management.repository.DepartmentsRepository;
import com.meeting.management.repository.ExternalAttendeesRepository;
import com.meeting.management.repository.FileRepository;
import com.meeting.management.repository.GroupsRepository;
import com.meeting.management.repository.InboxInventoryRepository;
import com.meeting.management.repository.MeetingRepository;
import com.meeting.management.repository.TaskRepository;
import com.meeting.management.repository.UserRepository;
import com.meeting.management.repository.UsersRepository;

@Service
public class MeetingService {
	@Value("${minio.rest-url}")
	private String baseUrl;

	@Value("${minio.rest-port}")
	private String port;

	@Autowired
	private AWSClientConfigService awsClientConfig;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private AttendeesRepository attendeesRepository;
	@Autowired
	private MeetingRepository meetingRepository;
	@Autowired
	private AgendaRepository agendaRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private InboxInventoryRepository inboxInventoryRepository;
	@Autowired
	DecisionRepository decisionRepository;
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	DepartmentsRepository departmentsRepository;
	@Autowired
	ExternalAttendeesRepository externalAttendeesRepository;
	@Autowired
	CalendarViewRepository calendarRepository;
	@Autowired
	GroupsRepository groupRepository;
	@Autowired
	RepeatMeeting repeatMeeting;

	HashMap<String, Object> hash = new HashMap<String, Object>();

	// create Metting
	public ResponseEntity<HashMap<String, Object>> createMeeting(Meeting meeting, String roleName) {
		hash.clear();
		if (meeting.getTitle() == "" || meeting.getLocationPlace() == "" || meeting.getInitiator() == ""
				|| meeting.getProposedLevel() == "" || meeting.getStartDate() == "" || meeting.getDuration() == 0
				|| meeting.getMeetingDescription() == "") {
			throw new CustomException(" Fields can not be blank pls Fill the required fields",
					HttpStatus.LENGTH_REQUIRED);
		} else {
			LocalDateTime dateTime = LocalDateTime.parse(meeting.getStartDate());
			int monthofyear=dateTime.getMonth().getValue();
			int year=dateTime.getYear();
			int dayofmonth=dateTime.getDayOfMonth();
			int dayofweek=dateTime.getDayOfWeek().getValue();
			int hour=dateTime.getHour();
			int min=dateTime.getMinute();
			int sec =dateTime.getSecond();
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, monthofyear, dayofmonth);
			int weekofMonth=calendar.get(Calendar.WEEK_OF_MONTH);
			System.out.print("Details of Meeting  ");
			System.out.println("month"+monthofyear+"year"+year+"dayofmonth"+dayofmonth+"dayofweek"+dayofweek+"hour"+hour+"min"+min+"week"+weekofMonth);
			LocalDateTime demo = dateTime.plusHours(meeting.getDuration());
			meeting.setStartDate(meeting.getStartDate());
			meeting.setEndDate(demo.toString());
			meeting.setCreatedDate(LocalDateTime.now());
			Meeting createdMeeting = meetingRepository.save(meeting);
			System.out.println("1st Meeting" + createdMeeting);
			repeatMeeting.repeatMeeting(createdMeeting,hour,min,sec,monthofyear,year,weekofMonth,dayofweek,dayofmonth);
			System.err.println("reached ouside the repeat");
			// add chairman as attendee
			if (meeting.getChairman() != null) {
				Attendees attendee = new Attendees();
				attendee.setUserName(createdMeeting.getChairman());
				attendee.setDeptName(createdMeeting.getChairmanDept());
				attendee.setMeetingId(createdMeeting.getId());
				attendee.setChairman(true);
				attendeesRepository.save(attendee);
			}

			Optional<Users> keycloakuser = usersRepository.findByDeptUserName(createdMeeting.getInitiator());
			Attendees attendee2 = new Attendees();
			attendee2.setUserName(createdMeeting.getInitiator());
			attendee2.setDeptName(keycloakuser.get().getDeptName());
			attendee2.setMeetingId(createdMeeting.getId());
			attendee2.setUserDesignation(keycloakuser.get().getUserDesignation());
			attendeesRepository.save(attendee2);
			hash.put("status", HttpStatus.OK);
			hash.put("id", createdMeeting.getId());
			return new ResponseEntity<>(hash, HttpStatus.OK);
		}
	}

	public ResponseEntity<HashMap<String, Object>> addAgenda(Agenda agenda, String meetingId) {
		hash.clear();
		if (agenda.getAgenda() == "" || agenda.getRequestedBy() == "" || agenda.getDescription() == "") {
			throw new CustomException("Fields can not be blank pls Fill the required fields",
					HttpStatus.LENGTH_REQUIRED);
		} else {
			Optional<Meeting> meeting = meetingRepository.findById(meetingId);
			if (meeting.isPresent()) {
				// if freazeAgendaIsTrue can not addAgenda
				if (!meeting.get().getFreezeAgenda()) {
					agenda.setMeetingId(meetingId);
					Agenda addedAgenda = agendaRepository.save(agenda);
					hash.put("status", HttpStatus.OK);
					hash.put("data", addedAgenda);
					return new ResponseEntity<>(hash, HttpStatus.OK);
				} else {
					throw new CustomException("Agenda is freezed, can not add agenda", HttpStatus.NOT_ACCEPTABLE);
				}
			} else {
				throw new CustomException("Meeting Id Does Not Exist", HttpStatus.BAD_REQUEST);
			}
		}
	}

	public ResponseEntity<HashMap<String, Object>> getAgendaOfMeeting(String meetingId) {
		hash.clear();
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		if (meeting.isPresent()) {
			List<Agenda> agenda = agendaRepository.findByMeetingId(meetingId);
			hash.put("Status", HttpStatus.OK);
			hash.put("agenda", agenda);
			return new ResponseEntity<>(hash, HttpStatus.OK);
		} else {
			throw new CustomException("Meeting Id Does Not Exist", HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<HashMap<String, Object>> meetingDepartment(String meetingId) {
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		hash.clear();
		List<String> depts = new ArrayList<String>();
		if (meeting.isPresent()) {
			for (String dept : meeting.get().getAttendingDepartment()) {
				depts.add(dept);
			}
			hash.put("status", HttpStatus.OK);
			hash.put("data", depts);
			return new ResponseEntity<>(hash, HttpStatus.OK);
		} else {
			throw new CustomException("meeting Id Dose Not Exist", HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<HashMap<String, Object>> getAttendees(String meetingId) {
		hash.clear();
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		List<Attendees> atd = attendeesRepository.findByMeetingIdAndStatusNot(meetingId, "Deleted");
		if (meeting.isPresent()) {
			hash.put("status", HttpStatus.OK);
			hash.put("data", atd);
			return new ResponseEntity<>(hash, HttpStatus.OK);
		} else {
			throw new CustomException("meeting Id Dose Not Exist", HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<HashMap<String, Object>> getExternalAttendee(String meetingId) {
		hash.clear();
		;
		List<ExternalAttendees> exated = externalAttendeesRepository.findByMeetingId(meetingId);
		hash.put("status", HttpStatus.OK);
		hash.put("data", exated);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> addAttendee(Attendees attendee, String meetingId) {
		hash.clear();
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		if (meeting.isPresent()) {
			Optional<Attendees> attendee1 = attendeesRepository.findByMeetingIdAndUserName(meetingId,
					attendee.getUserName());
			if (!attendee1.isPresent()) {
				attendee.setMeetingId(meetingId);
				Attendees addedAttendee = attendeesRepository.save(attendee);
				hash.put("status", HttpStatus.OK);
				hash.put("data", addedAttendee);
				return new ResponseEntity<>(hash, HttpStatus.OK);
			} else {
				throw new CustomException("Attendee Already Exist", HttpStatus.ALREADY_REPORTED);
			}
		} else {
			throw new CustomException("meeting Id Dose Not Exist", HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<HashMap<String, Object>> addExternalAttendee(String meetingId, ExternalAttendees attendees) {
		hash.clear();
		Optional<ExternalAttendees> externalAttendee = externalAttendeesRepository
				.findByMeetingIdAndExternalAttendeesAndEmailId(meetingId, attendees.getExternalAttendees(),
						attendees.getEmailId());
		if (!externalAttendee.isPresent()) {
			attendees.setMeetingId(meetingId);
			externalAttendeesRepository.save(attendees);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new CustomException("External_Attendee Already_Exist", HttpStatus.ALREADY_REPORTED);
		}
	}

//	public HashMap<String, Object> distribute(String roleName, String userName, String department) {
//		try {
////			List<Meeting> meeting = meetingRepository
////					.findByChairmanAndStatusOrRoleNameAndStatusOrRoleNameAndStatusOrRoleNameAndStatus(userName,
////							"Approved", roleName, "Created", roleName, "Approved", roleName, "Rejected");
//			List<Meeting> meeting = meetingRepository
//					.findByChairmanAndMeetingStatusOrInitiatorAndMeetingStatusOrInitiatorAndMeetingStatusOrInitiatorAndMeetingStatus(
//							userName, "Approved", userName, "Created", userName, "Approved", userName, "Reject");
//			List<Attendees> attendees = attendeesRepository.findByUserNameAndIsChairmanAndStatus(userName, false,
//					"Attending");
//			List<String> ids = new ArrayList<String>();
//			HashMap<String, Object> json = new HashMap<String, Object>();
//			for (Attendees attendee : attendees) {
//				ids.add(attendee.getMeetingId());
//			}
//			List<Meeting> meeting1 = (List<Meeting>) meetingRepository.findAllById(ids);
//			meeting.addAll(meeting1);
//
//			if (!meeting.isEmpty()) {
//				json.put("status", HttpStatus.OK);
//				json.put("data", meeting);
//			} else {
//				json.put("status", HttpStatus.NOT_FOUND);
//				json.put("message", "meeting is not present");
//			}
//			return json;
//		} catch (Exception e) {
//			e.printStackTrace();
//			HashMap<String, Object> json1 = new HashMap<String, Object>();
//			json1.put("status", HttpStatus.NOT_FOUND);
//			json1.put("message", "Database is not working");
//			return json1;
//		}
//	}

	public ResponseEntity<HashMap<String, Object>> department() {
		hash.clear();
		List<String> deptName = new ArrayList<String>();
		deptName.add("7wghrc");
		deptName.add("7wgcad");
		deptName.add("7wgmeto");
		deptName.add("7wgadm");
		hash.put("status", HttpStatus.OK);
		hash.put("data", deptName);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> groupsName() {
		hash.clear();
		List<Groups> group = groupRepository.findAll();
		List<String> groupName = new ArrayList<String>();
		for (Groups grps : group) {
			groupName.add(grps.getGroupName());
		}
		hash.put("Status", HttpStatus.OK);
		hash.put("data", groupName);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> groupUsers(String groupName) {
		hash.clear();
		Optional<Groups> group = groupRepository.findByGroupName(groupName);
		hash.put("Status", HttpStatus.OK);
		hash.put("data", group.get().getGroupUsers());
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> Users(String deptName) {
		hash.clear();
		List<Users> users = usersRepository.findByDeptName(deptName);
		List<String> dept = new ArrayList<String>();
		for (Users user : users) {
			dept.add(user.getDeptUserName());
		}
		if (!dept.isEmpty()) {
			hash.put("status", HttpStatus.OK);
			hash.put("data", dept);
		} else {
			hash.put("status", HttpStatus.NOT_FOUND);
			hash.put("message", "Users are not Present");
		}
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> getCoordUser(String deptName) {
		hash.clear();
		;
		Optional<Departments> depart = departmentsRepository.findByDeptName(deptName);
		Optional<Users> user = usersRepository.findByDeptRole(depart.get().getDeptCoordRole());
		hash.put("status", HttpStatus.OK);
		hash.put("CoordUser", user.get().getDeptUserName());
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> getMeetingUser(String deptName) {
		hash.clear();
		hash.put("data", userRepository.findByDeptName(deptName));
		hash.put("status", HttpStatus.OK);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> inboxData(String userName, String roleName) {
		hash.clear();
		List<InboxInventory> inbox = inboxInventoryRepository.findByToUsersAndStatusOrToUsersAndStatus(userName,
				"chairman", userName, "attendee");
		hash.put("status", HttpStatus.OK);
		hash.put("data", inbox);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> getCalendar(String username) {
		hash.clear();
		List<CalendarView> calendardata = calendarRepository
				.findByToUsersAndMeetingStatusOrToUsersAndMeetingStatusOrToUsersAndMeetingStatus(username, "created",
						username, "Approved", username, "rejected");
		hash.put("status", HttpStatus.OK);
		hash.put("data", calendardata);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> chairmanInbox(String meetingId) {
		hash.clear();
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		List<Meeting> meeting1 = meetingRepository.findByMeetingId(meetingId);
		if (meeting.isPresent()) {
			List<Attendees> attendees = attendeesRepository.findByMeetingId(meetingId);
			List<Agenda> agenda = agendaRepository.findByMeetingId(meetingId);
			InboxInventory inboxInventory = new InboxInventory();
			CalendarView calenderView = new CalendarView();
			calenderView.setMeetingId(meetingId);
			calenderView.setMeetingTitle(meeting.get().getTitle());
			calenderView.setMeetingReceivedDate(LocalDateTime.now());
			calenderView.setStartDate(meeting.get().getStartDate());
			calenderView.setEndDate(meeting.get().getEndDate());
			calenderView.setToUsers(new ArrayList<String>(Arrays.asList(meeting.get().getInitiator())));
			calenderView.setStatus("Initiator");
			calendarRepository.save(calenderView);
			if(!meeting1.isEmpty()) {
				for(Meeting m:meeting1) {
					CalendarView cvw = new CalendarView();
					cvw.setMeetingId(m.getMeetingId());
					cvw.setMeetingTitle(m.getTitle());
					cvw.setMeetingReceivedDate(LocalDateTime.now());
					cvw.setStartDate(m.getStartDate());
					cvw.setEndDate(m.getEndDate());
					cvw.setToUsers(new ArrayList<String>(Arrays.asList(m.getInitiator())));
					cvw.setStatus("Initiator");
					calendarRepository.save(cvw);
				}
			}
			List<CalendarView> clw2=calendarRepository.findByMeetingId(meetingId);
			if (meeting.get().getApproveMeeting()) {
				meeting.get().setMeetingStatus("Approved");
				for (Meeting m : meeting1) {
					m.setMeetingStatus("Approved");
				}
				for(CalendarView cw:clw2) {
					cw.setMeetingStatus("Approved");			
					}
				List<String> users = new ArrayList<String>();
				for (Attendees attendee : attendees) {
					users.add(attendee.getUserName());
				}
				inboxInventory.setMeetingId(meetingId);
				inboxInventory.setMeetingTitle(meeting.get().getTitle());
				inboxInventory.setMeetingRecivedDate(LocalDateTime.now());
				inboxInventory.setStartDate(meeting.get().getStartDate());
				inboxInventory.setEndDate(meeting.get().getEndDate());
				inboxInventory.setToUsers(users);
				inboxInventory.setMeetingStatus("Approved");
				inboxInventory.setStatus("Attendee");
				hash.put("message", "meeting sends to attendees");
				hash.put("status", HttpStatus.OK);
			} else {
				meeting.get().setMeetingStatus("created");
				for (Meeting m : meeting1) {
					m.setMeetingStatus("created");
				}
				for(CalendarView cw:clw2) {
					cw.setMeetingStatus("created");			
					}
				inboxInventory.setMeetingId(meetingId);
				inboxInventory.setMeetingTitle(meeting.get().getTitle());
				inboxInventory.setMeetingRecivedDate(LocalDateTime.now());
				inboxInventory.setToUsers(new ArrayList<String>(Arrays.asList(meeting.get().getChairman())));
				inboxInventory.setStatus("chairman");
				inboxInventory.setMeetingStatus("created");
			}
			if (meeting.get().getApproveAgenda()) {
				for (Agenda atd : agenda) {
					atd.setStatus("Approved");
				}
			}
			calendarRepository.saveAll(clw2);
			meetingRepository.save(meeting.get());
			meetingRepository.saveAll(meeting1);
			agendaRepository.saveAll(agenda);
			inboxInventoryRepository.save(inboxInventory);
		} else {
			throw new CustomException("meeting Id Dose Not Exist", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> getMeetingById(String meetingId) {
		hash.clear();
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		if (meeting.isPresent()) {
			hash.put("status", HttpStatus.OK);
			hash.put("data", meeting);
			return new ResponseEntity<>(hash, HttpStatus.OK);
		} else {
			throw new CustomException("meeting Id Dose Not Exist", HttpStatus.NOT_FOUND);
		}
	}

	// meeting Approved By chairman
	public ResponseEntity<HashMap<String, Object>> approveMeeting(String userName, String meetingPriority,
			String meetingId, Comment comment) {
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		if (meeting.isPresent()) {
			List<Attendees> attendees1 = attendeesRepository.findByMeetingIdAndIsChairman(meetingId, true);
			Optional<InboxInventory> inbox = inboxInventoryRepository.findByMeetingIdAndToUsersAndStatus(meetingId,
					userName, "chairman");
			Optional<CalendarView> calendarview = calendarRepository.findByMeetingIdAndToUsers(meetingId,
					meeting.get().getInitiator());
			List<Attendees> attendees = attendeesRepository.findByMeetingIdAndIsChairman(meetingId, false);
			List<String> users = new ArrayList<String>();
			CalendarView calendarview1 = new CalendarView();
			calendarview.get().setMeetingStatus("Approved");// approve meeting status in calendarView
			inbox.get().setStatus(null);// remove chairman from InputBox
			for (Attendees atd : attendees1) {
				atd.setStatus("Attending");
				attendeesRepository.save(atd);
			}
			calendarview1.setMeetingId(meetingId);
			calendarview1.setMeetingTitle(meeting.get().getTitle());
			calendarview1.setMeetingReceivedDate(LocalDateTime.now());
			calendarview1.setStartDate(meeting.get().getStartDate());
			calendarview1.setEndDate(meeting.get().getEndDate());
			calendarview1.setMeetingStatus("Approved");
			calendarview1.setToUsers(new ArrayList<String>(Arrays.asList(userName)));// set chairman data in calendar
																						// view
			calendarview1.setStatus("chairman");
			// update meeting
			meeting.get().setChairmanStatus("Attending");
			meeting.get().setMeetingStatus("Approved");
			meeting.get().setMeetingPriority(meetingPriority);
			comment.setMeetingId(meetingId);
			comment.setUserFrom(userName);
			for (Attendees attendee : attendees) {
				users.add(attendee.getUserName());
			}
			InboxInventory inboxInventory = new InboxInventory();// sending meeting to attendees
			inboxInventory.setMeetingId(meetingId);
			inboxInventory.setMeetingTitle(meeting.get().getTitle());
			inboxInventory.setMeetingRecivedDate(LocalDateTime.now());
			inboxInventory.setToUsers(users);
			inboxInventory.setStatus("attendee");
			inboxInventoryRepository.save(inboxInventory);
			meetingRepository.save(meeting.get());
			calendarRepository.save(calendarview1);
			calendarRepository.save(calendarview.get());
			inboxInventoryRepository.save(inbox.get());
			commentRepository.save(comment);
			hash.put("status", HttpStatus.OK);
			hash.put("message", "meeting Approved");
			return new ResponseEntity<>(hash, HttpStatus.OK);
		} else {
			throw new CustomException("meeting Id Dose Not Exist", HttpStatus.NOT_FOUND);
		}
	}

	// meeting reject by chairman
	public ResponseEntity<HashMap<String, Object>> rejectMeeting(String userName, String meetingId, Comment comment) {
		hash.clear();
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		if (meeting.isPresent()) {
			List<InboxInventory> inboxData = inboxInventoryRepository.findByMeetingId(meetingId);
			Optional<CalendarView> calendarview = calendarRepository.findByMeetingIdAndToUsers(meetingId,
					meeting.get().getInitiator());
			// updating meeting status
			calendarview.get().setMeetingStatus("reject");
			meeting.get().setChairmanStatus("Not Attending");
			meeting.get().setMeetingStatus("Rejected");
			// comment for reject meeting
			comment.setMeetingId(meetingId);
			comment.setUserFrom(userName);
			comment.setStatus("Reject Meeting");

			for (InboxInventory inbox : inboxData) {
				inbox.setStatus(null);
			}
			inboxInventoryRepository.saveAll(inboxData);
			calendarRepository.save(calendarview.get());
			commentRepository.save(comment);
			commentRepository.save(comment);
			hash.put("status", HttpStatus.OK);
			hash.put("message", "meeting rejected");
			return new ResponseEntity<>(hash, HttpStatus.OK);
		} else {
			throw new CustomException("meetingId Does Not Exist", HttpStatus.NOT_FOUND);
		}
	}

	// agenda Freeze
	public ResponseEntity<HashMap<String, Object>> freezeAgenda(String meetingId) {
		hash.clear();
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		if (meeting.isPresent())
			if (meeting.get().getFreezeAgenda()) {
				meeting.get().setFreezeAgenda(false);
				hash.put("status", HttpStatus.OK);
				hash.put("message", "Agenda unfreeze");
				meetingRepository.save(meeting.get());
			} else {
				meeting.get().setFreezeAgenda(true);
				hash.put("status", HttpStatus.OK);
				hash.put("message", "Agenda freeze");
			}
		else {
			throw new CustomException("meetingId Does Not Exist", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	// User Attendee accept meeting
	public ResponseEntity<HashMap<String, Object>> userAcceptMeeting(String meetingId, String deptUserName) {
		hash.clear();
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		Optional<Attendees> attendees = attendeesRepository.findByMeetingIdAndUserName(meetingId, deptUserName);
		Optional<InboxInventory> inbox = inboxInventoryRepository.findByMeetingIdAndToUsersAndStatus(meetingId,
				deptUserName, "attendee");
		if (attendees.isPresent()) {
			attendees.get().setStatus("Attending");
			attendeesRepository.save(attendees.get());
			CalendarView calendarview = new CalendarView();
			calendarview.setMeetingId(meetingId);
			calendarview.setMeetingTitle(meeting.get().getTitle());
			calendarview.setStartDate(meeting.get().getStartDate());
			calendarview.setEndDate(meeting.get().getEndDate());
			calendarview.setToUsers(new ArrayList<String>(Arrays.asList(deptUserName)));
			calendarview.setStatus("attendee");
			calendarview.setMeetingStatus(meeting.get().getMeetingStatus());
			calendarRepository.save(calendarview);
			inbox.get().setStatus(null);
			inboxInventoryRepository.save(inbox.get());
			hash.put("status", HttpStatus.OK);
			hash.put("message", "user Accept meeting");
			return new ResponseEntity<>(hash, HttpStatus.OK);
		} else {
			hash.put("status", HttpStatus.NOT_FOUND);
			hash.put("message", "attendees not present");
			return new ResponseEntity<>(hash, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<HashMap<String, Object>> getUserAttendees(String meetingId, String deptUserName) {
		hash.clear();
		Optional<Attendees> attendees = attendeesRepository.findByMeetingIdAndUserNameAndIsChairman(meetingId,
				deptUserName, false);
		if (attendees.isPresent()) {
			hash.put("status", HttpStatus.OK);
			hash.put("data", attendees);
		} else {
			hash.put("status", HttpStatus.NO_CONTENT);
			hash.put("message", "attendee is not present");
		}
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	// User Attendee Reject Meeting
	public ResponseEntity<HashMap<String, Object>> userRejectMeeting(String meetingId, String deptUserName,
			Comment comment) {
		hash.clear();
		Optional<Attendees> attendees = attendeesRepository.findByMeetingIdAndUserName(meetingId, deptUserName);
		Optional<InboxInventory> inbox = inboxInventoryRepository.findByMeetingIdAndToUsersAndStatus(meetingId,
				deptUserName, "attendee");
		comment.setMeetingId(meetingId);
		comment.setUserFrom(deptUserName);
		comment.setStatus("User Reject Meeting");
		if (attendees.isPresent()) {
			attendees.get().setStatus("RejectMeeting");
			inbox.get().setStatus(null);
			hash.put("status", HttpStatus.OK);
		} else {
			hash.put("status", HttpStatus.NOT_FOUND);
			hash.put("message", "attendees not present");
		}
		commentRepository.save(comment);
		attendeesRepository.save(attendees.get());
		inboxInventoryRepository.save(inbox.get());
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	// User Forward Meeting
	public ResponseEntity<HashMap<String, Object>> userForwardMeeting(String meetingId, String deptUserName,
			String meetingPriority, String forwardUserDept, String forwarUser, Comment comment) {
		hash.clear();
		Optional<Attendees> attendees = attendeesRepository.findByMeetingIdAndUserName(meetingId, deptUserName);
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		Optional<InboxInventory> inboxInventory = inboxInventoryRepository.findByMeetingIdAndToUsersAndStatus(meetingId,
				deptUserName, "attendee");
		if (attendees.isPresent()) {
			// Add new Attendee
			Attendees atdes = new Attendees();
			atdes.setDeptName(forwardUserDept);
			atdes.setUserName(forwarUser);
			atdes.setMeetingPriority(meetingPriority);
			atdes.setMeetingId(attendees.get().getMeetingId());
			atdes.setUserDesignation(attendees.get().getUserDesignation());
			// Add comment during forwardMeeting
			comment.setMeetingId(meetingId);
			comment.setUserFrom(deptUserName);
			comment.setStatus("User forward Meeting");
			// send meeting to forward user InboxInventory
			InboxInventory inbox = new InboxInventory();
			inbox.setMeetingId(meetingId);
			inbox.setToUsers(new ArrayList<String>(Arrays.asList(forwarUser)));
			inbox.setMeetingTitle(meeting.get().getTitle());
			inbox.setStatus("attendee");
			inboxInventoryRepository.save(inbox);
			inboxInventory.get().setStatus(null);
			inboxInventoryRepository.save(inboxInventory.get());
			commentRepository.save(comment);
			attendeesRepository.save(atdes);
			// Set forwardMeeting Status
			attendees.get().setStatus("ForwardMeeting");
			attendeesRepository.save(attendees.get());
			hash.put("status", HttpStatus.OK);
			return new ResponseEntity<>(hash, HttpStatus.OK);
		} else {
			hash.put("status", HttpStatus.NOT_FOUND);
			hash.put("message", "attendees not present");
			return new ResponseEntity<>(hash, HttpStatus.NOT_FOUND);
		}
	}

	// rescheduled meeting
	public ResponseEntity<HashMap<String, Object>> rescheduledMeeting(String meetingId, Meeting meeting,
			String comment) {
		hash.clear();
		Optional<Meeting> meeting1 = meetingRepository.findById(meetingId);
		if (meeting1.isPresent()) {
			meeting1.get().setChairmanDept(meeting.getChairmanDept());
			meeting1.get().setChairman(meeting.getChairman());
			meeting1.get().setStartDate(meeting.getStartDate());
			meeting1.get().setDuration(meeting.getDuration());
			meetingRepository.save(meeting1.get());
			Comment comment1 = new Comment();
			comment1.setComment(comment);
			comment1.setMeetingId(meetingId);
			comment1.setUserFrom(meeting1.get().getInitiator());
			comment1.setStatus("rescheduledMeeting");
			commentRepository.save(comment1);
			hash.put("status", HttpStatus.OK);
			hash.put("message", "meeting escheduled");
			return new ResponseEntity<>(hash, HttpStatus.OK);
		} else {
			throw new CustomException("meetingIs Dose Not Exist", HttpStatus.NOT_FOUND);
		}
	}

	// meeting cancel
	public ResponseEntity<HashMap<String, Object>> cancelMeeting(String meetingId) {
		hash.clear();
		;
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		if (meeting.isPresent()) {
			List<CalendarView> calendarview = calendarRepository.findByMeetingId(meetingId);
			List<InboxInventory> inbox = inboxInventoryRepository.findByMeetingId(meetingId);
			meeting.get().setMeetingStatus("Cancel");
			for (InboxInventory inbox1 : inbox) {
				inbox1.setMeetingStatus("cancel");
			}
			for (CalendarView cvw : calendarview) {
				cvw.setMeetingStatus("cancel");
			}
			meetingRepository.save(meeting.get());
			calendarRepository.saveAll(calendarview);
			inboxInventoryRepository.saveAll(inbox);
			hash.put("status", HttpStatus.OK);
			hash.put("message", "meeting has been cancelled");
			return new ResponseEntity<>(hash, HttpStatus.OK);
		} else {
			throw new CustomException("mettingId dose Not Exist", HttpStatus.NOT_FOUND);
		}
	}

	// Decision On Agenda
	public ResponseEntity<HashMap<String, Object>> decisionOnAgenda(Decision decision) {
		hash.clear();
		;
		decisionRepository.save(decision);
		hash.put("status", HttpStatus.OK);
		hash.put("decisionId", decision.getId());
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	// Fetch Decision
//	public ResponseEntity<HashMap<String, Object>> getDecision(String meetingId, String agenda, String user) {
//		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
//		if (meeting.get().getInitiator().equals(user)) {
//			decisionRepository.findByAgenda(agenda);
//		}
//		return null;
//
//	}
	// fetch decisions of meeting
	public ResponseEntity<HashMap<String, Object>> GetDecision(String meetingId) {
		hash.clear();
		List<Decision> decision = decisionRepository.findByMeetingId(meetingId);
		hash.put("status", HttpStatus.OK);
		hash.put("decision", decision);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	// Assign Task on the decision of meeting
	public ResponseEntity<HashMap<String, Object>> AssignTask(String decisionId, Task task) {
		hash.clear();
		Optional<Decision> decision = decisionRepository.findById(decisionId);
		task.setDecision(decision.get().getDecision());
		task.setMeetingId(decision.get().getMeetingId());
		task.setDecisionId(decisionId);
		taskRepository.save(task);
		hash.put("status", HttpStatus.OK);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	// fetch tasks of meeting
	public ResponseEntity<HashMap<String, Object>> getTask(String meetingId) {
		hash.clear();
		List<Task> task = taskRepository.findBymeetingId(meetingId);
		hash.put("Status", HttpStatus.OK);
		hash.put("task", task);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	// Attach document in minio using aws s3
	public ResponseEntity<HashMap<String, Object>> attachDocument(String token, String userName, String meetingId,
			String decisonId, MultipartFile file) {
		hash.clear();
		AmazonS3Client awsClient = null;
		String url;
		awsClient = (AmazonS3Client) awsClientConfig.awsClientConfiguration(token);
		if (!awsClient.doesBucketExistV2("meetingmanagement"))
			awsClient.createBucket("meetingmanagement");
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.addUserMetadata("content-Type", file.getContentType());
		url = baseUrl + ":" + port + "/meetingmanagement" + "/" + "AttachDocument" + "/" + userName + "/" + meetingId+"/"+decisonId + "/"
				+ file.getOriginalFilename();
		try {
			awsClient.putObject("meetingmanagement", "AttachDocument" + "/" + userName + "/" + meetingId + "/"
					+ decisonId + "/" + file.getOriginalFilename(), file.getInputStream(), metadata);
		} catch (SdkClientException | IOException e) {
			e.printStackTrace();
		}
		File newfile = new File();
		newfile.setMeetingId(meetingId);
		newfile.setFileUrl(url);
		newfile.setFiletitle(file.getOriginalFilename());
//	Optional<Task> task=taskRepository.findBydecisionId(decisonId);
//	task.get().setFileUrl(url);

		fileRepository.save(newfile);
//	    taskRepository.save(task.get());
		hash.put("status", HttpStatus.OK);
		hash.put("fileUrl", url);
		hash.put("fileTitle", file.getOriginalFilename());
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	// fetch the url of Attach documents
	public ResponseEntity<HashMap<String, List>> getDocument(String meetingId) {
		hash.clear();
		List<File> file = fileRepository.findByMeetingId(meetingId);
		List<Object> arr = new ArrayList<Object>();
		for (int i = 0; i < file.size(); i++) {
			hash.put("fileTitle", file.get(i).getFiletitle());
			hash.put("fileUrl", file.get(i).getFileUrl());
			arr.add(hash);
		}
		HashMap<String,List> hash1=new HashMap<String,List>();
		hash1.put("data", arr);
		return new ResponseEntity<>(hash1, HttpStatus.OK);
	}

	// meeting MOM
	public ResponseEntity<HashMap<String, Object>> meetingMemo(String token, String meetingId, List<String> Attended)
			throws IOException {
		System.out.println(Attended);
		hash.clear();
		List<Attendees> attendee = attendeesRepository.findByMeetingIdAndStatus(meetingId, "Attending");
		System.out.println(attendee);
		
		for (Attendees attendee1 : attendee) {
			System.out.println("inside attendee"+attendee1.getUserName());
			for (String i : Attended) {
				if (attendee1.getUserName().equals(i)) {
					attendee1.setStatus("Attended");
					System.out.println("inside array"+attendee1.getUserName());
				} else {
					attendee1.setStatus("NotAttended");
					System.out.println("inside array"+attendee1.getUserName());
				}
			}
		}
		attendeesRepository.saveAll(attendee);
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		List<Attendees> attendees = attendeesRepository.findByMeetingIdAndStatus(meetingId, "Attended");
		List<Agenda> agenda = agendaRepository.findByMeetingIdAndStatus(meetingId, "Approved");
		String srl = "http://11.0.0.119:9000/meetingmanagement/MeetingTemplate/MeetingMemoTemp.docx";
		URL url = new URL(srl);
		InputStream input1 = url.openStream();
		try (XWPFDocument doc = new XWPFDocument(input1)) {
			List<XWPFParagraph> xwpfParagraphList = doc.getParagraphs();
			for (XWPFParagraph xwpfParagraph : xwpfParagraphList) {
				for (XWPFRun xwpfRun : xwpfParagraph.getRuns()) {
					String docText = xwpfRun.getText(0);
					xwpfParagraph.setSpacingAfter(0);
					if (docText != null) {
						docText = docText.replace("title", meeting.get().getTitle());
						docText = docText.replace("date", meeting.get().getStartDate());
						docText = docText.replace("loc", meeting.get().getLocationPlace());
						docText = docText.replace("init", meeting.get().getInitiator());
						docText = docText.replace("chairman", meeting.get().getChairman());
						docText = docText.replace("chdept", meeting.get().getChairmanDept());
//						docText = docText.replace("dur", meeting.get().getDuration());
//						docText = docText.replace("rec", meeting.get().getRepeat());
						docText = docText.replace("atdept", meeting.get().getAttendingDepartment().toString());
						List<String> att = new ArrayList<>();
						for (Attendees attend : attendees) {
							att.add(attend.getUserName());
						}
						docText = docText.replace("atdes", att.toString()
//							.replace("[","").replace("]", "")
						);
//						docText = docText.replace("eats", meeting.get().getExternalAttendees());
						xwpfRun.setText(docText, 0);
					}
				}
			}
			XWPFParagraph p2 = doc.createParagraph();
			XWPFRun r2 = p2.createRun();
			r2.setBold(true);
			for (int i = 0; i < agenda.size(); i++) {
				int a = i + 1;
				r2.setText("Agenda ." + a + ":" + agenda.get(i).getAgenda());
				r2.addBreak();
				List<Decision> decision = decisionRepository.findByAgenda(agenda.get(i).getAgenda());
				for (int j = 0; j < decision.size(); j++) {
					int b = j + 1;
					if (j == 0) {
						r2.setText("Decision ." + b + ":" + decision.get(j).getDecision());
					} else {
						r2.setText("                 " + b + ":" + decision.get(j).getDecision());
					}
					r2.addBreak();
					List<Task> task = taskRepository.findBydecision(decision.get(j).getDecision());
					;
					for (int k = 0; k < task.size(); k++) {
						int c = k + 1;
						if (k == 0) {
							r2.setText("Task :" + c + ":" + task.get(k).getSubject());
						} else {
							r2.setText("                 " + c + ":" + task.get(k).getSubject());
						}
					}
					r2.addBreak();
				}
				r2.addBreak();
				ByteArrayOutputStream b = new ByteArrayOutputStream();
				doc.write(b); // doc should be a XWPFDocument
				InputStream targetStream = new ByteArrayInputStream(b.toByteArray());
				// upload update doc in minio
				AmazonS3Client awsClient = (AmazonS3Client) awsClientConfig.awsClientConfiguration(token);
				if (!awsClient.doesBucketExistV2("meetingmanagement"))
					awsClient.createBucket("meetingmanagement");
				ObjectMetadata metadata = new ObjectMetadata();
				metadata.addUserMetadata("content-Type",
						"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
				String urls = baseUrl + ":" + port + "/meetingmanagement" + "/" + "meetingMemo" + "/" + meetingId
						+ meeting.get().getTitle() + ".docx";
				awsClient.putObject("meetingmanagement",
						"meetingMemo" + "/" + meetingId + "/" + meeting.get().getTitle() + ".docx", targetStream,
						metadata);
				meeting.get().setMomURl(urls);
				meetingRepository.save(meeting.get());
				hash.put("status", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, List>> getMeetingMemo(String meetingId) {
		hash.clear();
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		ArrayList<Object> arr = new ArrayList<>();
		hash.put("momTitle", meeting.get().getTitle());
		hash.put("url", meeting.get().getMomURl());
		arr.add(hash);
		HashMap<String, List> hash1=new HashMap<String, List>();
		hash1.put("data", arr);
		return new ResponseEntity<>(hash1, HttpStatus.OK);
	}

	// meeting Delete
	public ResponseEntity<HashMap<String, Object>> deleteMeeting(String meetingId) {
		hash.clear();
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		if (meeting.isPresent()) {
			meeting.get().setMeetingStatus("Deleted");
			meetingRepository.save(meeting.get());
			hash.put("status", HttpStatus.OK);
			hash.put("message", "meeting deleted succesfully");
//			return new ResponseEntity<>(hash,HttpStatus.OK);)
		} else {
			throw new CustomException("meetingId dose Not Exist", HttpStatus.NOT_FOUND);
		}
		return null;
	}

	// Delete Attendee
//	public ResponseEntity<HashMap<String, Object>> deleteAttendee(List<String> meetingId) {
//		hash.clear();
//		 for (Map.Entry<String,String> entry : meetingId.entrySet()) {
//			Optional<Attendees> attedee= attendeesRepository.findByMeetingIdAndUserName(entry.getKey(), entry.getValue());
//			attedee.get().setStatus("Deleted");
//			attendeesRepository.save(attedee.get());
//		 }
//		    hash.put("status", HttpStatus.OK);
//			hash.put("message", "meeting deleted succesfully");
//			return new ResponseEntity<>(hash,HttpStatus.OK);
//	}
	// Delete Agenda
//	public HashMap<String, Object> deleteAgenda(HashMap<String,String> agenda) {
//		hash.clear();
//
//		Iterable<Agenda> agendas = agendaRepository.findAllById(meetingId);
//		for (Map.Entry<String,String> entry : agenda.entrySet()) {
//			Optional<Agenda> agenda1 =agendaRepository.FindByMeetingIdAndStatus(entry.getKey(), entry.getValue());
//		}
//		if (agendas != null) {
//			for (Agenda agenda : agendas) {
//				agenda.setStatus("Deleted");
//				agendaRepository.save(agenda);
//			}
//			json.put("status", HttpStatus.OK);
//		} else {
//			json.put("status", HttpStatus.NOT_FOUND);
//			json.put("message", "Id Does Not Exist");
//		}
//		return json;
//	}

	public HashMap<String, Object> confirmAgenda(List<String> meetingId) {
		Iterable<Agenda> agendas = agendaRepository.findAllById(meetingId);
		HashMap<String, Object> json = new HashMap<String, Object>();
		if (agendas != null) {
			for (Agenda agenda : agendas) {
				agenda.setStatus(agenda.getId());
				agendaRepository.save(agenda);
			}
			json.put("status", HttpStatus.OK);
		} else {
			json.put("status", HttpStatus.NOT_FOUND);
			json.put("message", "Id Does Not Exist");
		}
		return json;
	}

	public HashMap<String, Object> openTaskDetails(String meetingId) {
		List<Task> task = taskRepository.findBymeetingId(meetingId);
		List<File> file = fileRepository.findByMeetingId(meetingId);
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);
		task.get(0).setFileUrl(file.get(0).getFileUrl());
		task.get(0).setMemoMeetingUrl(meeting.get().getMomURl());
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("status", HttpStatus.OK);
		json.put("data", task.get(0));
		return json;
	}

	public HashMap<String, Object> taskAssignFurther(String meetingId, Task task1) {
		List<Task> task = taskRepository.findBymeetingId(meetingId);
		task.get(0).setStatus("AssignFurther");
		task1.setMeetingId(task.get(0).getMeetingId());
		task1.setDecision(task.get(0).getDecision());
		task1.setReference(task.get(0).getReference());
		task1.setSubject(task.get(0).getSubject());
		task1.setOnBehalfOf(task.get(0).getOnBehalfOf());
		task1.setDescription(task.get(0).getDescription());
		task1.setDeptUserName(task.get(0).getDeptUserName());
		task1.setInternalUser(task.get(0).getInternalUser());
		taskRepository.save(task.get(0));
		taskRepository.save(task1);
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("status", HttpStatus.OK);
		json.put("data", task1);
		return json;
	}

	public HashMap<String, Object> getComment(String meetingId) {
		List<Comment> comment = commentRepository.findByMeetingId(meetingId);
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("status", HttpStatus.OK);
		json.put("data", comment);
		return json;
	}

//meetingInvitation

	public HashMap<String, Object> meetingInvitation(String meetingId, String token) throws IOException {
		HashMap<String, Object> json = new HashMap<String, Object>();
		Optional<Meeting> meeting;
		List<Attendees> attendees;
		List<Agenda> agenda;
		try {
			meeting = meetingRepository.findById(meetingId);
			attendees = attendeesRepository.findByMeetingId(meetingId);
			agenda = agendaRepository.findByMeetingId(meetingId);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", "Database Exception");
			json.put("message", "Database is not Working");
			return json;
		}
		String srl = "http://11.0.0.119:9000/meetingmanagement/MeetingTemplate/MeetingInitiatorTemp.docx";
		URL url = new URL(srl);
		InputStream input1 = url.openStream();
		try (XWPFDocument doc = new XWPFDocument(input1)) {
			List<XWPFParagraph> xwpfParagraphList = doc.getParagraphs();
			for (XWPFParagraph xwpfParagraph : xwpfParagraphList) {
				for (XWPFRun xwpfRun : xwpfParagraph.getRuns()) {
					String docText = xwpfRun.getText(0);
					xwpfParagraph.setSpacingAfter(0);

					if (docText != null) {
						docText = docText.replace("user", "Amardeeep");
//						docText = docText.replace("date", meeting.get().getDate().toString());
						docText = docText.replace("time", "10 am");
//						docText = docText.replace("loc", meeting.get().getLocation());
						docText = docText.replace("cham", meeting.get().getChairman());
						docText = docText.replace("init", meeting.get().getInitiator());
						xwpfRun.setText(docText, 0);
					}
				}
			}
			XWPFParagraph p2 = doc.createParagraph();
			XWPFRun r2 = p2.createRun();
			r2.setBold(true);
			r2.setText("Topic To Discuss");
			r2.setBold(false);
			r2.addBreak();
			for (int i = 0; i < agenda.size(); i++) {
				int a = i + 1;
				r2.setText(a + ": " + agenda.get(i).getAgenda());
				r2.addBreak();
			}
			r2.addBreak();
			r2.setBold(true);
			r2.setText("Attendees Of The Meeting");
			r2.addBreak();
			for (int j = 0; j < attendees.size(); j++) {
				int a = j + 1;
				r2.setText(a + ": " + attendees.get(j).getUserName());
				r2.addBreak();
			}
			r2.addBreak();
			r2.setText("Regards");
			r2.addBreak();
			r2.setText("shabdita");
			r2.addBreak();
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			doc.write(b); // doc should be a XWPFDocument
			InputStream targetStream = new ByteArrayInputStream(b.toByteArray());
			// upload update doc in minio
			AmazonS3Client awsClient = null;
			try {
				awsClient = (AmazonS3Client) awsClientConfig.awsClientConfiguration(token);
			} catch (Exception e) {
				e.printStackTrace();
				json.put("status", HttpStatus.BAD_REQUEST);
				json.put("message", "minio can not accessed");
				return json;
			}
			if (!awsClient.doesBucketExistV2("meetingmanagement"))
				awsClient.createBucket("meetingmanagement");
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.addUserMetadata("content-Type",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			String urls = baseUrl + ":" + port + "/meetingmanagement" + "/" + "meetinginvitation" + "/" + meetingId
					+ "demotest.docx";
			awsClient.putObject("meetingmanagement", "meetinginvitation" + "/" + "demotest.docx", targetStream,
					metadata);
			json.put("status", HttpStatus.OK);
		}
//	SimpleMailMessage message = new SimpleMailMessage();
//	MailMessage message =  MailMessage.lo.setFrom("");
//	message.setTo("mishragyanendra628@gmail.com");
////	message.setText(body);
//	message.setSubject("Meeting Invitation");
//	message.set
//	mailSender.send(message);
		return json;

	}
}