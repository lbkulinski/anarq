package com.anarq.update.username;

import org.springframework.stereotype.Controller;
import java.util.Set;
import com.anarq.update.UserType;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import com.anarq.update.ValidationUtils;
import java.io.BufferedReader;
import java.util.HashSet;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.anarq.update.UserInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * A controller for updating a user's username.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version March 24, 2020
 */
@Controller
public final class UpdateUsernameController {
    /**
     * Returns a set of alternative usernames that are available and based on the specified username.
     *
     * @param userType the user type to be used in the operation
     * @param username the username to be used in the operation
     * @return a set of alternative usernames that are available and based on the specified username
     * @throws NullPointerException if the specified user type, username, or password is {@code null}
     */
    private Set<String> getAltUsernames(UserType userType, String username) {
        int randomNumber;
        int bound = 1_000;
        String altUsername0;
        boolean exists;
        int iterationCount = 0;
        int maxIterations = 1_000;
        String altUsername1;
        String altUsername2;

        Objects.requireNonNull(userType, "the specified user type is null");

        Objects.requireNonNull(username, "the specified username is null");

        do {
            if (iterationCount == maxIterations) {
                return Set.of();
            } //end if

            randomNumber = ThreadLocalRandom.current()
                                            .nextInt(bound);

            altUsername0 = String.format("%s%d", username, randomNumber);

            exists = ValidationUtils.userPresent(userType, altUsername0);

            iterationCount++;
        } while (exists);

        iterationCount = 0;

        do {
            if (iterationCount == maxIterations) {
                return Set.of();
            } //end if

            randomNumber = ThreadLocalRandom.current()
                                            .nextInt(bound);

            altUsername1 = String.format("%s%d", username, randomNumber);

            exists = Objects.equals(altUsername1, altUsername0);

            exists |= ValidationUtils.userPresent(userType, altUsername1);

            iterationCount++;
        } while (exists);

        iterationCount = 0;

        do {
            if (iterationCount == maxIterations) {
                return Set.of();
            } //end if

            randomNumber = ThreadLocalRandom.current()
                                            .nextInt(bound);

            altUsername2 = String.format("%s%d", username, randomNumber);

            exists = Objects.equals(altUsername2, altUsername0);

            exists |= Objects.equals(altUsername2, altUsername1);

            exists |= ValidationUtils.userPresent(userType, altUsername2);

            iterationCount++;
        } while (exists);

        return Set.of(altUsername0, altUsername1, altUsername2);
    } //getAltUsernames

    /**
     * Returns whether or not the specified new username is valid. A username is valid if it does not contain any
     * "bad words".
     *
     * @param newUsername the new username to be used in the operation
     * @return {@code true}, if the specified new username is valid and {@code false} otherwise
     * @throws NullPointerException if the specified new username is {@code null}
     * @throws UncheckedIOException if an I/O error occurs while trying to read the file containing the bad words
     */
    private boolean newUsernameValid(String newUsername) {
        String fileName = "src/main/BadWords.txt";
        BufferedReader reader;
        String line;
        Set<String> badWords;
        String pattern;

        Objects.requireNonNull(newUsername, "the specified new username is null");

        newUsername = newUsername.toLowerCase();

        badWords = new HashSet<>();

        try {
            reader = new BufferedReader(new FileReader(fileName));

            line = reader.readLine();

            while (line != null) {
                line = line.toLowerCase();

                badWords.add(line);

                line = reader.readLine();
            } //end while

            reader.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } //end try catch

        for (String badWord : badWords) {
            pattern = String.format(".*%s.*", badWord);

            if (newUsername.matches(pattern)) {
                return false;
            } //end if
        } //end for

        return true;
    } //newUsernameValid

    /**
     * Attempts to update the username of the user with the specified current username with the specified new username.
     *
     * @param userType the user type to be used in the operation
     * @param currentUsername the current username to be used in the operation
     * @param newUsername the new username to be used in the operation
     * @return {@code true}, if the user's username was successfully updated and {@code false} otherwise
     * @throws NullPointerException if the specified user type, current username, or new username is {@code null}
     */
    private boolean updateUsername(UserType userType, String currentUsername, String newUsername) {
        String databaseUsername;
        String databasePassword;
        String format = "mongodb+srv://%s:%s@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";
        String uri;
        MongoClient client;
        String databaseName = "user-database";
        MongoDatabase userDatabase;
        String collectionName;
        MongoCollection<Document> collection;
        Bson filter;
        String fieldName = "username";
        Bson update;
        UpdateResult result;

        Objects.requireNonNull(userType, "the specified user type is null");

        Objects.requireNonNull(currentUsername, "the specified current username is null");

        Objects.requireNonNull(newUsername, "the specified new username is null");

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

        filter = Filters.eq(fieldName, currentUsername);

        update = Updates.set(fieldName, newUsername);

        result = collection.updateOne(filter, update);

        client.close();

        return result.getModifiedCount() == 1;
    } //updateUsername

    /**
     * Displays the form for updating a user's username and collects the user's input.
     *
     * @param model the model to be used in the operation
     * @return the HTML code for the username update form
     */
    @GetMapping("/updateUsername")
    public String updateUsernameForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "updateUsernameForm";
    } //updateUsernameForm

    /**
     * Displays the result after attempting to update the user's username.
     *
     * @param userInformation the user information to be used in the operation
     * @param model the model to be used in the operation
     * @return the HTML code for the username update result
     */
    @PostMapping("/updateUsername")
    public String updateUsernameSubmit(@ModelAttribute UserInformation userInformation, Model model) {
        UserType userType = userInformation.getUserType();
        String currentUsername = userInformation.getUsername();
        String password = userInformation.getPassword();
        String newUsername = userInformation.getNewValue();

        currentUsername = currentUsername.toLowerCase();

        newUsername = newUsername.toLowerCase();

        if (!ValidationUtils.userPresent(userType, currentUsername)) {
            return "updateUsernameUserNotFoundResult";
        } else if (!ValidationUtils.passwordCorrect(userType, currentUsername, password)) {
            return "updateUsernameIncorrectPasswordResult";
        } else if (Objects.equals(currentUsername, newUsername)) {
            return "updateUsernameNoChangeResult";
        } else if (ValidationUtils.userPresent(userType, newUsername)) {
            Set<String> altUsernames = this.getAltUsernames(userType, newUsername);

            model.addAttribute("altUsernames", altUsernames);

            return "updateUsernameNewUsernameTakenResult";
        } else if (!this.newUsernameValid(newUsername)) {
            return "updateUsernameInvalidNewUsernameResult";
        } else {
            boolean success = this.updateUsername(userType, currentUsername, newUsername);

            return success ? "updateUsernameSuccessResult" : "updateUsernameFailureResult";
        } //end if
    } //updateUsernameSubmit
}