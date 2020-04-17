package com.anarq.core;

/* 
	Song
		Contains information about a song
	
	Author(s):
		Patrick
*/
public class Song {
	
	// Private variables
	private final String songName;
	private final String albumName;
	private final String artistName;
	private final String songGenre;
	private final String songId;
	private final int duration;
	private final boolean isExplicit;
	private final int bpm;
	//private final Image albumCover;
	
	/* Constructs a new Song class */

	public Song(String songName, String albumName, String artistName, String songGenre, String songId,
			int duration, boolean isExplicit, int bpm) {
		
		this.songName = songName;
		this.albumName = albumName;
		this.artistName = artistName;
		this.songGenre = songGenre;
		this.songId = songId;
		this.duration = duration;
		this.isExplicit = isExplicit;
		this.bpm = bpm;
	}
	
	/* Returns the name of the song */
	public String getSongName() {
		
		return songName;
		
	}
	
	/* Returns the album name of the song */
	public String getAlbumName() {
		
		return albumName;
		
	}
	
	/* Returns the artist name of the song */
	public String getArtistName() {
		
		return artistName;
		
	}
	
	/* Returns the genre of the song */
	public String getSongGenre() {
		
		return songGenre;
		
	}
	
	/* Returns the id of the song */
	public String getSongId() {
		
		return songId;
		
	}
	
	/* Returns the duration of the song in seconds */
	public int getDuration() {
		
		return duration;
		
	}
	
	/* Returns the explicity of the song */
	public boolean getIsExplicit() {
		
		return isExplicit;
		
	}
	
	/* Returns the BPM of the song in seconds */
	public int getBPM() {
		
		return bpm;
		
	}
	
}