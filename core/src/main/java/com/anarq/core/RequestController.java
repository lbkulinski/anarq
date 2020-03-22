package com.anarq.core;

import com.anarq.songrequests.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class RequestController {
	
	@GetMapping("/current-requests")
	public SongRequest[] getSongQueue(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request to terminate session that doesn't exist");
			return null;
		}
		
		return session.getRequestQueue().getSongQueue();
		
	}
	
	@GetMapping("/current-connected-users")
	public ConnectedClient[] getConnectedClients(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request to terminate session that doesn't exist");
			return null;
		}
		
		return session.getConnectedClients();
		
	}
	
}