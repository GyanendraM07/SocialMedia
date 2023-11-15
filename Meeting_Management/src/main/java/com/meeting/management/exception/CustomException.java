package com.meeting.management.exception;

import org.springframework.http.HttpStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
//@AllArgsConstructor
public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus httpStatus;

	public CustomException(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
		// TODO Auto-generated constructor stub
	}

	public CustomException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
