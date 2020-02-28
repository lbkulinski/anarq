import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

public class ConnectToDatabase {

    final String url = "mongodb+srv://anarq:anarq@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";
    final String databaseName = "TestDB";

    public MongoDatabase connect() {
        try {
            MongoClientURI uri = new MongoClientURI(url);
            MongoClient mongoClient = new MongoClient(uri);
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            return database;
        } catch (Exception e) {
            System.out.println("\n\nCould not connect to MongoDB Atlas\n\n");
            System.exit(0);
        }
        return null;
    }

    public static void main(String[] args) {
        ConnectToDatabase c = new ConnectToDatabase();
        c.connect();
    }
}
