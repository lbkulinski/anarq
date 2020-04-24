package com.anarq.songrequests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import com.anarq.core.*;

/**
 * A {@code Comparator} used to compare song votes in the AnarQ Application.
 *
 * @version April 24, 2020
 */
class VotesComparator implements Comparator<SongRequest> {
    /**
     * Compares the first specified song with the second specified song.
     *
     * @param song1 the first song to be used in the operation
     * @param song2 the scond song to be used in the operation
     * @return {@code -1}, {@code 0}, or {@code 1} if the first specified song is less than, equal to, or greater than
     * the second specified song.
     */
    @Override
    public int compare(SongRequest song1, SongRequest song2) {
        if (song1.getVotes() < song2.getVotes()) {
            return 1;
        } else if (song1.getVotes() > song2.getVotes()) {
            return -1;
        } else {
            return 0;
        } //end if
    } //compare
}

/**
 * A genre used in the AnarQ Application.
 *
 * @version April 24, 2020
 */
class Genre {
    /**
     * The pop flag of this genre.
     */
    public boolean pop;

    /**
     * The rock flag of this genre.
     */
    public boolean rock;

    /**
     * The country flag of this genre.
     */
    public boolean country;

    /**
     * The jazz flag of this genre.
     */
    public boolean jazz;

    /**
     * The rap flag of this genre.
     */
    public boolean rap;

    /**
     * The metal flag of this genre.
     */
    public boolean metal;

    /**
     * The R&B flag of this genre.
     */
    public boolean rb;

    /**
     * The hip hop flag of this genre.
     */
    public boolean hiphop;

    /**
     * The electronic flag of this genre.
     */
    public boolean electronic;

    /**
     * The christian flag of this genre.
     */
    public boolean christian;

    /**
     * Constructs a newly allocated {@code Genre} object.
     */
    public Genre() {
        this.pop = true;
        this.rock = true;
        this.country = true;
        this.jazz = true;
        this.rap = true;
        this.metal = true;
        this.rb = true;
        this.hiphop = true;
        this.electronic = true;
        this.christian = true;
    } //Genre

    /**
     * Constructs a newly allocated {@code Genre} object with the specified values.
     *
     * @param values the values to be used in construction
     */
    public Genre(boolean[] values) {
        if (values.length != 10) {
            this.pop = true;
            this.rock = true;
            this.country = true;
            this.jazz = true;
            this.rap = true;
            this.metal = true;
            this.rb = true;
            this.hiphop = true;
            this.electronic = true;
            this.christian = true;
        } else {
            this.pop = values[0];
            this.rock = values[1];
            this.country = values[2];
            this.jazz = values[3];
            this.rap = values[4];
            this.metal = values[5];
            this.rb = values[6];
            this.hiphop = values[7];
            this.electronic = values[8];
            this.christian = values[9];
        } //end if
    } //Genre
}

