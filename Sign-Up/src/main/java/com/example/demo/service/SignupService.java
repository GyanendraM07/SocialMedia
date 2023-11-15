package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.licensemanager.model.Metadata;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.demo.Repository.FollowersRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.configure.AWSClientConfigService;
import com.example.demo.model.Followers;
import com.example.demo.model.User;

@Service
public class SignupService {

	@Value("${minio.rest-url}")
	private String baseUrl;

	@Value("${minio.rest-port}")
	private String port;

	@Autowired
	private AWSClientConfigService awsClientConfig;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FollowersRepository followerRepo;
	HashMap<String, Object> hash = new HashMap<>();

	public ResponseEntity<HashMap<String, Object>> createUser(User user, MultipartFile file, String token) {
		hash.clear();
		AmazonS3Client awsClient = (AmazonS3Client) awsClientConfig.awsClientConfiguration(token);
		if (!awsClient.doesBucketExistV2("socialmedia")) {
			awsClient.createBucket("socialmedia");
		}
		System.err.println(" outside loop Reached");
		ObjectMetadata metadata = new ObjectMetadata();
		System.err.println("content type" + file.getContentType());
		metadata.addUserMetadata("content-Type", file.getContentType());
		metadata.setContentLength(file.getSize());
		String url = baseUrl + ":" + port + "/socialmedia" + "/" + "users" + "/" + "profileimage" + "/"
				+ user.getUserName() + "/" + file.getOriginalFilename();

		try {
			awsClient.putObject("socialmedia",
					"users" + "/" + "profileimage" + "/" + user.getUserName() + "/" + file.getOriginalFilename(),
					file.getInputStream(), metadata);
		} catch (SdkClientException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setProfilepicUrl(url);
		User user1 = userRepository.save(user);
		hash.put("status", HttpStatus.OK);
		hash.put("data", user1);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> checkUser(String user, String password) {
		hash.clear();
		Optional<User> user1 = userRepository.findByUserNameAndPassword(user, password);
		if (user1.isPresent()) {
			hash.put("data", true);
		} else {
			hash.put("data", false);
		}
		hash.put("status", HttpStatus.OK);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> followUser(String followUser,Long followingUserId) {
       Followers follow=new Followers();
       follow.setFollowerUser(followUser);
       follow.setFollowingUserId(followingUserId);
       followerRepo.save(follow);
		hash.put("status", HttpStatus.OK);
		return new ResponseEntity<>(hash, HttpStatus.OK);

	}
     
	@Cacheable(value="twenty-second-cache", key = "#id+#userNmae+#fname")
	public ResponseEntity<HashMap<String, Object>> findById(Long id,String fname,String userNmae) {
		// TODO Auto-generated method stub
		System.err.println("reached");
//		Optional<User> user=userRepository.findByIdAndUserName(id,userNmae);
		Optional<User> user=userRepository.findByIdAndFirstNameAndUserName(id,fname,userNmae);
		hash.put("status", HttpStatus.OK);
		hash.put("data", user);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	
//	@Cacheable(value="twenty-second-cache", key = "#root.methodName")
	public ResponseEntity<HashMap<String, Object>> findAll2() {
		
		System.out.println("reached");
		List<User> user=userRepository.findAll();
		hash.put("status", HttpStatus.OK);
		hash.put("data", user);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> unfollowUser(String followerUser, Long followingUserId) {
		followerRepo.deleteFollower(followerUser,followingUserId);
		hash.put("status", HttpStatus.OK);
//		hash.put("users", al);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

}
