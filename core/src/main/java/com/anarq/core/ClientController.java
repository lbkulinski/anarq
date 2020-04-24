package com.anarq.core;

import com.anarq.spotify.*;
import com.anarq.database.*;
import com.anarq.songrequests.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.time.Instant;
import java.time.Duration;

/**
 * A controller for a client associated with the AnarQ Application.
 *
 * @version April 23, 2020
 */
@RestController
public class ClientController {
	/**
     * Attempts to connect the client with the specified username to the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param username the username to be used in the operation
     * @param isAccount the account flag to be used in the operation
     * @return the connected client
     */
	@PutMapping("/connect")
	public ConnectedClient attemptToConnectToSession(
	        @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
            @RequestParam(value="username", defaultValue="no_username") String username,
            @RequestParam(value="isAccount", defaultValue="false") String isAccount) {
	    Session session = CoreApplication.getSessionForSessionId(sessionId);

		if (session == null) {
			System.err.println("Error: Request for non-existant session...\n ID: " + sessionId + " Username: " +
                                       username + "...");

			return null;
		} //end if
		
		if (NaughtyWords.isANaughtyWord(username)) {
			return null;
		} //end if

        ConnectedClient newConnectedClient = new ConnectedClient(username, isAccount.equals("false"),
                                                                 Permission.JAMMER);

		session.addClient(newConnectedClient);

		return newConnectedClient;
	} //attemptToConnectToSession

    /**
     * Attempts to disconnect the client with the specified user ID from the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return {@code true}, if the user with the specified user ID was disconnected from the session with the
     * specified session ID and {@code false} otherwise
     */
	@PutMapping("/disconnect")
	public boolean attemptToCloseConnectionToSession(
	        @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
            @RequestParam(value="userId", defaultValue="no_user_id") String userId) {
	    Session session = CoreApplication.getSessionForSessionId(sessionId);

	    if (session == null) {
	        System.err.println("Error: Request for non-existant session...\n ID: " + sessionId + " UserId: " + userId +
                                       "...");

	        return false;
		} //end if

	    session.removeClient(userId);

	    return true;
	} //attemptToCloseConnectionToSession

	/**
     * Returns whether or not the session with the specified session ID is connected to Spotify.
     *
     * @param sessionId the session ID to be used in the operation
     * @return {@code true}, if the session with the specified session ID is connected to Spotify and {@code false}
     * otherwise
     */
    @GetMapping("/connected-to-spotify-client")
    public boolean isConnectedToSpotify(
            @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
			@RequestParam(value="userId", defaultValue="no_user_id") String userId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return false;
        } //end if

		if (session.hasUserForId(userId)) {

			ConnectedClient c = session.getClientForId(userId);

			return !c.getSpotifyAuthKey().equals("");
		
		}
		
