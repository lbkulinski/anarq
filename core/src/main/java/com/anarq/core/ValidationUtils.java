package com.anarq.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.regex.Pattern;
import org.bson.conversions.Bson;
import com.mongodb.client.FindIterable;
import java.util.Objects;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * A set of validation utility methods used by account operations.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version March 29, 2020
 */
public final class ValidationUtils {
    /**
     * Constructs a newly allocated {@code ValidationUtils} objects.
     *
     * @throws UnsupportedOperationException if an {@code ValidationUtils} object is attempting to be created
     */
    private ValidationUtils() {
        throw new UnsupportedOperationException("an ValidationUtils object is attempting to be created");
    } //UpdateUtils

    /**
     * Returns whether or not a user with the specified username is present in the database.
     *
     * @param username the username to be used in the operation
     * @return {@code true}, if a user with the specified username and is present in the database and {@code false}
     * otherwise
     * @throws NullPointerException if the specified username is {@code null}
     */
    public static boolean userPresent(String username) {
        String format = "mongodb+srv://%s:%s@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";
        String databaseUsername;
        String databasePassword;
        String uri;
        MongoClient client;
        String databaseName = "user-database";
        MongoDatabase userDatabase;
        String collectionName = "users";
        MongoCollection<Document> collection;
        String regexString;
        Pattern regex;
        Bson filter;
        String fieldName = "username";
        FindIterable<Document> results;
        boolean present;

        Objects.requireNonNull(username, "the specified username is null");

        databaseUsername = System.getProperty("database-username");

        databasePassword = System.getProperty("database-password");

        uri = String.format(format, databaseUsername, databasePassword);

        client = MongoClients.create(uri);

        userDatabase = client.getDatabase(databaseName);

        collection = userDatabase.getCollection(collectionName);

        regexString = String.format("^%s$", username);

        regex = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);

        filter = Filters.regex(fieldName, regex);

        results = collection.find(filter);

        present = results.first() != null;

        client.close();

        return present;
    } //presentInDatabase

    /**
     * Returns whether or not a user with the specified username entered the correct password.
     *
     * @param username the username to be used in the operation
     * @param password the password to be used in the operation
     * @return {@code true}, if a user with the specified username entered the correct password and {@code false}
     * otherwise
     * @throws NullPointerException if the specified username or password is {@code null}
     */
    public static boolean passwordCorrect(String username, String password) {
        String format = "mongodb+srv://%s:%s@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";
        String databaseUsername;
        String databasePassword;
        String uri;
        MongoClient client;
        String databaseName = "user-database";
        MongoDatabase userDatabase;
        String collectionName = "users";
        MongoCollection<Document> collection;
        String regexString;
        Pattern regex;
        Bson filter;
        String fieldName = "username";
        FindIterable<Document> results;
        Document result;
        String readPasswordHash;

        Objects.requireNonNull(username, "the specified username is null");

        Objects.requireNonNull(password, "the specified password is null");

        databaseUsername = System.getProperty("database-username");

        databasePassword = System.getProperty("database-password");

        uri = String.format(format, databaseUsername, databasePassword);

        client = MongoClients.create(uri);

        userDatabase = client.getDatabase(databaseName);

        collection = userDatabase.getCollection(collectionName);

        regexString = String.format("^%s$", username);

        regex = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);

        filter = Filters.regex(fieldName, regex);

        results = collection.find(filter);

        result = results.first();

        if (result == null) {
            throw new IllegalStateException("the user with the specified username is not present in the database");
        } //end if

        readPasswordHash = result.get("password-hash", String.class);

        client.close();

        return BCrypt.checkpw(password, readPasswordHash);
    } //correctPassword
}