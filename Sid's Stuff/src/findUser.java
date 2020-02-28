import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

public class findUser {

    String text;
    String field;

    public findUser(String text) {
        this.text = text;
        field = "Username";
    }
    public findUser(String field, String text) {
        this.field = field;
        this.text = text;
    }

    public Document find() {

        ConnectToDatabase newConnection = new ConnectToDatabase();
        MongoDatabase database = newConnection.connect();
        MongoCollection<Document> jammerCollection = database.getCollection("jammers-list");

        BsonString searchString = new BsonString(text);
        BsonDocument searchDocument = new BsonDocument(field, searchString);
//        System.out.println("Matches = " + jammerCollection.countDocuments(searchDocument));

        if (jammerCollection.find(searchDocument) == null) {
            return null;
        }
        else {
            for (Document document : jammerCollection.find(searchDocument)) {
                return document;
            }
        }
        return null;
    }

    public Document findHost() {

        ConnectToDatabase newConnection = new ConnectToDatabase();
        MongoDatabase database = newConnection.connect();
        MongoCollection<Document> hostCollection = database.getCollection("hosts-list");

        BsonString searchString = new BsonString(text);
        BsonDocument searchDocument = new BsonDocument(field, searchString);
        //System.out.println("Matches = " + jammerCollection.countDocuments(searchDocument));

        if (hostCollection.find(searchDocument) == null) {
            return null;
        }
        else {
            for (Document document : hostCollection.find(searchDocument)) {
                return document;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        findUser f = new findUser("testing");
        f.find();
    }
}
