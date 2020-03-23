package com.anarq.core;

import org.springframework.web.bind.annotation.*;

@RestController
public class HostController {
	
	@PutMapping("/host-new-session")
	public ConnectedClient attemptToHostNewSession() {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.createNewSession();
		if (session == null) {
			System.err.println("Error: Request to create new session failed...");
			return null;
		}
		
		// Redirect
		return session.getHostClient();
		
	}
	
	@PutMapping("/terminate-session")
	public boolean attemptToCloseConnectionToSession(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="userId", defaultValue="no_user_id")String userId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request to terminate session that doesn't exist");
			return false;
		}
		// Check if that session has that user
		if (!session.hasUserForId(userId)) {
			return false;
		}
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
	
	@PutMapping("/kick-user")
	public boolean kickUser(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="userId", defaultValue="no_song_id")String userId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session was created!\n ID: "
				+ sessionId);
			return false;
		}
		
		return session.removeClient(userId);
		
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
		
		// Redirect
		return true;
		
	}
	
}