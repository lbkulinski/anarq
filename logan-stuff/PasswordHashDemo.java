import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

/**
 * A demo program used to hash passwords.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version February 23, 2020
 */
public final class PasswordHashDemo {
    /**
     * Hashes the specified command line argument and displays its value.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String password;
        final int saltLength = 16;
        byte[] salt;
        SecureRandom random;
        SecretKeyFactory factory;
        final String algorithm = "PBKDF2WithHmacSHA1";
        PBEKeySpec keySpec;
        final int iterationCount = 65_535;
        final int keyLength = 256;
        SecretKey secretKey;
        byte[] hashedPassword;

        assert args.length == 1;

        password = args[0];

        salt = new byte[saltLength];

        random = new SecureRandom();

        random.nextBytes(salt);

        try {
            factory = SecretKeyFactory.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

            return;
        } //end try catch

        keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);

        try {
            secretKey = factory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();

            return;
        } //end try catch

        hashedPassword = secretKey.getEncoded();

        System.out.println(Arrays.toString(hashedPassword));
    } //main
}