package com.anarq.core;

import com.anarq.songrequests.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

/* 
	RequestListController
		Handles the requests from the frontend.
	
	Author(s):
		Patrick
*/
@RestController
public class RequestListController {
  
	// Public variables
	public static List<Session> sessions = new ArrayList<Session>();
	
	/* Returns a session in relation to the id parsed in */
	public static Session getSessionForId(String in) {
		
		long id = (long) Integer.parseInt(in);
		
		return getSessionForId(id);
		
	}
	
	/* Returns a session in relation to the id parsed in */
	public static Session getSessionForId(long id) {
		
		for (int i = 0; i < sessions.size(); i++) {
			
			if(sessions.get(i).getId() == id) {
				
				return sessions.get(i);
				
			}
			
		}
		
		return null;
		
	}
	
	/* End a session with that ID */
	@PutMapping("/{sessionId}/end-session")
	public static void endSession(@PathVariable long sessionId) {
		
		System.out.println(sessionId + "> Terminating Session... ");
		
		for (int i = 0; i < sessions.size(); i++) {
			
			if(sessions.get(i).getId() == sessionId) {
				
				sessions.remove(i);
				
			}
			
		}
		
		
	}
	
	/* Leave a session with that ID */
	@PutMapping("/{sessionId}/leave-session/{id}")
	public static void endSession(@PathVariable long sessionId, @PathVariable String id) {
		
		System.out.println(sessionId + "> User Disconnected: " + id);
		
		Session currentSession = getSessionForId(sessionId);
		currentSession.kickUserForUsername(id);
		
	}
	
	/* Get the song requests from a session using an id */
	@GetMapping("/{sessionId}/song-requests")
	public static List<SongRequest> getSongRequests(@PathVariable long sessionId) {
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
			Collections.sort(currentSession.getSongRequests(), new Comparator<SongRequest>() {
				@Override
				public int compare(SongRequest a, SongRequest b) {
					if (a.getVotes() < b.getVotes())
						return 1;
					if (a.getVotes() > b.getVotes())
						return -1;
					return 0;
				}
			});
			
			return currentSession.getSongRequests();
		
		}
		
		return null;
		
	}
	
	/* Get the connected clients of a session based on the ID */
	@GetMapping("/{sessionId}/connected-users")
	public static List<ConnectedClient> getConnectedClients(@PathVariable long sessionId) {
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
			return currentSession.getConnectedClients();
		
		}
		
		return null;
		
		
	}
	
	/* Get the current song of a session based on the ID */
	@GetMapping("/{sessionId}/current-song")
	public static SongRequest getCurrentSong(@PathVariable long sessionId) {
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
			return currentSession.getCurrentSong();
		
		}
		
		return null;
		
	}
	
	/* Remove request from the current session with that ID */
	@PutMapping("/{sessionId}/remove-request/{id}")
	public static void removeRequest(@PathVariable long sessionId, @PathVariable long id) {
		
		System.out.println(sessionId + "> Removing song request " + id);
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
			currentSession.removeSongRequestForId(id);
		
		}
		
	}
	
	/* Kick user from the current session with that ID */
	@PutMapping("/{sessionId}/kick-user/{id}")
	public static void addKickUser(@PathVariable long sessionId, @PathVariable String id) {
		
		System.out.println(sessionId + "> Kicking User: " + id);
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
			currentSession.kickUserForId(id);
		
		}
		
	}
	
	/* Add to the score of a song from the current session with that ID */
	@PutMapping("/{sessionId}/add-score/{id}")
	public static SongRequest addScoreToRequest(@PathVariable long sessionId, @PathVariable long id) {
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
		SongRequest sr = currentSession.getSongRequestForId(id);
		
			if (sr != null) {

				sr.likeSong("0");
				
				return sr;
				
			}

		
			return null;
		
		}
		
		return null;
		
	}
	
	/* Remove from the score of a song from the current session with that ID */
	@PutMapping("/{sessionId}/sub-score/{id}")
	public static SongRequest subScoreToRequest(@PathVariable long sessionId, @PathVariable long id) {
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
		SongRequest sr = currentSession.getSongRequestForId(id);
		
			if (sr != null) {

				sr.dislikeSong("0");
				
				return sr;
				
			}

		
			return null;
		
		}
		
		return null;
		
	}

	/* Return current server info */
	@GetMapping("/get-server-info")
	public String getServerInfo() {
	  return LoginController.lastHost;
	}

	/* Return current client info */
	@GetMapping("/get-client-info")
	public ConnectedClient getClientInfo() {
	  return LoginController.lastClientInfo;
	}

}