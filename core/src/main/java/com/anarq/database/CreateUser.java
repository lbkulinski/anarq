package com.anarq.database;

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
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CreateUser {

    /**/

    protected String firstName;
    protected String lastName;
    protected String password;
	protected String email;

    protected String username;
    protected int birthDay;
    protected int birthMonth;
    protected int birthYear;

    ConnectToDatabase newConnection;
    MongoDatabase database;
    Map<String, Object> userDetails;

    public CreateUser(String username, String password, String firstName, String lastName, String email, int birthDay, int birthMonth, int birthYear) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
		this.email = email;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;

        database = ConnectToDatabase.getDatabaseConnection();
		
    }

    /**
     * Method to add a user to the database
     * @return The result code from the sign up process
     */
    public String addJammer() {
		
		try {
		
        UsernameValidation usernameCriteria = new UsernameValidation(username);
        PasswordValidation passwordCriteria = new PasswordValidation(password);

		String usernameValidation = usernameCriteria.validateUsername();
		String passwordValidation = passwordCriteria.validatePassword();

        if (usernameValidation.equals("Username Taken")) {
			
			 String [] altUsernames = new String[3];
            for (int i = 0; i < 3; i++) {
                int randomNumber = Integer.parseInt(getRandomAlternativeString());
                altUsernames[i] = username + randomNumber;
            }
			String output = "";
            output = output + (username + " is Already Taken. Try: ");
            for (int i = 0; i < 3; i++) {
                output = output + (altUsernames[i] + " ");
            }
            return output;
		}
		if (!usernameValidation.equals("Username Ok")) {
			
			return usernameValidation;
			
		}
		
		if (!passwordValidation.equals("Password Ok")) {
			
			return passwordValidation;
			
		}
		
		userDetails = Map.of("user-id", "ID" + String.format("%X", (int) (Math.random() * 999999.0f)),
				"username", username,
				"password", password,
				"first-name", firstName,
				"last-name", lastName,
				"email", email,
				"birth-day", birthDay,
				"birth-month", birthMonth,
				"birth-year", birthYear);

		MongoCollection<Document> jammerCollection = database.getCollection("users");
		Document user = new Document(userDetails);
		jammerCollection.insertOne(user);
		System.out.println("New User has Signed up!");
		
		} catch (Exception e) {
			
			e.printStackTrace();
			return "Fatal Error!";
			
		}
		
		return "Sign Up Success!";
 
    }

    public int addHost() {

        int roomCode = Integer.parseInt(getRandomNumberString());
        userDetails = Map.of("username", username,
				"password", password,
				"first-name", firstName,
				"last-name", lastName,
				"email", email,
				"birth-day", birthDay,
				"birth-month", birthMonth,
				"birth-year", birthYear);

        MongoCollection<Document> hostCollection = database.getCollection("hosts-list");
        Document user = new Document(userDetails);
        hostCollection.insertOne(user);
        System.out.println("Added user and room code = " + roomCode);
        return 1;
    }

    // code from https://stackoverflow.com/questions/51322750/generate-6-digit-random-number
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public static String getRandomAlternativeString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999);
        // this will convert any number sequence into 6 character.
        return String.format("%03d", number);
    }

}
