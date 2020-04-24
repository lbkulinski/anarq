package com.anarq.songrequests;

import com.anarq.spotify.*;
import java.util.ArrayList;
import com.anarq.core.*;

/**
 * A DJ used to automatically play the next song in the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class AutoDJ {
    /**
     * The past genres of this auto DJ.
     */
    private final ArrayList<String> pastGenres;

    /**
     * The past BPMs of this auto DJ.
     */
    private final ArrayList<Integer> pastBPMs;

    /**
     * The past artists of this auto DJ.
     */
    private final ArrayList<String> pastArtists;

    /**
     * The session of this auto DJ.
     */
    private final Session session;

    /**
     * Constructs a newly allocated {@code AutoDJ} object with the specified session.
     *
     * @param session the session to be used in construction
     */
    public AutoDJ(Session session) {
        pastGenres = new ArrayList<>();
        pastBPMs = new ArrayList<>();
        pastArtists = new ArrayList<>();
        this.session = session;
    } //AutoDJ

    /**
     * Adds the specified genre to this auto DJ.
     *
     * @param genre the genre to be used in the operation
     */
    public void addGenre(String genre) {
        pastGenres.add(genre);
    } //addGenre

    /**
     * Adds the specified BPM to this auto DJ.
     *
     * @param bpm the BPM to be used in the operation
     */
    public void addBPM(int bpm) {
        pastBPMs.add(bpm);
    } //addBPM

    /**
     * Adds the specified artist to this auto DJ.
     *
     * @param artist the artist to be used in the operation
     */
    public void addArtist(String artist) {
        pastArtists.add(artist);
    } //addArtist

    /**
     * Returns a song recommendation from this auto DJ.
     *
     * @return a song recommendation from this auto DJ
     */
    public Song getSongRecommendation() {
        int size_A = (int) ((float) Math.random() * (float) pastArtists.size());

        System.out.println("\n" + pastArtists.get(size_A) + "\n");

        return SearchArtistsExample.getRandomReccomendation(pastArtists.get(size_A), session.getMusicChooser());
    } //getSongRecommendation
}