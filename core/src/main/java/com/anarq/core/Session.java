package com.anarq.core;

public class Session {
	
	private final String sessionId;
	private MusicChooser musicChooser;
	
	public Session() {
		
		// Generate a Session ID 
		sessionId = String.format("%06x", (int) ((float)Math.random() * 10000000.0f));
		System.out.println("New Session created with ID " + sessionId + ".");
		
		musicChooser = new MusicChooser();
		
	}
	
	public String getSessionId() {
		
		return sessionId;
		
	}
	
	public MusicChooser getMusicChooser() {
		
		return musicChooser;
		
	}
	
}