package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	
	Optional<User> findByUserNameAndPassword(String user, String password);

	Optional<User> findById(int followingUserId);


	Optional<User> findByIdAndFirstNameAndGender(Long id, String fname, String gender);

	Optional<User> findByIdAndFirstNameAndUserName(Long id, String fname, String userNmae);

	Optional<User> findByIdAndUserName(Long id, String userNmae);
    

}
