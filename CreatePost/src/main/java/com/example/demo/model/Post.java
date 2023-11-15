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
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "User_Post")
public class Post {
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private long id;
	@Column(name="Post_User_Id")
	private Long postUserId;
	@Column(name="File_url")
	private String  fileurl;
	@Column(name="File_content_type")
	private String  filetype;
	@Column(name="_Caption")
	private String caption;
	@Column(name="_Like")
	private int like;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "Post_Id", referencedColumnName = "id")
	private List < LikeUsers > likeUsers = new ArrayList < > ();
	
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "Post_Id", referencedColumnName = "id")
//	private List < Comment > comment = new ArrayList < > ();
	
	@Column(name="Created_Date")
	@Builder.Default
	private LocalDate createdDate=LocalDate.now();
	
	

}
