package com.example.demo.model;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="User_Followers")
public class Followers {
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long id;
	
	@Column(name="Follower_user_Name")
	private String followerUser;
	
	@Column(name="Following_user_id")
	private Long followingUserId;
	
	@Builder.Default
	@Column(name="Follow_Date")
	private LocalDateTime followDate=LocalDateTime.now();

}
