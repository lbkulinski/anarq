package com.anarq.core;

import com.anarq.qr.*;
import com.anarq.spotify.*;
import java.util.*;
import com.anarq.songrequests.*;

/**
 * A session associated with the AnarQ Application.
 *
 * @version April 23, 2020
 */
public class Session {
    /**
     * The time used for a song placed in a cooldown period.
     */
    public static final int SONG_COOLDOWN_TIME = 300;

    /**
     * The named of the Auto DJ.
     */
    public static final String AUTODJ_NAME = "AutoDJ";

    /**
     * The ID of this session.
     */
    private final String sessionId;

    /**
     * The Spotify authentication key of this session.
     */
    private String spotifyAuthKey = "";

    /**
     * The Spotify instance of this session.
     */
    private final ClientCredentialsExample spotify;

    /**
     * The auto DJ of this session.
     */
    private final AutoDJ autoDJ;

    /**
     * The music chooser of this session.
     */
    private final MusicChooser musicChooser;

    /**
     * The request queue of this session.
     */
    private final RequestQueue requestQueue;

    /**
     * The connected clients of this session.
     */
    private final List<ConnectedClient> connectedClients;

    /**
     * The blacklisted clients of this session.
     */
    private final List<ConnectedClient> blacklistedIds;

    /**
     * The cooldown clients of this session.
     */
    private final Map<ConnectedClient, Long> cooldownClients;

    /**
     * The cooldown song of this session.
     */
    private final CooldownSong cooldownSong;

    /**
     * The host client of this session.
     */
    private final ConnectedClient hostClient;

    /**
     * The QR Code of this session.
     */
    private byte[] qrCode;

    /**
     * The thread of this session.
     */
    private Thread sessionThread;

    /**
     * Whether or not this session is running.
     */
    private boolean isRunning = true;

    /**
     * Constructs a newly allocated {@code Session} object.
     */
    public Session() {
        sessionId = String.format("%06X", (int) ((float) Math.random() * 10000000.0f));

        System.out.println("New Session created with ID " + sessionId + ".");

        spotify = new ClientCredentialsExample();

        autoDJ = new AutoDJ(this);

        musicChooser = new MusicChooser();

        requestQueue = new RequestQueue();

        connectedClients = new ArrayList<>();

        blacklistedIds = new ArrayList<>();

        cooldownClients = new HashMap<>();

        cooldownSong = new CooldownSong();

        hostClient = new ConnectedClient("HOST", true, sessionId);

        connectedClients.add(hostClient);

        qrCodeGenerator qr = new qrCodeGenerator();

        try {
            qrCode = qr.getQRCodeImage(sessionId);
        } catch (Exception e) {
            qrCode = null;
        } //end try catch

        System.out.println("\n\nQR CODE: " + qrCode + "\n\n");

        sessionThread = new Thread() {
            boolean primed = false;

            public void run() {
                while (isRunning) {
                    if (requestQueue.getCurrentSong() == null || !spotify.isTrackCurrentlyPlaying()) {
                        if (!requestQueue.isEmpty()) {
                            spotify.playSongAsNext(requestQueue.playNextSong().getId());

                            spotify.resume();
                        } //end if
                    } //end if

                    if (getRequestQueue().isEmpty() && requestQueue.autoDJ) {
                        requestSong(getAutoDJ().getSongReccomendation(), Session.AUTODJ_NAME);
                    } //end if

                    if (spotify.isTimeToSwitchSong() ) {
                        if (!requestQueue.isEmpty() && !primed) {
                            spotify.addSong(requestQueue.playNextSong().getId());

                            spotify.resume();

                            primed = true;
                        } //end if
                    } else {
                        primed = false;
                    } //end if

                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } //end try catch
                } //end while
            } //run
        };

