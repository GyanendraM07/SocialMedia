package com.example.demo.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "First_Name")
	private String firstName;
	@Column(name = "Last_Name")
	private String lastName;
	@Column(name = "Profile_pic_Url")
	private String profilepicUrl;
	@Column(name = "Gender")
	private String gender;
	@Column(name = "User_Name")
	private String userName;
	@Column(name = "Password")
	private String password;
	@Column(name="Date_Of_Birth")
	private String dateofBirth;
	
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "User_Id", referencedColumnName = "id")
//	private List < Following > following = new ArrayList < > ();
	@Builder.Default
	@Column(name="Created_Date")
	private LocalDateTime createdDate=LocalDateTime.now();
	
}
