import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.miscellaneous.PlaylistTracksInformation;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Test extends ClientCredentialsExample{
    public static void main(String[] args) {
        /* Search for song; top choice will be added to queue */
        clientCredentials_Sync();
        /**
        authorizationCode_Sync();
        authorizationCodeUri_Sync();
        **/
        while(true) {
            //check for search input
                //if search_request_token.txt has value "1"
                    //commence search for string in search_request_field.txt
                    //after searching, return all relevant info to search_result.txt
                    //wait for user's choice, frontend will put 'album' or 'track' or 'playlist' or 'artist' followed by a space and the index number in search_choice.txt
                    //delete contents of file search_request_token.txt and search_request_field.txt
                //if search_request_token.txt has no contents, do nothing




            SearchArtistsExample search = new SearchArtistsExample(spotifyApi, "love");
        }
        //search object contains all search information to be stored in any way desired

        /**
        clientCredentials_Sync();
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.println("quit, search song, search playlist, or create playlist");
            String line = input.nextLine();
            if(line.equals("quit")) {
                System.out.println("Goodbye!");
                break;
            }
            else if (line.equals("search song")) {
                searchSongDemo();
            }
            else if (line.equals("search playlist")) {
                searchPlaylistDemo();
            }
            else if (line.equals("create playlist")) {
                authorizationCodeUri_Sync();
                authorizationCode_Sync();
                System.out.println("What do you want the name of your playlist to be?");
                createPlaylistDemo(input.nextLine());
            }
        }
        **/

        //
        //addToQueue();
        //searchSongDemo();
        /* all songs' info is in file, parse data to SongRequest class */



        //QueueUI display = new QueueUI();
        //display.populateQueue(queue.songQueue);


    }

    public static void searchSongDemo() {
        //Allow for searches until you type exit into file music_attributes
        Scanner input = new Scanner(System.in);
        PrintWriter out = null;
        try {
            out = new PrintWriter("music_attributes.txt");
        } catch(IOException e) {
            System.out.println("Error: File not found!");
            return;
        }
        System.out.println("What songs would you like to search for?");
        String inputLine = input.nextLine();
        while(!inputLine.equals("exit")) {
            //search input line
            SearchArtistsExample searchDemo = new SearchArtistsExample(spotifyApi, inputLine);
            searchDemo.search(out);
            inputLine= input.nextLine();
        }
        out.close();

        //Display contents of music_attributes
        //add them to the queue
        //addToQueue();

        //Search for and find a playlist

        //Make a playlist
        //Print contents of playlist, and its attributes

    }

    public static void searchPlaylistDemo() {
        Scanner input = new Scanner(System.in);
        SearchArtistsExample search = new SearchArtistsExample(spotifyApi, "filler");
        System.out.println("What is the name of the playlist you would like to access?");
        PlaylistSimplified pl = search.searchPlaylist(input.nextLine());
        if(pl == null) {
            System.out.println("Error: Playlist not found!");
            return;
        }
        PlaylistTracksInformation playlistTrackInfo = pl.getTracks();
        GetPlaylistsTracksRequest plTrackRequest = spotifyApi.getPlaylistsTracks(pl.getId()).build();
        Paging<PlaylistTrack> plTP;
        try {
            plTP = plTrackRequest.execute();
        } catch(SpotifyWebApiException | IOException e) {
            System.out.println("Error: Spotify API and Web Error");
            return;
        }
        System.out.println("==========================PLAYLIST==========================");
        for(int i = 0; i < plTP.getItems().length; i++) {
            Track temp = plTP.getItems()[i].getTrack();
            System.out.println(temp.getName() + " - " + temp.getArtists()[0].getName() + (temp.getIsExplicit() ? " (explicit)" : " (not explicit)") + " - Album: " + temp.getAlbum().getName());
        }
        System.out.println("============================================================");

    }

    public static void createPlaylistDemo(String playlistName) {
        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
        User user = null;
        try {
            user = getCurrentUsersProfileRequest.execute();
        } catch (SpotifyWebApiException | IOException e) {
            System.out.println("Error! Profile Request not executed!");
        }

        CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(user.getDisplayName(), playlistName)
          .collaborative(false)
          .public_(true)
          .description("Demo for Sprint 1!")
                .build();
        try {
            Playlist playlist = createPlaylistRequest.execute();

        } catch (SpotifyWebApiException | IOException f) {
            System.out.println("Error! "+ f.getMessage());
            return;
        }
        System.out.println("Playlist created!");
    }

    public static void searchToFile(String inputName) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(inputName));
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
    }
/*
    public static void addToQueue() {
        ArrayList<SongRequest> songs = new ArrayList<SongRequest>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("music_attributes.txt"));
            String line = reader.readLine();
            int id = 0;
            while(line != null) {
                String[] lineParts = line.split(";");
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
    }
*/
    public static void test_file(String filename) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException fnf) {
            System.out.println("File was not found!");
        }
    }

}
