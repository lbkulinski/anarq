package com.anarq.core;

import java.time.LocalTime;
import com.anarq.songrequests.*;

/* 
	ConnectedClient
		Handles the information about a certain client who is connected to a Session.
	
	Author(s):
		Nick
		Patrick
*/
public class ConnectedClient {
	
	// Private Varaibles
    private final String name;
    private final String id;
	private final boolean isRegistered;
    private final Permission permissionLevel;
    private LocalTime lastActive;

	/* Initalizes a new ConnectedClient*/
    public ConnectedClient(String name, boolean isRegistered, Permission permissionLevel) {
        this.name = name;
		this.isRegistered = isRegistered;
        this.permissionLevel = permissionLevel;
		
		id = generateId();
		System.out.println(id);
    }

	/* Generates an ID number for this client */
	private String generateId() {
		return String.format("%08x%08x%08x", name.hashCode()*37, (isRegistered + "").hashCode()*37, permissionLevel.hashCode()*37);
	}

	/* Returns the client's name */
    public String getName() {
        return this.name;
    }
	
	/* Returns the IP Address of the client */
    public String getId() {
        return this.id;
    }

	/* Returns the Permission level of the client */
    public Permission getPermissionLevel() {
        return this.permissionLevel;
    }

	/* Returns the last active time of the client */
    public LocalTime getLastActive() {
        return this.lastActive;
    }
}