import com.mongodb.MongoClientURI;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.UUID;
import java.util.Map;
import org.bson.BsonDocument;
import org.bson.BsonString;

/**
 * A demo program used to connect to our MongoDB database and perform some operations.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version February 22, 2020
 */
public final class DatabaseDemo {
    /**
     * Connects to our MongoDB database and performs some operations.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int requiredLength = 2;
        String format;
        String uriString;
        MongoClientURI uri;
        MongoClient client;
        String databaseName;
        MongoDatabase database;
        MongoCollection<Document> jammerCollection;
        String name;
        String username;
        String password;
        Map<String, Object> entry;
        Document insertDocument;
        BsonString searchString;
        BsonDocument searchDocument;

        assert args.length == requiredLength;

        format = "mongodb+srv://%s:%s@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";

        uriString = String.format(format, args[0], args[1]);

        uri = new MongoClientURI(uriString);

        client = new MongoClient(uri);

        databaseName = "user-database";

        database = client.getDatabase(databaseName);

        jammerCollection = database.getCollection("jammers");

        name = UUID.randomUUID()
                   .toString();

        username = UUID.randomUUID()
                       .toString();

        password = UUID.randomUUID()
                       .toString();

        entry = Map.of("name", name, "username", username, "password", password);

        insertDocument = new Document(entry);

        jammerCollection.insertOne(insertDocument);

        searchString = new BsonString(username);

        searchDocument = new BsonDocument("username", searchString);

        for (Document document : jammerCollection.find(searchDocument)) {
            System.out.printf("Found document: %s%n", document.toJson());
        } //end for
    } //main
}