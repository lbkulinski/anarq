package com.anarq.spotify;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.miscellaneous.Device;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.player.*;
import com.wrapper.spotify.requests.data.search.simplified.SearchPlaylistsRequest;
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import com.wrapper.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import com.wrapper.spotify.model_objects.specification.SavedTrack;
import com.wrapper.spotify.requests.data.library.GetUsersSavedTracksRequest;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.data.library.SaveTracksForUserRequest;

import com.anarq.core.Song;

import com.wrapper.spotify.requests.data.tracks.GetTrackRequest;
import org.apache.hc.core5.http.ParseException;

import jdk.nashorn.internal.runtime.ParserException;

import java.io.IOException;
import java.net.URI;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;


public class ClientCredentialsExample {
    /**
     * This class is a basic example of using the Client Credentials Flow
     * to retrieve information from Spotify. The Client Credentials Flow
     * is used in situations where you don't need permissions from the
     * authorized user to perform a task. Examples of this include fetching
     * information for an album, searching for artists/albums, etc.
     * NOTE: This does not return a refresh token
     */

    class MyDevice {
        String name;
        String id;
    }

    private static String clientId = "ad002d4ba9fa4766b6a6ad03fd440d46"; // This is the Client ID for our Spotify Application
    private static String clientSecret = "e1e7830578254628af9d7b10dca9341e"; // This is the Client Secret for our Spotify Application
    private static URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/sessionhost.html");
    private static String code = "";

    public static final SpotifyApi spotifyApi = new SpotifyApi.Builder() // Creating the Spotify API object spotifyApi
            .setClientId(clientId) //setting our client ID
            .setClientSecret(clientSecret) // setting our client secret
            .setRedirectUri(redirectUri)
            .build(); //build parameter
	public final SpotifyApi spotifyApiLocal = new SpotifyApi.Builder() // Creating the Spotify API object spotifyApi
            .setClientId(clientId) //setting our client ID
            .setClientSecret(clientSecret) // setting our client secret
            .setRedirectUri(redirectUri)
            .build(); //build parameter
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials() // This created the request object for the client credential
            .build(); // build parameter
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//          .state("x4xkmn9pu3j6ukrs8n")
          .scope("playlist-modify-public user-read-email streaming user-read-private user-read-playback-state user-modify-playback-state")
//          .show_dialog(true)
            .build();
    private final AuthorizationCodeUriRequest authorizationCodeUriRequestLocal = spotifyApiLocal.authorizationCodeUri()
        .scope("playlist-modify-public user-read-email streaming user-read-private user-read-playback-state user-modify-playback-state")
        .build();
    private static AuthorizationCodeRequest authorizationCodeRequest;
	private AuthorizationCodeRequest authorizationCodeRequestLocal;



    public static void authorizationCode_Sync() { //static
        spotifyApi.setAccessToken(getToken());
    }

    public void authorizationCode() { //local way to authorization code
        spotifyApiLocal.setAccessToken(getTokenLocal());
    }

    public static void reauthorize(String token) { // static, may bbe redundant
        spotifyApi.setAccessToken(token);
    }

