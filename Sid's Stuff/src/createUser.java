import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;

public class createUser {

    protected String firstName;
    protected String lastName;
    protected String password;

    protected String username;
    protected int birthDay;
    protected int birthMonth;
    protected int birthYear;

    ConnectToDatabase newConnection;
    MongoDatabase database;
    Map<String, Object> userDetails;

    public createUser(String username, String password, String firstName, String lastName,  int birthDay, int birthMonth, int birthYear) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;

        newConnection = new ConnectToDatabase();
        database = newConnection.connect();
    }

    /**
     * Method to add a user to the database
     * @return 1 if the user was successfully added or 0 if the user was not added to the database
     */
    public int addJammer()  throws Exception{
        UsernameValidation meetsCriteria = new UsernameValidation(username);
        PasswordValidation passwordCriteria = new PasswordValidation(password);
        if (meetsCriteria.validateUsername() /* && passwordCriteria.validatePassword */) {
            userDetails = Map.of("Username", username,
                    "Password", password,
                    "First name", firstName,
                    "Last name", lastName,
                    "Birth Day", birthDay,
                    "Birth Month", birthMonth,
                    "Birth Year", birthYear);

            MongoCollection<Document> jammerCollection = database.getCollection("jammers-list");
            Document user = new Document(userDetails);
            jammerCollection.insertOne(user);
            System.out.println("Added user");
            return 1;
        } else {

            String [] altUsernames = new String[3];
            for (int i = 0; i < 3; i++) {
                int randomNumber = Integer.parseInt(getRandomAlternativeString());
                altUsernames[i] = username + randomNumber;
            }
            System.out.println();
            System.out.print(username + " is Already Taken. Try: ");
            for (int i = 0; i < 3; i++) {
                System.out.print(altUsernames[i] + " ");
            }
            return 0;
        }
    }

    public int addHost() {

        int roomCode = Integer.parseInt(getRandomNumberString());
        userDetails = Map.of("Username", username,
                             "Password", password,
                             "First name", firstName,
                             "Last name", lastName,
                             "Room Code", roomCode,
                             "Birth Day", birthDay,
                             "Birth Month", birthMonth,
                             "Birth Year", birthYear);

        MongoCollection<Document> hostCollection = database.getCollection("hosts-list");
        Document user = new Document(userDetails);
        hostCollection.insertOne(user);
        System.out.println("Added user and room code = " + roomCode);
        return 1;
    }

    // code from https://stackoverflow.com/questions/51322750/generate-6-digit-random-number
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public static String getRandomAlternativeString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999);
        // this will convert any number sequence into 6 character.
        return String.format("%03d", number);
    }

    public static void main(String[] args) throws Exception {
        createUser u = new createUser("ex", "1234", "bones", "miek", 3,1,109);
        System.out.println(u.addJammer());
    }
}
