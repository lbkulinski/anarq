import java.io.*;
import java.util.Scanner;

public class UsernameValidation {

    String username;

    public UsernameValidation(String name) {
        username = name;
    }

    public boolean hasBadWords () throws IOException{


        BufferedReader br = null;
        try {
//            File f = new File("./BadWords.txt");
            File f = new File("/Users/sidmad/Desktop/Projects Folder/Purdue/Spring 2020/CS 307/src/BadWords.txt");
            br = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException f) {
            System.out.println("\n\n FILE NOT FOUND \n\n");
        }

        String word = null;

        while ((word = br.readLine()) != null) {

//            if (username.contains(word)) {
//                return false;
//            }
            if (contains(word)) {
                return true;
            }
        }
        return false;
    }


    public boolean contains (String word) {

        String name = username.toLowerCase();
        do {

            int position = name.indexOf(word.charAt(0));
            if (position == -1) {
                break;
            }
            try {
                String substring = name.substring(position, (position + word.length()));
                System.out.println("word = " + word + " substring = " + substring);
                if (substring.equalsIgnoreCase(word)) {
                    System.out.println("Bad word present in " + name + " is " + word);
                    return true;
                } else {
                    name = name.substring(position + 1);
                }

            } catch (StringIndexOutOfBoundsException s) {
                return false;
            }
        } while (name.length() > 0);






        return false;
    }

}

