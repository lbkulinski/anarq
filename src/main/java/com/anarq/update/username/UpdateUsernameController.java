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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.anarq.update.UserInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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

        if (!presentInDatabase(userType, username)) {
            return "updateUsernameNotFoundResult";
        } //end if

        return "updateUsernameResult";
    } //updatePasswordSubmit
}
