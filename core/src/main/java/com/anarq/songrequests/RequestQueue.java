package com.anarq.songrequests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import com.anarq.core.*;

/*
VotesComparator
Contains information about comparing the votes of two songs

Author(s):
Nick
*/
class VotesComparator implements Comparator<SongRequest> {

  /* Compare two song requests based on vote count */
  @Override
  public int compare(SongRequest song1, SongRequest song2)
  {
    if (song1.getVotes() < song2.getVotes()) {
      return 1;
    }
    else if (song1.getVotes() > song2.getVotes()) {
      return -1;
    }
    else {
      return 0;
    }
  }
}

class Genre {
  public boolean pop;
  public boolean rock;
  public boolean country;
  public boolean jazz;
  public boolean rap;
  public boolean metal;
  public boolean rb;
  public boolean hiphop;
  public boolean electronic;
  public boolean christian;

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
  }

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
    }
    else {
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
    }
  }
}
/*
RequestQueue
Contains methods pertaining to the organization and deployment of songrequests

Author(s):
Nick
Patrick
*/
public class RequestQueue {

  private int dislikeThreshold = 1;

  // Private Varaibles
  private int maxRequests;
  private int minBPM = 32;
  private int maxBPM = 400;
  private boolean acceptingRequests;
  public boolean autoDJ;
  private boolean explicitFilter;
  private boolean visibility; // true = public access, false = private access
  private MusicChooser musicChooser;
  private SongRequest currentSong = null;
  private ArrayList<SongRequest> songs = new ArrayList<SongRequest>();
  private PriorityQueue<SongRequest> songQueue = new PriorityQueue<SongRequest>(new VotesComparator());
  private ArrayList<SongRequest> overrides = new ArrayList<SongRequest>();
  private Genre blacklistedGenres;
  CooldownSong cooldown;

