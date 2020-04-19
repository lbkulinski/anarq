package com.anarq.update.disable;

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
import com.anarq.update.UserInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.anarq.update.ValidationUtils;

/**
 * A controller for disabling a user's account.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version April 19, 2020
 */
@Controller
public final class DisableAccountController {
    /**
     * Returns whether or not the account of the user with the specified username is already disabled.
     *
     * @param username the username to be used in the operation
     * @return {@code true}, if the account of the user with the specified username is already disabled and
     * {@code false} otherwise
     * @throws NullPointerException if the specified username is {@code null}
     */
    private boolean alreadyDisabled(String username) {
        String databaseUsername;
        String databasePassword;
        String format = "mongodb+srv://%s:%s@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";
        String uri;
        MongoClient client;
        String databaseName = "user-database";
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

        databaseUsername = System.getProperty("database-username");

        databasePassword = System.getProperty("database-password");

        uri = String.format(format, databaseUsername, databasePassword);

        client = MongoClients.create(uri);

        userDatabase = client.getDatabase(databaseName);

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

        client.close();

        return !currentEnabled;
    } //alreadyDisabled

    /**
     * Attempts to disable the account of the user with the specified username.
     *
     * @param username the username to be used in the operation
     * @return {@code true}, if the user's account was successfully disabled and {@code false} otherwise
     */
    private boolean disableAccount(String username) {
        String databaseUsername;
        String databasePassword;
        String format = "mongodb+srv://%s:%s@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";
        String uri;
        MongoClient client;
        String databaseName = "user-database";
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

        databaseUsername = System.getProperty("database-username");

        databasePassword = System.getProperty("database-password");

        uri = String.format(format, databaseUsername, databasePassword);

        client = MongoClients.create(uri);

        userDatabase = client.getDatabase(databaseName);

        collection = userDatabase.getCollection(collectionName);

        regexString = String.format("^%s$", username);

        regex = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);

        filter = Filters.regex(usernameFieldName, regex);

        update = Updates.set(enabledFieldName, false);

        result = collection.updateOne(filter, update);

        client.close();

        return result.getModifiedCount() == 1;
    } //disableAccount

    /**
     * Displays the form for disabling a user's account and collects the user's input.
     *
     * @param model the model to be used in the operation
     * @return the HTML code for the account disabling form
     */
    @GetMapping("/disableAccount")
    public String disableAccountForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "disableAccountForm";
    } //disableAccountForm

    /**
     * Displays the result after attempting to disable the user's account.
     *
     * @param userInformation the user information to be used in the operation
     * @return the HTML code for the account disabling result
     */
    @PostMapping("/disableAccount")
    public String disableAccountSubmit(@ModelAttribute UserInformation userInformation) {
        String username = userInformation.getUsername();
        String password = userInformation.getPassword();

        if (!ValidationUtils.userPresent(username)) {
            return "disableAccountUserNotFoundResult";
        } else if (!ValidationUtils.passwordCorrect(username, password)) {
            return "disableAccountIncorrectPasswordResult";
        } else if (this.alreadyDisabled(username)) {
            return "disableAccountNoChangeResult";
        } else {
            boolean success = this.disableAccount(username);

            return success ? "disableAccountSuccessResult" : "disableAccountFailureResult";
        } //end if
    } //disableAccountSubmit
}