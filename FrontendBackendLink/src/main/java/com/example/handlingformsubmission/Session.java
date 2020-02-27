package com.example.handlingformsubmission;

import java.util.*;

public class Session {

	private long id;
	private SongRequest currentSong = new SongRequest("Revolution 9", "The Beatles", "pcrowne");
	private List<SongRequest> songRequests = new ArrayList<SongRequest>();
	private List<ConnectedUser> connectedUsers = new ArrayList<ConnectedUser>();

	public Session () {
	  
		setId((long) (Math.random() * 1000000));
	  
		songRequests.add(new SongRequest("Smells Like Teen Spirit", "Nirvana", "pcrowne"));
		songRequests.add(new SongRequest("Money Machine", "100 Gecs", "pcrowne"));
		songRequests.add(new SongRequest("My Boy", "Car Seat Headrest", "pcrowne"));
		songRequests.add(new SongRequest("You Shook Me", "Led Zeppelin", "pcrowne"));
		songRequests.add(new SongRequest("Paranoid Android", "Radiohead", "pcrowne"));
		
		//connectedUsers.add(new ConnectedUser("pcrowne"));
		//connectedUsers.add(new ConnectedUser("jonesbbq223"));
		//connectedUsers.add(new ConnectedUser("weliveinasociety12"));
		//connectedUsers.add(new ConnectedUser("thecodemaster"));
	  
	}

	public void setCurrentSong(SongRequest currentSong) {
		this.currentSong = currentSong;
	}
	
	public SongRequest getCurrentSong() {
		return currentSong;
	}
	
	public void setSongRequests(List<SongRequest> songRequests) {
		this.songRequests = songRequests;
	}
	
	public List<SongRequest> getSongRequests() {
		return songRequests;
	}

	public void setConnectedUsers(List<ConnectedUser> connectedUsers) {
		this.connectedUsers = connectedUsers;
	}
	
	public List<ConnectedUser> getConnectedUsers() {
		return connectedUsers;
	}
	
	public void addClient(ConnectedUser user) {
		connectedUsers.add(user);	
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SongRequest getSongRequestForId(long id) {
		
		for (int i = 0; i < songRequests.size(); i++) {
			
			if(songRequests.get(i).getId() == id) {
				
				return songRequests.get(i);
				
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