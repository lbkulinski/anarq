package com.anarq.database;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * A utility class for encryption and decryption in the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class EncryptionDecryptionAES {
    /**
     * The cipher of this encryption/decryption utility class.
     */
    static Cipher cipher;

    /**
     * The secret key of this encryption/decryption utility class.
     */
    static SecretKey secretKey;

    /**
     * Constructs a newly allocated {@code EncryptionDecryptionAES} object.
     */
    public EncryptionDecryptionAES() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

            keyGenerator.init(128);

            secretKey = keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        } //end try catch
    } //EncryptionDecryptionAES

    /*
     * Code derived from:
     * https://stackoverflow.com/questions/10303767/encrypt-and-decrypt-in-java
     * https://javapapers.com/java/java-symmetric-aes-encryption-decryption-using-jce/
     */

    /**
     * Returns the encryption of the specified text.
     *
     * @param text the text to be used in the operation
     * @return the encryption of the specified text
     */
    public static String encrypt(String text) {
        try {
            cipher = Cipher.getInstance("AES");

            return encryptText(text, secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        } //encrypt

        return "encrypt_failed";
    } //encrypt

    /**
     * Returns the decryption of the specified text.
     *
     * @param text the text to be used in the operation
     * @return the decryption of the specified text
     */
    public static String decrypt(String text) {
        try {
            cipher = Cipher.getInstance("AES");

            return decryptText(text, secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        } //end try catch

        return "decrypt_failed";
    } //decrypt

    /**
     * Returns the encryption of the specified plain text.
     *
     * @param plainText the plain text to be used in the operation
     * @param secretKey the secret key to be used in the operation
     * @return the encryption of the specified plain text
     * @throws Exception if an exception occurs
     */
    public static String encryptText(String plainText, SecretKey secretKey) throws Exception {
        byte[] plainTextByte = plainText.getBytes();

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedByte = cipher.doFinal(plainTextByte);

        Base64.Encoder encoder = Base64.getEncoder();

        return encoder.encodeToString(encryptedByte);
    } //encryptText

    /**
     * Returns the decryption of the specified encrypted text.
     *
     * @param encryptedText the encrypted text to be used in the operation
     * @param secretKey the secret key to be used in the operation
     * @return the decryption of the specified encrypted text
     * @throws Exception if an exception occurs
     */
    public static String decryptText(String encryptedText, SecretKey secretKey) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);

        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);

        return new String(decryptedByte);
    } //decryptText
}