package com.anarq.core;

import com.anarq.qr.*;
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
	private List<ConnectedClient> blacklistedIds;
	private ConnectedClient hostClient;
	private byte[] qrCode;
	
	/* Constructor */
	public Session() {
		
		// Generate a Session ID 
		sessionId = String.format("%06X", (int) ((float)Math.random() * 10000000.0f));
		System.out.println("New Session created with ID " + sessionId + ".");
		
		musicChooser = new MusicChooser();
		requestQueue = new RequestQueue();
		connectedClients = new ArrayList<ConnectedClient>();
		blacklistedIds = new ArrayList<ConnectedClient>();
		
		// Create a master client for the host
		hostClient = new ConnectedClient("HOST", true, sessionId);
		connectedClients.add(hostClient);
		
		qrCodeGenerator qr = new qrCodeGenerator();
		
		try {
		
			qrCode = qr.getQRCodeImage(sessionId);
		
		}catch (Exception e) {
			
			qrCode = null;
			
		}
		
		System.out.println("\n\nQR CODE: " + qrCode + "\n\n");
		
	}
	
	/* Requests a new song for the RequestQueue */
	public boolean requestSong(Song song, String requesterId) {
		
		SongRequest newSongRequest = new SongRequest(song, requesterId);
		
		return requestQueue.addSong(newSongRequest);
		
	}
	
	/* Removes a song request from the request queue */
	public boolean deleteSongRequest(String songRequestId) {
		
		return requestQueue.removeSong(requestQueue.getSongFromQueue(songRequestId), hostClient);
		
	}
	
	/* Removes a song request from the request queue */
	public boolean deleteOverrideSongRequest(String songRequestId) {
		
		requestQueue.removeOverride(requestQueue.getSongFromQueue(songRequestId));
		return true;
		
	}
	
	public boolean approveOverrideSongRequest(String songRequestId) {
		
		requestQueue.approveOverride(requestQueue.getSongFromQueue(songRequestId));
		return true;
		
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
	
	/* Returns a reference to the current music queue of this session */
	public ConnectedClient getHostClient() {
		
		return hostClient;
		
	}
	
	public byte[] getQrCode() {
		
		return qrCode;
		
	}
	
	public boolean blacklistClient(String userId) {
		
		for (int i = 0; i < connectedClients.size(); i++) {
		
			if (connectedClients.get(i).getId().equals(userId)) {
		
				blacklistedIds.add(connectedClients.get(i));
				removeClient(userId);
				return true;
			
			}
		
		}
		
		return false;
		
	}
	
	public boolean unblacklistClient(String userId) {
		
		for (int i = 0; i < blacklistedIds.size(); i++) {
		
			if (blacklistedIds.get(i).getId().equals(userId)) {
		
				blacklistedIds.remove(i);
				return true;
			
			}
		
		}
		
		return false;
		
	}
	
	public void addClient(ConnectedClient c) {
		
		for (int i = 0; i < blacklistedIds.size(); i++) {
			
			if (blacklistedIds.get(i).getId().equals(c.getId())) {
				
				// User is blacklisted
				return;
				
			}
			
		}
		
		connectedClients.add(c);
		
	}
	
	public boolean removeClient(String userId) {
		
		for (int i = 0; i < connectedClients.size(); i++) {
			
			if (connectedClients.get(i).getId().equals(userId)) {
				System.out.println("Session: Removed user with ID " + userId);
				connectedClients.remove(i);
				return true;
			}
			
		}
		
		return false;
		
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
	
	/* Returns a reference to the current music queue of this session */
	public ConnectedClient[] getBlacklistedClients() {
		
		ConnectedClient[] output = new ConnectedClient[blacklistedIds.size()];
		Object[] array = blacklistedIds.toArray();
		
		for (int i = 0; i < blacklistedIds.size(); i++) {
			output[i] = (ConnectedClient) array[i];
		}
		
		return output;
		
	}
	
}