/**
 * A request queue used in the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class RequestQueue {
    /**
     * The dislike threshold of this request queue.
     */
    private int dislikeThreshold;

    /**
     * The maximum number of requests of this request queue.
     */
    private final int maxRequests;

    /**
     * The minimum BPM of this request queue.
     */
    private int minBPM;

    /**
     * The maximum BPM of this request queue.
     */
    private int maxBPM;

    /**
     * Whether or not this request queue is accepting requests.
     */
    private boolean acceptingRequests;

    /**
     * Whether or not this request queue is using auto DJ.
     */
    public boolean autoDJ;

    /**
     * Whether or not this request queue is using an explicit filter.
     */
    private boolean explicitFilter;

    /**
     * Whether or not this request queue has public access.
     */
    private boolean visibility;

    /**
     * The music chooser of this request queue.
     */
    private final MusicChooser musicChooser;

    /**
     * The current song of this request queue.
     */
    private SongRequest currentSong = null;

    /**
     * The songs of this request queue.
     */
    private final ArrayList<SongRequest> songs = new ArrayList<>();

    /**
     * The song queue of this request queue.
     */
    private final PriorityQueue<SongRequest> songQueue = new PriorityQueue<>(new VotesComparator());

    /**
     * The overrides of this request queue.
     */
    private final ArrayList<SongRequest> overrides = new ArrayList<>();

    /**
     * The blacklisted genres of this request queue.
     */
    private Genre blacklistedGenres;

    /**
     * The songs in a cooldown period of this request queue.
     */
    CooldownSong cooldown;

    /**
     * Constructs a newly allocated {@code RequestQueue} object.
     */
    public RequestQueue() {
        this.acceptingRequests = true;
        this.maxRequests = 50;
        this.musicChooser = new MusicChooser();
        this.autoDJ = false;
        this.explicitFilter = false;
        this.minBPM = 32;
        this.maxBPM = 400;
        this.dislikeThreshold = 10;
        this.visibility = true;
        this.blacklistedGenres = new Genre();
        this.cooldown = new CooldownSong();
    } //RequestQueue

    /**
     * Constructs a newly allocated {@code RequestQueue} object with the specified information.
     *
     * @param musicChooser the music chooser to be used in construction
     * @param maxRequests the maximum number of requests to be used in construction
     * @param acceptingRequests the request acceptance flag to be used in construction
     * @param explicitFilter the explicit filter flag to be used in construction
     * @param minBPM the minimum BPM flag to be used in construction
     * @param maxBPM the maximum BPM flag to be used in construction
     * @param dislikeThreshold the dislike threshold to be used in construction
     * @param visibility the visibility flag to be used in construction
     * @param blacklistedGenres the blacklisted genres to be used in construction
     */
    public RequestQueue(MusicChooser musicChooser, int maxRequests, boolean acceptingRequests, boolean explicitFilter,
                        int minBPM, int maxBPM, int dislikeThreshold, boolean visibility,
                        boolean[] blacklistedGenres) {
        this.maxRequests = maxRequests;
        this.musicChooser = musicChooser;
        this.acceptingRequests = acceptingRequests;
        this.autoDJ = false;
        this.explicitFilter = explicitFilter;

        if (minBPM > maxBPM || minBPM < 0) {
            this.minBPM = 0;
            this.maxBPM = Integer.MAX_VALUE;
        } else {
            this.minBPM = minBPM;
            this.maxBPM = maxBPM;
        } //end if

        this.dislikeThreshold = dislikeThreshold;
        this.visibility = visibility;
        this.blacklistedGenres = new Genre(blacklistedGenres);
        this.cooldown = new CooldownSong();
    } //RequestQueue

    /**
     * Returns the preference packet of this request queue.
     *
     * @return the preference packet of this request queue
     */
    public PreferencePacket getPreferencePacket() {
        return new PreferencePacket(maxBPM, minBPM, dislikeThreshold, blacklistedGenres.pop, blacklistedGenres.rock,
                                    blacklistedGenres.country, blacklistedGenres.jazz, blacklistedGenres.rap,
                                    blacklistedGenres.metal, blacklistedGenres.rb, blacklistedGenres.hiphop,
                                    blacklistedGenres.electronic, blacklistedGenres.christian, explicitFilter,
                                    visibility, acceptingRequests);
    } //getPreferencePacket

    /**
     * Updates the blacklisted genres of this request queue with the specified blacklisted genres.
     *
     * @param blacklistedGenres the blacklisted genres to be used in the update
     */
    public void setBlacklistedGenres(boolean[] blacklistedGenres) {
        this.blacklistedGenres = new Genre(blacklistedGenres);
    } //setBlacklistedGenres

    /**
     * Updates the maximum BPM of this request queue with the specified maximum BPM.
     *
     * @param maxBPM the maximum BPM to be used in the update
     */
    public void setMaxBPM(int maxBPM) {
        this.maxBPM = maxBPM;
    } //setMaxBPM

    /**
     * Updates the minimum BPM of this request queue with the specified minimum BPM.
     *
     * @param minBPM the minimum BPM to be used in the update
     */
    public void setMinBPM(int minBPM) {
        this.minBPM = minBPM;
    } //setMinBPM

    /**
     * Updates the dislike threshold of this request queue with the specified dislike threshold.
     *
     * @param dislikeThreshold the dislike threshold to be used in the update
     */
    public void setDislikeThreshold(int dislikeThreshold) {
        this.dislikeThreshold = dislikeThreshold;
    } //setDislikeThreshold

    /**
     * Updates the explicit filter flag of this request queue with the specified explicit filter flag.
     *
     * @param explicitFilter the explicit filter flag to be used in the update
     */
    public void setExplicitFilter(boolean explicitFilter) {
        this.explicitFilter = explicitFilter;
    } //setExplicitFilter

    /**
     * Updates the visibility flag of this request queue with the specified visibility flag.
     *
     * @param visibility the visibility flag to be used in the update
     */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    } //setVisibility

    /**
     * Updates the accepting requests flag of this request queue with the specified accepting requests flag.
     *
     * @param acceptingRequests the accepting requests flag to be used in the update
     */
    public void setAcceptingRequests(boolean acceptingRequests) {
        this.acceptingRequests = acceptingRequests;
    } //setAcceptingRequests

    /**
     * Returns the maximum number of requests of this request queue.
     *
     * @return the maximum number of requests of this request queue
     */
    public int getMaxRequests() {
        return this.maxRequests;
    } //getMaxRequests

    /**
     * Returns the explicit filter flag of this request queue.
     *
     * @return the explicit filter flag of this request queue
     */
    public boolean hasExplicitFilter() {
        return this.explicitFilter;
    } //hasExplicitFilter

    /**
     * Returns the minimum BPM of this request queue.
     *
     * @return the minimum BPM of this request queue
     */
    public int getMinBPM() {
        return this.minBPM;
    } //getMinBPM

    /**
     * Returns the maximum BPM of this request queue.
     *
     * @return the maximum BPM of this request queue
     */
    public int getMaxBPM() {
        return this.maxBPM;
    } //getMaxBPM

    /**
     * Returns the dislike threshold of this request queue.
     *
     * @return the dislike threshold of this request queue
     */
    public int getDislikeThreshold() {
        return this.dislikeThreshold;
    } //getDislikeThreshold

    /**
     * Returns the visibility flag of this request queue.
     *
     * @return the visibility flag of this request queue
     */
    public boolean getVisibility() {
        return this.visibility;
    } //getVisibility

    /**
     * Returns the blacklisted genres of this request queue.
     *
     * @return the blacklisted genres of this request queue
     */
    public Genre getBlacklistedGenres() {
        return this.blacklistedGenres;
    } //getBlacklistedGenres

    /**
     * Attempts to add the specified song request to this request queue.
     *
     * @param song the song request to be used in the operation
     * @return {@code true}, if the specified song request was successfully added to this request queue and
     * {@code false} otherwise
     */
    public boolean addSong(SongRequest song) {
        if (!musicChooser.isValidGenre(song.getGenre())) {
            System.out.println("Request Failed: Genre (" + song.getGenre() + ") is not accepted in the queue");

            return false;
        } //end if

        if (isSongAlreadyInQueue(song)) {
            System.out.println("Request Failed: Queue already contains " + song.getName());

            return false;
        } //end if

        if (!acceptingRequests) {
            System.out.println("Request Failed: Queue is no longer accepting requests");

            return false;
        } //end if

        if (!cooldown.canSongBePlayed(song.songInfo)) {
            System.out.println("Request Failed: This song has been put under a cooldown period and cannot be played right now");

            return false;
        } //end if

        if (explicitFilter && song.getIsExplicit()) {
            System.out.println("Request Pending: Explicit Content is not allowed ... Override request sent to host");

            overrides.add(song);

            return false;
        } //edn if

        System.out.println(song.getBPM() + " " + maxBPM + " " + minBPM);

        if (song.getBPM() > this.maxBPM || song.getBPM() < this.minBPM) {
            System.out.println("Request Pending: BPM is not in the allowed range ... Override request sent to host");

            overrides.add(song);

            return false;
        } //end if

        if(!checkGenres(song, this.getBlacklistedGenres())) {
            System.out.println("Request pending: Genre is not allowed in the queue ... Override request sent to host");

            overrides.add(song);

            return false;
        } //end if

        if (songQueue.size() < getMaxRequests()) {
            this.autoDJ = false;

            System.out.println("Song added to queue: " + song.getId());

            return songQueue.add(song);
        } else {
            System.out.println("Request Failed: Queue is full");

            return false;
        } //end if
    } //addSong

    /**
     * Determines whether or not the genres of the specified song request are valid.
     *
     * @param song the song request to be used in the operation
     * @param genres the genres to be used in the operation
     * @return {@code true}, if the genres of the specified song request are valid and {@code false} otherwise
     */
    public boolean checkGenres(SongRequest song, Genre genres) {
        String spotifyGenre = song.getGenre();

        return (!spotifyGenre.contains("pop") || genres.pop) &&
                (!spotifyGenre.contains("rock") || genres.rock) &&
                (!spotifyGenre.contains("country") || genres.country) &&
                (!spotifyGenre.contains("jazz") || genres.jazz) &&
                (!spotifyGenre.contains("rap") || genres.rap) &&
                (!spotifyGenre.contains("metal") || genres.metal) &&
                (!spotifyGenre.contains("r&b") || genres.rb) &&
                (!spotifyGenre.contains("hip hop") || genres.hiphop) &&
                (!spotifyGenre.contains("electronic") || genres.electronic) &&
                (!spotifyGenre.contains("christian") || genres.christian);
    } //checkGenres

    /**
     * Removes the override of the specified song request.
     *
     * @param song the song request to be used in the operation
     */
    public void removeOverride(SongRequest song) {
        overrides.remove(song);
    } //removeOverride

    /**
     * Approves the override of the specified song request.
     *
     * @param song the song request to be used in the operation
     */
    public void approveOverride(SongRequest song) {
        songQueue.add(song);

        overrides.remove(song);
    } //approveOverride

    /**
     * Returns the requested song from this request queue.
     *
     * @param song the song request to be used in the operation
     * @return the requested song from this request queue
     */
    public SongRequest getSong(SongRequest song) {
        return songs.get(songs.indexOf(song));
    } //getSong

    /**
     * Attempts to like the song with the specified song ID.
     *
     * @param songId the song ID to be used in the operation
     * @param client the client to be used in the operation
     * @return {@code true}, if the song with the specified song ID was successfully liked and {@code false} otherwise
     */
    public boolean likeSong(String songId, String client) {
        SongRequest song = getSongFromQueue(songId);

        if (song == null) {
            return false;
        } //end if

        boolean accepting = this.acceptingRequests;

        this.acceptingRequests = true;

        songQueue.remove(song);

        song.likeSong(client);

        songQueue.add(song);

        if (!accepting) {
            this.acceptingRequests = false;
        } //end if

        return true;
    } //likeSong

    /**
     * Attempts to dislike the song with the specified song ID.
     *
     * @param songId the song ID to be used in the operation
     * @param client the client to be used in the operation
     * @return {@code true}, if the song with the specified song ID was successfully disliked and {@code false}
     * otherwise
     */
    public boolean dislikeSong(String songId, String client) {
        SongRequest song = getSongFromQueue(songId);

        if (song == null) {
            return false;
        } //end if

        boolean accepting = this.acceptingRequests;

        this.acceptingRequests = true;

        songQueue.remove(song);

        song.dislikeSong(client);

        if (song.getVotes() > -1 * getDislikeThreshold()) {
            songQueue.add(song);
        } //end if

        if (!accepting) {
            this.acceptingRequests = false;
        } //end if

        return true;
    } //dislikeSong

    /**
     * Attempts to remove the specified song request from this request queue.
     *
     * @param song the song request to be used in the operation
     * @param client the client to be used in the operation
     * @return {@code true}, if the specified song request was successfully removed and {@code false} otherwise
     */
    public boolean removeSong(SongRequest song, ConnectedClient client) {
        if ((client.getId().equals(song.getClientIp()) || (client.getPermissionLevel() == Permission.DJ))
                && !song.isPlaying()) {
            boolean ret = songQueue.remove(song);

            if (isEmpty()) {
                this.autoDJ = true;
            } //end if

            return ret;
        } else {
            if (song.isPlaying()) {
                System.out.println("You can not remove a song that is playing");
            } else {
                System.out.println("You can only remove your own song");
            } //end if

            return false;
        } //end if
    } //removeSong

    /**
     * Returns the next song request in this request queue.
     *
     * @return the next song request in this request queue
     */
    public SongRequest viewNextSong() {
        return songQueue.peek();
    } //viewNextSong

    /**
     * Removes and returns the next song request in this request queue.
     *
     * @return the next song request in this request queue
     */
    public SongRequest playNextSong() {
        SongRequest ret = songQueue.poll();

        if (isEmpty()) {
            this.autoDJ = true;
        } //end if

        currentSong = ret;

        return ret;
    } //playNextSong

    /**
     * Clears this request queue.
     */
    public void clear() {
        songQueue.clear();

        this.autoDJ = true;
    } //clear

    /**
     * Determines whether or not this request queue is empty.
     *
     * @return {@code true}, if this request queue is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return songQueue.isEmpty();
    } //isEmpty

    /**
     * Returns the current song request of this request queue.
     *
     * @return the current song request of this request queue
     */
    public SongRequest getCurrentSong() {
        return currentSong;
    } //getCurrentSong

    /**
     * Determines whether or not the specified song request is already in this request queue.
     *
     * @param song the song request to be used in the operation
     * @return {@code true}, if the specified song request is already in this request queue and {@code false} otherwise
     */
    public boolean isSongAlreadyInQueue(SongRequest song) {
        SongRequest[] queue = getSongQueue();

        for (SongRequest songRequest : queue) {
            if (songRequest.getId().equals(song.getId())) {
                return true;
            } //end if
        } //end for

        return false;
    } //isSongAlreadyInQueue

    /**
     * Returns the song request in this request queue with the specified ID.
     *
     * @param id the ID to be used in the operation
     * @return the song request in this request queue with the specified ID
     */
    public SongRequest getSongFromQueue(String id) {
        SongRequest[] queue = getSongQueue();

        for (SongRequest songRequest : queue) {
            if (songRequest.getId().equals(id)) {
                return songRequest;
            } //end if
        } //end for

        return null;
    } //getSongFromQueue

    /**
     * Returns the song requests of this request queue.
     *
     * @return the song requests of this request queue
     */
    public SongRequest[] getSongQueue() {
        SongRequest[] output = new SongRequest[songQueue.size()];
        Object[] array = songQueue.toArray();

        for (int i = 0; i < songQueue.size(); i++) {
            output[i] = (SongRequest) array[i];
        } //end for

        return output;
    } //getSongQueue

    /**
     * Returns the overrides of this request queue.
     *
     * @return the overrides of this request queue
     */
    public SongRequest[] getOverrides() {
        SongRequest[] output = new SongRequest[overrides.size()];
        Object[] array = overrides.toArray();

        for (int i = 0; i < overrides.size(); i++) {
            output[i] = (SongRequest) array[i];
        } //end for

        return output;
    } //getOverrides

    /**
     * Prints this request queue to the console.
     */
    public void printQueue() {
        System.out.println("Queue <");

        for (SongRequest song : songQueue) {
            System.out.println("    " + song.getName());
        } //end for

        System.out.println(">");
    } //printQueue

    /**
     * Returns this request queue as a {@code String}.
     *
     * @return this request queue as a {@code String}
     */
    public String[] saveSongQueue() {
        SongRequest[] queue = getSongQueue();
        String[] output = new String[queue.length];
        String details = "Name: %s\nArtist: %s\nAlbum: %s";
        int counter = 0;

        for (SongRequest song: queue) {
            output[counter] = String.format(details, song.getName(), song.getArtist(), song.getAlbum());

            counter++;
        } //end for

        return output;
    } //saveSongQueue
}