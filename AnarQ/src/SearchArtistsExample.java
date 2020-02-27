import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SearchArtistsExample {

    private static SpotifyApi spotifyApi = null;
    private static SearchArtistsRequest searchArtistsRequest = null;
    private static SearchAlbumsRequest searchAlbumsRequest = null;
    private static SearchTracksRequest searchTracksRequest = null;
    private static String artist;

    public SearchArtistsExample (SpotifyApi spotifyApi, String artist) {
        this.spotifyApi = spotifyApi;
        this.artist = artist;
        this.searchArtistsRequest = spotifyApi.searchArtists(this.artist).limit(10).build();
        this.searchAlbumsRequest = spotifyApi.searchAlbums(this.artist).limit(10).build();
        this.searchTracksRequest = spotifyApi.searchTracks(this.artist).limit(10).build();
    }

    public void search(PrintWriter out) {
        if (spotifyApi == null || searchArtistsRequest == null) {
            System.out.println("Error: Could not verify API Information");
            return;
        }
        try {
            final Paging<Artist> artistPaging = searchArtistsRequest.execute();
            final Paging<AlbumSimplified> albumSimplifiedPaging = searchAlbumsRequest.execute();
            final Paging<Track> trackPaging = searchTracksRequest.execute();

            Artist[] artistResults = artistPaging.getItems();
            boolean artistNotFound = artistResults.length == 0;
            AlbumSimplified[] albumResults = albumSimplifiedPaging.getItems();
            boolean albumNotFound = albumResults.length == 0;
            Track[] trackResults = trackPaging.getItems();
            boolean trackNotFound = trackResults.length == 0;
            for(int i = 0; i < 1; i++) {
                /*
                System.out.println("=========================\n        TOP ARTIST        \n=========================");
                if(artistNotFound) {
                    System.out.println("Artist not found!");
                }
                else {
                    System.out.println("Artist Name: " + artistResults[i].getName());
                    System.out.println("Artist's Main Genre: " + ((artistResults[i].getGenres().length > 0) ? artistResults[i].getGenres()[0] : "[Genre not found]"));
                    System.out.println("Artist's Popularity: " + artistResults[i].getPopularity());
                    System.out.println("Artist's Followers: " + artistResults[i].getFollowers().getTotal());
                    System.out.println();
                }
                System.out.println("=========================\n        TOP ALBUM        \n=========================");
                if(albumNotFound) {
                    System.out.println("Album not found!");
                }
                else {
                    System.out.println("Album Name: " + albumResults[i].getName());
                    System.out.println("Album Release Date: " + albumResults[i].getReleaseDate());
                    System.out.println();
                }
                */

                //System.out.println("=========================\n        TOP TRACK        \n=========================");
                if(trackNotFound) {
                    System.out.println("'" + artist + "'" + " : Track not found!");
                }
                else {
                    /*
                    System.out.println("Name: " + trackResults[i].getName());
                    System.out.println("Track is in the album: " + (trackResults[i].getAlbum().getName()));
                    System.out.println("Track's Main Artist: " + trackResults[i].getArtists()[i].getName());
                    System.out.println("This track is " + (trackResults[i].getIsExplicit() ? "explicit" : "not explicit"));
                    System.out.println();

                     */
                    String split = ";,;";


                    out.println(trackResults[i].getId() + split + trackResults[i].getName() + split
                            + trackResults[i].getArtists()[i].getName() + split + trackResults[i].getAlbum().getName());


                }
            }
            //System.out.println("Total: " + artistPaging.getTotal());
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


