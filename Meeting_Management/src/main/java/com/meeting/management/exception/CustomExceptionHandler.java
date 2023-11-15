package com.meeting.management.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import java.text.SimpleDateFormat; 
import java.util.Date;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.securitytoken.model.AWSSecurityTokenServiceException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{

	HashMap<String,Object> hash = new HashMap<String,Object>();
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ExceptionResponseObject> customException(CustomException customException) {
		hash.clear();
		Date date = new Date();
		ExceptionResponseObject exceptionResponseObjectBuilder = ExceptionResponseObject.builder()
				.message(customException.getMessage()).httpStatus(customException.getHttpStatus()).time(date).build();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		hash.put("message", exceptionResponseObjectBuilder.getMessage());
		hash.put("serviceName", exceptionResponseObjectBuilder.getServiceName());
		hash.put("time", formatter.format(date));
		hash.put("httpStatus", customException.getHttpStatus());
		System.err.println("custom exception = "+exceptionResponseObjectBuilder.getServiceName());
		return new ResponseEntity<ExceptionResponseObject>(exceptionResponseObjectBuilder,customException.getHttpStatus());
	}

	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<ExceptionResponseObject> amazonS3Exception(AmazonS3Exception amazonS3Exception) {
		System.out.println("AmazonS3Exception");
		hash.clear();
		Date date = new Date();
		HttpStatus failedDependency = HttpStatus.FAILED_DEPENDENCY;
		ExceptionResponseObject exceptionResponseObjectBuilder = ExceptionResponseObject.builder()
				.message(amazonS3Exception.getMessage()).httpStatus(failedDependency).time(date).build();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		hash.put("message", exceptionResponseObjectBuilder.getMessage());
		hash.put("serviceName", exceptionResponseObjectBuilder.getServiceName());
		hash.put("time", formatter.format(date));
		hash.put("httpStatus", failedDependency);
		return new ResponseEntity<ExceptionResponseObject>(exceptionResponseObjectBuilder, failedDependency);

	}

	@ExceptionHandler(AWSSecurityTokenServiceException.class)
	public ResponseEntity<ExceptionResponseObject> aWSSecurityTokenServiceException(
			AWSSecurityTokenServiceException aWSSecurityTokenServiceException) {
		System.out.println("Error connecting minIO with keycloak ");
		hash.clear();
		Date date = new Date();
		HttpStatus failedDependency = HttpStatus.FAILED_DEPENDENCY;
		ExceptionResponseObject exceptionResponseObjectBuilder = ExceptionResponseObject.builder()
				.message("It seems like your minIO is not integrated with keycloak. Please check your minIO connection.")
				.httpStatus(failedDependency)
				.time(date)
				.build();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		hash.put("message", exceptionResponseObjectBuilder.getMessage());
		hash.put("serviceName", exceptionResponseObjectBuilder.getServiceName());
		hash.put("time", formatter.format(date));
		hash.put("httpStatus", failedDependency);
		return new ResponseEntity<ExceptionResponseObject>(exceptionResponseObjectBuilder, failedDependency);
	}
	@ExceptionHandler(SdkClientException.class)
	public ResponseEntity<ExceptionResponseObject> sdkClientException(SdkClientException sdkClientException) {

		System.out.println("Exception in minIO connection ");
		hash.clear();
		Date date = new Date();
		HttpStatus failedDependency = HttpStatus.FAILED_DEPENDENCY;
		ExceptionResponseObject exceptionResponseObjectBuilder = ExceptionResponseObject.builder()
				.message("There is a problem in minIO connection. Please check your minIO server.")
				.httpStatus(failedDependency).time(date).build();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		hash.put("message", exceptionResponseObjectBuilder.getMessage());
		hash.put("serviceName", exceptionResponseObjectBuilder.getServiceName());
		hash.put("time", formatter.format(date));
		hash.put("httpStatus", failedDependency);
		return new ResponseEntity<ExceptionResponseObject>(exceptionResponseObjectBuilder, failedDependency);

	}

	@ExceptionHandler(MongoTimeoutException.class)
	public ResponseEntity<ExceptionResponseObject> mongoTimeoutException(MongoTimeoutException mongoSocketException) {

		System.out.println("Exception in mongo ");
		hash.clear();
		Date date = new Date();
		HttpStatus failedDependency = HttpStatus.FAILED_DEPENDENCY;
		ExceptionResponseObject exceptionResponseObjectBuilder = ExceptionResponseObject.builder()
				.message("It seems like there is problem in connection application with the Database. Please check your MongoDB connection.")
				.httpStatus(failedDependency)
				.time(date).build();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		hash.put("message", exceptionResponseObjectBuilder.getMessage());
		hash.put("serviceName", exceptionResponseObjectBuilder.getServiceName());
		hash.put("time", formatter.format(date));
		hash.put("httpStatus", failedDependency);
		return new ResponseEntity<ExceptionResponseObject>(exceptionResponseObjectBuilder, failedDependency);
	}

	@ExceptionHandler(MongoWriteException.class)
	public ResponseEntity<ExceptionResponseObject> mongoWriteException(MongoWriteException mongoWriteException) {
		System.out.println("Exception in writing data in mongo ");
		hash.clear();
		Date date = new Date();
		HttpStatus failedDependency = HttpStatus.FAILED_DEPENDENCY;
		ExceptionResponseObject exceptionResponseObjectBuilder = ExceptionResponseObject.builder()
				.message("It seems like there is problem in writing data into the Database. Please check your MongoDB server.")
				.httpStatus(failedDependency).time(date).build();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		hash.put("message", exceptionResponseObjectBuilder.getMessage());
		hash.put("serviceName", exceptionResponseObjectBuilder.getServiceName());
		hash.put("time", formatter.format(date));
		hash.put("httpStatus", failedDependency);
		return new ResponseEntity<ExceptionResponseObject>(exceptionResponseObjectBuilder, failedDependency);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponseObject> genericException(Exception e){
		System.out.println("Generic Exception");
		e.printStackTrace();
		hash.clear();
		Date date =new Date();
			ExceptionResponseObject exceptionResponseObjectBuilder = ExceptionResponseObject.builder()
			.message(e.getMessage())
			.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).time(date).build();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			hash.put("message", exceptionResponseObjectBuilder.getMessage());
			hash.put("serviceName", exceptionResponseObjectBuilder.getServiceName());
			hash.put("time", formatter.format(date));
			hash.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<ExceptionResponseObject>(exceptionResponseObjectBuilder, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}

