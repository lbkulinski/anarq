package com.anarq.core;

import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
	
	@PutMapping("/connect")
	public boolean attemptToConnectToSession(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="username", defaultValue="no_input_requested")String username) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session...\n ID: "
				+ sessionId + " Username: " + username + "...");
			return false;
		}
		
		// TODO: Add client to Session
		
		// Redirect
		return true;
		
	}
	
	@PutMapping("/disconnect")
	public boolean attemptToCloseConnectionToSession(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="username", defaultValue="no_input_requested")String username) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session...\n ID: "
				+ sessionId + " Username: " + username + "...");
			return false;
		}
		
		// TODO: Remove client from Session
		
		// Redirect
		return true;
		
	}
	
	@GetMapping("/authenticate")
	public boolean isSessionStillActive(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="username", defaultValue="no_input_requested")String username) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Auth Failed.");
			return false;
		}
		
		// TODO: Add client to Session
		
		// Redirect
		return true;
		
	}
	
}