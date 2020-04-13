package com.anarq.database;

import com.anarq.core.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.types.Binary;
import org.bson.Document;

public class FindUser {

    String text;
    String field;

    public FindUser(String text) {
        this.text = text;
        field = "username";
    }
    public FindUser(String field, String text) {
        this.field = field;
        this.text = text;
    }

    public Document find() {

        MongoDatabase database = ConnectToDatabase.getDatabaseConnection();
        MongoCollection<Document> jammerCollection = database.getCollection("users");

        BsonString searchString = new BsonString(text);
        BsonDocument searchDocument = new BsonDocument(field, searchString);

        if (jammerCollection.find(searchDocument) == null) {
            return null;
        }
        else {
            for (Document document : jammerCollection.find(searchDocument)) {
                return document;
            }
        }
        return null;
    }
	
	public AccountInfo findAccountInfo() {

        Document rawDocument = find();
		
		Binary image = ((Binary) rawDocument.get("picture-bytes"));
		byte[] imageData = new byte[64];
		if (image != null) {
			imageData = image.getData();
		}
		
		String bio = (String) rawDocument.get("bio");
		
		if (bio == null) {
			bio = "No Bio";
		}
		
		return new AccountInfo(
		(String) rawDocument.get("username"),
		(String) rawDocument.get("first-name"),
		(String) rawDocument.get("last-name"),
		(String) rawDocument.get("email"),
		bio,
		imageData
		);
		
    }
	
	public Document attemptLogin(String password) {
		
		Document userInfo = find();
		
		if (userInfo == null) {
			System.out.println("User Not Found!!!!!");
			return null;
		}
		
		if (((String) (userInfo.get("password"))).equals(password)) {
			return userInfo;
		}
		
		return null;
		
	}

    public Document findHost() {

        ConnectToDatabase newConnection = new ConnectToDatabase();
        MongoDatabase database = newConnection.connect();
        MongoCollection<Document> hostCollection = database.getCollection("hosts-list");

        BsonString searchString = new BsonString(text);
        BsonDocument searchDocument = new BsonDocument(field, searchString);
        //System.out.println("Matches = " + jammerCollection.countDocuments(searchDocument));

        if (hostCollection.find(searchDocument) == null) {
            return null;
        }
        else {
            for (Document document : hostCollection.find(searchDocument)) {
                return document;
            }
        }
        return null;
    }

}
