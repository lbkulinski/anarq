package com.anarq.database;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

public class ConnectToDatabase {

    static final String url = "mongodb+srv://anarq:anarq@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";
    static final String databaseName = "user-database";
	static MongoDatabase databaseConnection;

	public static MongoDatabase getDatabaseConnection() {
		
		if (databaseConnection == null) {
			connect();
		}
		
		return databaseConnection;
		
	}

    public static MongoDatabase connect() {
        try {
            MongoClientURI uri = new MongoClientURI(url);
            MongoClient mongoClient = new MongoClient(uri);
            MongoDatabase database = mongoClient.getDatabase(databaseName);
			databaseConnection = database;
            return database;
        } catch (Exception e) {
            System.out.println("\n\nCould not connect to MongoDB Atlas\n\n");
            System.exit(0);
        }
        return null;
    }
	
	public static MongoDatabase connectToDatabase() {
        try {
            MongoClientURI uri = new MongoClientURI(url);
            MongoClient mongoClient = new MongoClient(uri);
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            return database;
        } catch (Exception e) {
            System.out.println("\n\nCould not connect to MongoDB Atlas\n\n");
            System.exit(0);
        }
        return null;
    }
	
}
