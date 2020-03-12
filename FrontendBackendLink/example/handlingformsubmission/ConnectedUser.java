package com.example.handlingformsubmission;

import java.util.Objects;

public class ConnectedUser {

	private String username;
	private long id;

	public ConnectedUser(String username) {
	  
		setUsername(username);
		setId((long) (Math.random() * 1000000));
	  
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return
		"{" + 
		"username='" + username + "'" +
		"id='" + id + "'" +
		"}";
	}

}