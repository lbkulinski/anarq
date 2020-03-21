package com.anarq.core;

import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {
	
	@GetMapping("/search")
	public Song[] searchForSongsWithQuery(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="query", defaultValue="no_input_requested")String query) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session was created!\n ID: "
				+ sessionId + " Query: " + query + "...");
			return null;
		}
		
		// Return a list of songs from the music chooser of the session
		return session.getMusicChooser().searchForSongs(query);
		
	}
	
}