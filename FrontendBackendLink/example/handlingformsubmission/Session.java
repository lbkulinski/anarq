package com.example.handlingformsubmission;

import java.util.*;

public class Session {

	private long id;
	private RequestQueue songRequestQueue = new RequestQueue();
	private List<ConnectedClient> connectedClients = new ArrayList<ConnectedClient>();

	public Session () {
	  
		setId((long) (Math.random() * 1000000));
	  
		songRequestQueue.addSong(new SongRequest(1, "Nevermind", "In Bloom", "Nirvana", "Grunge", "2"));
		songRequestQueue.addSong(new SongRequest(2, "Nevermind", "In Bloom", "Nirvana", "Grunge", "3"));
		songRequestQueue.addSong(new SongRequest(3, "Nevermind", "In Bloom", "Nirvana", "Grunge", "4"));
		songRequestQueue.addSong(new SongRequest(4, "Nevermind", "In Bloom", "Nirvana", "Grunge", "5"));
		songRequestQueue.addSong(new SongRequest(5, "Nevermind", "In Bloom", "Nirvana", "Grunge", "6"));
		songRequestQueue.addSong(new SongRequest(6, "Nevermind", "In Bloom", "Nirvana", "Grunge", "7"));
	  
	}
	
	public SongRequest getCurrentSong() {
		return songRequestQueue.currentSong;
	}
	
	public List<SongRequest> getSongRequests() {
		return new ArrayList<SongRequest>(songRequestQueue.songQueue);
	}

	public void setConnectedClients(List<ConnectedClient> connectedClients) {
		this.connectedClients = connectedClients;
	}
	
	public List<ConnectedClient> getConnectedClients() {
		return connectedClients;
	}
	
	public void addClient(ConnectedClient user) {
		connectedClients.add(user);	
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void removeSongRequestForId(long id) {
		
		songRequestQueue.removeSong(getSongRequestForId(id), null);
		
	}
	
	public void kickUserForUsername(String name) {
		
		for (int i = 0; i < connectedClients.size(); i++) {
			
			if(connectedClients.get(i).getName().equals(name)) {
				
				connectedClients.remove(i);
				
			}
			
		}
		
	}
	
	public void kickUserForId(String id) {
		
		for (int i = 0; i < connectedClients.size(); i++) {
			
			if(connectedClients.get(i).getIpAddress().equals(id)) {
				
				connectedClients.remove(i);
				
			}
			
		}
		
	}

	public SongRequest getSongRequestForId(long id) {
		
		for (int i = 0; i < getSongRequests().size(); i++) {
			
			if(getSongRequests().get(i).getId() == id) {
				
				return getSongRequests().get(i);
				
			}
			
		}
		
		return null;
		
	}

	
	@Override
	public String toString() {
		return
		"{" + 
		"id='" + id + "'" +
		"}";
	}

}