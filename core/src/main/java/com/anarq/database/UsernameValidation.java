package com.anarq.database;

/**
 * A utility class for performing username validation in the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class UsernameValidation {
    /**
     * The minimum length of a username.
     */
	private static final int MIN_USERNAME_LENGTH = 3;

    /**
     * The maximum length of a username.
     */
	private static final int MAX_USERNAME_LENGTH = 16;

    /**
     * The username of this username validation utility class.
     */
    String username;

    /**
     * Constructs a newly allocated {@code UsernameValidation} object with the specified username.
     *
     * @param username the username to be used in construction
     */
    public UsernameValidation(String username) {
        this.username = username;
    } //UsernameValidation

    /**
     * Determines whether or not the username of this username validation utility class contains bad words.
     *
     * @return {@code true}, if the username of this username validation utility class contains bad words and
     * {@code false} otherwise
     */
    public boolean hasBadWords() {
        return NaughtyWords.isANaughtyWord(username);
    } //hasBadWords

    /**
     * Determines whether or not the username of this username validation utility class is taken.
     *
     * @return {@code true}, if the username of this username validation utility class is taken and {@code false}
     * otherwise
     */
    public boolean isTaken() {
        FindUser user = new FindUser(username);

        return user.find() != null;
    } //isTaken

    /**
     * Returns the result of validation of the username of this username validation utility class.
     *
     * @return the result of validation of the username of this username validation utility class
     */
    public String validateUsername() {
        if (username.length() < MIN_USERNAME_LENGTH) {
            return "Username must be at least " + MIN_USERNAME_LENGTH + " characters in length!";
        } //end if

        if (username.length() > MAX_USERNAME_LENGTH) {
            return "Username must be less than " + MAX_USERNAME_LENGTH + " characters in length!";
        } //end if

        if (isTaken()) {
            return "Username Taken";
        } //end if

        if (hasBadWords()) {
            return "Username cannot contain any innappropriate words!";
        } //end if

        return "Username Ok";
    } //validateUsername

    /**
     * Determines whether or not the username of this username validation utility class contains the specified word.
     *
     * @param word the word to be used in the operation
     * @return {@code true}, if the username of this username validation utility class contains the specified word and
     * {@code false} otherwise
     */
    public boolean contains(String word) {
        String name = username.toLowerCase();

        do {
            int position = name.indexOf(word.charAt(0));

            if (position == -1) {
                return false;
            } //end if

            try {
                String substring = name.substring(position, (position + word.length()));

                if (substring.equalsIgnoreCase(word)) {
                    return true;
                } else {
                    name = name.substring(position + 1);
                } //end if
            } catch (StringIndexOutOfBoundsException s) {
                return false;
            } //end try catch
        } while (name.length() > 0);

        return false;
    } //contains
}