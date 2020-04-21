package com.anarq.core;

import com.anarq.database.*;

import org.springframework.stereotype.Controller;
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
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.model.Updates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * A controller for enabling a user's account.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version April 19, 2020
 */
@Controller
public final class EnableAccountController {
    /**
     * Returns whether or not the account of the user with the specified username is already enabled.
     *
     * @param username the username to be used in the operation
     * @return {@code true}, if the account of the user with the specified username is already enabled and
     * {@code false} otherwise
     * @throws NullPointerException if the specified username is {@code null}
     */
    private boolean alreadyEnabled(String username) {
        MongoDatabase userDatabase;
        String collectionName = "users";
        MongoCollection<Document> collection;
        String regexString;
        Pattern regex;
        Bson filter;
        String usernameFieldName = "username";
        FindIterable<Document> results;
        Document result;
        boolean currentEnabled;
        String enabledFieldName = "enabled";

        Objects.requireNonNull(username, "the specified username is null");

        userDatabase = ConnectToDatabase.getDatabaseConnection();

        collection = userDatabase.getCollection(collectionName);

        regexString = String.format("^%s$", username);

        regex = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);

        filter = Filters.regex(usernameFieldName, regex);

        results = collection.find(filter);

        result = results.first();

        if (result == null) {
            throw new IllegalStateException("the user with the specified username is not present in the database");
        } //end if

        currentEnabled = result.getBoolean(enabledFieldName, false);

        return currentEnabled;
    } //alreadyEnabled

    /**
     * Attempts to enable the account of the user with the specified username.
     *
     * @param username the username to be used in the operation
     * @return {@code true}, if the user's account was successfully enabled and {@code false} otherwise
     */
    private boolean enableAccount(String username) {
        MongoDatabase userDatabase;
        String collectionName = "users";
        MongoCollection<Document> collection;
        String regexString;
        Pattern regex;
        Bson filter;
        String usernameFieldName = "username";
        String enabledFieldName = "enabled";
        Bson update;
        UpdateResult result;

        Objects.requireNonNull(username, "the specified username is null");

        userDatabase = ConnectToDatabase.getDatabaseConnection();

        collection = userDatabase.getCollection(collectionName);

        regexString = String.format("^%s$", username);

        regex = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);

        filter = Filters.regex(usernameFieldName, regex);

        update = Updates.set(enabledFieldName, true);

        result = collection.updateOne(filter, update);

        return result.getModifiedCount() == 1;
    } //enableAccount

    /**
     * Displays the form for enabling a user's account and collects the user's input.
     *
     * @param model the model to be used in the operation
     * @return the HTML code for the account enabling form
     */
    @GetMapping("/enableAccount")
    public String enableAccountForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "enableAccountForm";
    } //enableAccountForm

    /**
     * Displays the result after attempting to enable the user's account.
     *
     * @param userInformation the user information to be used in the operation
     * @return the HTML code for the account enabling result
     */
    @PostMapping("/enableAccount")
    public String enableAccountSubmit(@ModelAttribute UserInformation userInformation) {
        String username = userInformation.getUsername();
        String password = userInformation.getPassword();

        if (!ValidationUtils.userPresent(username)) {
            return "enableAccountUserNotFoundResult";
        } else if (!ValidationUtils.passwordCorrect(username, password)) {
            return "enableAccountIncorrectPasswordResult";
        } else if (this.alreadyEnabled(username)) {
            return "enableAccountNoChangeResult";
        } else {
            boolean success = this.enableAccount(username);

            return success ? "enableAccountSuccessResult" : "enableAccountFailureResult";
        } //end if
    } //enableAccountSubmit
}