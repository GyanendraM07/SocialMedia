package com.example.demo.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ProfileService;

@RestController
@RequestMapping("/profile")
@CrossOrigin("*")
public class ProfileController {
	
	@Autowired
	ProfileService profileService;
	
	@PostMapping("/profiledata")
	public ResponseEntity<HashMap<String, Object>> getProfileData(@RequestParam String user)  {
		return profileService.getProfileData(user);
	}

}
