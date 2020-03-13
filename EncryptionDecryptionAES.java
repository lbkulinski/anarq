import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncryptionDecryptionAES {

    static Cipher cipher;

    /*
        Code derived from:
            https://stackoverflow.com/questions/10303767/encrypt-and-decrypt-in-java
            https://javapapers.com/java/java-symmetric-aes-encryption-decryption-using-jce/
     */


    /**
     * Function to accept a plain text, encrypt it and save the new encrypted Password
     * along wit the key used to encrypt it
     * @param password the text to be encrypted
     * @throws Exception
     */

    public void encrypt (String password) throws Exception {
        // TODO save the encrypted password and the key

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // block size is 128bits
        SecretKey secretKey = keyGenerator.generateKey();

        cipher = Cipher.getInstance("AES");
        String encryptedPassword = encryptText(password, secretKey);

    }

    /**
     * Function to accept a plain text, encrypt it using the given key
     * @param plainText The text to be encrypted
     * @param secretKey The key used to encrypt the text
     * @return The encrypted text
     * @throws Exception
     */

    public static String encryptText(String plainText, SecretKey secretKey)
            throws Exception {

        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    /**
     * Function to accept an encrypted text and decrypt it using the given key
     * @param encryptedText The text to be decrypted
     * @param secretKey The key used to decrypt the text
     * @return The decrypted text
     * @throws Exception
     */

    public static String decryptText(String encryptedText, SecretKey secretKey)
            throws Exception {

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }
}
