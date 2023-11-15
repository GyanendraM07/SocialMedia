package com.meeting.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.User;
@Repository
public interface UserRepository  extends MongoRepository<User, String>{
	
	@Query(value = "{'deptName' : ?0}",fields = "{'userName' : 1}")
	List<User> findDepartment(String deptName);

	List<User> findByDeptName(String deptName);

	User findByDeptNameAndUserName(String chairmanDept, String chairman);
	Optional<User>findByUserName(String userName);
	
	
}
