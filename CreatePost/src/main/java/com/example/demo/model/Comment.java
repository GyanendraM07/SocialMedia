package com.example.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "Post_Comment")
public class Comment {
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long id;
//	@Builder.Default
//	@Column(name="Comment_Id")
//	private String commentId=null;
//	@Column(name="Post_Comment")
//	private String comment;
//	@Column(name="Comment_Like")
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "Comment_Id", referencedColumnName = "id")
//	private List < LikeUsers > likeUsers = new ArrayList < > ();
	
	@Column(name="commented_User")
	private String commentedUser;
	@Column(name="post_User")
	private String postUser;
	private String comment;
	@Column(name="comment_Like")
	private int commentLike;
	@Column(name="post_Id")
	private int postId;
	
	@Builder.Default
	@Column(name="Comment_Date")
	private LocalDate commentDate=LocalDate.now();

}
