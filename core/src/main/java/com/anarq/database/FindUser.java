package com.anarq.database;

import com.anarq.core.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.types.Binary;
import org.bson.Document;

/**
 * A utility class for finding a user in the database of the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class FindUser {
    /**
     * The text of this delete user utility class.
     */
    String text;

    /**
     * The field of this delete user utility class.
     */
    String field;

    /**
     * Constructs a newly allocated {@code FindUser} object with the specified text.
     *
     * @param text the text to be used in construction
     */
    public FindUser(String text) {
        this.text = text;
        field = "username";
    } //FindUser

    /**
     * Constructs a newly allocated {@code FindUser} object with the specified field and text.
     *
     * @param field the field to be used in construction
     * @param text the text to be used in construction
     */
    public FindUser(String field, String text) {
        this.field = field;
        this.text = text;
    } //FindUser

    /**
     * Attempts to find a jammer in the database.
     *
     * @return the result of attempting to find a jammer in the database
     */
    public Document find() {
        MongoDatabase database = ConnectToDatabase.getDatabaseConnection();
        MongoCollection<Document> jammerCollection = database.getCollection("users");
        BsonString searchString = new BsonString(text);
        BsonDocument searchDocument = new BsonDocument(field, searchString);

        if (jammerCollection.find(searchDocument) == null) {
            return null;
        } else {
            for (Document document : jammerCollection.find(searchDocument)) {
                return document;
            } //end for
        } //end if

        return null;
    } //find

    /**
     * Attempts to find some account information in the database.
     *
     * @return the result of attempting to find some account information in the database
     */
	public AccountInfo findAccountInfo() {
        Document rawDocument = find();
		Binary image = ((Binary) rawDocument.get("picture-bytes"));
		byte[] imageData = new byte[64];

		if (image != null) {
			imageData = image.getData();
		} //end if
		
		String bio = (String) rawDocument.get("bio");

		if (bio == null) {
			bio = "No Bio";
		} //end if

		return new AccountInfo((String) rawDocument.get("username"), (String) rawDocument.get("first-name"),
                               (String) rawDocument.get("last-name"), (String) rawDocument.get("email"), bio,
                               imageData);
    } //findAccountInfo

    /**
     * Attempts to login using the specified password.
     *
     * @param password the password to be used in the operation
     * @return the result of attempting to login
     */
	public Document attemptLogin(String password) {
		Document userInfo = find();
		
		if (userInfo == null) {
			System.out.println("User Not Found!!!!!");

			return null;
		} //end if

		if (userInfo.get("password").equals(password)) {
			return userInfo;
		} //end if
		
		return null;
	} //attemptLogin

    /**
     * Attempts to find a host in the database.
     *
     * @return the result of attempting to find a host in the database
     */
    public Document findHost() {
        ConnectToDatabase newConnection = new ConnectToDatabase();
        MongoDatabase database = newConnection.connect();
        MongoCollection<Document> hostCollection = database.getCollection("hosts-list");
        BsonString searchString = new BsonString(text);
        BsonDocument searchDocument = new BsonDocument(field, searchString);

        if (hostCollection.find(searchDocument) == null) {
            return null;
        } else {
            for (Document document : hostCollection.find(searchDocument)) {
                return document;
            } //end for
        } //end if

        return null;
    } //findHost
}