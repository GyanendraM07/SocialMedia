package com.example.demo.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.hibernate.grammars.hql.HqlParser.ConcatenationExpressionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.demo.configure.AWSClientConfigService;
import com.example.demo.model.Comment;
import com.example.demo.model.LikeUsers;
import com.example.demo.model.Post;
import com.example.demo.repository.LikeUserRepository;
import com.example.demo.repository.NestedCommentRepository;
import com.example.demo.repository.PostCommentRepository;
import com.example.demo.repository.PostRepository;


@Service
public class CreatePostService {

	@Value("${minio.rest-url}")
	private String baseUrl;

	@Value("${minio.rest-port}")
	private String port;
	
//	@Value("${spring.datasource.url}")
//	private String url;
//	
//	@Value("${spring.datasource.password}")
//	private String pass;
//	
//	@Value("${spring.datasource.username}")
//	private String user;
//	
//	@Value("${spring.datasource.driver-class-name}")
//	private String driver;
	

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
	

	public ResponseEntity<HashMap<String, Object>> createPost(MultipartFile file, String token, String caption,
			String user) {
		hash.clear();
		Long userId=postrepo.findUserId(user);
		Post post = new Post();
		post.setCaption(caption);
//		post.setPostuserName(user);
		post.setPostUserId(userId);
		Post post1 = postrepo.save(post);
		String url = null;
		AmazonS3Client awsClient = (AmazonS3Client) awsClientConfig.awsClientConfiguration(token);
		System.err.println(" Token acces Reached");
		if (!awsClient.doesBucketExistV2("socialmedia")) {
			awsClient.createBucket("socialmedia");
		}
		System.err.println(" outside loop Reached");
		ObjectMetadata metadata = new ObjectMetadata();
		System.err.println("content type" + file.getContentType());
		metadata.addUserMetadata("content-Type", file.getContentType());
		metadata.setContentLength(file.getSize());
		try {
			if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
				url = baseUrl + ":" + port + "/socialmedia" + "/" + "Post" + "/" + "image" + "/" + userId + "/"
						+ file.getOriginalFilename();
				

				awsClient.putObject("socialmedia",
						"Post" + "/" + "image" + "/" + userId + "/" + file.getOriginalFilename(), file.getInputStream(),
						metadata);
			} else if (file.getContentType().equals("video/mp4") || file.getContentType().equals("video/mp3")) {
				url = baseUrl + ":" + port + "/socialmedia" + "/" + "Post" + "/" + "vedeo" + "/" + userId + "/"
						+ file.getOriginalFilename();
				awsClient.putObject("socialmedia",
						"post" + "/" + "vedeo" + "/" + userId + "/" + file.getOriginalFilename(), file.getInputStream(),
						metadata);
			}
			post1.setFileurl(url);
			post1.setFiletype(file.getContentType());
			postrepo.save(post1);
		} catch (SdkClientException | IOException e) {
			e.printStackTrace();
		}
		hash.put("status", HttpStatus.OK);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> showPost(String user)  {
		hash.clear();
		String dtls=postrepo.finduserDetails(user);
			List<String> userDetails = Arrays.asList(dtls.split("\\s*,\\s*"));
			System.out.println(userDetails.size());
		List<Post> post = postrepo.findAll();
		System.out.println(post);
		ArrayList<HashMap<String, Object>> al = new ArrayList<>();
		ArrayList<HashMap<String, Object>> al1 = new ArrayList<>();
		ArrayList<HashMap<String, Object>> cmtal = new ArrayList<>();
		for (Post ob : post) {
			System.err.println(ob.getFiletype());
			Optional<LikeUsers> lkuser=likeuserRepo.findByLikeUserAndPost_Id(user,ob.getId());
			int count  =likeuserRepo.findfollow(user,ob.getPostUserId());
			List<Comment> postComts= postcommentRepo.findByPostId(ob.getId());
			HashMap<String, Object> hashcmt = new HashMap<>();
			for(Comment cmt:postComts) {
				hashcmt.put("comment", cmt.getComment());
				hashcmt.put("commentlike", cmt.getCommentLike());
				hashcmt.put("commentUser", cmt.getCommentedUser());
				hashcmt.put("commentPostid", cmt.getPostId());
				hashcmt.put("commentuserDp", postrepo.findByuserName(cmt.getCommentedUser()));
				cmtal.add(hashcmt);
//				String cmtuseUrl=postrepo.findByuserName(cmt.getCommentedUser());
			}
			
			if (ob.getFiletype().equals("image/jpeg") || ob.getFiletype().equals("image/png")) {
				System.err.println("insise image" + ob.getFiletype());
				HashMap<String, Object> hash1 = new HashMap<>();
				hash1.clear();
				hash1.put("caption", ob.getCaption());
				hash1.put("fileurl", ob.getFileurl());
				hash1.put("user", postrepo.findName(ob.getPostUserId()));
				hash1.put("userId", ob.getPostUserId());
				hash1.put("like", String.valueOf(ob.getLike()));
				hash1.put("postid", String.valueOf(ob.getId()));
				hash1.put("dpurl", postrepo.finddpUrl(ob.getPostUserId()));
				if(lkuser.isPresent()) {
					hash1.put("checkLike", true);
				}
				else {
					hash1.put("checkLike", false);
				}
				if(count>0) {
					hash1.put("checkFollow", true);
				}
				else {
					hash1.put("checkFollow", false);
				}
				
				
				al.add(hash1);
			} else if (ob.getFiletype().equals("video/mp4") || ob.getFiletype().equals("video/mp3")) {

				System.err.println(" insise video" + ob.getFiletype() + " " + ob.getFileurl() + " " + ob.getCaption());
				HashMap<String, Object> hash2 = new HashMap<>();
				hash2.clear();
				hash2.put("caption", ob.getCaption());
				hash2.put("fileurl", ob.getFileurl());
				hash2.put("user", postrepo.findName(ob.getPostUserId()));
				hash2.put("userId", ob.getPostUserId());
				hash2.put("like", String.valueOf(ob.getLike()));
				hash2.put("postid", String.valueOf(ob.getId()));
				hash2.put("dpurl", postrepo.finddpUrl(ob.getPostUserId()));
				if(lkuser.isPresent()) {
					hash2.put("check", true);
				}
				else {
					hash2.put("check", false);
				}
				if(count>0) {
					hash2.put("checkFollow", true);
				}
				else {
					hash2.put("checkFollow", false);
				}
				System.out.println("hash insise video" + hash2);
				al1.add(hash2);
				System.out.println("hash insise video" + al1);
			}
		}
		hash.put("userfullname", userDetails.get(0)+userDetails.get(1));
		hash.put("userprofilepic", userDetails.get(2));
		hash.put("status", HttpStatus.OK);
		hash.put("image", al);
		hash.put("video", al1);
		hash.put("cmtdata", cmtal);
		System.out.println(al1);
		System.out.println(hash);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}

