package com.anarq.songrequests;

import java.util.ArrayList;
import com.anarq.core.*;

/**
* A song request used in the AnarQ Application.
*
* @version April 24, 2020
*/
public class SongRequest {
  /**
  * The votes of this song request.
  */
  private int votes;

  /**
  * Whether or not this song request is playing.
  */
  private final boolean playing;

  /**
  * The song information of this song request.
  */
  protected Song songInfo;

  /**
  * The client IP of this song request.
  */
  private final String clientIp;

  /**
  * The users that liked this song request.
  */
  private final ArrayList<String> usersLiked;

  /**
  * The users that disliked this song request.
  */
  private final ArrayList<String> usersDisliked;

  protected long timeAdded;

  /**
  * Constructs a newly allocated {@code SongRequest} object with the specified song information and client IP.
  *
  * @param songInfo the song information to be used in construction
  * @param clientIp the client IP to be used in construction
  */
  public SongRequest(Song songInfo, String clientIp) {
    this.votes = 0;
    this.timeAdded = 0;
    this.playing = false;
    this.songInfo = songInfo;
    this.clientIp = clientIp;
    this.usersLiked = new ArrayList<>();
    this.usersDisliked = new ArrayList<>();
  } //SongRequest

  /**
  * Likes this song request by increasing its vote count by one.
  *
  * @param clientIp the client IP to be used in the operation
  */
  public void likeSong(String clientIp) {
    if (!clientIp.equals(getClientIp()) && !playing) {
      if (!hasLiked(clientIp)) {
        if (hasDisliked(clientIp)) {
          usersDisliked.remove(clientIp);

          this.votes++;
        } //end if

        usersLiked.add(clientIp);

        this.votes++;
      } else {
        usersLiked.remove(clientIp);

        this.votes--;
      } //end if
    } //end if
  } //likeSong

  /**
  * Dislikes this song request by decreasing its vote count by one.
  *
  * @param clientIp the client IP to be used in the operation
  */
  public void dislikeSong(String clientIp) {
    if (!clientIp.equals(getClientIp()) && !playing) {
      if (!hasDisliked(clientIp)) {
        if (hasLiked(clientIp)) {
          usersLiked.remove(clientIp);

          this.votes--;
        } //end if

        usersDisliked.add(clientIp);

        this.votes--;
      } else {
        usersDisliked.remove(clientIp);

        this.votes++;
      } //end if
    } //end if
  } //dislikeSong

  /**
  * Determines whether or not the client with the specified client IP has liked this song request.
  *
  * @param clientIp the client IP to be used in the operation
  * @return {@code true}, if the client with the specified client IP has liked this song request and {@code false}
  * otherwise
  */
  public boolean hasLiked(String clientIp) {
    return usersLiked.contains(clientIp);
  } //hasLiked

  /**
  * Determines whether or not the client with the specified client IP has disliked this song request.
  *
  * @param clientIp the client IP to be used in the operation
  * @return {@code true}, if the client with the specified client IP has disliked this song request and
  * {@code false} otherwise
  */
  public boolean hasDisliked(String clientIp) {
    return usersDisliked.contains(clientIp);
  } //hasDisliked

  /**
  * Returns the ID of this song request.
  *
  * @return the ID of this song request
  */
  public String getId() {
    return songInfo.getSongId();
  } //getId

  /**
  * Returns the album of this song request.
  *
  * @return the album of this song request
  */
  public String getAlbum() {
    return songInfo.getAlbumName();
  } //getAlbum

  /**
  * Returns the name of this song request.
  *
  * @return the name of this song request.
  */
  public String getName() {
    return songInfo.getSongName();
  } //getName

  /**
  * Returns the artist of this song request.
  *
  * @return the artist of this song request
  */
  public String getArtist() {
    return songInfo.getArtistName();
  } //getArtist

  /**
  * Returns the genre of this song request.
  *
  * @return the genre of this song request
  */
  public String getGenre() {
    return songInfo.getSongGenre();
  } //getGenre

  /**
  * Determines whether or not this song request is explicit.
  *
  * @return {@code true}, if this song request is explicit and {@code false} otherwise
  */
  public boolean getIsExplicit() {
    return songInfo.getIsExplicit();
  } //getIsExplicit

  /**
  * Returns the BPM of this song request.
  *
  * @return the BPM of this song request
  */
  public int getBPM() {
    return songInfo.getBPM();
  } //getBPM

  /**
  * Returns the album cover of this song request.
  *
  * @return the album cover of this song request
  */
  public String getAlbumCover() {
    return songInfo.getAlbumCover();
  } //getAlbumCover

  /**
  * Returns the votes of this song request.
  *
  * @return the votes of this song request
  */
  public int getVotes() {
    return votes;
  } //getVotes

  public long getTimeAdded() {
    return timeAdded;
  } //getVotes

  /**
  * Returns the client IP of this song request.
  *
  * @return the client IP of this song request
  */
  public String getClientIp() {
    return clientIp;
  } //getClientIp

  /**
  * Returns whether or not this song request is playing.
  *
  * @return {@code true}, if this song request is playing and {@code false} otherwise
  */
  public boolean isPlaying() {
    return playing;
  } //isPlaying

  /**
  * Returns the song information of this song request.
  *
  * @return the song information of this song request
  */
  public Song getSongInfo() {
    return songInfo;
  } //getSongInfo

  /**
  * Determines whether or not the specified object is equal to this song request.
  *
  * @param object
  * @return {@code true}, if the specified object is equal to this song request and {@code false} otherwise
  */
  @Override
  public boolean equals(Object object) {
    if (object instanceof SongRequest) {
      return this.getId().equals(((SongRequest) object).getId());
    } //end if

    return false;
  } //equals

  /**
  * Returns the {@code String} representation of this song request.
  *
  * @return the {@code String} representation of this song request
  */
  public String toString() {
    return getName() + ": id(" + getId() + ") artist(" + getArtist() +") album(" + getAlbum() + ") genre(" +
    getGenre() + ") clientIp(" + getClientIp() + ") voteScore(" + getVotes() + ")";
  } //toString
}
