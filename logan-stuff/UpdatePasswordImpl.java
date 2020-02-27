import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;
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
import java.util.List;

/**
 * An implementation for updating a user's password in a MongoDB database.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version February 23, 2020
 */
public final class UpdatePasswordImpl {
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
     * A wrapper for a password hash and salt.
     */
    private static final class HashedPassword {
        /**
         * The password hash of this hashed password.
         */
        byte[] passwordHash;

        /**
         * The salt of this hash password.
         */
        byte[] salt;

        /**
         * Constructs a newly allocated {@code HashedPassword} object with the specified password hash and salt.
         *
         * @param passwordHash the password hash to be used in construction
         * @param salt the salt to be used in construction
         */
        HashedPassword(byte[] passwordHash, byte[] salt) {
            this.passwordHash = passwordHash;
            this.salt = salt;
        } //HashedPassword
    } //HashedPassword

    /**
     * The database username to be used in this implementation.
     */
    private static String databaseUsername;

    /**
     * The database password to be used in this implementation.
     */
    private static String databasePassword;

    /**
     * Hashes the specified password.
     *
     * @param password the password to be used in the operation
     * @return the password hash and salt
     * @throws Exception if an exception occurs during the hashing computation
     */
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

    /**
     * Attempts to update the password of the user with the specified username with the specified new password.
     *
     * @param userType the user type to be used in the operation
     * @param username the username to be used in the operation
     * @param newPassword the new password to be used in the operation
     * @return {@code true}, if the user's password was successfully updated and {@code false} otherwise
     * @throws Exception if an exception occurs during the update
     */
    public static boolean updatePassword(UserType userType, String username, String newPassword) throws Exception {
        final String format = "mongodb+srv://%s:%s@cluster0-kwfia.mongodb.net/test?retryWrites=true&w=majority";
        String uri;
        MongoClient client;
        final String databaseName = "user-database";
        MongoDatabase userDatabase;
        String collectionName;
        MongoCollection<Document> collection;
        Bson filter;
        final String usernameField = "username";
        HashedPassword hashedPassword;
        Bson passwordHashUpdate;
        final String passwordHashField = "password-hash";
        Bson saltUpdate;
        final String saltField = "salt";
        UpdateResult result;

        Objects.requireNonNull(userType, "the specified user type is null");

        Objects.requireNonNull(username, "the specified username is null");

        Objects.requireNonNull(newPassword, "the specified new password is null");

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

        filter = Filters.eq(usernameField, username);

        hashedPassword = hash(newPassword);

        passwordHashUpdate = Updates.set(passwordHashField, hashedPassword.passwordHash);

        saltUpdate = Updates.set(saltField, hashedPassword.salt);

        result = collection.updateOne(filter, List.of(passwordHashUpdate, saltUpdate));

        client.close();

        return result.getModifiedCount() == 1;
    } //updatePassword

    /**
     * A test of the {@code updateUsername()} method.
     *
     * @param args the command line arguments
     * @throws Exception if an exception occurs during the update
     */
    public static void main(String[] args) throws Exception {
        String username = "jimseven";
        String newPassword = "coffee";

        assert args.length == 2;

        databaseUsername = args[0];

        databasePassword = args[1];

        System.out.println(updatePassword(UserType.JAMMER, username, newPassword));
    } //main
}