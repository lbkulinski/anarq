package com.example.handlingformsubmission;

import java.util.ArrayList;

public class MusicChooser {
    ArrayList<String> validGenres;
    
    public MusicChooser() {
        validGenres = new ArrayList<String>();
    }

    public void addValidGenre(String genre) {
        validGenres.add(genre);
    }

    public boolean isValidGenre(String genre) {
        return !validGenres.contains(genre);
    }
}