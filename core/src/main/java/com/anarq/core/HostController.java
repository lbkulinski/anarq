package com.anarq.core;

import com.anarq.songrequests.*;
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
	
	@PutMapping("/update-preferences")
	public boolean setPreferences(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="userId", defaultValue="no_user_id")String userId,
	@RequestParam(value="maxBPM", defaultValue="400")int maxBPM,
	@RequestParam(value="minBPM", defaultValue="32")int minBPM,
	@RequestParam(value="dislikeThreshold", defaultValue="10")int dislikeThreshold,
	@RequestParam(value="allowExplicit", defaultValue="true")boolean explicit,
	@RequestParam(value="allowRequests", defaultValue="true")boolean requests,
	@RequestParam(value="isVisible", defaultValue="true")boolean visible,
	@RequestParam(value="allowPop", defaultValue="true")boolean pop,
	@RequestParam(value="allowRock", defaultValue="true")boolean rock,
	@RequestParam(value="allowCountry", defaultValue="true")boolean country,
	@RequestParam(value="allowJazz", defaultValue="true")boolean jazz,
	@RequestParam(value="allowRap", defaultValue="true")boolean rap,
	@RequestParam(value="allowMetal", defaultValue="true")boolean metal,
	@RequestParam(value="allowRB", defaultValue="true")boolean rb,
	@RequestParam(value="allowHipHop", defaultValue="true")boolean hiphop,
	@RequestParam(value="allowElectronic", defaultValue="true")boolean electronic,
	@RequestParam(value="allowChristian", defaultValue="true")boolean christian) {
		
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
		
		System.out.println("Updating preferences....");
		
		session.getRequestQueue().setMaxBPM(maxBPM);
		session.getRequestQueue().setMinBPM(minBPM);
		session.getRequestQueue().setDislikeThreshold(dislikeThreshold);
		
		session.getRequestQueue().setExplicitFilter(explicit);
		session.getRequestQueue().setAcceptingRequests(requests);
		session.getRequestQueue().setVisibility(visible);
		
		session.getRequestQueue().setBlacklistedGenres(new boolean[] {pop, rock, country, jazz, rap, metal, rb, hiphop, electronic, christian});
		
		// Redirect
		return true;
		
	}
	
	@GetMapping("/get-preferences")
	public PreferencePacket getPreferences(
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
		
		return session.getRequestQueue().getPreferencePacket();
		
	}
	
	@GetMapping("/get-qr-code")
	public byte[] getQrCode(
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
		
		return session.getQrCode();
		
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
	public boolean deleteSongRequest(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="songId", defaultValue="no_song_id")String songId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session was created!\n ID: "
				+ sessionId);
			return false;
		}
		
		String artist = session.getMusicChooser().getSongForSongId(songId).getArtistName();
		boolean ret = session.deleteSongRequest(songId);

		// AUTODJ

		if (session.getRequestQueue().isEmpty()) {
			System.out.println("My artist = " + artist);
			session.requestSong(session.getAutoDJ().getSongReccomendation(), Session.AUTODJ_NAME);
		}

		return ret;
	}
	
	@PutMapping("/approve-override-request")
	public boolean approveOverrideRequest(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="songId", defaultValue="no_song_id")String songId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session was created!\n ID: "
				+ sessionId);
			return false;
		}
		
		String artist = session.getMusicChooser().getSongForSongId(songId).getArtistName();
		boolean ret = session.approveOverrideSongRequest(songId);

		return ret;
	}
	
	@PutMapping("/delete-override-request")
	public boolean deleteOverrideRequest(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="songId", defaultValue="no_song_id")String songId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session was created!\n ID: "
				+ sessionId);
			return false;
		}
		
		String artist = session.getMusicChooser().getSongForSongId(songId).getArtistName();
		boolean ret = session.deleteOverrideSongRequest(songId);

		return ret;
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
	
	@PutMapping("/blacklist-user")
	public boolean blacklistUser(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="userId", defaultValue="no_song_id")String userId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session was created!\n ID: "
				+ sessionId);
			return false;
		}
		
		return session.blacklistClient(userId);
		
	}
	
	@PutMapping("/unblacklist-user")
	public boolean unblacklistUser(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="userId", defaultValue="no_song_id")String userId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session was created!\n ID: "
				+ sessionId);
			return false;
		}
		
		return session.unblacklistClient(userId);
		
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