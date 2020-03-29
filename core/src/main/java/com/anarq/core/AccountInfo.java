package com.anarq.core;

/* 
	Song
		Contains information about a User
	
	Author(s):
		Patrick
*/
public class AccountInfo {
	
	// Private variables
	private final String username;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String bio;
	
	/* Constructs a new Song class */

	public AccountInfo(String username, String firstName, String lastName, String email, String bio) {
		
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.bio = bio;

	}
	
	/* Returns the username of the user */
	public String getUsername() {
		
		return username;
		
	}
	
	/* Returns the first name of the user */
	public String getFirstName() {
		
		return firstName;
		
	}
	
	/* Returns the last name of the user */
	public String getLastName() {
		
		return lastName;
		
	}
	
	/* Returns the last name of the user */
	public String getEmail() {
		
		return email;
		
	}
	
	/* Returns the bio of the user */
	public String getBio() {
		
		return bio;
		
	}
	
}