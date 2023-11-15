package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.configure.AWSClientConfigService;
import com.example.demo.repository.LikeUserRepository;
import com.example.demo.repository.NestedCommentRepository;
import com.example.demo.repository.PostCommentRepository;
import com.example.demo.repository.PostRepository;

@Service
public class ProfileService {

	@Value("${minio.rest-url}")
	private String baseUrl;

	@Value("${minio.rest-port}")
	private String port;
	
	@Autowired
	private AWSClientConfigService awsClientConfig;
	@Autowired
	PostRepository postrepo;
	@Autowired
	LikeUserRepository likeuserRepo;
	@Autowired
	PostCommentRepository postcommentRepo;
	@Autowired
	NestedCommentRepository nestedcommentRepo;
	HashMap<String, Object> hash = new HashMap<>();
	
	public ResponseEntity<HashMap<String, Object>> getProfileData(String user) {
		hash.clear();
		List<String> postUr1=new ArrayList<>();
		List<String> postUr2=new ArrayList<>();
		List<String> postUr3=new ArrayList<>();
		List<List<String>> userdetails= postrepo.findProfiledata(user);
		int check=1;
		for(int i=0;i<userdetails.size();i++) {
			if(check==1) {
			postUr1.add(userdetails.get(i).get(1));
			check=2;
			}
			else if(check==2) {
				postUr2.add(userdetails.get(i).get(1));
				check=3;
				}
			else if(check==3) {
				postUr3.add(userdetails.get(i).get(1));
				check=1;
				}
		}
		hash.put("postUrl1", postUr1);
		hash.put("postUrl2", postUr2);
		hash.put("postUrl3", postUr3);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

}
