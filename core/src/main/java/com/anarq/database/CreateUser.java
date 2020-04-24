package com.anarq.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Map;
import java.util.Random;

/**
 * A utility class for adding a new user to the database of the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class CreateUser {
    /**
     * The first name of this create user utility class.
     */
    protected String firstName;

    /**
     * The last name of this create user utility class.
     */
    protected String lastName;

    /**
     * The password of this create user utility class.
     */
    protected String password;

    /**
     * The email of this create user utility class.
     */
    protected String email;

    /**
     * The username of this create user utility class.
     */
    protected String username;

    /**
     * The birth day of this create user utility class.
     */
    protected int birthDay;

    /**
     * The birth month of this create user utility class.
     */
    protected int birthMonth;

    /**
     * The birth year of this create user utility class.
     */
    protected int birthYear;

    /**
     * The enabled status of this create user utility class.
     */
    protected boolean enabled;

    /**
     * The database connection of this create user utility class.
     */
    MongoDatabase database;

    /**
     * The user details of this create user utility class.
     */
    Map<String, Object> userDetails;

    /**
     * Constructs a newly allocated {@code CreateUser} object with the specified information.
     *
     * @param username the username to be used in construction
     * @param password the password to be used in construction
     * @param firstName the first name to be used in construction
     * @param lastName the last name to be used in construction
     * @param email the email to be used in construction
     * @param birthDay the birth day to be used in construction
     * @param birthMonth the birth month to be used in construction
     * @param birthYear the birth year to be used in construction
     */
    public CreateUser(String username, String password, String firstName, String lastName, String email, int birthDay,
                      int birthMonth, int birthYear) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        this.enabled = true;

        database = ConnectToDatabase.getDatabaseConnection();
    } //CreateUser

    /**
     * Attempts to add a new jammer to the database.
     *
     * @return the result of attempting to add a new jammer to the database
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
                } //end for

                StringBuilder output = new StringBuilder();

                output.append(username).append(" is Already Taken. Try: ");

                for (int i = 0; i < 3; i++) {
                    output.append(altUsernames[i]).append(" ");
                } //end for

                return output.toString();
            } //end if

            if (!usernameValidation.equals("Username Ok")) {
                return usernameValidation;
            } //end if

            if (!passwordValidation.equals("Password Ok")) {
                return passwordValidation;
            } //end if

            userDetails = Map.of("user-id", "ID" + String.format("%X", (int) (Math.random() * 999999.0f)),
                                 "username", username,
                                 "password", password,
                                 "first-name", firstName,
                                 "last-name", lastName,
                                 "email", email,
                                 "birth-day", birthDay,
                                 "birth-month", birthMonth,
                                 "birth-year", birthYear,
                                 "enabled", enabled);

            MongoCollection<Document> jammerCollection = database.getCollection("users");

            Document user = new Document(userDetails);

            jammerCollection.insertOne(user);

            System.out.println("New User has Signed up!");
        } catch (Exception e) {
            e.printStackTrace();

            return "Fatal Error!";
        } //end try catch

        return "Sign Up Success!";
    } //addJammer

    /**
     * Attempts to add a new host to the database.
     *
     * @return the result of attempting to add a new host to the database
     */
    public int addHost() {
        int roomCode = Integer.parseInt(getRandomNumberString());

        userDetails = Map.of("username", username,
                             "password", password,
                             "first-name", firstName,
                             "last-name", lastName,
                             "email", email,
                             "birth-day", birthDay,
                             "birth-month", birthMonth,
                             "birth-year", birthYear,
                             "enabled", enabled);

        MongoCollection<Document> hostCollection = database.getCollection("hosts-list");

        Document user = new Document(userDetails);

        hostCollection.insertOne(user);

        System.out.println("Added user and room code = " + roomCode);

        return 1;
    } //addHost

    /**
     * Returns a random number {@code String} that contains six digits.
     *
     * @return a random number {@code String} that contains six digits
     */
    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%06d", number);
    } //getRandomNumberString

    /**
     * Returns a random alternative {@code String} that contains six digits.
     *
     * @return a random alternative {@code String} that contains six digits
     */
    public static String getRandomAlternativeString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999);

        return String.format("%03d", number);
    } //getRandomAlternativeString
}