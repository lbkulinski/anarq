package com.anarq.core;

import com.anarq.songrequests.*;
import org.springframework.web.bind.annotation.*;

/**
 * A controller for requests made to the AnarQ Application.
 *
 * @version April 23, 2020
 */
@RestController
public class RequestController {
    /**
     * Returns the song queue of the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return the song queue of the session with the specified session ID
     */
    @GetMapping("/current-requests")
    public SongRequest[] getSongQueue(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
                                      @RequestParam(value="userId", defaultValue="no_user_id") String userId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request to terminate session that doesn't exist");

            return null;
        } //end if

        if (!session.hasUserForId(userId)) {
            return null;
        } //end if

        return session.getRequestQueue()
                      .getSongQueue();
    } //getSongQueue

    /**
     * Returns the override requests of the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return the override requests of the session with the specified session ID
     */
    @GetMapping("/override-requests")
    public SongRequest[] getOverrideRequests(
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

        return session.getRequestQueue()
                      .getOverrides();
    } //getOverrideRequests

    /**
     * Returns the connected clients of the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return the connected clients of the session with the specified session ID
     */
    @GetMapping("/current-connected-users")
    public ConnectedClient[] getConnectedClients(
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

        return session.getConnectedClients();
    } //getConnectedClients

    /**
     * Returns the blacklisted users of the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return the blacklisted users of the session with the specified session ID
     */
    @GetMapping("/blacklisted-users")
    public ConnectedClient[] getBlacklistedClients(
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

        return session.getBlacklistedClients();
    } //getBlacklistedClients

    /**
     * Attempts to like the song with the specified song ID in the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param songId the song ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return {@code true}, if the song with the specified song ID was successfully liked and {@code false} otherwise
     */
    @PutMapping("/like-song")
    public boolean likeSong(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
                            @RequestParam(value="songId", defaultValue="no_song_id") String songId,
                            @RequestParam(value="userId", defaultValue="no_user_id") String userId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request to terminate session that doesn't exist");

            return false;
        } //end if

        if (!session.hasUserForId(userId)) {
            return false;
        } //end if

        return session.getRequestQueue()
                      .likeSong(songId, userId);
    } //likeSong

    /**
     * Attempts to dislike the song with the specified song ID in the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param songId the song ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return {@code true}, if the song with the specified song ID was successfully dislike and {@code false}
     * otherwise
     */
    @PutMapping("/dislike-song")
    public boolean dislikeSong(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
                               @RequestParam(value="songId", defaultValue="no_song_id") String songId,
                               @RequestParam(value="userId", defaultValue="no_user_id") String userId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request to terminate session that doesn't exist");

            return false;
        } //end if

        if (!session.hasUserForId(userId)) {
            return false;
        } //end if

        return session.getRequestQueue()
                      .dislikeSong(songId, userId);
    } //dislikeSong
}