package com.anarq.update.username;

import com.anarq.update.UserType;
import org.springframework.stereotype.Controller;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.Objects;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import com.mongodb.client.FindIterable;
import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;
import org.bson.types.Binary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.anarq.update.UserInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.Arrays;

/**
 * A controller for updating a user's username.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version March 8, 2020
 */
@Controller
public final class UpdateUsernameController {
    /**
     * Returns whether or not a user with the specified username and user type is present in the database.
     *
     * @param userType the user type to be used in the operation
     * @param username the username to be used in the operation
     * @return {@code true}, if a user with the specified username and user type is present in the database and
     * {@code false} otherwise
     */
    private boolean presentInDatabase(UserType userType, String username) {
        final String format = "mongodb+srv://%s:%s@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";
        String databaseUsername;
        String databasePassword;
        String uri;
        MongoClient client;
        final String databaseName = "user-database";
        MongoDatabase userDatabase;
        String collectionName;
        MongoCollection<Document> collection;
        Bson filter;
        final String fieldName = "username";
        FindIterable<Document> results;
        boolean present;

        Objects.requireNonNull(userType, "the specified user type is null");

        Objects.requireNonNull(username, "the specified username is null");

        databaseUsername = System.getProperty("database-username");

        databasePassword = System.getProperty("database-password");

        uri = String.format(format, databaseUsername, databasePassword);

        client = MongoClients.create(uri);

        userDatabase = client.getDatabase(databaseName);

        switch (userType) {
            case DJ:
                collectionName = "djs";
                break;
            case JAMMER:
                collectionName = "jammers";
                break;
            default:
                throw new IllegalStateException(String.format("unexpected user type: %s", userType));
        } //end switch

        collection = userDatabase.getCollection(collectionName);

        filter = Filters.eq(fieldName, username);

        results = collection.find(filter);

        present = results.first() != null;

        client.close();

        return present;
    } //presentInDatabase

    /**
     * Hashes the specified password.
     *
     * @param salt the salt to be used in the operation
     * @param password the password to be used in the operation
     * @return the password hash and salt
     * @throws NullPointerException if the specified salt or password is {@code null}
     * @throws Exception if an exception occurs during the hashing computation
     */
    private byte[] hash(byte[] salt, String password) throws Exception {
        SecureRandom random;
        SecretKeyFactory factory;
        final String algorithm = "PBKDF2WithHmacSHA1";
        PBEKeySpec keySpec;
        final int iterationCount = 65_535;
        final int keyLength = 256;
        SecretKey secretKey;
        byte[] passwordHash;

        Objects.requireNonNull(salt, "the specified salt is null");

        Objects.requireNonNull(password, "the specified password is null");

        random = new SecureRandom();

        random.nextBytes(salt);

        factory = SecretKeyFactory.getInstance(algorithm);

        keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);

        secretKey = factory.generateSecret(keySpec);

        passwordHash = secretKey.getEncoded();

        return passwordHash;
    } //hash

    /**
     * Returns whether or not a user with the specified username entered the correct password.
     *
     * @param userType the user type to be used in the operation
     * @param username the username to be used in the operation
     * @param password the password to be used in the operation
     * @return {@code true}, if a user with the specified username entered the correct password and {@code false}
     * otherwise
     */
    private boolean correctPassword(UserType userType, String username, String password) {
        final String format = "mongodb+srv://%s:%s@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";
        String databaseUsername;
        String databasePassword;
        String uri;
        MongoClient client;
        final String databaseName = "user-database";
        MongoDatabase userDatabase;
        String collectionName;
        MongoCollection<Document> collection;
        Bson filter;
        final String fieldName = "username";
        FindIterable<Document> results;
        Document result;
        Binary readPasswordHashBinary;
        Binary readSaltBinary;
        byte[] readPasswordHash;
        byte[] readSalt;
        byte[] passwordHash;

        Objects.requireNonNull(userType, "the specified user type is null");

        Objects.requireNonNull(username, "the specified username is null");

        Objects.requireNonNull(password, "the specified password is null");

        databaseUsername = System.getProperty("database-username");

        databasePassword = System.getProperty("database-password");

        uri = String.format(format, databaseUsername, databasePassword);

        client = MongoClients.create(uri);

        userDatabase = client.getDatabase(databaseName);

        switch (userType) {
            case DJ:
                collectionName = "djs";
                break;
            case JAMMER:
                collectionName = "jammers";
                break;
            default:
                throw new IllegalStateException(String.format("unexpected user type: %s", userType));
        } //end switch

        collection = userDatabase.getCollection(collectionName);

        filter = Filters.eq(fieldName, username);

        results = collection.find(filter);

        result = results.first();

        if (result == null) {
            throw new IllegalStateException("the user with the specified username is not present in the database");
        } //end if

        readPasswordHashBinary = result.get("password-hash", Binary.class);

        readSaltBinary = result.get("salt", Binary.class);

        readPasswordHash = readPasswordHashBinary.getData();

        readSalt = readSaltBinary.getData();

        client.close();

        try {
            passwordHash = hash(readSalt, password);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } //end try catch

        System.out.println(Arrays.toString(readPasswordHash));

        System.out.println(Arrays.toString(passwordHash));

        return Arrays.equals(passwordHash, readPasswordHash);
    } //correctPassword

    /**
     * Displays the form for updating a user's username and collects the user's input.
     *
     * @param model the model to be used in the operation
     * @return the HTML code for the username update form
     */
    @GetMapping("/updateUsername")
    public String updateUsernameForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "updateUsername";
    } //updatePasswordForm

    /**
     * Displays the result after attempting to update the user's username.
     *
     * @param userInformation the user information to be used in the operation
     * @return the HTML code for the username update result
     */
    @PostMapping("/updateUsername")
    public String updateUsernameSubmit(@ModelAttribute UserInformation userInformation) {
        UserType userType = userInformation.getUserType();
        String username = userInformation.getUsername();
        String password = userInformation.getPassword();

        if (!presentInDatabase(userType, username)) {
            return "updateUsernameNotFoundResult";
        } else if (!correctPassword(userType, username, password)) {
            return "updateUsernameIncorrectPasswordResult";
        } //end if

        return "updateUsernameResult";
    } //updatePasswordSubmit
}
