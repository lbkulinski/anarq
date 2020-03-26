package com.anarq.database;

import java.io.*;

public class UsernameValidation {

	private static final int MIN_USERNAME_LENGTH = 3; 
	private static final int MAX_USERNAME_LENGTH = 16; 
    String username;

    public UsernameValidation(String name) {
        username = name;
    }

    /**
     * Function to read all the words from the BadWords.txt file and check if the inputted
     * Username contains any of the words in it
     * @return True if there is a 'bad word' in the username
     * @throws IOException
     */

    public boolean hasBadWords () {

        return NaughtyWords.isANaughtyWord(username);
		
    }

    /**
     * Function to iterate through the database to check if the inputted username has already been taken
     * @return True if the username is already take and False if it isn't
     */

    public boolean isTaken () {

        FindUser user = new FindUser(username);
        if (user.find() == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Driver function to check if the username meets our requirements
     * @return True if it meets our requirements and false if it doesn't
     * @throws Exception
     */

    public String validateUsername() {


        if ((username.length() < MIN_USERNAME_LENGTH)) {
            return "Username must be at least " + MIN_USERNAME_LENGTH + " characters in length!";
        }
		if ((username.length() > MAX_USERNAME_LENGTH)) {
            return "Username must be less than " + MAX_USERNAME_LENGTH + " characters in length!";
        }
        if (isTaken()) {
            return "Username Taken";
        }
        if (hasBadWords()) {
            return "Username cannot contain any innappropriate words!";
        }

     return "Username Ok";
    }

    /**
     * Helper function to check if the inputted Username contains the specified word
     * @param word is the word that user cannot have in the username
     * @return true if a user has any word from the BadWords.txt in their username
     */

    public boolean contains (String word) {

        String name = username.toLowerCase();

        do {
            int position = name.indexOf(word.charAt(0));
            if (position == -1) {
                return false;
            }
            try {
                String substring = name.substring(position, (position + word.length()));
                if (substring.equalsIgnoreCase(word)) {
//                    System.out.println("Bad word present in " + username + " is " + word);
                    return true;
                } else {
                    name = name.substring(position + 1);
                }
            } catch (StringIndexOutOfBoundsException s) {   // If the 'bad word' is longer than the remaining username,
                return false;                               // It cannot be present in the username
            }
        } while (name.length() > 0);

        return false;
    }

}

