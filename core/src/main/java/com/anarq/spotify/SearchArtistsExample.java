package com.anarq.spotify;

import com.anarq.songrequests.*;
import com.anarq.core.*;
import com.wrapper.spotify.requests.data.AbstractDataRequest.*;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchPlaylistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import com.wrapper.spotify.requests.data.browse.GetRecommendationsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SearchArtistsExample {

    private static SpotifyApi spotifyApi = null; // spotifyAPI object for authenticated users
    private static SearchArtistsRequest searchArtistsRequest = null; // object to search for artists
    private static SearchAlbumsRequest searchAlbumsRequest = null; // object to search for albums
    private static SearchTracksRequest searchTracksRequest = null; // object to search for songs
    private static SearchPlaylistsRequest searchPlaylistsRequest = null; // object to search for playlists
    private static String field; // string to search
    static Artist[] artists;
    static boolean artistNotFound;
    static Track[] tracks;
    static boolean trackNotFound;
    static AlbumSimplified[] albums;
    static boolean albumNotFound;
    static PlaylistSimplified[] playlists;
    static boolean playlistNotFound;

    public SearchArtistsExample (SpotifyApi spotifyApi, String field) {
        this.spotifyApi = spotifyApi; // brings in spotifyAPI from client credentials class
        this.field = field; // brings in desired search field
        SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(this.field).limit(12).build(); // searches artists for field
        SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums(this.field).limit(12).build(); // searches albums for the field
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(this.field).limit(12).build(); // searches songs for the field
        SearchPlaylistsRequest searchPlaylistsRequest = spotifyApi.searchPlaylists(this.field).limit(12).build();

        if (spotifyApi == null || searchArtistsRequest == null) {
            System.out.println("Error: Could not verify API Information");
            return;
        }
        try {
            final Paging<Artist> artistPaging = searchArtistsRequest.execute();
            final Paging<AlbumSimplified> albumSimplifiedPaging = searchAlbumsRequest.execute();
            final Paging<Track> trackPaging = searchTracksRequest.execute();
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = searchPlaylistsRequest.execute();

            this.artists = artistPaging.getItems();
            this.artistNotFound = this.artists.length == 0;
            this.albums = albumSimplifiedPaging.getItems();
            this.albumNotFound = this.albums.length == 0;
            this.tracks = trackPaging.getItems();
            this.trackNotFound = this.tracks.length == 0;
            this.playlists = playlistSimplifiedPaging.getItems();
            this.playlistNotFound = this.playlists.length == 0;
        } catch (SpotifyWebApiException | IOException | ParseException e) {
            System.out.println("Error: Spotify Error");
        }
    }

    public PlaylistSimplified searchPlaylist(String name) {

        Paging<PlaylistSimplified> playlistSimplifiedPaging;
        try {
            playlistSimplifiedPaging = searchPlaylistsRequest.execute();
        } catch(SpotifyWebApiException | IOException | ParseException e) {
            System.out.println("Error: Spotify Error");
            return null;
        }
        for(int i = 0; i < playlistSimplifiedPaging.getItems().length; i++) {
            //System.out.println("Playlist: " + playlistSimplifiedPaging.getItems()[i].getName() + " - by " + playlistSimplifiedPaging.getItems()[i].getOwner().getDisplayName());
        }
        if (playlistSimplifiedPaging.getItems().length != 0) {
            return playlistSimplifiedPaging.getItems()[0];
        }
        else {
            return null;
        }
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
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = searchPlaylistsRequest.execute();

            Artist[] artistResults = artistPaging.getItems();
            boolean artistNotFound = artistResults.length == 0;
            AlbumSimplified[] albumResults = albumSimplifiedPaging.getItems();
            boolean albumNotFound = albumResults.length == 0;
            Track[] trackResults = trackPaging.getItems();
            boolean trackNotFound = trackResults.length == 0;
            PlaylistSimplified[] playlistResults = playlistSimplifiedPaging.getItems();
            boolean playlistNotFound = playlistResults.length == 0;
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
                    //System.out.println("'" + artist + "'" + " : Track not found!");
                }
                else {
                    /*
                    System.out.println("Name: " + trackResults[i].getName());
                    System.out.println("Track is in the album: " + (trackResults[i].getAlbum().getName()));
                    System.out.println("Track's Main Artist: " + trackResults[i].getArtists()[i].getName());
                    System.out.println("This track is " + (trackResults[i].getIsExplicit() ? "explicit" : "not explicit"));
                    System.out.println();

                     */
                    String split = ";";


                    out.println(trackResults[i].getId() + split + trackResults[i].getName() + split
                            + trackResults[i].getArtists()[i].getName() + split + trackResults[i].getAlbum().getName());

                }
            }
            //System.out.println("Total: " + artistPaging.getTotal());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
	
	public static Song getRandomReccomendation(String artist, MusicChooser musicChooser) {
		try {
			GetRecommendationsRequest getRecommendationsRequest = spotifyApi.getRecommendations()
			.limit(12)
			.seed_artists(artist)
			.build();
		
			final CompletableFuture<Recommendations> recommendationsFuture = getRecommendationsRequest.executeAsync();

			// Thread free to do other tasks...

			// Example Only. Never block in production code.
			final Recommendations recommendations = recommendationsFuture.join();

			int index = (int) ((float) Math.random() * (float) recommendations.getTracks().length);
			return musicChooser.getSongForSongId(recommendations.getTracks()[index].getId());

		} catch (CompletionException e) {
		  System.out.println("Error: " + e.getCause().getMessage());
		} catch (CancellationException e) {
		  System.out.println("Async operation cancelled.");
		}
		
		return null;
		
	}	

	
}


