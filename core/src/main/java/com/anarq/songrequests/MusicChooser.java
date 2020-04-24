package com.anarq.songrequests;

import com.anarq.spotify.*;
import java.util.ArrayList;
import com.anarq.core.*;

/**
 * A music chooser used to select songs in the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class MusicChooser {
    /**
     * The valid genres of this music chooser.
     */
    private final ArrayList<String> validGenres;

    /**
     * Constructs a newly allocated {@code MusicChooser} object.
     */
    public MusicChooser() {
        validGenres = new ArrayList<>();
    } //MusicChooser

    /**
     * Adds the specified genre to this music chooser's valid genres.
     *
     * @param genre the genre to be used in the operation
     */
    public void addValidGenre(String genre) {
        validGenres.add(genre);
    } //addValidGenre

    /**
     * Removes the specified genre from this music chooser's valid genres.
     *
     * @param genre the genre to be used in the operation
     */
    public void removeValidGenre(String genre) {
        validGenres.remove(genre);
    } //removeValidGenre

    /**
     * Determines whether or not the specified genre is part of this music chooser's valid genres.
     *
     * @param genre the genre to be used in the operation
     * @return {@code true}, if the specified genre is part of this music chooser's valid genres and {@code false}
     * otherwise
     */
    public boolean isValidGenre(String genre) {
        return !validGenres.contains(genre);
    } //isValidGenre

    /**
     * Returns songs that match the specified query.
     *
     * @param query the query to be used in the operation
     * @return songs that match the specified query
     */
	public Song[] searchForSongs(String query) {
		return SpotifyGateway.searchForSongs(query);
	} //searchForSongs

    /**
     * Returns the song associated with the specified ID.
     *
     * @param id the ID to be used in the operation
     * @return the song associated with the specified ID
     */
	public Song getSongForSongId(String id) {
		return SpotifyGateway.getSongForSongId(id);
	} //getSongForSongId
}