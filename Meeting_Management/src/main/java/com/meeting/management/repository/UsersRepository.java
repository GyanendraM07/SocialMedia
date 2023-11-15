package com.meeting.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.User;
import com.meeting.management.model.Users;

@Repository
public interface UsersRepository extends MongoRepository<Users, String>{

	List<Users>findByDeptName(String deptName);

	Optional<Users> findByDeptRole(String deptCoordRole);

	Optional<Users> findByDeptNameAndDeptUserName(String chairmanDept, String chairman);

	Optional<Users> findByDeptUserName(String initiator);

}
