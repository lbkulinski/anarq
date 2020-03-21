package com.anarq.core;

public class Song {
	
	private final String songName;
	private final String albumName;
	private final String artistName;
	private final String songId;
	private final int duration;
	private final boolean isExplicit;
	
	/* Constructs a new Song class */
	public Song(String songName, String albumName, String artistName, String songId,
			int duration, boolean isExplicit) {
		
		this.songName = songName;
		this.albumName = albumName;
		this.artistName = artistName;
		this.songId = songId;
		this.duration = duration;
		this.isExplicit = isExplicit;
		
	}
	
	public String getSongName() {
		
		return songName;
		
	}
	
	public String getAlbumName() {
		
		return albumName;
		
	}
	
	public String getArtistName() {
		
		return artistName;
		
	}
	
	public String getSongId() {
		
		return songId;
		
	}
	
	public int getDuration() {
		
		return duration;
		
	}
	
	public boolean getIsExplicit() {
		
		return isExplicit;
		
	}
	
}