package com.anarq.songrequests;

import com.anarq.spotify.*;
import java.util.ArrayList;
import com.anarq.core.*;

/* 
	AutoDJ
		Handles the automatic song choosing of the application
	
	Author(s):
		Patrick
*/
public class AutoDJ {
	
	private ArrayList<String> pastGenres;
	private ArrayList<Integer> pastBPMs;
	private ArrayList<String> pastArtists;
	private Session session;
	
	public AutoDJ (Session parentSession) {
		
		pastGenres = new ArrayList<String>();
		pastBPMs = new ArrayList<Integer>();
		pastArtists = new ArrayList<String>();
		
		session = parentSession;

		
	}
	
	public void addGenre(String genre) {	
		pastGenres.add(genre);	
	}
	
	public void addBPM(int bpm) {	
		pastBPMs.add(bpm);	
	}
	
	public void addArtist(String artist) {	
		pastArtists.add(artist);	
	}
	
	public Song getSongReccomendation() {
		
		int size_G = (int) ((float) Math.random() * (float) pastGenres.size());
		int size_A = (int) ((float) Math.random() * (float) pastArtists.size());
		String[] genreName = pastGenres.get(size_G).split(" ");
		
		System.out.println("\n" + pastArtists.get(size_A) + "\n");
		
		Song result = SearchArtistsExample.getRandomReccomendation(pastArtists.get(size_A), session.getMusicChooser());
		
		return result;
		
	}
	
}