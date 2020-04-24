package com.anarq.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * A utility class for getting and setting the Spotify token of the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class GetAndSetSpotifyToken {
    /**
     * The username of this Spotify token utility class.
     */
    String username;

    /**
     * The Spotify token of this Spotify token utility class.
     */
    String spotifyToken;

    /**
     * The user of this Spotify token utility class.
     */
    FindUser user;

    /**
     * Constructs a newly allocated {@code GetAndSetSpotifyToken} object with the specified username and Spotify token.
     *
     * @param username the username to be used in construction
     * @param spotifyToken the Spotify token to be used in construction
     */
    public GetAndSetSpotifyToken(String username, String spotifyToken) {
        this.username = username;
        this.spotifyToken = spotifyToken;
        user = new FindUser(username);
    } //GetAndSetSpotifyToken

    /**
     * Returns the Spotify token of this Spotify token utility class.
     *
     * @return the Spotify token of this Spotify token utility class
     */
    public String getSpotifyToken() {
        Document userDetails = user.find();

        if (userDetails == null) {
            System.out.println("Couldn't find user");

            return null;
        } //end if

        Object authCode = userDetails.get("authorization-code");

        if (authCode == null) {
            System.out.println("User does not have an authorization-code");

            return null;
        } //end if

        System.out.println("\n auth code = " + authCode.toString());

        return (String) authCode;
    } //getSpotifyToken

    /**
     * Updates the Spotify token of this Spotify token utility class.
     */
    public void setSpotifyToken() {
        MongoCollection<Document> collection;
        Bson filter;
        String fieldName = "authorization-code";
        Bson update;
        ConnectToDatabase newConnection = new ConnectToDatabase();
        MongoDatabase database = newConnection.connect();

        collection = database.getCollection("users");

        filter = Filters.regex("username", username);

        update = Updates.set(fieldName, spotifyToken);

        collection.updateOne(filter, update);
    } //setSpotifyToken
}