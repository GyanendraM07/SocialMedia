package com.user1.repositary;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.user1.model.User1;
import com.user1.model.UserDtata;

public interface User1Repositary extends MongoRepository<User1, String>{

	List<UserDtata> findByUser(String user);
	
}
