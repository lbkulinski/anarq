package com.anarq.core;

public class MusicChooser {
	
	/* STUB: Searches for songs and returns an array of song objects */
	public Song[] searchForSongs(String query) {
		
		Song[] songs = new Song[4];
		songs[0] = new Song(query, query, query, "1234ABCD", 150, false);
		songs[1] = new Song("So Long", "So Long", "Nick Olson", "0000001", 200, false);
		songs[2] = new Song("Prisoner", "Prisoner", "Dance Gavin Dance", "0000002", 180, false);
		songs[3] = new Song("Git Got", "The Money Store", "Death Grips", "0000003", 237, true);
		
		return songs;
		
	}
	
}