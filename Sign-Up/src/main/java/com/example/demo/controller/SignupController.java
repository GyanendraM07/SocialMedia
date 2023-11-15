package com.example.demo.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.User;
import com.example.demo.service.SignupService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SignupController {

	@Autowired
	SignupService signupService;

	@PostMapping("signup")
	public ResponseEntity<HashMap<String, Object>> createUser(HttpServletRequest request,@RequestParam String firstName,@RequestParam String lastName,@RequestParam String gender,@RequestParam String userName,
			@RequestParam String password,@RequestParam String dateofBirth,@RequestParam MultipartFile file) {

		String token = (String) request.getHeader("Authorization");
		String clippedToken = token.replace("Bearer ", "");
		System.err.println(file);
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setUserName(userName);
		user.setGender(gender);
		user.setDateofBirth(dateofBirth);
		System.err.println(user.getFirstName());
		return signupService.createUser(user,file, clippedToken);
	}

	@PostMapping("signin")
	public ResponseEntity<HashMap<String, Object>> createUser(@RequestParam String user,
			@RequestParam String password) {
//		System.err.println(user.getFirstName());
		return signupService.checkUser(user, password);
	}
	@PostMapping("/followUser")
	public ResponseEntity<HashMap<String, Object>> followuser(@RequestParam String followerUser,@RequestParam Long followingUserId) { 
		return signupService.followUser(followerUser,followingUserId);
	}
	
	@PostMapping("/unfollow")
	public ResponseEntity<HashMap<String, Object>> unfollowUser(@RequestParam String followerUser,@RequestParam Long followingUserId) { 
		return signupService.unfollowUser(followerUser,followingUserId);
	}
	
	@PostMapping("/demoUser")
	public ResponseEntity<HashMap<String, Object>> demoUser(@RequestParam Long id,@RequestParam String fname,@RequestParam String user){
		System.err.println(id+" "+fname+" "+user);
		return signupService.findById(id,fname,user);
		
	}
	@GetMapping("/demoUser2")
	public ResponseEntity<HashMap<String, Object>> demoUser2(){
		System.out.println("Reached123");
		return signupService.findAll2();
		
	}

}
