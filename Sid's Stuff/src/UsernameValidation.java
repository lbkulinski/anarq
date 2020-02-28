import java.io.*;

public class UsernameValidation {

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

    public boolean hasBadWords () throws IOException{

        BufferedReader br = null;
        try {
//            File f = new File("./BadWords.txt");
            File f = new File("/Users/sidmad/Desktop/Projects Folder/Purdue/Spring 2020/CS 307/src/BadWords.txt");
            br = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException f) {
            System.out.println("\n\n FILE NOT FOUND \n\n");
        }
        String word;
        while ((word = br.readLine()) != null) {
            if (contains(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function to iterate through the database to check if the inputted username has already been taken
     * @return True if the username is already take and False if it isn't
     */

    public boolean isTaken () {

        findUser user = new findUser(username);
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

    public boolean validateUsername() throws Exception{


        if ((username.length() < 1) || (username.length() > 16)) {
            return false;
        }
        if (isTaken()) {
            return false;
        }
        if (hasBadWords()) {
            return false;
        }

     return true;
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

