public class PasswordValidation {

    private final static String salt = "ANARQ";
    private String password;

    public PasswordValidation (String pswrd) {
        password = pswrd;
    }

    /**
     * Driver function to check if the password meets our requirements
     * @return True if it meets our requirements and false if it doesn't
     */

    public boolean validatePassword(){


        if ((password.length() < 8) || (password.length() > 16)) {
            return false;
        }
        if (!containsNumbers()) {
            return false;
        }
        if (!containsLowerCaseCharacters()) {
            return false;
        }
        if (!containsUpperCaseCharacters()) {
            return false;
        }
        if (!containsSpecialCharacters()) {
            return false;
        }
        return true;
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

        for (int i = 0; i < password.length(); i++) {
            if ((password.charAt(i)+"").equals("@")) {
                return true;
            }
            if ((password.charAt(i)+"").equals("$")) {
                return true;
            }
            if ((password.charAt(i)+"").equals("#")) {
                return true;
            }
            if ((password.charAt(i)+"").equals("&")) {
                return true;
            }
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
