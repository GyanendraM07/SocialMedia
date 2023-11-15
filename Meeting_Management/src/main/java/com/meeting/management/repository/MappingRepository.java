package com.meeting.management.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.meeting.management.model.Mapping;

@Repository
public interface MappingRepository extends MongoRepository<Mapping, String>{
	
	@Query(value="{ roleName : ?0}", fields="{ meetings : 1 }")
	public List<Mapping> findByRoleName(String roleName);

}
