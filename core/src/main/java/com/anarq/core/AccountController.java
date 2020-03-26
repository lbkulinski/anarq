package com.anarq.core;

import org.bson.Document;
import com.anarq.database.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

	@PutMapping("/sign-up")
	public String signUpForAccount(
	@RequestParam(value="username", defaultValue="username")String username,
	@RequestParam(value="email", defaultValue="no_email")String email,
	@RequestParam(value="firstname", defaultValue="joe")String firstname,
	@RequestParam(value="lastname", defaultValue="swanson")String lastname,
	@RequestParam(value="day", defaultValue="1")int day,
	@RequestParam(value="month", defaultValue="1")int month,
	@RequestParam(value="year", defaultValue="2000")int year,
	@RequestParam(value="password", defaultValue="password")String password) {
		
		// TODO: Add sign up testing	
		CreateUser newUser = new CreateUser(username, password, firstname, lastname, day, month, year);
		
		return newUser.addJammer();
		
	}
	
	@PutMapping("/log-in")
	public String loginToAccount(
	@RequestParam(value="username", defaultValue="username")String username,
	@RequestParam(value="password", defaultValue="password")String password) {
		
		FindUser userLogin = new FindUser(username);
		
		if (userLogin == null) {
			
			return "No combination of that Username and Password was found!";
			
		}
		
		Document info = userLogin.attemptLogin(password);
		
		if (info == null) {
			
			return "No combination of that Username and Password was found!";
			
		}
		
		return "Login Success";
		
	}
	
}