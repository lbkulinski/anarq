package com.anarq.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * A utility class for deleting a user from the database of the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class DeleteUser {
    /**
     * The text of this delete user utility class.
     */
    String text;

    /**
     * The field of this delete user utility class.
     */
    String field;

    /**
     * Constructs a newly allocated {@code DeleteUser} object with the specified text.
     *
     * @param text the text to be used in construction
     */
    public DeleteUser(String text) {
        this.text = text;
        field = "username";
    } //DeleteUser

    /**
     * Attempts to delete a jammer from the database.
     *
     * @return the result of attempting to delete a jammer from the database
     */
    public int delete() {
        FindUser jammer = new FindUser(text);
        Document user = jammer.find();

        if (user == null){
            System.out.println("\n\nUser does not exist\n\n");

            return -1;
        } else {
            MongoDatabase database = ConnectToDatabase.getDatabaseConnection();
            MongoCollection<Document> jammerCollection = database.getCollection("users");

            System.out.println("Deleted User - " + user.toString());

            jammerCollection.deleteOne(user);

            return 1;
        } //end if
    } //delete
}