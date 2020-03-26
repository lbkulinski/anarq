package com.anarq.database;

public class PasswordValidation {

	private static final int MIN_PASSWORD_LENGTH = 8; 
	private static final int MAX_PASSWORD_LENGTH = 64;
    private final static String salt = "ANARQ";
    private String password;

    public PasswordValidation (String pswrd) {
        password = pswrd;
    }

    /**
     * Driver function to check if the password meets our requirements
     * @return True if it meets our requirements and false if it doesn't
     */

    public String validatePassword(){


        if ((password.length() < MIN_PASSWORD_LENGTH)) {
			return "Password must be at least " + MIN_PASSWORD_LENGTH + " characters in length!";
		}
		if ((password.length() > MAX_PASSWORD_LENGTH)) {
            return "Password must be less than " + MAX_PASSWORD_LENGTH + " characters in length!";
        }
        if (!containsNumbers()) {
            return "Password must contain at least one (1) numeral!";
        }
        if (!containsLowerCaseCharacters()) {
            return "Password must contain at least one (1) lower case character!";
        }
        if (!containsUpperCaseCharacters()) {
            return "Password must contain at least one (1) upper case character!";
        }
        if (!containsSpecialCharacters()) {
            return "Password must contain at least one (1) special character! (@,$,#,&)";
        }
        return "Password Ok";
    }

    /**
     * Helper function to check if the password contains any numbers
     * @return True if the password contains a digit and false if it doesn't
     */

    public boolean containsNumbers() {

        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper function to check if the password contains any special characters
     * @return True if the password contains a special character and false if it doesn't
     */

    public boolean containsSpecialCharacters() {

            if (password.contains("@")) {
                return true;
            }
            if (password.contains("$")) {
                return true;
            }
            if (password.contains("#")) {
                return true;
            }
            if (password.contains("&")) {
                return true;
            }
        return false;

    }

    /**
     * Helper function to check if the password contains any upper case Letters
     * @return True if the password contains a upper case character and false if it doesn't
     */

    public boolean containsUpperCaseCharacters() {

        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper function to check if the password contains any upper case Letters
     * @return True if the password contains a upper case character and false if it doesn't
     */

    public boolean containsLowerCaseCharacters() {

        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

}
