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

import com.wrapper.spotify.requests.data.tracks.GetTrackRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.net.URI;


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
    private static URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/connect.html");
    private static String code = "";

    public static final SpotifyApi spotifyApi = new SpotifyApi.Builder() // Creating the Spotify API object spotifyApi
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

    private static AuthorizationCodeRequest authorizationCodeRequest;



    public static void authorizationCode_Sync() {
        spotifyApi.setAccessToken(getToken());
    }

    public static void reauthorize(String token) {
        spotifyApi.setAccessToken(token);
    }

    public static String authorizationCodeUri_Sync() {
        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

    public static void setAuthorizationCodeRequest(String code) {
        authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
    }

    public static String getToken() {
        try {
            AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
            return authorizationCodeCredentials.getAccessToken();
        } catch (IOException | SpotifyWebApiException | ParseException gt) {
            System.out.println("Error: " + gt.getMessage());
        }
        return null;
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

    public static MyDevice[] getDevices() {
        GetUsersAvailableDevicesRequest getUsersAvailableDevicesRequest = spotifyApi.getUsersAvailableDevices().build();
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

    public static void chooseDevice(MyDevice[] devices, int i) {
        JsonArray deviceIds = JsonParser.parseString("[\"" + devices[i].name + "\"]").getAsJsonArray();
        TransferUsersPlaybackRequest transferUsersPlaybackRequest = spotifyApi.transferUsersPlayback(deviceIds).build();
        try {
            transferUsersPlaybackRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException cd) {
            System.out.println("Error: " + cd.getMessage());
        }
    }

    public static String getSong(String id) {
        GetTrackRequest getTrackRequest = spotifyApi.getTrack(id).build();
        try {
            Track track = getTrackRequest.execute();
            return track.getUri();
        } catch (IOException | SpotifyWebApiException | ParseException gs) {
            System.out.println("Error: " + gs.getMessage());
        }
        return null;
    }

    public static void playSongAsNext(String songID) {
        addSong(songID);
        skip();
    }

    public static void skip() {
        SkipUsersPlaybackToNextTrackRequest skipUsersPlaybackToNextTrackRequest = spotifyApi.skipUsersPlaybackToNextTrack().build();
        try {
            skipUsersPlaybackToNextTrackRequest.execute();
        } catch(IOException | SpotifyWebApiException | ParseException s) {
            System.out.println("Error: " + s.getMessage());
        }
    }

    public static void addSong(String songID) {
        AddItemToUsersPlaybackQueueRequest addItemToUsersPlaybackQueueRequest = spotifyApi.addItemToUsersPlaybackQueue(getSong(songID)).build();
        try {
            String val = addItemToUsersPlaybackQueueRequest.execute();
            System.out.println("Null: " + val);
        } catch(IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void pause() {
        PauseUsersPlaybackRequest pauseUsersPlaybackRequest = spotifyApi.pauseUsersPlayback().build();
        try {
            String returnValue = pauseUsersPlaybackRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException p) {
            System.out.println("Null: " + p.getMessage());
        }
    }


    public static void resume() {
        StartResumeUsersPlaybackRequest startResumeUsersPlaybackRequest = spotifyApi.startResumeUsersPlayback().build();

        try {
            startResumeUsersPlaybackRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException r) {
            System.out.println("There was an error while trying to resume the song");
        }
    }

}