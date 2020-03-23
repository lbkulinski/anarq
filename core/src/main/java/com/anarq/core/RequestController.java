package com.anarq.core;

import com.anarq.songrequests.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class RequestController {
	
	@GetMapping("/current-requests")
	public SongRequest[] getSongQueue(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="userId", defaultValue="no_user_id")String userId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request to terminate session that doesn't exist");
			return null;
		}
		// Check if that session has that user
		if (!session.hasUserForId(userId)) {
			return null;
		}	
		
		return session.getRequestQueue().getSongQueue();
		
	}
	
	@GetMapping("/current-connected-users")
	public ConnectedClient[] getConnectedClients(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="userId", defaultValue="no_user_id")String userId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request to terminate session that doesn't exist");
			return null;
		}
		// Check if that session has that user
		if (!session.hasUserForId(userId)) {
			return null;
		}	
		
		return session.getConnectedClients();
		
	}
	
	@PutMapping("/like-song")
	public boolean likeSong(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="songId", defaultValue="no_song_id")String songId,
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
		
		return session.getRequestQueue().likeSong(songId, userId);
		
	}
	
	@PutMapping("/dislike-song")
	public boolean dislikeSong(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="songId", defaultValue="no_song_id")String songId,
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
		
		return session.getRequestQueue().dislikeSong(songId, userId);
		
	}
	
	
}