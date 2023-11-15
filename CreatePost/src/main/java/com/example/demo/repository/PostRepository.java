package com.example.demo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Post;


public interface PostRepository extends JpaRepository<Post, Long> {
    
	@Query(value = "SELECT Profile_pic_Url FROM userservice.user where id= ( :long1)",nativeQuery = true)
	String finddpUrl(Long long1);

	Optional<Post> findById(int i);
    @Query(value = "SELECT id FROM userservice.user where User_Name=( :name)",nativeQuery = true)
	Long findUserId(String name);
    
    @Query(value ="SELECT User_Name FROM userservice.user where id= ( :postUserId)",nativeQuery = true)
	String findName(Long postUserId);
    
    @Query(value = "delete from userservice.user_followers where Follower_userName=( :followerUser) and Following_user_id=( :followingUserId)",nativeQuery = true)
	void deleteFollower(String followerUser, Long followingUserId);
     
    @Query(value ="SELECT First_Name,Last_Name,Profile_pic_Url FROM user where User_Name= :user",nativeQuery = true)
	String finduserDetails(String user);
     
    @Query(value = "SELECT User_Name,File_url,_like FROM user u,user_post ua where u.id=ua.Post_User_Id  and u.User_Name= :user order by ua.Created_Date",nativeQuery = true)
	List<List<String>> findProfiledata(String user);
    
    @Query(value ="SELECT Profile_pic_Url FROM user where User_Name= ( :commentedUser)",nativeQuery = true)
	String findByuserName(String commentedUser);
    
//    @Query(value = "delete from userservice.user_followers where Follower_userName=( :followerUser) and Following_user_id=( :followingUserId)",nativeQuery = true)
//	void deleteFollower(String followerUser, Long followingUserId);
     
	

}
