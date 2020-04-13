package com.anarq.spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.miscellaneous.Device;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchPlaylistsRequest;
import com.wrapper.spotify.requests.data.player.GetUsersAvailableDevicesRequest;
import com.wrapper.spotify.requests.data.player.PauseUsersPlaybackRequest;
import com.wrapper.spotify.requests.data.player.StartResumeUsersPlaybackRequest;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
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
    private static String clientId = "ad002d4ba9fa4766b6a6ad03fd440d46"; // This is the Client ID for our Spotify Application
    private static String clientSecret = "e1e7830578254628af9d7b10dca9341e"; // This is the Client Secret for our Spotify Application
    private static URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8888/authorized");
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

    private static GetUsersAvailableDevicesRequest getUsersAvailableDevicesRequest = null;
    private static PauseUsersPlaybackRequest pauseUsersPlaybackRequest = null;
    private static StartResumeUsersPlaybackRequest startResumeUsersPlaybackRequest = null;
    private static AuthorizationCodeRequest authorizationCodeRequest;



    public static void authorizationCode_Sync() {
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void authorizationCodeUri_Sync() {
        final URI uri = authorizationCodeUriRequest.execute();
        Desktop desktop = java.awt.Desktop.getDesktop();
        try {
            desktop.browse(uri);
        } catch(IOException e) {
            System.out.println("IO EXCEPTION");
        }
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        code = line;
        authorizationCodeRequest = spotifyApi.authorizationCode(code)
                .build();
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
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("RIP");
            System.out.println("Error: " + e.getMessage()); // catches any errors and prints exceptions
            return;
        }
    }

    public static void clientCredentials_Async() {
        /*
         *This method executes the client credential request asynchronously, creating a temporary access
         * token to use Spotify features. This is an asynchronous function, so it will allow multiple
         * requests to be executed simultaneously. Therefore, requests will not have to wait for one
         * another to finish execution before starting its own.
         */

        try {
            final CompletableFuture<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final ClientCredentials clientCredentials = clientCredentialsFuture.join(); // unsure of what this does...

            spotifyApi.setAccessToken(clientCredentials.getAccessToken()); // sets access token for further "spotifyApi" usage

            System.out.println("Expires in: " + clientCredentials.getExpiresIn()); // prints how much time before this access token expires
        } catch (CompletionException e) { //catches completion exception which does [unknown]
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) { // catches cancellation exception which does [unknown]
            System.out.println("Async operation cancelled.");
        }
    }

    public static void playback() {
        getUsersAvailableDevicesRequest = spotifyApi.getUsersAvailableDevices().build();
        try {
            Device[] devices = getUsersAvailableDevicesRequest.execute();
            for(int i = 0; i < devices.length; i++) {
                Device temp = devices[i];
                System.out.println("The name of device " + (i + 1) + " is: " + temp.getName());
            }
            pause();
            Thread.sleep(5000);
            resume();

        } catch (IOException | SpotifyWebApiException | InterruptedException pb) {
            System.out.println("Error in Playback: " + pb.getMessage());
        }
    }

    public static void pause() {
        pauseUsersPlaybackRequest = spotifyApi.pauseUsersPlayback().build();
        try {
            String returnValue = pauseUsersPlaybackRequest.execute();
        } catch (IOException | SpotifyWebApiException p) {
            System.out.println("Null: " + p.getMessage());
        }
    }

    public static void resume() {
        startResumeUsersPlaybackRequest= spotifyApi.startResumeUsersPlayback().build();

        try {
            startResumeUsersPlaybackRequest.execute();
        } catch (IOException | SpotifyWebApiException r) {
            System.out.println("There was an error while trying to resume the song");
        }
    }

}