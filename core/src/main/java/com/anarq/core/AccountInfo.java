package com.anarq.core;

/**
 * A user's account information associated with the AnarQ Application.
 *
 * @version April 23, 2020
 */
public class AccountInfo {
    /**
     * The username of this account information.
     */
    private final String username;

    /**
     * The first name of this account information.
     */
    private final String firstName;

    /**
     * The last name of this account information.
     */
    private final String lastName;

    /**
     * The email of this account information.
     */
    private final String email;

    /**
     * The bio of this account information.
     */
    private final String bio;

    /**
     * The image bytes of this account information.
     */
    private final byte[] imageBytes;

    /**
     * Constructs a newly allocated {@code AccountInfo} object with the specified username, first name, last name,
     * email, bio, and image bytes.
     *
     * @param username the username to be used in construction
     * @param firstName the first name to be used in construction
     * @param lastName the last name to be used in construction
     * @param email the email to be used in construction
     * @param bio the bio to be used in construction
     * @param imageBytes the image bytes to be used in construction
     */
    public AccountInfo(String username, String firstName, String lastName, String email, String bio,
                       byte[] imageBytes) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bio = bio;
        this.imageBytes = imageBytes;
    } //AccountInfo

    /**
     * Returns the username of this account information.
     *
     * @return the username of this account information
     */
    public String getUsername() {
        return username;
    } //getUsername

    /**
     * Returns the first name of this account information.
     *
     * @return the first name of this account information
     */
    public String getFirstName() {
        return firstName;
    } //getFirstName

    /**
     * Returns the last name of this account information.
     *
     * @return the last name of this account information
     */
    public String getLastName() {
        return lastName;
    } //getLastName

    /**
     * Returns the email of this account information.
     *
     * @return the email of this account information
     */
    public String getEmail() {
        return email;
    } //getEmail

    /**
     * Returns the bio of this account information.
     *
     * @return the bio of this account information
     */
    public String getBio() {
        return bio;
    } //getBio

    /**
     * Returns the image bytes of this account information.
     *
     * @return the image bytes of this account information
     */
    public byte[] getImageBytes() {
        return imageBytes;
    } //getImageBytes
}