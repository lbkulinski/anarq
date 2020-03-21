package com.anarq.core;

import com.anarq.spotify.*;

import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {

	private static ArrayList<Session> activeSessions = new ArrayList<Session>();

	public static void main(String[] args) {
		
		SpringApplication.run(CoreApplication.class, args);
		
		SpotifyInteraction.searchTracks("Stairway To Heaven");
		
	}
	
	/* Creates a new Session and returns the created Session's ID */
	public static Session createNewSession() {
		
		Session newSession = new Session();
		activeSessions.add(newSession);
		
		return newSession;
		
	}
	
	/* Ends a session based on that Session's ID */
	public static boolean terminateSession(String sessionId) {
		
		for (int i = 0; i < activeSessions.size(); i++) {
			if (activeSessions.get(i).getSessionId().equals(sessionId)) {
				
				activeSessions.remove(i);
				return true;
				
			}
		}
		
		return false;
		
	}
	
	/* Using the given Id, find a session and return the reference to it,
		otherwise, return null */
	public static Session getSessionForSessionId(String sessionId) {

		for (int i = 0; i < activeSessions.size(); i++) {
			if (activeSessions.get(i).getSessionId().equals(sessionId)) {
				
				return activeSessions.get(i);
				
			}
		}
		
		return null;

	}		

}
