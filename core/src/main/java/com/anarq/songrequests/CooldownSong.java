package com.anarq.songrequests;

import com.anarq.spotify.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import com.anarq.core.*;

class container {
  Song song;
  long timeAdded;
  int duration;
}

public class CooldownSong {

  ArrayList<container> cooldownSongs;

  public CooldownSong() {
    cooldownSongs = new ArrayList<>();
  }

  /**
  * Function to set a cool-down period of duration d for the given song s
  * @param newSong the song to be cooled-down
  * @param duration the duration for which the song can't be played, in minutes
  */
  public void addSong(Song newSong, int duration) {

    for (container c: cooldownSongs) {
      if (c.song.equals(newSong))
      return;
    }
    container songToBeCooled = new container();
    songToBeCooled.song = newSong;
    songToBeCooled.timeAdded = System.currentTimeMillis();
    songToBeCooled.duration = duration;
    cooldownSongs.add(songToBeCooled);
  }

  /**
  * Function to determine whether the given song has a cool-down period and can be played
  * @param songToBeChecked the song to be played
  * @return true if the song can be played and false if the songs cool-down period isn't over yet
  */
  public boolean canSongBePlayed (Song songToBeChecked) {

    long timeAdded = 0;
    int duration = 0;
    container song = null;
    for (container c: cooldownSongs) {
      if (c.song.getSongId().equals(songToBeChecked.getSongId())) {
        timeAdded = c.timeAdded;
        duration = c.duration;
        song = c;
      }
    }
    if (song == null) {
      return true;
    }
    long currentTime = System.currentTimeMillis();
    long difference = currentTime - timeAdded;
    long durationInMilliseconds = duration * 60 * 1000; // 1000ms = 1 second and 60000ms = 1 minute
    if (difference > durationInMilliseconds) {
      cooldownSongs.remove(song);
      return true;
    }
    return false;
  }
}
