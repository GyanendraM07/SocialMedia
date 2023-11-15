package com.user1.controller;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.user1.configuration.AWSClientConfigService;
import com.user1.model.User1;
import com.user1.model.UserDtata;
import com.user1.repositary.User1Repositary;
import com.user1.repositary.UserFavRepositary;

import io.minio.BucketExistsArgs;
import io.minio.DownloadObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;

@Controller
@RequestMapping
public class User1Controller {
	
	@Autowired
	private AWSClientConfigService awsClientConfig;
//	FOR MINIO 
//	MinioClient minioClient =
//	          MinioClient.builder()
//	              .endpoint("http:localhost:9000")
//	              .credentials("minioadmin", "minioadmin")
//	              .build();
	
	@Autowired
User1Repositary user1repositary;
	UserFavRepositary userfavrepo;
	User1 user1=new User1();
	@GetMapping("/first")
	public String first(Principal principal) {
		String id=principal.getName();
//		if(id.equals("97666e5a-2132-4a97-bc35-1f97a59505a6")) {
//			user1.setUser("user1");
//		}
//		if(id.equals("c9eca970-789a-497e-8845-4ede7467f4b9")) {
//		user1.setUser("user2");
//		}
		user1.setUser(id);
		return "first.jsp";
		}
	
//	@GetMapping("/fav")
//	public String Favorite(ModelMap mudel) {
//		
//		String user=user1.getUser();
//		List<UserDtata> users = user1repositary.findByUser(user);
//		System.out.println(users);
//		mudel.addAttribute("list", users);
//		return "favorite.jsp";
//	}
	String dirc="C://MusicApp";
	//for storing data 
	@PostMapping("/data")
	public String addDemo(HttpServletRequest request,@RequestParam String song,@RequestParam String singer,@RequestParam String musicd,@RequestParam String date,MultipartFile file) throws IOException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException{
		    song+=".mp3";
//			byte[] bbt=file.getBytes();
//	        Path path=Paths.get(dirc,song );
//	         	Files.write(path,bbt);
//		user1.setUser(user);
		user1.setSong(song);
		user1.setSinger(singer);
		user1.setMusic(musicd) ;
		user1.setDate(date);
		User1 user = user1repositary.save(user1);
		//title is ID
		String title = user.getId();
		 title+=".mp3";
//			byte[] bbt=file.getBytes();
//	        Path path=Paths.get(dirc,title );
//	         	Files.write(path,bbt);
		
		 
//		 minioClient.putObject(
//				   PutObjectArgs.builder()
//			              .bucket("testing")
//			              .object(title)
//			              .stream( file.getInputStream(),file.getSize() , -1)
////			              .stream(file.getInputStream(),-1,file.getSize())
//			              .build());
		 //for minio token
		 String token = (String) request.getHeader("Authorization");
			String clippedToken = token.replace("Bearer ", "");

			AmazonS3Client awsClient = (AmazonS3Client) awsClientConfig.awsClientConfiguration(token);
		 
		return "upload.jsp";
	}
		UserDtata udt= new UserDtata();
		//for fetch data and playlist 
		@GetMapping("/fetch")
		public String findAll(	ModelMap model){
			String user=user1.getUser();
			List<UserDtata> users = user1repositary.findByUser(user);
			model.addAttribute("list", users);
			return "playlist.jsp";
		}
		String fileBasePath="C:\\Users\\admin\\Downloads\\";
		
		//for download data 
		@GetMapping("/download")
		public String downloadFileFromLocal(@RequestParam(name = "id") String id,@RequestParam(name = "filename") String title) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {
			
			 
//			Path path = Paths.get(fileBasePath+"\\"+ id+".mp3");
//			Resource resource = null;
//			try {
//				resource = new UrlResource(path.toUri());
//			} catch (MalformedURLException e) {
//				System.out.println("failed");
//				e.printStackTrace();
//			}
//			System.out.println( resource.getFilename());
			 id+=".mp3";
//			 title+=".mp3";
//			minioClient.downloadObject(
//		            DownloadObjectArgs.builder()
//		                .bucket("testing")
//		                .object(id)
//		                .filename("C:\\Users\\admin\\Downloads\\"+title)
//		                .build());
//			return ResponseEntity.ok()
//					.header(HttpHeaders.ACCEPT)
//					.body("succes");
                    return "first.jsp";
		}
		//for fav list 
		
//		@PostMapping("/fav")
//		public String favorate(@RequestParam String favsong,@RequestParam String favuser) {
//			fav.setSong(favsong);
//			fav.setUser(favuser);
//			userfavrepo.save(fav);
//			return "first.jsp";
//			
//		}
	
@GetMapping("/minio")
public String minio(@RequestHeader("bucketName") String bucketName)
{
	try {
	      // Create a minioClient with the MinIO server playground, its access key and secret key.
	      MinioClient minioClient =
	          MinioClient.builder()
	              .endpoint("http:localhost:9000")
	              .credentials("minioadmin", "minioadmin")
	              .build();

	      // Make 'asiatrip' bucket if not exist.
	      boolean found =
	          minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
	      if (!found) {
	        // Make a new bucket called 'asiatrip'.
	        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
	      } else {
	        System.out.println("Bucket "+bucketName+" already exists.");
	      }
	
	return "success";
	
	
}
	catch(Exception e)
	{
		return "failed";
	}
}
@PostMapping("/upload")
public String upload(InputStream stream ) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {
//	InputStream stream =  new BufferedInputStream((InputStream) file);
	MinioClient minioClient =
	          MinioClient.builder()
	              .endpoint("http:localhost:9000")
	              .credentials("minioadmin", "minioadmin")
	              .build();
	   minioClient.putObject(
			   PutObjectArgs.builder()
		              .bucket("testing")
		              .object("asiaphotos-2015.zip")
		              .stream(stream, 0, 0)
		              .build());
	          return "succes ";
}
@GetMapping("/mdownload")
public String download() throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {
	
//	minioClient.downloadObject(
//            DownloadObjectArgs.builder()
//                .bucket("testing")
//                .object("asiaphotos-2015.zip")
//                .filename("C:\\Users\\admin\\Downloads\\demo.mp3")
//                .build());
	
	return"succse";
      }
@DeleteMapping("/daelete")
public String delete() throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {

//    minioClient.removeObject(
//        RemoveObjectArgs.builder().bucket("testing").object("asiaphotos-2015.zip").build());
	return "success";
	
}
@GetMapping("/delete")
public String deleteFile(@RequestParam(name = "id") String id) throws IOException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException {
//    Optional<Song> song= songRepository.findById(id);
//    
//    Song song2=song.get();
//    if(song2.getId() == null)
//    	return new ResponseEntity<>("File Already Deleted", HttpStatus.OK);
//    else songRepository.delete(song2);
//	 minioClient.removeObject(
//		        RemoveObjectArgs.builder().bucket("testing").object(id+".mp3").build());
//	 user1repositary.deleteById(id);
    
    return"first.jsp";

}}
