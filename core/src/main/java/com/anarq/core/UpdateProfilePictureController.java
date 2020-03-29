package com.anarq.core;

import org.springframework.stereotype.Controller;
import com.anarq.update.UserType;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.FindIterable;
import org.bson.types.Binary;
import java.util.Objects;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import java.util.Arrays;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.model.Updates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.anarq.update.UserInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.UncheckedIOException;
import com.anarq.update.ValidationUtils;

/**
 * A controller for updating a user's profile picture.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version March 24, 2020
 */
@Controller
public final class UpdateProfilePictureController {
    /**
     * Returns whether or not the specified bytes is the same as the bytes of the user with the specified username.
     *
     * @param userType the user type to be used in the operation
     * @param username the username to be used in the operation
     * @param bytes the bytes to be used in the operation
     * @return {@code true}, if the specified bytes is the same as the bytes of the user with the specified username
     * and {@code false} otherwise
     * @throws NullPointerException if the specified user type, username, or bytes is {@code null}
     */
    private boolean profilePictureSame(UserType userType, String username, byte[] bytes) {
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
        String usernameFieldName = "username";
        FindIterable<Document> results;
        Document result;
        Binary currentBinary;
        String pictureBytesFieldName = "picture-bytes";
        byte[] currentBytes;
        boolean same;

        Objects.requireNonNull(userType, "the specified user type is null");

        Objects.requireNonNull(username, "the specified username is null");

        Objects.requireNonNull(bytes, "the specified bytes is null");

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

        filter = Filters.eq(usernameFieldName, username);

        results = collection.find(filter);

        result = results.first();

        if (result == null) {
            throw new IllegalStateException("the user with the specified username is not present in the database");
        } //end if

        currentBinary = result.get(pictureBytesFieldName, Binary.class);

        currentBytes = currentBinary.getData();

        same = Arrays.equals(bytes, currentBytes);

        client.close();

        return same;
    } //profilePictureSame

    /**
     * Attempts to update the profile picture of the user with the specified username with the specified bytes.
     *
     * @param userType the user type to be used in the operation
     * @param username the username to be used in the operation
     * @param bytes the bytes to be used in the operation
     * @return {@code true}, if the user's profile picture was successfully updated and {@code false} otherwise
     * @throws NullPointerException if the specified user type, username, or bytes is {@code null}
     */
    private boolean updateProfilePicture(UserType userType, String username, byte[] bytes) {
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
        String usernameFieldName = "username";
        Bson update;
        String pictureBytesFieldName = "picture-bytes";
        UpdateResult result;

        Objects.requireNonNull(userType, "the specified user type is null");

        Objects.requireNonNull(username, "the specified username is null");

        Objects.requireNonNull(bytes, "the specified bytes is null");

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

        filter = Filters.eq(usernameFieldName, username);

        update = Updates.set(pictureBytesFieldName, bytes);

        result = collection.updateOne(filter, update);

        client.close();

        return result.getModifiedCount() == 1;
    } //updateProfilePicture

    /**
     * Displays the form for updating a user's profile picture and collects the user's input.
     *
     * @param model the model to be used in the operation
     * @return the HTML code for the profile picture update form
     */
    @GetMapping("/updateProfilePicture")
    public String updateProfilePictureForm(Model model) {
        model.addAttribute("userInformation", new UserInformation());

        return "updateProfilePictureForm";
    } //updateProfilePictureForm

    /**
     * Displays the result after attempting to update the user's profile picture.
     *
     * @param userInformation the user information to be used in the operation
     * @param file the file to be used in the operation
     * @return the HTML code for the profile picture update result
     */
    @PostMapping("/updateProfilePicture")
    public String updateProfilePictureSubmit(@ModelAttribute UserInformation userInformation,
                                             @RequestParam("file") MultipartFile file) {
        UserType userType;
        String username;
        String password;
        String contentType;
        byte[] bytes;

        userType = userInformation.getUserType();

        username = userInformation.getUsername();

        password = userInformation.getPassword();

        contentType = file.getContentType();

        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } //end try catch

        if (!ValidationUtils.userPresent(userType, username)) {
            return "updateProfilePictureUserNotFoundResult";
        } else if (!ValidationUtils.passwordCorrect(userType, username, password)) {
            return "updateProfilePictureIncorrectPasswordResult";
        } else if (file.isEmpty()) {
            return "updateProfilePictureEmptyFileResult";
        } else if (!Objects.equals(contentType, "image/jpeg")) {
            return "updateProfilePictureNotJPEGResult";
        } else if (this.profilePictureSame(userType, username, bytes)) {
            return "updateProfilePictureNoChangeResult";
        } else {
            boolean success = this.updateProfilePicture(userType, username, bytes);

            return success ? "updateProfilePictureSuccessResult" : "updateProfilePictureFailureResult";
        } //end if
    } //updateProfilePictureSubmit
}