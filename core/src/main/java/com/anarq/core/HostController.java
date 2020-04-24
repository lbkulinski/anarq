package com.anarq.core;

import com.anarq.spotify.*;
import com.anarq.songrequests.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * A controller for a host associated with the AnarQ Application.
 *
 * @version April 23, 2020
 */
@RestController
public class HostController {
    /**
     * Attempts to host a new session.
     *
     * @return the host of the new session
     */
    @PutMapping("/host-new-session")
    public ConnectedClient attemptToHostNewSession() {
        Session session = CoreApplication.createNewSession();

        if (session == null) {
            System.err.println("Error: Request to create new session failed...");

            return null;
        } //end if

        return session.getHostClient();
    } //attemptToHostNewSession

    /**
     * Updates the preferences of the session with the specified session ID with the specified preferences.
     *
     * @param sessionId the session ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @param maxBPM the max BPM to be used in the operation
     * @param minBPM the min BPM to be used in the operation
     * @param dislikeThreshold the dislike threshold to be used in the operation
     * @param explicit the explicitness to be used in the operation
     * @param requests the requests to be used in the operation
     * @param visible the visibility to be used in the operation
     * @param pop the pop flag to be used in the operation
     * @param rock the rock flag to be used in the operation
     * @param country the country flag to be used in the operation
     * @param jazz the jazz flag to be used in the operation
     * @param rap the rap flag to be used in the operation
     * @param metal the metal flag to be used in the operation
     * @param rb the R&B flag to be used in the operation
     * @param hiphop the hip hop flag to be used in the operation
     * @param electronic the electronic flag to be used in the operation
     * @param christian the christian flag to be used in the operation
     * @return {@code true}, if the preferences were successfully updated and {@code false} otherwise
     */
    @PutMapping("/update-preferences")
    public boolean setPreferences(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
                                  @RequestParam(value="userId", defaultValue="no_user_id") String userId,
                                  @RequestParam(value="maxBPM", defaultValue="400") int maxBPM,
                                  @RequestParam(value="minBPM", defaultValue="32") int minBPM,
                                  @RequestParam(value="dislikeThreshold", defaultValue="10") int dislikeThreshold,
                                  @RequestParam(value="allowExplicit", defaultValue="true") boolean explicit,
                                  @RequestParam(value="allowRequests", defaultValue="true") boolean requests,
                                  @RequestParam(value="isVisible", defaultValue="true") boolean visible,
                                  @RequestParam(value="allowPop", defaultValue="true") boolean pop,
                                  @RequestParam(value="allowRock", defaultValue="true") boolean rock,
                                  @RequestParam(value="allowCountry", defaultValue="true") boolean country,
                                  @RequestParam(value="allowJazz", defaultValue="true") boolean jazz,
                                  @RequestParam(value="allowRap", defaultValue="true") boolean rap,
                                  @RequestParam(value="allowMetal", defaultValue="true") boolean metal,
                                  @RequestParam(value="allowRB", defaultValue="true") boolean rb,
                                  @RequestParam(value="allowHipHop", defaultValue="true") boolean hiphop,
                                  @RequestParam(value="allowElectronic", defaultValue="true") boolean electronic,
                                  @RequestParam(value="allowChristian", defaultValue="true") boolean christian) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request to terminate session that doesn't exist");

