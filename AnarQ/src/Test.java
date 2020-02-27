import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Test extends ClientCredentialsExample{
    public static void main(String[] args) {
        /* Search for song; top choice will be added to queue */
        clientCredentials_Sync();
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader("test_search_failure.txt"));
        } catch(IOException e) {
            System.out.println("Test File Not Found");
            return;
        }
        String field = "";
        PrintWriter out = null;
        try{
            out = new PrintWriter("music_attributes.txt");
        } catch(IOException f) {
            System.out.println("File was not found!");
            return;
        }
        while(true) {
            //System.out.print("What would you like to search for? ");
            try {
                field = input.readLine();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
            if (field.equals("exit")) {
                break;
            }
            SearchArtistsExample search = new SearchArtistsExample(spotifyApi, field);
            search.search(out);
        }
        out.close();

        /* all songs' info is in file, parse data to SongRequest class */
        ArrayList<SongRequest> songs = new ArrayList<SongRequest>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("music_attributes.txt"));
            String line = reader.readLine();
            int id = 0;
            while(line != null) {
                String lineParts[] = line.split(";,;");
                String song = lineParts[1];
                String artist = lineParts[2];
                String album = lineParts[3];
                songs.add(new SongRequest(id, album, song, artist, "genre", "ip"));
                id++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestQueue queue = new RequestQueue();
        for(int i = 0; i < songs.size(); i++) {
            queue.addSong(songs.get(i));
        }
        queue.printQueue();

        authorizationCodeUri_Sync();
        authorizationCode_Sync();


    }

    public static void testSameSong() {

    }

}