  /* Creates a new RequestQueue Instance */
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
    cooldown = new CooldownSong();
  }

  /* Creates a new RequestQueue Instance using the inputs */
  public RequestQueue(MusicChooser musicChooser, int maxRequests, boolean acceptingRequests, boolean explicitFilter, int minBPM, int maxBPM, int dislikeThreshold, boolean visibility, boolean blacklistedGenres[]) {
    this.maxRequests = maxRequests;
    this.musicChooser = musicChooser;
    this.acceptingRequests = acceptingRequests;
    this.autoDJ = false;
    this.explicitFilter = explicitFilter;
    if (minBPM > maxBPM || minBPM < 0 || maxBPM < 0) {
      this.minBPM = 0;
      this.maxBPM = Integer.MAX_VALUE;
    }
    else {
      this.minBPM = minBPM;
      this.maxBPM = maxBPM;
    }
    this.dislikeThreshold = dislikeThreshold;
    this.visibility = visibility;
    this.blacklistedGenres = new Genre(blacklistedGenres);
    cooldown = new CooldownSong();
  }

  public PreferencePacket getPreferencePacket() {

    return new PreferencePacket(maxBPM, minBPM, dislikeThreshold, blacklistedGenres.pop,
    blacklistedGenres.rock,
    blacklistedGenres.country,
    blacklistedGenres.jazz,
    blacklistedGenres.rap,
    blacklistedGenres.metal,
    blacklistedGenres.rb,
    blacklistedGenres.hiphop,
    blacklistedGenres.electronic,
    blacklistedGenres.christian,
    explicitFilter,
    visibility,
    acceptingRequests);

  }



  public void setBlacklistedGenres(boolean[] b) {
    this.blacklistedGenres = new Genre(b);
  }

  public void setMaxBPM(int newValue) {
    this.maxBPM = newValue;
  }

  public void setMinBPM(int newValue) {
    this.minBPM = newValue;
  }

  public void setDislikeThreshold(int newValue) {
    this.dislikeThreshold = newValue;
  }

  public void setExplicitFilter(boolean newValue) {
    this.explicitFilter = newValue;
  }

  public void setVisibility(boolean newValue) {
    this.visibility = newValue;
  }

  public void setAcceptingRequests(boolean newValue) {
    this.acceptingRequests = newValue;
  }

  /* Returns the max number of requests */
  public int getMaxRequests() {
    return this.maxRequests;
  }

  public boolean hasExplicitFilter() {
    return this.explicitFilter;
  }

  public int getMinBPM() {
    return this.minBPM;
  }

  public int getMaxBPM() {
    return maxBPM;
  }

  public int getDislikeThreshold() {
    return this.dislikeThreshold;
  }

  public boolean getVisibility() {
    return visibility;
  }

  public Genre getBlacklistedGenres() {
    return blacklistedGenres;
  }

  /* Adds a song to the request queue */
  public boolean addSong(SongRequest song) {
    if (!musicChooser.isValidGenre(song.getGenre())) {
      System.out.println("Request Failed: Genre (" + song.getGenre() + ") is not accepted in the queue");
      return false;
    }
    if (isSongAlreadyInQueue(song)) {
      System.out.println("Request Failed: Queue already contains " + song.getName());
      return false;
    }

    if (!acceptingRequests) {
      System.out.println("Request Failed: Queue is no longer accepting requests");
      return false;
    }

    if (cooldown.canSongBePlayed(song.songInfo) == false) {
      System.out.println("Request Failed: This song has been put under a cooldown period and cannot be played right now");
      //System.out.println("Request Failed: This song has been put under a cooldown period ... Override request sent to host");
      //overrides.add(song);
      return false;
    }

    if (explicitFilter && song.getIsExplicit()) { //checks if song is explicit AND explicit filter is on
      //add song to override list and return false
      System.out.println("Request Pending: Explicit Content is not allowed ... Override request sent to host");
      overrides.add(song);
      return false;
    }

    System.out.println(song.getBPM() + " " + maxBPM + " " + minBPM);

    if (song.getBPM() > this.maxBPM || song.getBPM() < this.minBPM) {
      System.out.println("Request Pending: BPM is not in the allowed range ... Override request sent to host");
      overrides.add(song);
      return false;
    }

    if(!checkGenres(song, this.getBlacklistedGenres())) {
      System.out.println("Request pending: Genre is not allowed in the queue ... Override request sent to host");
      overrides.add(song);
      return false;
    }

    if (songQueue.size() < getMaxRequests()) {
      this.autoDJ = false;
      System.out.println("Song added to queue: " + song.getId());
      return songQueue.add(song);
    }
    else {
      System.out.println("Request Failed: Queue is full");
      return false;
    }
  }

  public boolean checkGenres(SongRequest song, Genre genres) {
    String spotifyGenre = song.getGenre();
    if(
    (spotifyGenre.contains("pop") && !genres.pop)
    || (spotifyGenre.contains("rock") && !genres.rock)
    || (spotifyGenre.contains("country") && !genres.country)
    || (spotifyGenre.contains("jazz") && !genres.jazz)
    || (spotifyGenre.contains("rap") && !genres.rap)
    || (spotifyGenre.contains("metal") && !genres.metal)
    || (spotifyGenre.contains("r&b") && !genres.rb)
    || (spotifyGenre.contains("hip hop") && !genres.hiphop)
    || (spotifyGenre.contains("electronic") && !genres.electronic)
    || (spotifyGenre.contains("christian") && !genres.christian)
    ) {
      return false;
    }
    return true;
  }

  public void removeOverride(SongRequest i) {
    overrides.remove(i);
  }

  public void approveOverride(SongRequest i) {

    songQueue.add(i);
    overrides.remove(i);

  }

  /* Returns a song from the queue */
  public SongRequest getSong(SongRequest song) {
    return songs.get(songs.indexOf(song));
  }

  /* Attempt to like a song */
  public boolean likeSong(String songId, String client) {

    SongRequest song = getSongFromQueue(songId);

    if(song == null) {
      return false;
    }

    boolean accepting = this.acceptingRequests;
    this.acceptingRequests = true;
    songQueue.remove(song);
    song.likeSong(client);
    songQueue.add(song);

    if (!accepting) {
      this.acceptingRequests = false;
    }

    return true;

  }

  /* Attempt to dislike a song */
  public boolean dislikeSong(String songId, String client) {

    SongRequest song = getSongFromQueue(songId);

    if(song == null) {
      return false;
    }

    boolean accepting = this.acceptingRequests;
    this.acceptingRequests = true;
    songQueue.remove(song);
    song.dislikeSong(client);
    if (song.getVotes() > -1 * getDislikeThreshold()) {
      songQueue.add(song);
    }

    if (!accepting) {
      this.acceptingRequests = false;
    }

    return true;

  }

  /* Attempt to remove a song */
  public boolean removeSong(SongRequest song, ConnectedClient client) {
    if ((client.getId().equals(song.getClientIp()) || (client.getPermissionLevel() == Permission.DJ)) && !song.isPlaying()) {
      boolean ret = songQueue.remove(song);
      if (isEmpty()) {
        this.autoDJ = true;
      }
      return ret;
    }
    else {
      if (song.isPlaying()) {
        System.out.println("You can not remove a song that is playing");
      }
      else {
        System.out.println("You can only remove your own song");
      }
      return false;
    }
  }

  /* See which song is next on the queue */
  public SongRequest viewNextSong() {
    return songQueue.peek();
  }

  /* Move the next song to the song being played */
  public SongRequest playNextSong() {
    SongRequest ret = songQueue.poll();
    if (isEmpty()) {
      this.autoDJ = true;
    }
    currentSong = ret;
    return ret;
  }

  /* Clear the queue */
  public void clear() {
    songQueue.clear();
    this.autoDJ = true;
  }

  /* Returns true if the queue is empty */
  public boolean isEmpty() {
    return songQueue.isEmpty();
  }

  /* Returns the song currently being played */
  public SongRequest getCurrentSong() {
    return currentSong;
  }

  /* Returns true if that song is already in the queue */
  public boolean isSongAlreadyInQueue(SongRequest song) {

    SongRequest[] queue = getSongQueue();

    for (int i = 0; i < queue.length; i++) {
      if(queue[i].getId().equals(song.getId())) {
        return true;
      }

    }

    return false;

  }

  /* Returns true if that song is already in the queue */
  public SongRequest getSongFromQueue(String id) {

    SongRequest[] queue = getSongQueue();

    for (int i = 0; i < queue.length; i++) {
      if(queue[i].getId().equals(id)) {
        return queue[i];
      }

    }

    return null;

  }

  /* Returns the queue of songs to being played */
  public SongRequest[] getSongQueue() {

    SongRequest[] output = new SongRequest[songQueue.size()];
    Object[] array = songQueue.toArray();

    for (int i = 0; i < songQueue.size(); i++) {

      output[i] = (SongRequest) array[i];

    }

    return output;
  }

  public SongRequest[] getOverrides() {

    SongRequest[] output = new SongRequest[overrides.size()];
    Object[] array = overrides.toArray();

    for (int i = 0; i < overrides.size(); i++) {

      output[i] = (SongRequest) array[i];

    }

    return output;

  }

  /* Print a representation of the queue out into the console */
  public void printQueue() {
    System.out.println("Queue <");
    for (SongRequest song : songQueue) {
      System.out.println("    " + song.getName());
    }
    System.out.println(">");
  }

  public String[] saveSongQueue() {
    SongRequest[] queue = getSongQueue();
    String[] output = new String[queue.length];
    String details = "Name: %s\nArtist: %s\nAlbum: %s";
    int counter = 0;
    for (SongRequest song: queue) {
      output[counter++] = String.format(details, song.getName(),
      song.getArtist(), song.getAlbum());
    }
    return output;
  }
}
