package com.anarq.core;

import com.anarq.spotify.*;
import java.time.LocalTime;
import com.anarq.songrequests.*;

/**
 * A client that is connected to a session of the AnarQ Application.
 *
 * @version April 23, 2020
 */
public class ConnectedClient {
    /**
     * The name of this client.
     */
    private final String name;

    /**
     * The ID of this client.
     */
    private final String id;

    /**
     * Whether or not this client is registered.
     */
	private final boolean isRegistered;

    /**
     * The permission level of this client.
     */
    private final Permission permissionLevel;

	/**
     * The Spotify instance of this client.
     */
    private final ClientCredentialsExample spotify;

    /**
     * The last activity time of this client.
     */
    private LocalTime lastActive;

    /**
     * The host session ID of this client.
     */
	private final String hostSessionId;

	/**
     * The Spotify authentication key of this session.
     */
    private String spotifyAuthKey = "";

    /**
     * Constructs a newly allocated {@code ConnectedClient} object with the specified name, registration status, and
     * permission level.
     *
     * @param name the name to be used in construction
     * @param isRegistered the registration status to be used in construction
     * @param permissionLevel the permission level to be used in construction
     */
    public ConnectedClient(String name, boolean isRegistered, Permission permissionLevel) {
        this.name = name;
		this.isRegistered = isRegistered;
        this.permissionLevel = permissionLevel;
		this.hostSessionId = "NOT_HOST";
		spotify = new ClientCredentialsExample();
		
		id = generateId();

		System.out.println(id);
    } //ConnectedClient
	
	/**
     * Constructs a newly allocated {@code ConnectedClient} object with the specified name, registration status, and
     * host session ID. This constructor should be used to create a host.
     *
     * @param name the name to be used in construction
     * @param isRegistered the registration status to be used in construction
     * @param hostSessionId the host session ID to be used in construction
     */
    public ConnectedClient(String name, boolean isRegistered, String hostSessionId) {
        this.name = name;
		this.isRegistered = isRegistered;
        this.permissionLevel = Permission.DJ;
		this.hostSessionId = hostSessionId;
		spotify = new ClientCredentialsExample();
		
		id = generateId();

		System.out.println(id);
    } //ConnectedClient

	/* Generates an ID number for this client */

    /**
     * Returns a generated ID for this client.
     *
     * @return a generated ID for this client
     */
	private String generateId() {
		return String.format("%08x%08x%08x", name.hashCode() * 37, (isRegistered + "").hashCode() * 37,
                             permissionLevel.hashCode() * 37);
	} //generateId

    /**
     * Returns the name of this client.
     *
     * @return the name of this client
     */
    public String getName() {
        return this.name;
    } //getName

    /**
     * Returns the ID of this client.
     *
     * @return the ID of this client
     */
    public String getId() {
        return this.id;
    } //getId
	
	/**
     * Returns the Spotify instance of this client.
     *
     * @return the Spotify instance of this client
     */
    public ClientCredentialsExample retrieveSpotify() {
        return this.spotify;
    } //getId

	/**
     * Updates the Spotify authentication key of this session with the specified Spotify authentication key.
     *
     * @param key the Spotify authentication key
     */
    public void setSpotifyAuthKey(String key) {
        spotifyAuthKey = key;
    } //setSpotifyAuthKey

    /**
     * Returns the Spotify authentication key of this session.
     *
     * @return the Spotify authentication key of this session
     */
    public String getSpotifyAuthKey() {
        return spotifyAuthKey;
    } //getSpotifyAuthKey

    /**
     * Returns the permission level of this client.
     *
     * @return the permission level of this client
     */
    public Permission getPermissionLevel() {
        return this.permissionLevel;
    } //getPermissionLevel

    /**
     * Returns the last activity time of this client.
     *
     * @return the last activity time of this client
     */
    public LocalTime getLastActive() {
        return this.lastActive;
    } //getLastActive

    /**
     * Returns the host session ID of this client.
     *
     * @return the host session ID of this client
     */
    public String getHostSessionId() {
        return this.hostSessionId;
    } //getHostSessionId
}