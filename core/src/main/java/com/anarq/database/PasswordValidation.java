package com.anarq.database;

/**
 * A utility class for performing password validation in the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class PasswordValidation {
    /**
     * The minimum length of a password.
     */
	private static final int MIN_PASSWORD_LENGTH = 8;

    /**
     * The maximum length of a password.
     */
	private static final int MAX_PASSWORD_LENGTH = 64;

    /**
     * The password of this password validation utility class.
     */
    private final String password;

    /**
     * Constructs a newly allocated {@code PasswordValidation} object with the specified password.
     *
     * @param password the password to be used in construction
     */
    public PasswordValidation(String password) {
        this.password = password;
    } //PasswordValidation

    /**
     * Returns the result of validation of the password of this password validation utility class.
     *
     * @return the result of validation of the password of this password validation utility class
     */
    public String validatePassword(){
        if (password.length() < MIN_PASSWORD_LENGTH) {
			return "Password must be at least " + MIN_PASSWORD_LENGTH + " characters in length!";
		} //end if

		if (password.length() > MAX_PASSWORD_LENGTH) {
            return "Password must be less than " + MAX_PASSWORD_LENGTH + " characters in length!";
        } //end if

        if (!containsNumbers()) {
            return "Password must contain at least one (1) numeral!";
        } //end if

        if (!containsLowerCaseCharacters()) {
            return "Password must contain at least one (1) lower case character!";
        } //end if

        if (!containsUpperCaseCharacters()) {
            return "Password must contain at least one (1) upper case character!";
        } //end if

        if (!containsSpecialCharacters()) {
            return "Password must contain at least one (1) special character! (@,$,#,&)";
        } //end if

        return "Password Ok";
    } //validatePassword

    /**
     * Determines whether or not the password of this password validation utility class contains numbers.
     *
     * @return {@code true}, if the password of this password validation utility class contains numbers and
     * {@code false} otherwise
     */
    public boolean containsNumbers() {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            } //end if
        } //end if

        return false;
    } //containsNumbers

    /**
     * Determines whether or not the password of this password validation utility class contains numbers.
     *
     * @return {@code true}, if the password of this password validation utility class contains numbers and
     * {@code false} otherwise
     */
    public boolean containsSpecialCharacters() {
        if (password.contains("@")) {
            return true;
        } //end if

        if (password.contains("$")) {
            return true;
        } //end if

        if (password.contains("#")) {
            return true;
        } //end if

        return password.contains("&");
    } //containsSpecialCharacters

    /**
     * Determines whether or not the password of this password validation utility class contains uppercase letters.
     *
     * @return {@code true}, if the password of this password validation utility class contains uppercase letters and
     * {@code false} otherwise
     */
    public boolean containsUpperCaseCharacters() {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                return true;
            } //end if
        } //end for

        return false;
    } //containsUpperCaseCharacters

    /**
     * Determines whether or not the password of this password validation utility class contains lowercase letters.
     *
     * @return {@code true}, if the password of this password validation utility class contains lowercase letters and
     * {@code false} otherwise
     */
    public boolean containsLowerCaseCharacters() {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                return true;
            } //end if
        } //end for

        return false;
    } //containsLowerCaseCharacters
}