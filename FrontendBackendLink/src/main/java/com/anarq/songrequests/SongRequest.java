package com.anarq.songrequests;

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
    private int id;
	private int votes;
	private boolean playing;
    private String album;
    private String name;
    private String artist;
    private String genre;
    private String clientIp;
    
	/* Initalizes a new SongRequest */
    public SongRequest(int id, String album, String name, String artist, String genre, String clientIp) {
        this.id = id;
        this.album = album;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.votes = 0;
        this.clientIp = clientIp;
        this.playing = false;
    }

	/* Adds one to the score of the request if the clientIP is ok */
    public void likeSong(String clientIp) {
        if (clientIp != getClientIp() && !playing) {
            this.votes++;
        }
        //updateQueue()
    }

	/* Removes one from the score of the request if the clientIP is ok */
    public void dislikeSong(String clientIp) {
        if (clientIp != getClientIp() && !playing) {
            this.votes--;
        }
        //updateQueue()
    }
    
	/* Returns the ID of the song request */
    public int getId() {
        return id;
    }

	/* Returns the album name of this song request */
    public String getAlbum() {
        return album;
    }

	/* Returns the name of the song request */
    public String getName() {
        return name;
    }

	/* Returns the artist name of the song request */
    public String getArtist() {
        return artist;
    }

	/* Returns the genre name of the song request */
    public String getGenre() {
        return genre;
    }

	/* Returns the vote score of the song request */
    public int getVotes() {
        return votes;
    }

	/* Returns the ip of the client who submitted the song request */
    public String getClientIp() {
        return clientIp;
    }

	/* Compare two song requests */
    @Override
    public boolean equals(Object o) {
        return (o.getClass() == this.getClass() && this.id == ((SongRequest) o).id);
    }

	/* Print the info pertaining to a song request */
    public String printSongInfo() {
        return (getName() + ": id(" + getId() + ") artist(" + getArtist() +") album(" + getAlbum() + ") genre(" + getGenre() + ") clientIp(" +
                getClientIp() + ") voteScore(" + getVotes() + ")");
    }
}