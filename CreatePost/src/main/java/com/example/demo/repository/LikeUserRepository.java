package com.example.demo.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.LikeUsers;

@Repository
public interface LikeUserRepository extends JpaRepository<LikeUsers, Long>{

//	Optional<LikeUsers> findByPost_IdAndLikeUser(Long id, String likeUser);
    
	@Query(value = "SELECT * FROM userservice.like_users where Post_Id=(:id)And Like_User=(:user)",nativeQuery = true)
	Optional<LikeUsers> findByLikeUserAndPost_Id(String user, long id);
	
    @Query(value = "SELECT * FROM userservice.like_users where Post_Id= 2",nativeQuery = true)
	List<LikeUsers> findByPostId(Long postId);
    
    @Query(value ="SELECT COUNT(*) FROM userservice.user_followers WHERE Follower_user_Name = ( :user) and Following_user_id= ( :postUserId)",nativeQuery = true)
	int findfollow(String user, Long postUserId);
     
    @Query(value = "SELECT * FROM userservice.like_users where Post_Id= :postId",nativeQuery = true)
	List<LikeUsers> findByPost_Id(Long postId);
    
    @Query(value = "SELECT Profile_pic_Url FROM userservice.User where User_Name= :likeUser",nativeQuery = true)
	String findbyuserimage(String likeUser);
    
    @Query(value ="select count(*) from userservice.user_followers where Follower_user_Name = ( :followeruser) and Following_user_id= (select id from userservice.user where User_Name=( :followinguser))",nativeQuery = true)
	int checkFollower(String followeruser, String followinguser);
     

}
