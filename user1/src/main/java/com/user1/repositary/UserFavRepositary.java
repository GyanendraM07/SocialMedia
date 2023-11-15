package com.user1.repositary;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.user1.model.UserFavdata;
import com.user1.model.UserFavorite;

public interface UserFavRepositary extends MongoRepository<UserFavorite, String>{

	List<UserFavdata> findByUser(String user);
	

}