            return false;
        } //end if

        if (!session.hasUserForId(userId)) {
            return false;
        } //end if

        System.out.println("Updating preferences....");

        session.getRequestQueue().setMaxBPM(maxBPM);

        session.getRequestQueue().setMinBPM(minBPM);

        session.getRequestQueue().setDislikeThreshold(dislikeThreshold);

        session.getRequestQueue().setExplicitFilter(explicit);

        session.getRequestQueue().setAcceptingRequests(requests);

        session.getRequestQueue().setVisibility(visible);

        session.getRequestQueue().setBlacklistedGenres(new boolean[] {pop, rock, country, jazz, rap, metal, rb, hiphop,
                electronic, christian});

        return true;
    } //setPreferences

    /**
     * Returns the preferences of the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return the preferences of the session with the specified session ID
     */
    @GetMapping("/get-preferences")
    public PreferencePacket getPreferences(
            @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
            @RequestParam(value="userId", defaultValue="no_user_id") String userId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request to terminate session that doesn't exist");

            return null;
        } //end if

        if (!session.hasUserForId(userId)) {
            return null;
        } //end if

        return session.getRequestQueue().getPreferencePacket();
    } //getPreferences

    /**
     * Returns the QR Code of the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return the QR Code of the session with the specified session ID
     */
    @GetMapping("/get-qr-code")
    public byte[] getQrCode(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
                            @RequestParam(value="userId", defaultValue="no_user_id") String userId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request to terminate session that doesn't exist");

            return null;
        } //end if

        if (!session.hasUserForId(userId)) {
            return null;
        } //end if

        return session.getQrCode();
    } //getQrCode

    /**
     * Attempts to terminate the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return {@code true}, if the session with the specified session ID was successfully terminated and {@code false}
     * otherwise
     */
    @PutMapping("/terminate-session")
    public boolean attemptToCloseConnectionToSession(
            @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
            @RequestParam(value="userId", defaultValue="no_user_id") String userId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request to terminate session that doesn't exist");

            return false;
        } //end if

        if (!session.hasUserForId(userId)) {
            return false;
        } //end if

        session.terminate();

        CoreApplication.terminateSession(sessionId);

        return true;
    } //attemptToCloseConnectionToSession

    /**
     * Attempts to delete the song with the specified song ID from the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param songId the song ID to be used in the operation
     * @return {@code true}, if the song with the specified song ID was successfully deleted and {@code false}
     * otherwise
     */
    @PutMapping("/delete-request")
    public boolean deleteSongRequest(
            @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
            @RequestParam(value="songId", defaultValue="no_song_id") String songId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return false;
        } //end if`

        String artist = session.getMusicChooser()
                               .getSongForSongId(songId)
                               .getArtistName();

        // AUTODJ

        return session.deleteSongRequest(songId);
    } //deleteSongRequest

    /**
     * Attempts to connect the session with the specified session ID to Spotify.
     *
     * @param sessionId the session ID to be used in the operation
     * @return {@code true}, if the session with the specified session ID was successfully connected to Spotify and
     * {@code false} otherwise
     */
    @GetMapping("/connect-spotify")
    public String connectToSpotify(
            @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId) {
        return ClientCredentialsExample.authorizationCodeUri_Sync();
    } //connectToSpotify

    /**
     * Returns the current song of the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @return the current song of the session with the specified session ID
     */
    @GetMapping("/get-current-song")
    public Song gerCurrentSong(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return null;
        } //end if

        if (session.getRequestQueue().getCurrentSong() != null)  {
            return session.getRequestQueue().getCurrentSong().getSongInfo();
        } //end if

        return null;
    } //gerCurrentSong

    /**
     * Returns whether or not the session with the specified session ID is connected to Spotify.
     *
     * @param sessionId the session ID to be used in the operation
     * @return {@code true}, if the session with the specified session ID is connected to Spotify and {@code false}
     * otherwise
     */
    @GetMapping("/connected-to-spotify")
    public boolean isConnectedToSpotify(
            @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return false;
        } //end if

        return !session.getSpotifyAuthKey().equals("");
    } //isConnectedToSpotify

    /**
     * Attempts to update the Spotify code of the session with the specified session ID with the specified code.
     *
     * @param sessionId the session ID to be used in the operation
     * @param code the code to be used in the operation
     * @return {@code true}, if the Spotify code of the session with the specified session ID was successfully updated
     * and {@code false} otherwise
     */
    @PutMapping("/set-spotify-code")
    public String setSpotifyCode(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
                                 @RequestParam(value="code", defaultValue="no_code_given") String code) {
        if (code.equals("no_code_given")) {
            return "Failed";
        } //end if

        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return "Failed";
        } //end if

        session.setSpotifyAuthKey(code);

        session.getSpotify().setAuthCode(code);

        session.getSpotify().authorizationCode();

        return "Success!";
    } //setSpotifyCode

    /**
     * Attempts to approve the override request of the song with the specified song ID in the session with the
     * specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param songId the song ID to be used in the operation
     * @return {@code true}, if the override request of the song with the specified song ID was successfully approved
     * and {@code false} otherwise
     */
    @PutMapping("/approve-override-request")
    public boolean approveOverrideRequest(
            @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
            @RequestParam(value="songId", defaultValue="no_song_id") String songId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return false;
        } //end if

        String artist = session.getMusicChooser()
                               .getSongForSongId(songId)
                               .getArtistName();

        return session.approveOverrideSongRequest(songId);
    } //approveOverrideRequest

    /**
     * Attempts to place the song with the specified song ID in a cooldown period in the session with the specified
     * session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param songId the song ID to be used in the operation
     * @return {@code true}, if the song with the specified song ID was successfully placed in a cooldown period and
     * {@code false} otherwise
     */
    @PutMapping("/add-song-to-cooldown")
    public boolean addSongToCooldown(
            @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
            @RequestParam(value="songId", defaultValue="no_song_id") String songId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return false;
        } //end if

        session.getSongCooldownManager()
               .addSong(SpotifyGateway.getSongForSongId(songId), Session.SONG_COOLDOWN_TIME);

        return true;
    } //addSongToCooldown

    /**
     * Attempts to pause the song playing in the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @return {@code true}, if the song playing in the session with the specified session ID was successfully paused
     * and {@code false} otherwise
     */
    @PutMapping("/pause")
    public boolean pauseSong(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return false;
        } //end if

        session.getSpotify()
               .pause();

        return true;
    } //pauseSong

    /**
     * Attempts to resume the song playing in the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @return {@code true}, if the song playing in the session with the specified session ID was successfully resumed
     * and {@code false} otherwise
     */
    @PutMapping("/resume")
    public boolean resumeSong(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return false;
        } //end if

        session.getSpotify()
               .resume();

        return true;
    } //resumeSong

    @PutMapping("/delete-override-request")
    public boolean deleteOverrideRequest(
            @RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
            @RequestParam(value="songId", defaultValue="no_song_id")String songId) {

        // Attempt to obtain the given session based on the sessionId provided
        Session session = CoreApplication.getSessionForSessionId(sessionId);
        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: "
                                       + sessionId);
            return false;
        }

        String artist = session.getMusicChooser().getSongForSongId(songId).getArtistName();
        boolean ret = session.deleteOverrideSongRequest(songId);

        return ret;
    }

    @PutMapping("/kick-user")
    public boolean kickUser(
            @RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
            @RequestParam(value="userId", defaultValue="no_song_id")String userId) {

        // Attempt to obtain the given session based on the sessionId provided
        Session session = CoreApplication.getSessionForSessionId(sessionId);
        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: "
                                       + sessionId);
            return false;
        }

        return session.removeClient(userId);

    }

    @PutMapping("/blacklist-user")
    public boolean blacklistUser(
            @RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
            @RequestParam(value="userId", defaultValue="no_song_id")String userId) {

        // Attempt to obtain the given session based on the sessionId provided
        Session session = CoreApplication.getSessionForSessionId(sessionId);
        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: "
                                       + sessionId);
            return false;
        }

        return session.blacklistClient(userId);

    }

    @PutMapping("/unblacklist-user")
    public boolean unblacklistUser(
            @RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
            @RequestParam(value="userId", defaultValue="no_song_id")String userId) {

        // Attempt to obtain the given session based on the sessionId provided
        Session session = CoreApplication.getSessionForSessionId(sessionId);
        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: "
                                       + sessionId);
            return false;
        }

        return session.unblacklistClient(userId);

    }

    @GetMapping("/authenticate-host")
    public boolean isSessionStillActive(
            @RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId) {

        // Attempt to obtain the given session based on the sessionId provided
        Session session = CoreApplication.getSessionForSessionId(sessionId);
        if (session == null) {
            System.err.println("Error: Auth Failed.");
            return false;
        }

        // Redirect
        return true;

    }

    @GetMapping("/cooldown-user")
    public String cooldownUser(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
                               @RequestParam(value="username", defaultValue="default_username") String username,
                               @RequestParam(value="period", defaultValue="default_period") String periodString) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Auth Failed.");

            return "Failure";
        } else {
            String clientUsername;
            ConnectedClient foundClient = null;

            for (ConnectedClient client : session.getConnectedClients()) {
                clientUsername = client.getName();

                if (clientUsername.equalsIgnoreCase(username)) {
                    foundClient = client;

                    break;
                } //end if
            } //end if

            if (foundClient != null) {
                long period;
                boolean added;
                Map<ConnectedClient, Long> cooldownClients;
                long currentTime;
                long endTime;

                try {
                    period = Long.parseLong(periodString);
                } catch (NumberFormatException e) {
                    return "Period Not Integer";
                } //end try catch

                if (period <= 0) {
                    return "Period Out Of Bounds";
                } //end if

                cooldownClients = session.getCooldownClients();

                if (cooldownClients.containsKey(foundClient)) {
                    return "Already In Cooldown";
                } //end if

                currentTime = System.currentTimeMillis();

                endTime = currentTime + (period * 60_000);

                added = session.cooldownClient(username, endTime);

                return (added) ? "Success" : "Failure";
            } //end if

            return "User Not Found";
        } //end if
    } //cooldownUser
}