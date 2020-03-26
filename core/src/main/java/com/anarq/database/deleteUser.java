package com.anarq.database;

import java.util.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

public class deleteUser {

    String text;
    String field;

    public deleteUser(String text) {
        this.text = text;
        field = "Username";
    }

    /**
     *
     * @return -1 if user to be deleted is not found or 1 if the user is successfully deleted
     */
    public int delete() {
        findUser jammer = new findUser(text);
        Document user = jammer.find();

        if (user == null){
            System.out.println("\n\nUser does not exist\n\n");
            return -1;
        }
        else {

//            Scanner sc = new Scanner(System.in);
//            System.out.println("Enter password to delete account");
//            String password = sc.next();
//            if (!user.containsValue(password)) {
//                System.out.println("Incorrect Password. Account was not deleted");
//                return -1;
//            }

            ConnectToDatabase newConnection = new ConnectToDatabase();
            MongoDatabase database = newConnection.connect();
            MongoCollection<Document> jammerCollection = database.getCollection("jammers-list");
            System.out.println("Deleted User - " + user.toString());
            jammerCollection.deleteOne(user);
            return 1;
        }
    }

    public static void main(String[] args) {
        deleteUser u = new deleteUser("new");
        u.delete();
    }
}
