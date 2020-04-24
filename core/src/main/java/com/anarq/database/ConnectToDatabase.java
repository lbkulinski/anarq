package com.anarq.database;

import com.mongodb.client.*;

/**
 * A utility class for connecting to the database of the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class ConnectToDatabase {
    /**
     * The URL used to connect to the database.
     */
    static final String url = "mongodb+srv://anarq:anarq@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";

    /**
     * The name of the database.
     */
    static final String databaseName = "user-database";

    /**
     * The connection to the database.
     */
    static MongoDatabase databaseConnection;

    /**
     * Returns the connection to the database.
     *
     * @return the connection to the database
     */
	public static MongoDatabase getDatabaseConnection() {
		if (databaseConnection == null) {
			connect();
		} //end if

		return databaseConnection;
	} //getDatabaseConnection

    /**
     * Returns the connection to the database.
     *
     * @return the connection to the database
     */
    public static MongoDatabase connect() {
        try {
            MongoClient mongoClient = MongoClients.create(url);

            MongoDatabase database = mongoClient.getDatabase(databaseName);

			databaseConnection = database;

            return database;
        } catch (Exception e) {
            System.out.println("\n\nCould not connect to MongoDB Atlas\n\n");

            System.exit(0);
        } //end try catch

        return null;
    } //connect

    /**
     * Returns the connection to the database.
     *
     * @return the connection to the database
     */
	public static MongoDatabase connectToDatabase() {
        try {
            MongoClient mongoClient = MongoClients.create(url);

            return mongoClient.getDatabase(databaseName);
        } catch (Exception e) {
            System.out.println("\n\nCould not connect to MongoDB Atlas\n\n");

            System.exit(0);
        } //end try catch

        return null;
    } //connectToDatabase
}