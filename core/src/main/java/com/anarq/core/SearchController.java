package com.anarq.core;

import com.anarq.spotify.*;
import org.springframework.web.bind.annotation.*;

/**
 * A controller for searches made to the AnarQ Application.
 *
 * @version April 23, 2020
 */
@RestController
public class SearchController {
    /**
     * Returns the songs that contain the specified search query.
     *
     * @param sessionId the session ID to be used in the operation
     * @param query the query to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return the songs that contain the specified search query
     */
    @GetMapping("/search")
    public Song[] searchForSongsWithQuery(
            @RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
            @RequestParam(value="query", defaultValue="no_query") String query,
            @RequestParam(value="userId", defaultValue="no_user_id") String userId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return null;
        } //end if

        if (!session.hasUserForId(userId)) {
            return null;
        } //end if

        return session.getMusicChooser()
                      .searchForSongs(query);
    } //searchForSongsWithQuery

    /**
     * Attempts to request the song with the specified song ID in the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @param songId the song ID to be used in the operation
     * @param userId the user ID to be used in the operation
     * @return {@code true}, if the song with the specified song ID was successfully requested and {@code false}
     * otherwise
     */
    @PutMapping("/request-song")
    public boolean addSongRequest(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
                                  @RequestParam(value="songId", defaultValue="no_song_id") String songId,
                                  @RequestParam(value="userId", defaultValue="no_user_id") String userId) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Request for non-existant session was created!\n ID: " + sessionId);

            return false;
        } //end if

        if (!session.hasUserForId(userId)) {
            return false;
        } //end if
		
		System.out.println(songId);
		
		if (SpotifyGateway.getSongForSongId(songId) == null) {
			return false;
		}			
		
        return session.requestSong(SpotifyGateway.getSongForSongId(songId), userId);
    } //addSongRequest
}