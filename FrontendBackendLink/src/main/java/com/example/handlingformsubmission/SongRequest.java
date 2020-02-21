package com.example.handlingformsubmission;

public class SongRequest {

  private String songName;
  private String bandName;

  public SongRequest(String song, String band) {
	  
	setBandName(band);
	setSongName(song);
	  
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

}