        sessionThread.start();
    } //Session

    /**
     * Terminates this session.
     */
    public void terminate() {
        isRunning = false;
    } //terminate

    /**
     * Returns the cooldown song of this session.
     *
     * @return the cooldown song of this session
     */
    public CooldownSong getSongCooldownManager() {
        return cooldownSong;
    } //getSongCooldownManager

    /**
     * Attempts to request the specified song in this session by the requester with the specified requester ID.
     *
     * @param song the song to be used in the operation
     * @param requesterId the requester ID to be used in the operation
     * @return {@code true}, if the specified song was successfully requested and {@code false} otherwise
     */
    public boolean requestSong(Song song, String requesterId) {
        if (!getSongCooldownManager().canSongBePlayed(song)) {
            return false;
        } //end if

        SongRequest newSongRequest = new SongRequest(song, requesterId);

        if (!requesterId.equals(AUTODJ_NAME)) {
            autoDJ.addGenre(song.getSongGenre());

            autoDJ.addBPM(song.getBPM());

            autoDJ.addArtist(song.getArtistID());
        } //end if

        return requestQueue.addSong(newSongRequest);
    } //requestSong

    /**
     * Attempts to remove the song request with the specified song request ID from this session.
     *
     * @param songRequestId the song request ID to be used in the operation
     * @return {@code true}, if the song request with the specified song request ID was successfully removed and
     * {@code false} otherwise
     */
    public boolean deleteSongRequest(String songRequestId) {
        return requestQueue.removeSong(requestQueue.getSongFromQueue(songRequestId), hostClient);
    } //deleteSongRequest

    /* Removes a song request from the request queue */

    /**
     * Attempts to delete the override request for the song request with the specified song request ID.
     *
     * @param songRequestId the song request ID to be used in the operation
     * @return {@code true}, if the song request with the specified song request ID was successfully deleted and
     * {@code false} otherwise
     */
    public boolean deleteOverrideSongRequest(String songRequestId) {
        requestQueue.removeOverride(requestQueue.getSongFromQueue(songRequestId));

        return true;
    } //deleteOverrideSongRequest

    /**
     * Attempts to approve the override request for the song request with the specified song request ID.
     *
     * @param songRequestId the song request ID to be used in the operation
     * @return {@code true}, if the song request with the specified song request ID was successfully approved and
     * {@code false} otherwise
     */
    public boolean approveOverrideSongRequest(String songRequestId) {
        requestQueue.approveOverride(requestQueue.getSongFromQueue(songRequestId));

        return true;
    } //approveOverrideSongRequest

    /**
     * Returns the Spotify instance of this session.
     *
     * @return the Spotify instance of this session.
     */
    public ClientCredentialsExample getSpotify() {
        return spotify;
    } //getSpotify

    /**
     * Updates the Spotify authentication key of this session with the specified Spotify authentication key.
     *
     * @param key the Spotify authentication key
     */
    public void setSpotifyAuthKey(String key) {
        spotifyAuthKey = key;
    } //setSpotifyAuthKey

    /**
     * Returns the Spotify authentication key of this session.
     *
     * @return the Spotify authentication key of this session
     */
    public String getSpotifyAuthKey() {
        return spotifyAuthKey;
    } //getSpotifyAuthKey

    /**
     * Returns the ID of this session.
     *
     * @return the ID of this session
     */
    public String getSessionId() {
        return sessionId;
    } //getSessionId

    /**
     * Returns the auto DJ of this session.
     *
     * @return the auto DJ of this session
     */
    public AutoDJ getAutoDJ() {
        return autoDJ;
    } //getAutoDJ

    /**
     * Returns the music chooser of this session.
     *
     * @return the music chooser of this session
     */
    public MusicChooser getMusicChooser() {
        return musicChooser;
    } //getMusicChooser

    /**
     * Returns the request queue of this session.
     *
     * @return the request queue of this session
     */
    public RequestQueue getRequestQueue() {
        return requestQueue;
    } //getRequestQueue

    /**
     * Returns the host client of this session.
     *
     * @return the host client of this session
     */
    public ConnectedClient getHostClient() {
        return hostClient;
    } //getHostClient

    /**
     * Returns the QR Code of this session.
     *
     * @return the QR Code of this session
     */
    public byte[] getQrCode() {
        return qrCode;
    } //getQrCode

    /**
     * Attempts to blacklist the user with the specified user ID in this session.
     *
     * @param userId the user ID to be used in the operation
     * @return {@code true}, if the user with the specified user ID was successfully blacklisted and {@code false}
     * otherwise
     */
    public boolean blacklistClient(String userId) {
        for (ConnectedClient connectedClient : connectedClients) {
            if (connectedClient.getId().equals(userId)) {
                blacklistedIds.add(connectedClient);

                removeClient(userId);

                return true;
            } //end if
        } //end for

        return false;
    } //blacklistClient

    /**
     * Attempts to unblacklist the user with the specified user ID in this session.
     *
     * @param userId the user ID to be used in the operation
     * @return {@code true}, if the user with the specified user ID was successfully unblacklisted and {@code false}
     * otherwise
     */
    public boolean unblacklistClient(String userId) {
        for (int i = 0; i < blacklistedIds.size(); i++) {
            if (blacklistedIds.get(i).getId().equals(userId)) {
                blacklistedIds.remove(i);

                return true;
            } //end if
        } //end for

        return false;
    } //unblacklistClient

    /**
     * Attempts to place the user with the specified username in a cooldown period until the specified end time.
     *
     * @param username the username to be used in the operation
     * @param endTime the end time to be used in the operation
     * @return {@code true}, if the user with the specified username was successfully placed in a cooldown period and
     * {@code false} otherwise
     */
    public boolean cooldownClient(String username, long endTime) {
        String clientName;

        Objects.requireNonNull(username, "the specified username is null");

        for (ConnectedClient client : this.connectedClients) {
            clientName = client.getName();

            if (clientName.equalsIgnoreCase(username)) {
                this.cooldownClients.put(client, endTime);

                return true;
            } //end if
        } //end for

        return false;
    } //cooldownClient

    /**
     * Attempts to remove the user with the specified username from a cooldown period.
     *
     * @param username the username to be used in the operation
     * @return {@code true}, if the user with the specified username was successfully removed from a cooldown period
     * and {@code false} otherwise
     */
    public boolean uncooldownClient(String username) {
        String clientName;

        Objects.requireNonNull(username, "the specified username is null");

        for (ConnectedClient client : this.cooldownClients.keySet()) {
            clientName = client.getName();

            if (clientName.equalsIgnoreCase(username)) {
                this.cooldownClients.remove(client);

                return true;
            } //end if
        } //end for

        return false;
    } //uncooldownClient

    /**
     * Adds the specified client to this session.
     *
     * @param c the client to be used in the operation
     */
    public void addClient(ConnectedClient c) {
        for (ConnectedClient blacklistedId : blacklistedIds) {
            if (blacklistedId.getId().equals(c.getId())) {
                return;
            } //end if
        } //end for

        connectedClients.add(c);
    } //addClient

    /**
     * Attempts to remove the client with the specified user ID from this session.
     *
     * @param userId the user ID to be used in the operation
     * @return {@code true}, if the client with the specified user ID was successfully removed from this session and
     * {@code false} otherwise
     */
    public boolean removeClient(String userId) {
        for (int i = 0; i < connectedClients.size(); i++) {
            if (connectedClients.get(i).getId().equals(userId)) {
                System.out.println("Session: Removed user with ID " + userId);

                connectedClients.remove(i);

                return true;
            } //end if
        } //end if

        return false;
    } //removeClient

    /**
     * Determines whether or not this session contains a user with the specified user ID.
     *
     * @param id the user ID to be used in the operation
     * @return {@code true}, if this session contains a user with the specified user ID and {@code false} otherwise
     */
    public boolean hasUserForId(String id) {
        for (ConnectedClient connectedClient : connectedClients) {
            if (connectedClient.getId().equals(id)) {
                return true;
            } //end if
        } //end for

        return false;
    } //hasUserForId

    /**
     * Returns the connected clients of this session.
     *
     * @return the connected clients of this session
     */
    public ConnectedClient[] getConnectedClients() {
        ConnectedClient[] output = new ConnectedClient[connectedClients.size()];
        Object[] array = connectedClients.toArray();

        for (int i = 0; i < connectedClients.size(); i++) {
            output[i] = (ConnectedClient) array[i];
        } //end for

        return output;
    } //getConnectedClients

    /**
     * Returns the blacklisted clients of this session.
     *
     * @return the blacklisted clients of this session
     */
    public ConnectedClient[] getBlacklistedClients() {
        ConnectedClient[] output = new ConnectedClient[blacklistedIds.size()];
        Object[] array = blacklistedIds.toArray();

        for (int i = 0; i < blacklistedIds.size(); i++) {
            output[i] = (ConnectedClient) array[i];
        } //end for

        return output;
    } //getBlacklistedClients

    /**
     * Returns the cooldown clients of this session.
     *
     * @return the cooldown clients of this session
     */
    public Map<ConnectedClient, Long> getCooldownClients() {
        return new HashMap<>(cooldownClients);
    } //getCooldownClients
}