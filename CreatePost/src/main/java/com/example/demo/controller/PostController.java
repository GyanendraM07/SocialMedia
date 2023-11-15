package com.example.demo.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Comment;
import com.example.demo.service.CreatePostService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/create")
@CrossOrigin("*")
public class PostController {
	
	@Autowired
	CreatePostService createpostService;
	
	@PostMapping("/post")
	public ResponseEntity<HashMap<String, Object>> createPost(HttpServletRequest request, @RequestParam MultipartFile file,@RequestParam String caption,@RequestParam String user) {
		String token = (String) request.getHeader("Authorization");
		String clippedToken = token.replace("Bearer ", ""); 
		return createpostService.createPost(file, clippedToken,caption,user);
	}
	
	@PostMapping("/showpost")
	public ResponseEntity<HashMap<String, Object>> showPost(@RequestParam String user)  {
     System.err.println(user);
//		String token = (String) request.getHeader("Authorization");
//		String clippedToken = token.replace("Bearer ", ""); 
		return createpostService.showPost(user);
	}
	@PostMapping("/addLike")
	public ResponseEntity<HashMap<String, Object>> addLike(@RequestParam String likeUser,@RequestParam Long postId){
		return createpostService.addlike(likeUser, postId);
	}
	@PostMapping("/addComment")
	public ResponseEntity<HashMap<String, Object>> addcomment(@RequestBody Comment comment){
		System.out.println("reached");
		return createpostService.addComment(comment);
	}
	@PostMapping("/fetchComment")
	public ResponseEntity<HashMap<String, Object>> fetchComment(@RequestParam Long id) { 
		return createpostService.fetchComment(id);
	}
	@PostMapping("/fetchlikeusers")
	public ResponseEntity<HashMap<String, Object>> fetchlikeusers(@RequestParam Long postId,@RequestParam String loginUserName) { 
		return createpostService.likeUsers(postId,loginUserName);
	}
	//not working
	@PostMapping("/unfollow")
	public ResponseEntity<HashMap<String, Object>> unfollowUser(@RequestParam String followerUser,@RequestParam Long followingUserId) { 
		return createpostService.unfollowUser(followerUser,followingUserId);
	}
	
	
	
   
}
