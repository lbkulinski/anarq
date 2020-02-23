import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.Objects;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

/**
 * An implementation for updating a user's username in a MongoDB database.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version February 23, 2020
 */
public final class UpdateUsernameImpl {
    /**
     * The type of a user.
     */
    public enum UserType {
        /**
         * The singleton instance representing the DJ user type.
         */
        DJ,

        /**
         * The singleton instance representing the Jammer user type.
         */
        JAMMER
    } //UserType

    /**
     * The database username to be used in this implementation.
     */
    private static String databaseUsername;

    /**
     * The database password to be used in this implementation.
     */
    private static String databasePassword;

    /**
     * Attempts to update the username of the user with the specified current username with the specified new username.
     *
     * @param userType the user type to be used in the operation
     * @param currentUsername the current username to be used in the operation
     * @param newUsername the new username to be used in the operation
     * @return {@code true}, if the user's username was successfully updated and {@code false} otherwise
     */
    public static boolean updateUsername(UserType userType, String currentUsername, String newUsername) {
        final String format = "mongodb+srv://%s:%s@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";
        String uri;
        MongoClient client;
        final String databaseName = "user-database";
        MongoDatabase userDatabase;
        String collectionName;
        MongoCollection<Document> collection;
        Bson filter;
        final String fieldName = "username";
        Bson update;
        UpdateResult result;

        Objects.requireNonNull(userType, "the specified user type is null");

        Objects.requireNonNull(currentUsername, "the specified current username is null");

        Objects.requireNonNull(newUsername, "the specified new username is null");

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
     * A test of the {@code updateUsername()} method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String currentUsername = "5b47648b-94c4-4479-ba5f-f5fb9fc00c86";
        String newUsername = "jimseven";

        assert args.length == 2;

        databaseUsername = args[0];

        databasePassword = args[1];

        System.out.println(updateUsername(UserType.JAMMER, currentUsername, newUsername));
    } //main
}