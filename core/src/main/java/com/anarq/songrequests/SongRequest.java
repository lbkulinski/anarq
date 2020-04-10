package com.anarq.songrequests;

import java.util.ArrayList;

import com.anarq.core.*;

/* 
	SongRequest
		Handles the information pertaining to a specific song request
	
	Author(s):
		Nick
		Patrick
*/
public class SongRequest {
	
	// Private Variables
	private int votes;
	private boolean playing;
    private Song songInfo;
    private String clientIp;
    private ArrayList<String> usersLiked;
    private ArrayList<String> usersDisliked;
    
	/* Initalizes a new SongRequest */
    public SongRequest(Song songInfo, String clientIp) {
        this.votes = 0;
		this.playing = false;
		this.songInfo = songInfo;
        this.clientIp = clientIp;
        this.usersLiked = new ArrayList<String>();
        this.usersDisliked = new ArrayList<String>();
    }

	/* Adds one to the score of the request if the clientIP is ok */
    public void likeSong(String clientIp) {
        if (!clientIp.equals(getClientIp()) && !playing) {
            if (!hasLiked(clientIp)) {
                if (hasDisliked(clientIp)) {
                    usersDisliked.remove(clientIp);
                    this.votes++;
                }
                usersLiked.add(clientIp);
                this.votes++;
            }
            else {
                usersLiked.remove(clientIp);
                this.votes--;
            }
        }
        //updateQueue()
    }

	/* Removes one from the score of the request if the clientIP is ok */
    public void dislikeSong(String clientIp) {
        if (!clientIp.equals(getClientIp()) && !playing) {
            if (!hasDisliked(clientIp)) {
                if (hasLiked(clientIp)) {
                    usersLiked.remove(clientIp);
                    this.votes--;
                }
                usersDisliked.add(clientIp);
                this.votes--;
            }
            else {
                usersDisliked.remove(clientIp);
                this.votes++;   
            }
        }
        //updateQueue()
    }

    /* Returns whether or not the song has been liked by a user */
    public boolean hasLiked(String clientIp) {
        return usersLiked.contains(clientIp);
    }

    /* Returns whether or not the song has been disliked by a user */
    public boolean hasDisliked(String clientIp) {
        return usersDisliked.contains(clientIp);
    }
    
	/* Returns the ID of the song request */
    public String getId() {
        return songInfo.getSongId();
    }

	/* Returns the album name of this song request */
    public String getAlbum() {
        return songInfo.getAlbumName();
    }

	/* Returns the name of the song request */
    public String getName() {
        return songInfo.getSongName();
    }

	/* Returns the artist name of the song request */
    public String getArtist() {
        return songInfo.getArtistName();
    }

	/* Returns the genre name of the song request */
    public String getGenre() {
        return songInfo.getSongGenre();
    }
	
	/* Returns the explict nature of the song request */
    public boolean getIsExplicit() {
        return songInfo.getIsExplicit();
    }
	
	/* Returns the explict nature of the song request */
    public int getBPM() {
        return songInfo.getBPM();
    }

	/* Returns the vote score of the song request */
    public int getVotes() {
        return votes;
    }

	/* Returns the ip of the client who submitted the song request */
    public String getClientIp() {
        return clientIp;
    }
	
	/* Returns if the music is playing */
    public boolean isPlaying() {
        return playing;
    }

	/* Compare two song requests */
    @Override
    public boolean equals(Object o) {
        return (o.getClass() == this.getClass() && this.getId() == ((SongRequest) o).getId());
    }

	/* Print the info pertaining to a song request */
    public String toString() {
        return (getName() + ": id(" + getId() + ") artist(" + getArtist() +") album(" + getAlbum() + ") genre(" + getGenre() + ") clientIp(" +
                getClientIp() + ") voteScore(" + getVotes() + ")");
    }
}