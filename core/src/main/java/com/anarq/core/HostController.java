package com.anarq.core;

import org.springframework.web.bind.annotation.*;

@RestController
public class HostController {
	
	@PutMapping("/host-new-session")
	public String attemptToHostNewSession() {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.createNewSession();
		if (session == null) {
			System.err.println("Error: Request to create new session failed...");
			return "false";
		}
		
		// Redirect
		return session.getSessionId();
		
	}
	
	@PutMapping("/terminate-session")
	public boolean attemptToCloseConnectionToSession(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request to terminate session that doesn't exist");
			return false;
		}
		
		// TODO: Remove client from Session
		CoreApplication.terminateSession(sessionId);
		
		// Redirect
		return true;
		
	}
	
	@PutMapping("/delete-request")
	public boolean addSongRequest(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="songId", defaultValue="no_song_id")String songId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session was created!\n ID: "
				+ sessionId);
			return false;
		}
		
		return session.deleteSongRequest(songId);
		
	}
	
	@GetMapping("/authenticate-host")
	public boolean isSessionStillActive(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId) {
		
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