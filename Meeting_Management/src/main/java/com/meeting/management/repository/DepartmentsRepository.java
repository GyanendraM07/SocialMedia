package com.meeting.management.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.Departments;

@Repository
public interface DepartmentsRepository extends MongoRepository<Departments, String>{
	Optional<Departments>findByDeptName(String deptName);

}
