package com.anarq.core;

import java.util.*;
import com.anarq.songrequests.*;

/* 
	Session
		Handles the information about a certain session.
	
	Author(s):
		Patrick
*/
public class Session {

	// Private variables
	private long id;
	private RequestQueue songRequestQueue = new RequestQueue();
	private List<ConnectedClient> connectedClients = new ArrayList<ConnectedClient>();

	/* Initalizes a new session */
	public Session () {
	  
		id = ((long) (Math.random() * 1000000));
	  
		/* DUMMY CODE, FOR TESTING PURPOESES ONLY */
		/* TODO: REMOVE DUMMY CODE */
		songRequestQueue.addSong(new SongRequest(1, "Nevermind", "In Bloom", "Nirvana", "Grunge", "2"));
		songRequestQueue.addSong(new SongRequest(2, "Nevermind", "In Bloom", "Nirvana", "Grunge", "3"));
		songRequestQueue.addSong(new SongRequest(3, "Nevermind", "In Bloom", "Nirvana", "Grunge", "4"));
		songRequestQueue.addSong(new SongRequest(4, "Nevermind", "In Bloom", "Nirvana", "Grunge", "5"));
		songRequestQueue.addSong(new SongRequest(5, "Nevermind", "In Bloom", "Nirvana", "Grunge", "6"));
		songRequestQueue.addSong(new SongRequest(6, "Nevermind", "In Bloom", "Nirvana", "Grunge", "7"));
		/* END */
		
	}
	
	/* Returns the current song being played */
	public SongRequest getCurrentSong() {
		return songRequestQueue.getCurrentSong();
	}
	
	/* Returns the list of songs currently in the queue */
	public List<SongRequest> getSongRequests() {
		return new ArrayList<SongRequest>(songRequestQueue.getSongQueue());
	}
	
	/* Returns a list of connected clients */
	public List<ConnectedClient> getConnectedClients() {
		return connectedClients;
	}
	
	/* add a client to the list of connected clients */
	public void addClient(ConnectedClient user) {
		connectedClients.add(user);	
	}
	
	/* Returns an id of the current session */
	public long getId() {
		return id;
	}

	/* Remove a song request using an ID */
	public void removeSongRequestForId(long id) {
		
		songRequestQueue.removeSong(getSongRequestForId(id), null);
		
	}
	
	/* Kick a user from the session based on a certain username */
	public void kickUserForUsername(String name) {
		
		for (int i = 0; i < connectedClients.size(); i++) {
			
			if(connectedClients.get(i).getName().equals(name)) {
				
				connectedClients.remove(i);
				
			}
			
		}
		
	}
	
	/* Kick a user from the session based on a certain ID */
	public void kickUserForId(String id) {
		
		for (int i = 0; i < connectedClients.size(); i++) {
			
			if(connectedClients.get(i).getIpAddress().equals(id)) {
				
				connectedClients.remove(i);
				
			}
			
		}
		
	}

	/* Return a song request for a certain ID */
	public SongRequest getSongRequestForId(long id) {
		
		for (int i = 0; i < getSongRequests().size(); i++) {
			
			if(getSongRequests().get(i).getId() == id) {
				
				return getSongRequests().get(i);
				
			}
			
		}
		
		return null;
		
	}

	/* Return a string representation of the session */
	@Override
	public String toString() {
		return
		"{" + 
		"id='" + id + "'" +
		"}";
	}

}