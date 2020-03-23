package com.anarq.core;

import java.util.*;
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
	private List<ConnectedClient> connectedClients;
	
	/* Constructor */
	public Session() {
		
		// Generate a Session ID 
		sessionId = String.format("%06X", (int) ((float)Math.random() * 10000000.0f));
		System.out.println("New Session created with ID " + sessionId + ".");
		
		musicChooser = new MusicChooser();
		requestQueue = new RequestQueue();
		connectedClients = new ArrayList<ConnectedClient>();
		
	}
	
	/* Requests a new song for the RequestQueue */
	public boolean requestSong(Song song, String requesterId) {
		
		SongRequest newSongRequest = new SongRequest(song, requesterId);
		
		return requestQueue.addSong(newSongRequest);
		
	}
	
	/* Removes a song request from the request queue */
	public boolean deleteSongRequest(String songRequestId) {
		
		return requestQueue.removeSong(requestQueue.getSongFromQueue(songRequestId), new ConnectedClient("HOST", false, Permission.DJ));
		
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
	
	public void addClient(ConnectedClient c) {
		
		connectedClients.add(c);
		
	}
	
	public void removeClient(String userId) {
		
		for (int i = 0; i < connectedClients.size(); i++) {
			
			if (connectedClients.get(i).getId().equals(userId)) {
				System.out.println("Session: Removed user with ID " + userId);
				connectedClients.remove(i);
				break;
			}
			
		}
		
	}
	
	/* Returns a reference to the current music queue of this session */
	public boolean hasUserForId(String id) {
		
		for (int i = 0; i < connectedClients.size(); i++) {
			if (connectedClients.get(i).getId().equals(id)) {
				return true;
			}
		}
		
		return false;
		
	}
	
	/* Returns a reference to the current music queue of this session */
	public ConnectedClient[] getConnectedClients() {
		
		ConnectedClient[] output = new ConnectedClient[connectedClients.size()];
		Object[] array = connectedClients.toArray();
		
		for (int i = 0; i < connectedClients.size(); i++) {
			output[i] = (ConnectedClient) array[i];
		}
		
		return output;
		
	}
	
}