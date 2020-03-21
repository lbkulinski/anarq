package com.anarq.songrequests;

import java.util.ArrayList;
import com.anarq.core.*;

/* 
	MusicChooser
		Handles the kinds of music that is allowed to be chosen
	
	Author(s):
		Nick
		Patrick
*/
public class MusicChooser {
	
	// Private variables
    private ArrayList<String> validGenres;
    
	/* Initalize a new Music Chooser */
    public MusicChooser() {
        validGenres = new ArrayList<String>();
    }

	/* Adds a new valid genre to the music chooser */
    public void addValidGenre(String genre) {
        validGenres.add(genre);
    }
	
	/* Removes a new valid genre from the music chooser */
    public void removeValidGenre(String genre) {
        validGenres.remove(genre);
    }

	/* Checks to see if the genre is valid */
    public boolean isValidGenre(String genre) {
        return !validGenres.contains(genre);
    }
	
	/* Searches for songs and returns an array of song objects */
	public Song[] searchForSongs(String query) {
		
		Song[] songs = new Song[4];
		songs[0] = new Song(query, query, query, "1234ABCD", 150, false);
		songs[1] = new Song("So Long", "So Long", "Nick Olson", "0000001", 200, false);
		songs[2] = new Song("Prisoner", "Prisoner", "Dance Gavin Dance", "0000002", 180, false);
		songs[3] = new Song("Git Got", "The Money Store", "Death Grips", "0000003", 237, true);
		
		return songs;
		
	}
	
}