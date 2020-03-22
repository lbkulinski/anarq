package com.anarq.songrequests;

import com.anarq.spotify.*;
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
		
		return SpotifyGateway.searchForSongs(query);
		
	}
	
}