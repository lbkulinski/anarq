package com.anarq.core;

/**
 * A song associated with the AnarQ Application.
 *
 * @version April 23, 2020
 */
public class Song {
    /**
     * The name of this song.
     */
    private final String songName;

    /**
     * The album of this song.
     */
    private final String albumName;

    /**
     * The artist ID of this song.
     */
    private final String artistID;

    /**
     * The artist genres of this song.
     */
    private final String[] artistGenres;

    /**
     * The artist name of this song.
     */
    private final String artistName;

    /**
     * The genre of this song.
     */
    private final String songGenre;

    /**
     * The ID of this song.
     */
    private final String songId;

    /**
     * The duration of this song.
     */
    private final int duration;

    /**
     * Whether or not this song is explicit.
     */
    private final boolean isExplicit;

    /**
     * The BPM of this song.
     */
    private final int bpm;

    /**
     * The album cover of this song.
     */
    private final String albumCover;

    /**
     * Constructs a newly allocated {@code Song} object with the specified information.
     *
     * @param songName the name of the song to be used in construction
     * @param albumName the album to be used in construction
     * @param artistName the artist name to be used in construction
     * @param artistID the artist ID to be used in construction
     * @param artistGenres the artist genre to be used in construction
     * @param songGenre the song genre to be used in construction
     * @param songId the ID to be used in construction
     * @param duration the duration to be used in construction
     * @param isExplicit the explicitness flag to be used in construction
     * @param bpm the BPM to be used in construction
     * @param albumCover the album cover to be used in construction
     */
    public Song(String songName, String albumName, String artistName, String artistID, String[] artistGenres,
                String songGenre, String songId, int duration, boolean isExplicit, int bpm, String albumCover) {
        this.artistGenres = artistGenres;
        this.artistID = artistID;
        this.songName = songName;
        this.albumName = albumName;
        this.artistName = artistName;
        this.songGenre = songGenre;
        this.songId = songId;
        this.duration = duration;
        this.isExplicit = isExplicit;
        this.bpm = bpm;
        this.albumCover = albumCover;
    } //Song

    /**
     * Returns the name of this song.
     *
     * @return the name of this song
     */
    public String getSongName() {
        return songName;
    } //getSongName

    /**
     * Returns the album of this song.
     *
     * @return the album of this song
     */
    public String getAlbumName() {
        return albumName;
    } //getAlbumName

    /**
     * Returns the artist name of this song.
     *
     * @return the artist name of this song
     */
    public String getArtistName() {
        return artistName;
    } //getArtistName

    /**
     * Returns the artist ID of this song..
     *
     * @return the artist ID of this song.
     */
    public String getArtistID() {
        return artistID;
    } //getArtistID

    /**
     * Returns the artist genres of this song.
     *
     * @return the artist genres of this song
     */
    public String[] getArtistGenres() {
        return artistGenres;
    } //getArtistGenres

    /**
     * Returns the genre of this song.
     *
     * @return the genre of this song
     */
    public String getSongGenre() {
        return songGenre;
    } //getSongGenre

    /**
     * Returns the ID of this song.
     *
     * @return the ID of this song
     */
    public String getSongId() {
        return songId;
    } //getSongId

    /**
     * Returns the duration of this song.
     *
     * @return the duration of this song
     */
    public int getDuration() {
        return duration;
    } //getDuration

    /**
     * Returns whether or not this song is explicit.
     *
     * @return thether or not this song is explicit
     */
    public boolean getIsExplicit() {
        return isExplicit;
    } //getIsExplicit

    /**
     * Returns the BPM of this song.
     *
     * @return the BPM of this song
     */
    public int getBPM() {
        return bpm;
    } //getBPM

    /**
     * Returns the album cover of this song.
     *
     * @return the album cover of this song
     */
    public String getAlbumCover() {
        return albumCover;
    } //getAlbumCover
}