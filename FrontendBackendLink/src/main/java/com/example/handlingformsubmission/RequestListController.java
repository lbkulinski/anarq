package com.example.handlingformsubmission;



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


@RestController
public class RequestListController {
  
	public static List<Session> sessions = new ArrayList<Session>();
 
	
	public static Session getSessionForId(String in) {
		
		long id = (long) Integer.parseInt(in);
		
		return getSessionForId(id);
		
	}
	
	public static Session getSessionForId(long id) {
		
		for (int i = 0; i < sessions.size(); i++) {
			
			if(sessions.get(i).getId() == id) {
				
				return sessions.get(i);
				
			}
			
		}
		
		return null;
		
	}
	
	@GetMapping("/{sessionId}/end-session")
	public static void endSession(@PathVariable long sessionId) {
		
		for (int i = 0; i < sessions.size(); i++) {
			
			if(sessions.get(i).getId() == sessionId) {
				
				sessions.remove(i);
				
			}
			
		}
		
		
	}
	
	@GetMapping("/{sessionId}/leave-session/{id}")
	public static void endSession(@PathVariable long sessionId, @PathVariable long id) {
		
		Session currentSession = getSessionForId(sessionId);
		
		currentSession.kickUserForId(id);
		
	}
	
	@GetMapping("/{sessionId}/song-requests")
	public static List<SongRequest> getSongRequests(@PathVariable long sessionId) {
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
			Collections.sort(currentSession.getSongRequests(), new Comparator<SongRequest>() {
				@Override
				public int compare(SongRequest a, SongRequest b) {
					if (a.getScore() < b.getScore())
						return 1;
					if (a.getScore() > b.getScore())
						return -1;
					return 0;
				}
			});
			
			return currentSession.getSongRequests();
		
		}
		
		return null;
		
	}
	
	@GetMapping("/{sessionId}/connected-users")
	public static List<ConnectedUser> getConnectedUsers(@PathVariable long sessionId) {
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
			return currentSession.getConnectedUsers();
		
		}
		
		return null;
		
		
	}
	
	@GetMapping("/{sessionId}/current-song")
	public static SongRequest getCurrentSong(@PathVariable long sessionId) {
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
			return currentSession.getCurrentSong();
		
		}
		
		return null;
		
	}
	
	@PutMapping("/{sessionId}/remove-request/{id}")
	public static void addRemoveRequest(@PathVariable long sessionId, @PathVariable long id) {
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
			currentSession.removeSongRequestForId(id);
		
		}
		
	}
	
	@PutMapping("/{sessionId}/kick-user/{id}")
	public static void addKickUser(@PathVariable long sessionId, @PathVariable long id) {
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
			currentSession.kickUserForId(id);
		
		}
		
	}
	
	@PutMapping("/{sessionId}/add-score/{id}")
	public static SongRequest addScoreToRequest(@PathVariable long sessionId, @PathVariable long id) {
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
		SongRequest sr = currentSession.getSongRequestForId(id);
		
			if (sr != null) {

				sr.adjustScore(1);
				
				return sr;
				
			}

		
			return null;
		
		}
		
		return null;
		
	}
	
	@PutMapping("/{sessionId}/sub-score/{id}")
	public static SongRequest subScoreToRequest(@PathVariable long sessionId, @PathVariable long id) {
		
		Session currentSession = getSessionForId(sessionId);
		
		if (currentSession != null) {
		
		SongRequest sr = currentSession.getSongRequestForId(id);
		
			if (sr != null) {

				sr.adjustScore(-1);
				
				return sr;
				
			}

		
			return null;
		
		}
		
		return null;
		
	}

	@GetMapping("/get-server-info")
	public String getServerInfo() {
		System.out.println(LoginController.lastHost);
	  return LoginController.lastHost;
	}

	@GetMapping("/get-client-info")
	public ConnectedUser getClientInfo() {
	  return LoginController.lastClientInfo;
	}

}