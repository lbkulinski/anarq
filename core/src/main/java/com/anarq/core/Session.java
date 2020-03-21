package com.anarq.core;

import com.anarq.songrequests.*;

/* 
	Session
		Contains information about a session of AnarQ
	
	Author(s):
		Patrick
*/
public class Session {
	
	// Private Varaibles
	private final String sessionId;
	private MusicChooser musicChooser;
	private RequestQueue requestQueue;
	
	/* Constructor */
	public Session() {
		
		// Generate a Session ID 
		sessionId = String.format("%06x", (int) ((float)Math.random() * 10000000.0f));
		System.out.println("New Session created with ID " + sessionId + ".");
		
		musicChooser = new MusicChooser();
		requestQueue = new RequestQueue();
		
	}
	
	/* Requests a new song for the RequestQueue */
	public void requestSong(Song song, String requesterId) {
		
		SongRequest newSongRequest = new SongRequest(
		(int)((float)Math.random() * 10000.0f), song, requesterId);
		
		requestQueue.addSong(newSongRequest);
		
	}
	
	/* Returns a session ID for the current session */
	public String getSessionId() {
		
		return sessionId;
		
	}
	
	/* Returns a reference to the music chooser for this session */
	public MusicChooser getMusicChooser() {
		
		return musicChooser;
		
	}
	
	/* Returns a reference to the current music queue of this session */
	public RequestQueue getRequestQueue() {
		
		return requestQueue;
		
	}
	
}