package com.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	
    @Id
    @GeneratedValue
	public Long userID;
	public String username;

	
	public User(Long userID, String username)
	{
		this.userID = userID;
		this.username = username;
	}
	
	public User()
	{
		
	}
	
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
