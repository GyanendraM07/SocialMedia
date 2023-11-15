package com.example.demo.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Followers;

@Repository
public interface FollowersRepository extends JpaRepository<Followers, Long>{

	@Modifying
	@Transactional
	@Query(value = "delete from user_followers where Follower_user_Name= :fUser and Following_user_id= :fyserId",nativeQuery = true)
	void deleteFollower(String fUser, Long fyserId);

}
