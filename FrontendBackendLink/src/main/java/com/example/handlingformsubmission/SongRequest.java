package com.example.handlingformsubmission;

import java.util.Objects;

public class SongRequest {

	private String songName;
	private String bandName;
	private String requesterId;
	private long id;
	private int score = 0;

	public SongRequest(String song, String band, String requester) {
	  
		setBandName(band);
		setSongName(song);
		setRequesterId(requester);
		setId((long) (Math.random() * 1000000));
	  
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getBandName() {
		return bandName;
	}

	public void setBandName(String bandName) {
		this.bandName = bandName;
	}
	
	public String getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void adjustScore(int score) {
		this.score += score;
	}

	@Override
	public String toString() {
		return
		"{" + 
		"songName='" + songName + "'" +
		"bandName='" + bandName + "'" +
		"requesterId='" + requesterId + "'" +
		"id='" + id + "'" +
		"score='" + score + "'" +
		"}";
	}

}