		return false;
		
    } //isConnectedToSpotify

		/**
     * Attempts to save the song with the specified song ID in the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param songId the song ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return {@code true}, if the song with the specified song ID was successfully liked and {@code false} otherwise
     */
    @PutMapping("/save-song")
    public boolean likeSong(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
                            @RequestParam(value="songId", defaultValue="no_song_id") String songId,
                            @RequestParam(value="userId", defaultValue="no_user_id") String userId) {
         Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return false;
        } //end if

		if (session.hasUserForId(userId)) {

			ConnectedClient c = session.getClientForId(userId);

			c.retrieveSpotify().saveSong(songId);
			
			return true;
			
		}

        return false;
    } //saveSong

	/**
     * Attempts to connect the session with the specified session ID to Spotify.
     *
     * @param sessionId the session ID to be used in the operation
     * @return the result of the attempt to connect to Spotify
     */
    @GetMapping("/connect-spotify-client")
    public String connectToSpotify(
            @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
			@RequestParam(value="userId", defaultValue="no_user_id") String userId) {
        return ClientCredentialsExample.authorizationCodeUri_Sync();
    } //connectToSpotify
	
	/**
     * Attempts to connect the session with the specified session ID to Spotify.
     *
     * @param sessionId the session ID to be used in the operation
     * @return the result of the attempt to connect to Spotify
     */
    @GetMapping("/get-saved-tracks")
    public Song[] getSavedTracks(
            @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
			@RequestParam(value="userId", defaultValue="no_user_id") String userId) {
        
		Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return null;
        } //end if

		if (session.hasUserForId(userId)) {

			ConnectedClient c = session.getClientForId(userId);

			System.out.println("I got this far");

			return c.retrieveSpotify().getUsersSavedTracks();
		
		}
		
		return null;
		
    } //connectToSpotify

	/**
     * Attempts to update the Spotify code of the session with the specified session ID with the specified code.
     *
     * @param sessionId the session ID to be used in the operation
	 * @param userId the user ID to be used in the operation
     * @param code the code to be used in the operation
     * @return {@code true}, if the Spotify code of the session with the specified session ID was successfully updated
     * and {@code false} otherwise
     */
    @PutMapping("/set-spotify-code-client")
    public String setSpotifyCodeClient(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
								 @RequestParam(value="userId", defaultValue="no_user_id") String userId,
                                 @RequestParam(value="code", defaultValue="no_code_given") String code) {
        if (code.equals("no_code_given")) {
            return "Failed";
        } //end if

        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return "Failed";
        } //end if

		if (session.hasUserForId(userId)) {

			ConnectedClient c = session.getClientForId(userId);

			c.setSpotifyAuthKey(code);

			c.retrieveSpotify().setAuthCode(code);

			c.retrieveSpotify().authorizationCode();

			return "Success!";
		
		}
		
		else {
		
			return "Failed";
		
		}
		
    } //setSpotifyCode

    /**
     * Returns whether or not the session with the specified session ID is still active and contains the user with the
     * specified user ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return {@code true}, if the session with the specified session ID is still active and contains the user with
     * the specified user ID and {@code false} otherwise
     */
	@GetMapping("/authenticate")
	public boolean isSessionStillActive(
	        @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
            @RequestParam(value="userId", defaultValue="no_user_id") String userId) {
	    Session session = CoreApplication.getSessionForSessionId(sessionId);

	    if (session == null) {
	        System.err.println("Error: Auth Failed.");

	        return false;
		} //end if

        return session.hasUserForId(userId);
    } //isSessionStillActive

    /**
     * Returns the status of the cooldown period for the user with the specified user ID in the session with the
     * specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param username the username to be used in the operation
     * @return the status of the cooldown period for the user with the specified user ID in the session with the
     * specified session ID
     */
    @GetMapping("/cooldown-over")
    public String cooldownOver(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
                               @RequestParam(value="username", defaultValue="default_username") String username) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Auth Failed.");

            return "Failure";
        } else {
            Map<ConnectedClient, Long> cooldownClients = session.getCooldownClients();
            String clientUsername;
            ConnectedClient foundClient = null;

            for (ConnectedClient client : cooldownClients.keySet()) {
                clientUsername = client.getName();

                if (clientUsername.equalsIgnoreCase(username)) {
                    foundClient = client;
                    
                    break;
                } //end if
            } //end for

            if (foundClient != null) {
                long currentTime = System.currentTimeMillis();
                long endTime = cooldownClients.get(foundClient);

                if (currentTime < endTime) {
                    Instant currentInstant;
                    Instant endInstant;
                    Duration remainingTime;
                    int remainingMinutes;
                    int remainingSeconds;
                    String format;

                    currentInstant = Instant.ofEpochMilli(currentTime);

                    endInstant = Instant.ofEpochMilli(endTime);

                    remainingTime = Duration.between(currentInstant, endInstant);

                    remainingMinutes = remainingTime.toMinutesPart();

                    remainingSeconds = remainingTime.toSecondsPart();

                    if (remainingMinutes == 0) {
                        format = "%d seconds";

                        return String.format(format, remainingSeconds);
                    } else if (remainingMinutes == 1) {
                        format = "1 minute and %d seconds";

                        return String.format(format, remainingSeconds);
                    } else {
                        format = "%d minutes and %d seconds";

                        return String.format(format, remainingMinutes, remainingSeconds);
                    } //end if
                } //end if

                session.uncooldownClient(username);

                return "Cooldown Expired";
            } //end if

            return "User Not Found";
        } //end if
    } //cooldownUser
}