package com.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class User {
	
    @Id
    @GeneratedValue
	private Long userID;

    @NotNull
	public String username;

    private Set<Product> createdProducts;

    private User(){}

	public User(String username)
	{
		this.username = username;
	}

	public Long getUserID() {
		return userID;
	}
	private void setUserID(Long userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
