package com.anarq.core;

/* 
	LoginInfo
		Contains information about the login of a user.
	
	Author(s):
		Patrick
*/
public class LoginInfo {

	// Private variables
	private String ip;
	private String username;

	/* Return the IP */
	public String getIp() {
		return ip;
	}

	/* Sets the IP */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/* Return the Username */
	public String getUsername() {
		return username;
	}
	
	/* Sets the Username */
	public void setUsername(String username) {
		this.username = username;
	}

}