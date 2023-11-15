package com.user1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserFavorite {
	@Id
	private String id;
	private String user;
	private String Song;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSong() {
		return Song;
	}
	public void setSong(String song) {
		Song = song;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	

}
