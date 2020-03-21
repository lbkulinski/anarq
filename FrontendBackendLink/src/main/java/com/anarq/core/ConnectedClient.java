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
    private String name;
    private String ipAddress;
    private Permission permissionLevel;
    private LocalTime lastActive;

	/* Initalizes a new ConnectedClient*/
    public ConnectedClient(String name, String ipAddress, Permission permissionLevel, LocalTime lastActive) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.permissionLevel = permissionLevel;
        this.lastActive = lastActive;
    }

	/* Returns the client's name */
    public String getName() {
        return this.name;
    }
	
	/* Returns the IP Address of the client */
    public String getIpAddress() {
        return this.ipAddress;
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