	public ResponseEntity<HashMap<String, Object>> addlike(String likeUser, Long id) {
		hash.clear();
		Optional<LikeUsers> lkuser=likeuserRepo.findByLikeUserAndPost_Id(likeUser,id);
		if(lkuser.isEmpty()) {
		Optional<Post> post = postrepo.findById(id);
		post.get().setLike(post.get().getLike() + 1);
		postrepo.save(post.get());
		LikeUsers lk = new LikeUsers();
		lk.setLikeUser(likeUser);
		lk.setPostId(id);
		likeuserRepo.save(lk);
		hash.put("msg", "add like user");
		}
		else {
			Optional<Post> post = postrepo.findById(id);
			post.get().setLike(post.get().getLike() - 1);
			postrepo.save(post.get());
			likeuserRepo.deleteById(lkuser.get().getId());
			hash.put("msg", "remove like user");
		}
		hash.put("status", HttpStatus.OK);
		return new ResponseEntity<>(hash, HttpStatus.OK);

	}

	public ResponseEntity<HashMap<String, Object>> addComment(Comment comment) {
		hash.clear();
		System.out.println("reached");
//		Comment com = postcommentRepo.save(comment);
//		System.out.println(com);
//		Optional<Post>post=postrepo.findById(1);
//		post.get().setComment(new ArrayList<Comment>(Arrays.asList(comment)));
//		postrepo.save(post.get());
		postcommentRepo.save(comment);
		System.out.println("reached");
		hash.put("status", HttpStatus.OK);
		hash.put("message", "succesfull");
		return new ResponseEntity<>(hash, HttpStatus.OK);

	}

