package com.anarq.core;

import com.anarq.database.*;

import org.springframework.stereotype.Controller;
import java.util.Objects;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.regex.Pattern;
import org.bson.conversions.Bson;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import org.springframework.security.crypto.bcrypt.BCrypt;
import com.mongodb.client.model.Updates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * A controller for updating a user's password.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version March 29, 2020
 */
@Controller
public class UpdatePasswordController {
    /**
     * Returns whether or not the specified new password is valid. A valid password contains one or more digits,
     * lowercase letters, uppercase letters, and special characters (@, $, #, &).
     *
     * @param newPassword the new password to be used in the operation
     * @return {@code true}, if the specified new password is valid and {@code false} otherwise
     */
    private boolean newPasswordValid(String newPassword) {
        boolean valid;
        int minLength = 8;

        Objects.requireNonNull(newPassword, "the specified new password is null");

        valid = newPassword.length() >= minLength;

        valid &= newPassword.matches("(.)*(\\d)+(.)*");

        valid &= newPassword.matches("(.)*[a-z]+(.)*");

        valid &= newPassword.matches("(.)*[A-Z]+(.)*");

        valid &= newPassword.matches("(.)*[@$#&]+(.)*");

        return valid;
    } //newPasswordValid

    /**
     * Attempts to update the password of the user with the specified username with the specified new password.
     *
     * @param username the username to be used in the operation
     * @param newPassword the new password to be used in the operation
     * @return {@code true}, if the user's password was successfully updated and {@code false} otherwise
     */
    private boolean updatePassword(String username, String newPassword) {
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
        String passwordHash;
        String passwordHashFieldName = "password-hash";
        Bson update;
        UpdateResult result;

        Objects.requireNonNull(username, "the specified username is null");

        Objects.requireNonNull(newPassword, "the specified new password is null");

        /*databaseUsername = System.getProperty("database-username");

        databasePassword = System.getProperty("database-password");

        uri = String.format(format, databaseUsername, databasePassword);

        client = MongoClients.create(uri);*/

        userDatabase = ConnectToDatabase.getDatabaseConnection();

        collection = userDatabase.getCollection(collectionName);

        regexString = String.format("^%s$", username);

        regex = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);

        filter = Filters.regex(usernameFieldName, regex);

        passwordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        update = Updates.set(passwordHashFieldName, passwordHash);

        result = collection.updateOne(filter, update);

        //client.close();

        return result.getModifiedCount() == 1;
    } //updatePassword

    /**
     * Displays the form for updating a user's password and collects the user's input.
     *
     * @param model the model to be used in the operation
     * @return the HTML code for the password update form
     */
    @GetMapping("/updatePassword")
    public String updatePasswordForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "updatePasswordForm";
    } //updatePasswordForm

    /**
     * Displays the result after attempting to update the user's password.
     *
     * @param userInformation the user information to be used in the operation
     * @return the HTML code for the password update result
     */
    @PostMapping("/updatePassword")
    public String updatePasswordSubmit(@ModelAttribute UserInformation userInformation) {
        String username = userInformation.getUsername();
        String password = userInformation.getPassword();
        String newPassword = userInformation.getNewValue();

        if (!ValidationUtils.userPresent(username)) {
            return "updatePasswordUserNotFoundResult";
        } else if (!ValidationUtils.passwordCorrect(username, password)) {
            return "updatePasswordIncorrectPasswordResult";
        } else if (Objects.equals(password, newPassword)) {
            return "updatePasswordNoChangeResult";
        } else if (!this.newPasswordValid(newPassword)) {
            return "updatePasswordInvalidNewPasswordResult";
        } else {
            boolean success = this.updatePassword(username, newPassword);

            return success ? "updatePasswordSuccessResult" : "updatePasswordFailureResult";
        } //end if
    } //updatePasswordSubmit
}