    public static String authorizationCodeUri_Sync() { // static
        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

    public String getAuthorizationUri() { //local
        final URI uri = authorizationCodeUriRequestLocal.execute();
        return uri.toString();
    }

    public static void setAuthorizationCodeRequest(String code) { //static
        authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
    }

    public void setAuthCode(String code) { // local
        authorizationCodeRequestLocal = spotifyApiLocal.authorizationCode(code).build();
    }

    public static String getToken() { // static
        try {
            AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
            return authorizationCodeCredentials.getAccessToken();
        } catch (IOException | SpotifyWebApiException | ParseException gt) {
            System.out.println("Error: " + gt.getMessage());
        }
        return null;
    }

    public String getTokenLocal() { //local
        try {
            AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequestLocal.execute();
            return authorizationCodeCredentials.getAccessToken();
        } catch (IOException | SpotifyWebApiException | ParseException gt) {
            System.out.println("Error: " + gt.getMessage());
        }
        return null;
    }

	public void setAccessTokenForLocalAccess(String code) { // local
		
		try {
			
			authorizationCodeRequestLocal = spotifyApiLocal.authorizationCode(code).build();
			AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequestLocal.execute();
			spotifyApiLocal.setAccessToken(authorizationCodeCredentials.getAccessToken());
			
			
			
		} catch (IOException | SpotifyWebApiException | ParseException gt) {
            System.out.println("Error: " + gt.getMessage());
        }
		
	}

    public static void clientCredentials_Sync() {
        /*
         * This method executes the client credential request and creates a temporary access token
         * to use Spotify features. This is a synchronous function, so this will not allow multiple
         * requests to be executed simultaneously. Therefore, requests will wait for one another to
         * finish execution before starting its own.
         */

        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute(); // executes the request for the client credentials object
            spotifyApi.setAccessToken(clientCredentials.getAccessToken()); // Set access token for further "spotifyApi" object usage
            System.out.println("Expires in: " + clientCredentials.getExpiresIn()); // prints how much time before this access token will expire
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage()); // catches any errors and prints exceptions
            return;
        }
    }

    public MyDevice[] getDevices() {
        GetUsersAvailableDevicesRequest getUsersAvailableDevicesRequest = spotifyApiLocal.getUsersAvailableDevices().build();
        try {
            Device[] devices = getUsersAvailableDevicesRequest.execute();
            if(devices.length == 0) {
                return null;
            }
            MyDevice[] deviceList = new MyDevice[devices.length];
            String[] deviceNames = new String[devices.length];
            for(int i = 0; i < devices.length; i++) {
                deviceList[i].name = devices[i].getName();
                deviceList[i].id = devices[i].getId();
            }
            return deviceList;
        } catch (IOException | SpotifyWebApiException | ParseException gd) {
            System.out.println("Error: " + gd.getMessage());
        }
        return null;
    }

    public void chooseDevice(MyDevice[] devices, int i) {
        JsonArray deviceIds = JsonParser.parseString("[\"" + devices[i].name + "\"]").getAsJsonArray();
        TransferUsersPlaybackRequest transferUsersPlaybackRequest = spotifyApiLocal.transferUsersPlayback(deviceIds).build();
        try {
            transferUsersPlaybackRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException cd) {
            System.out.println("Error: " + cd.getMessage());
        }
    }

    public String getSong(String id) {
        GetTrackRequest getTrackRequest = spotifyApiLocal.getTrack(id).build();
        try {
            Track track = getTrackRequest.execute();
            return track.getUri();
        } catch (IOException | SpotifyWebApiException | ParseException gs) {
            System.out.println("Error: " + gs.getMessage());
        }
        return null;
    }

    public void playSongAsNext(String songID) {
        addSong(songID);
        skip();
    }

    public void skip() {
        SkipUsersPlaybackToNextTrackRequest skipUsersPlaybackToNextTrackRequest = spotifyApiLocal.skipUsersPlaybackToNextTrack().build();
        try {
            skipUsersPlaybackToNextTrackRequest.execute();
        } catch(IOException | SpotifyWebApiException | ParseException s) {
            System.out.println("Error: " + s.getMessage());
        }
    }

    public void addSong(String songID) {
        AddItemToUsersPlaybackQueueRequest addItemToUsersPlaybackQueueRequest = spotifyApiLocal.addItemToUsersPlaybackQueue(getSong(songID)).build();
        try {
            String val = addItemToUsersPlaybackQueueRequest.execute();
            System.out.println("Null: " + val);
        } catch(IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void pause() {
        PauseUsersPlaybackRequest pauseUsersPlaybackRequest = spotifyApiLocal.pauseUsersPlayback().build();
        try {
            String returnValue = pauseUsersPlaybackRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException p) {
            System.out.println("Null: " + p.getMessage());
        }
    }


    public void resume() {
        StartResumeUsersPlaybackRequest startResumeUsersPlaybackRequest = spotifyApiLocal.startResumeUsersPlayback().build();

        try {
            startResumeUsersPlaybackRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException r) {
            System.out.println("There was an error while trying to resume the song");
        }
    }
	
	public boolean isTrackCurrentlyPlaying() {
		
		CurrentlyPlaying cp = getUsersCurrentlyPlayingTrack_Async();
		
		if (cp == null) {
			return false;
		}
		else {
			return true;//cp.getIs_playing();
		}
		
	}
	
	public boolean isTimeToSwitchSong() {
		
		CurrentlyPlaying cp = getUsersCurrentlyPlayingTrack_Async();
		
		if (cp == null) {
			return true;
		}
		else {
			return (cp.getProgress_ms() + 3000) > cp.getItem().getDurationMs();
		}
		
	}
	
	public CurrentlyPlaying getUsersCurrentlyPlayingTrack_Async() {
		try {
			
			GetUsersCurrentlyPlayingTrackRequest getUsersCurrentlyPlayingTrackRequest = spotifyApiLocal
				.getUsersCurrentlyPlayingTrack()
				.build();
			
		  final CompletableFuture<CurrentlyPlaying> currentlyPlayingFuture = getUsersCurrentlyPlayingTrackRequest.executeAsync();

		  // Thread free to do other tasks...

		  // Example Only. Never block in production code.
		  return currentlyPlayingFuture.join();
		  
		} catch (CompletionException e) {
		  System.out.println("Error: " + e.getCause().getMessage());
		} catch (CancellationException e) {
		  System.out.println("Async operation cancelled.");
		}
		
		return null;
		
    }
    
    public Song[] getUsersSavedTracks() {
        GetUsersSavedTracksRequest getUsersSavedTracksRequest = spotifyApiLocal.getUsersSavedTracks().build();
        try {
            Paging<SavedTrack> savedTrackPaging = getUsersSavedTracksRequest.execute();
            SavedTrack[] savedTracks = savedTrackPaging.getItems();
            Song[] savedSongs = new Song[savedTracks.length];
            for(int i = 0; i < savedTracks.length; i++) {
                Track temp = savedTracks[i].getTrack();
                String genre = spotifyApiLocal.getArtist(temp.getArtists()[0].getId()).build().execute().getGenres().length > 0 ?
                        spotifyApiLocal.getArtist(temp.getArtists()[0].getId()).build().execute().getGenres()[0] : "N/A";

                savedSongs[i] = new Song(
				temp.getName(),
				temp.getAlbum().getName(),
                temp.getArtists()[0].getName(),
				temp.getArtists()[0].getId(),
				null,
                genre,
				temp.getId(),
				temp.getDurationMs()/1000,
				temp.getIsExplicit(),
                Math.round(spotifyApiLocal.getAudioFeaturesForTrack(temp.getId()).build().execute().getTempo()),
                temp.getAlbum().getImages()[0].getUrl()
                );
            }
            return savedSongs;
        } catch(IOException | SpotifyWebApiException | ParseException gust) {
            System.out.println("Error: " + gust.getMessage());
        }
        return null;
    }

    public void saveSong(String songId) {
        String[] temp = new String[1];
        temp[0] = songId;
        SaveTracksForUserRequest saveTracksForUserRequest = spotifyApiLocal.saveTracksForUser(temp).build();
        try {
            saveTracksForUserRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException ss) {
            
        }
    }
}