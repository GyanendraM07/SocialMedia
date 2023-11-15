package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Nested_Comment")
public class NestedComment {
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long id;
	@Column(name="postId")
	private Long postId;
	@Column(name="Comment_Id")
	private String commentId;
	@Column(name="Commented_User")
	private String commented_User;
	@Column(name="Comment_Date")
	private LocalDate commentDate=LocalDate.now();

}
