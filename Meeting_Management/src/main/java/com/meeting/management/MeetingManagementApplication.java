package com.meeting.management;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
@EnableEurekaClient
@SpringBootApplication
public class MeetingManagementApplication 
{
	public static void main(String[] args) {
		SpringApplication.run(MeetingManagementApplication.class, args);
	}

}