	public ResponseEntity<HashMap<String, Object>> fetchComment(Long id) {
		hash.clear();
		ArrayList<HashMap<String, Object>> al = new ArrayList<>();
//		List<Comment> comment = postcommentRepo.findByPostIdAndCommentId(id,null);
//		List<Comment> comment = postcommentRepo.findByPostId(id);
		HashMap<String, Object> hash1 = new HashMap<>();
//		for (Comment cm : comment) {
//			HashMap<String, Object> hash1 = new HashMap<>();
//			hash1.clear();
//			int commentId=
//			List<Comment> com1=postcommentRepo.findByPostIdAndCommentId(id, cm.getId());
//         System.out.println("com1"+""+com1);
//				ArrayList<HashMap<String, String>> al1 = new ArrayList<>();
//				for(Comment cm1:com1) {
//				HashMap<String, String> hash2 = new HashMap<>();
//				hash2.clear();
//				hash1.put("commentedUser", cm1.getCommentedUser());
//				hash1.put("comment", cm1.getComment());
//				hash1.put("like", String.valueOf(cm1.getLike()));
//				hash1.put("commentId",String.valueOf(cm1.getId()));
//				al1.add(hash2);
//			}
			hash1.put("commentedUser", "demo");
			hash1.put("comment","sddsd");
			hash1.put("like", 1);
			hash1.put("commentId",12);
//			hash1.put("nestedcomment", al1);
			al.add(hash1);
//		}
		hash.put("status", HttpStatus.OK);
		hash.put("comment", al);
		return new ResponseEntity<>(hash, HttpStatus.OK);

	}
	public ResponseEntity<HashMap<String, Object>> likeUsers(Long postId,String loginUserName) {
		hash.clear();
		List<LikeUsers> likeuser=likeuserRepo.findByPost_Id(postId);
		ArrayList<HashMap<String, Object>> al = new ArrayList<>();
		for(LikeUsers lk:likeuser) {
			HashMap<String, Object> hash1 = new HashMap<>();
			hash1.clear();
			String imageurl=likeuserRepo.findbyuserimage(lk.getLikeUser());
			int count=likeuserRepo.checkFollower(lk.getLikeUser(),loginUserName);
			if(count >0 && count <2) {
				hash1.put("checkFollower",true);
			}
			else {
				hash1.put("checkFollower",false);
			}
			hash1.put("likeUser", lk.getLikeUser());
//			hash1.put("postId", lk.getPostId());
			hash1.put("likeuserId", lk.getId());
			hash1.put("likeuserProfile",imageurl);
			al.add(hash1);
		}
		hash.put("status", HttpStatus.OK);
		hash.put("users", al);
		return new ResponseEntity<>(hash, HttpStatus.OK);
		
	}

	public ResponseEntity<HashMap<String, Object>> unfollowUser(String followerUser, Long followingUserId) {
	    
		postrepo.deleteFollower(followerUser,followingUserId);
		hash.put("status", HttpStatus.OK);
//		hash.put("users", al);
		return new ResponseEntity<>(hash, HttpStatus.OK);
	}
	
}




