package com.anarq.usernamevalidation;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;

/* 
	CreateUser
		Master class for handling User Creation for database activities.
	
	Author(s):
		Siddarth
*/
public class CreateUser {

    protected String firstName;
    protected String lastName;

    protected String username;
    protected int birthDay;
    protected int birthMonth;
    protected int birthYear;

    public CreateUser(String firstName, String lastName, String username, int birthDay, int birthMonth, int birthYear) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;

    }


    /**
     * Method to add a user to the database
     * @return 1 if the user was successfully added or 0 if the user was not added to the database
     */
    public int addUser() {
		
        try {
            MongoClient mongoClient = new MongoClient();

            DBObject newUser = new BasicDBObject("username", username)
                    .append("First name", firstName)
                    .append("Last name", lastName)

                    .append("DOB", new BasicDBObject("Day", birthDay)
                            .append("Month", birthMonth)
                            .append("Year", birthYear));


            DB userDatabase = mongoClient.getDB("Insert name of  DB here");
            DBCollection collection = userDatabase.getCollection("Insert name of Collections here");
			collection.insert(newUser);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }
}
