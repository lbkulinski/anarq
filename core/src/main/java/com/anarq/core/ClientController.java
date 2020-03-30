package com.anarq.core;

import com.anarq.database.*;
import com.anarq.songrequests.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
	
	@PutMapping("/connect")
	public ConnectedClient attemptToConnectToSession(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="username", defaultValue="no_username")String username,
	@RequestParam(value="isAccount", defaultValue="false")boolean isAccount) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session...\n ID: "
				+ sessionId + " Username: " + username + "...");
			return null;
		}
		
		if (NaughtyWords.isANaughtyWord(username)) {
			
			return null;
			
		}
		
		ConnectedClient newConnectedClient = new ConnectedClient(username, isAccount, Permission.JAMMER);
		session.addClient(newConnectedClient);
		
		// Redirect
		return newConnectedClient;
		
	}
	
	@PutMapping("/disconnect")
	public boolean attemptToCloseConnectionToSession(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="userId", defaultValue="no_user_id")String userId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session...\n ID: "
				+ sessionId + " UserId: " + userId + "...");
			return false;
		}
		
		// TODO: Remove client from Session
		session.removeClient(userId);
		
		// Redirect
		return true;
		
	}
	
	@GetMapping("/authenticate")
	public boolean isSessionStillActive(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="userId", defaultValue="no_user_id")String userId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Auth Failed.");
			return false;
		}
		// Check if that session has that user
		if (!session.hasUserForId(userId)) {
			return false;
		}			
		// Redirect
		return true;
		
	}
	
}