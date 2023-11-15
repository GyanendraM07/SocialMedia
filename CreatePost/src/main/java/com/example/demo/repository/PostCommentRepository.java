package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;

@Repository
public interface PostCommentRepository extends JpaRepository<Comment, Long> {

	
	@Query(value="SELECT * FROM userservice.post_comment where post_Id= :id",nativeQuery = true)
	List<Comment> findByPostId(long id);

//	List<Comment> findByPostId(Long id);

//	List<Comment> findByPostIdAndCommentId(Long id, Object object);
//
//	List<Comment> findByPostIdAndId(Long id, String commentId);

}
