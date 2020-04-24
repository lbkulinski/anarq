package com.anarq.core;

import com.anarq.database.*;

import org.springframework.stereotype.Controller;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.regex.Pattern;
import org.bson.conversions.Bson;
import com.mongodb.client.FindIterable;
import java.util.Objects;
import com.mongodb.client.model.Filters;
import java.io.UncheckedIOException;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.model.Updates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * A controller for updating a user's biography.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version April 23, 2020
 */
@Controller
public final class UpdateBioController {
    /**
     * Returns whether or not the specified bio is the same as the bio of the user with the specified username.
     *
     * @param username the username to be used in the operation
     * @param bio the bio to be used in the operation
     * @return {@code true}, if the specified bio is the same as the bio of the user with the specified username and
     * {@code false} otherwise
     * @throws NullPointerException if the specified username or bio is {@code null}
     */
    private boolean bioSame(String username, String bio) {
        MongoDatabase userDatabase;
        String collectionName = "users";
        MongoCollection<Document> collection;
        String regexString;
        Pattern regex;
        Bson filter;
        String usernameFieldName = "username";
        FindIterable<Document> results;
        Document result;
        String currentBio;
        String bioFieldName = "bio";
        boolean same;

        Objects.requireNonNull(username, "the specified username is null");

        Objects.requireNonNull(bio, "the specified bio is null");

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

        currentBio = result.getString(bioFieldName);

        same = Objects.equals(bio, currentBio);

        //client.close();

        return same;
    } //bioSame

    /**
     * Returns whether or not the specified bio is valid. A bio is valid if it does not contain any "bad words".
     *
     * @param bio the bio to be used in the operation
     * @return {@code true}, if the specified bio is valid and {@code false} otherwise
     * @throws NullPointerException if the specified bio is {@code null}
     * @throws UncheckedIOException if an I/O error occurs while trying to read the file containing the bad words
     */
    private boolean bioValid(String bio) {
        Objects.requireNonNull(bio, "the specified bio is null");

        bio = bio.toLowerCase();

        return !NaughtyWords.isANaughtyWord(bio);
    } //bioValid

    /**
     * Attempts to update the bio of the user with the specified username with the specified bio.
     *
     * @param username the username to be used in the operation
     * @param bio the bio to be used in the operation
     * @return {@code true}, if the user's bio was successfully updated and {@code false} otherwise
     * @throws NullPointerException if the specified username, or bio is {@code null}
     */
    private boolean updateBio(String username, String bio) {
        MongoDatabase userDatabase;
        String collectionName = "users";
        MongoCollection<Document> collection;
        String regexString;
        Pattern regex;
        Bson filter;
        String usernameFieldName = "username";
        Bson update;
        String bioFieldName = "bio";
        UpdateResult result;

        Objects.requireNonNull(username, "the specified username is null");

        Objects.requireNonNull(bio, "the specified bio is null");

        userDatabase = ConnectToDatabase.getDatabaseConnection();

        collection = userDatabase.getCollection(collectionName);

        regexString = String.format("^%s$", username);

        regex = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);

        filter = Filters.regex(usernameFieldName, regex);

        update = Updates.set(bioFieldName, bio);

        result = collection.updateOne(filter, update);

        return result.getModifiedCount() == 1;
    } //updateBio

    /**
     * Displays the form for updating a user's bio and collects the user's input.
     *
     * @param model the model to be used in the operation
     * @return the HTML code for the bio update form
     */
    @GetMapping("/updateBio")
    public String updateBioForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "updateBioForm";
    } //updateBioForm

    /**
     * Displays the result after attempting to update the user's bio.
     *
     * @param userInformation the user information to be used in the operation
     * @return the HTML code for the bio update result
     */
    @PostMapping("/updateBio")
    public String updateBioSubmit(@ModelAttribute UserInformation userInformation) {
        String username = userInformation.getUsername();
        String password = userInformation.getPassword();
        String bio = userInformation.getNewValue();

        if (!ValidationUtils.userPresent(username)) {
            return "updateBioUserNotFoundResult";
        } else if (!ValidationUtils.passwordCorrect(username, password)) {
            return "updateBioIncorrectPasswordResult";
        } else if (this.bioSame(username, bio)) {
            return "updateBioNoChangeResult";
        } else if (!this.bioValid(bio)) {
            return "updateBioInvalidBioResult";
        } else {
            boolean success = this.updateBio(username, bio);

            return success ? "updateBioSuccessResult" : "updateBioFailureResult";
        } //end if
    } //updateBioSubmit
}