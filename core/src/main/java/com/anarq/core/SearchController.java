package com.anarq.core;

import com.anarq.spotify.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {
	
	@GetMapping("/search")
	public Song[] searchForSongsWithQuery(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="query", defaultValue="no_query")String query,
	@RequestParam(value="userId", defaultValue="no_user_id")String userId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session was created!\n ID: "
				+ sessionId);
			return null;
		}
		// Check if that session has that user
		if (!session.hasUserForId(userId)) {
			return null;
		}	
		// Return a list of songs from the music chooser of the session
		return session.getMusicChooser().searchForSongs(query);
		
	}
	
	@PutMapping("/request-song")
	public boolean addSongRequest(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="songId", defaultValue="no_song_id")String songId,
	@RequestParam(value="userId", defaultValue="no_user_id")String userId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session was created!\n ID: "
				+ sessionId);
			return false;
		}
		// Check if that session has that user
		if (!session.hasUserForId(userId)) {
			return false;
		}	
		
		return session.requestSong(SpotifyGateway.getSongForSongId(songId), userId);
		
	}
	
}