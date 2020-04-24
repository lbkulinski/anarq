package com.anarq.songrequests;

import java.util.*;
import com.anarq.core.*;

/**
 * A container used in the cooldown song implementation.
 */
class Container {
    /**
     * The song of this container.
     */
    Song song;

    /**
     * The time added of this container.
     */
    long timeAdded;

    /**
     * The duration of this container.
     */
    int duration;
}

/**
 * A group of songs currently in a cooldown period.
 *
 * @version April 24, 2020
 */
public class CooldownSong {
    /**
     * The songs of this cooldown song period.
     */
    ArrayList<Container> cooldownSongs;

    /**
     * Constructs a newly allocated {@code CooldownSong} object.
     */
    public CooldownSong() {
        cooldownSongs = new ArrayList<>();
    } //CooldownSong

    /**
     * Adds the specified song to this cooldown song period.
     *
     * @param song the song to be used in the operation
     * @param duration the duration to be used in the operation
     */
    public void addSong(Song song, int duration) {
        for (Container c : cooldownSongs) {
            if (c.song.equals(song)) {
                return;
            } //end if
        } //end for

        Container songToBeCooled = new Container();

        songToBeCooled.song = song;

        songToBeCooled.timeAdded = System.currentTimeMillis();

        songToBeCooled.duration = duration;

        cooldownSongs.add(songToBeCooled);
    } //addSong

    /**
     * Determines whether or not the specified song can be played.
     *
     * @param song the song to be used in the operation
     * @return {@code true}, if the specified song can be played and {@code false} otherwise
     */
    public boolean canSongBePlayed(Song song) {
        long timeAdded = 0;
        int duration = 0;
        Container container = null;

        for (Container c: cooldownSongs) {
            if (c.song.getSongId().equals(song.getSongId())) {
                timeAdded = c.timeAdded;

                duration = c.duration;

                container = c;
            } //end if
        } //end for

        if (container == null) {
            return true;
        } //end if

        long currentTime = System.currentTimeMillis();
        long difference = currentTime - timeAdded;
        long durationInMilliseconds = duration * 60 * 1000; // 1000ms = 1 second and 60000ms = 1 minute

        if (difference > durationInMilliseconds) {
            cooldownSongs.remove(container);

            return true;
        } //end if

        return false;
    } //canSongBePlayed
}