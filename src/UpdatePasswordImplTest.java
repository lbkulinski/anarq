import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;
import org.junit.BeforeClass;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.UUID;
import java.util.Map;
import com.mongodb.client.MongoClients;
import org.junit.AfterClass;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;
import org.junit.Test;
import org.junit.Assert;

/**
 * A set of test cases for the {@code UpdatePasswordImpl} class.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version February 26, 2020
 */
public final class UpdatePasswordImplTest {
    private static final class HashedPassword {
        byte[] passwordHash;
        byte[] salt;

        HashedPassword(byte[] passwordHash, byte[] salt) {
            this.passwordHash = passwordHash;
            this.salt = salt;
        } //HashedPassword
    } //HashedPassword

    private static String databaseUsername;
    private static String databasePassword;
    private static String testName;
    private static String testUsername;
    private static String testPassword;

    private static HashedPassword hash(String password) throws Exception {
        final int saltLength = 16;
        byte[] salt;
        SecureRandom random;
        SecretKeyFactory factory;
        final String algorithm = "PBKDF2WithHmacSHA1";
        PBEKeySpec keySpec;
        final int iterationCount = 65_535;
        final int keyLength = 256;
        SecretKey secretKey;
        byte[] passwordHash;

        salt = new byte[saltLength];

        random = new SecureRandom();

        random.nextBytes(salt);

        factory = SecretKeyFactory.getInstance(algorithm);

        keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);

        secretKey = factory.generateSecret(keySpec);

        passwordHash = secretKey.getEncoded();

        return new HashedPassword(passwordHash, salt);
    } //hash

    @BeforeClass
    public static void setUpBeforeClass() {
        String format;
        String uri;
        MongoClient client;
        String databaseName;
        MongoDatabase database;
        MongoCollection<Document> jammerCollection;
        String name;
        String username;
        String password;
        HashedPassword hashedPassword;
        Map<String, Object> entry;
        Document insertDocument;

        databaseUsername = System.getProperty("database-username");

        databasePassword = System.getProperty("database-password");

        format = "mongodb+srv://%s:%s@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";

        uri = String.format(format, databaseUsername, databasePassword);

        client = MongoClients.create(uri);

        databaseName = "user-database";

        database = client.getDatabase(databaseName);

        jammerCollection = database.getCollection("jammers");

        name = UUID.randomUUID()
                   .toString();

        testName = name;

        username = UUID.randomUUID()
                       .toString();

        testUsername = username;

        password = UUID.randomUUID()
                       .toString();

        testPassword = password;

        try {
            hashedPassword = hash(password);
        } catch (Exception e) {
            e.printStackTrace();

            System.exit(1);

            return;
        } //end try catch

        entry = Map.of("name", name, "username", username, "password-hash", hashedPassword.passwordHash,
                       "salt", hashedPassword.salt);

        insertDocument = new Document(entry);

        jammerCollection.insertOne(insertDocument);

        client.close();
    } //setUpBeforeClass

    @AfterClass
    public static void tearDownAfterClass() {
        String format;
        String uri;
        MongoClient client;
        String databaseName;
        MongoDatabase database;
        MongoCollection<Document> jammerCollection;
        Bson filter;

        format = "mongodb+srv://%s:%s@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";

        uri = String.format(format, databaseUsername, databasePassword);

        client = MongoClients.create(uri);

        databaseName = "user-database";

        database = client.getDatabase(databaseName);

        jammerCollection = database.getCollection("jammers");

        filter = Filters.eq("name", testName);

        jammerCollection.deleteOne(filter);

        client.close();
    } //tearDownAfterClass

    @Test
    public void testExistingUserPasswordUpdate() {
        String password = UUID.randomUUID()
                              .toString();

        try {
            Assert.assertTrue(UpdatePasswordImpl.updatePassword(databaseUsername, databasePassword,
                                                                UpdatePasswordImpl.UserType.JAMMER, testUsername,
                                                                testPassword));

            Assert.assertTrue(UpdatePasswordImpl.updatePassword(databaseUsername, databasePassword,
                                                                UpdatePasswordImpl.UserType.JAMMER, testUsername,
                                                                password));
        } catch (Exception e) {
            e.printStackTrace();

            Assert.fail();
        } //end try catch
    } //testExistingUserPasswordUpdate

    @Test
    public void testNonExistingUserPasswordUpdate() {
        String username = UUID.randomUUID()
                              .toString();
        String password = UUID.randomUUID()
                              .toString();

        while (username.equals(testUsername)) {
            username = UUID.randomUUID()
                           .toString();
        } //end while

        try {
            Assert.assertFalse(UpdateUsernameImpl.updateUsername(databaseUsername, databasePassword,
                                                                 UpdateUsernameImpl.UserType.JAMMER, username,
                                                                 password));
        } catch (Exception e) {
            e.printStackTrace();

            Assert.fail();
        } //end try catch
    } //testNonExistingUserPasswordUpdate
}