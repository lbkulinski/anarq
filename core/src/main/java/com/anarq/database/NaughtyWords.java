package com.anarq.database;

import java.io.*;

public class NaughtyWords {

	public static final String[] naughtyWords = new String[]{
		"anal",
		"anus",
		"ass",
		"bastard",
		"bitch",
		"blowjob",
		"boob",
		"cock",
		"cunt",
		"cum",
		"coon",
		"pillock",
		"dick",
		"dildo",
		"fag",
		"fuck",
		"hell",
		"homo",
		"nig",
		"nigger",
		"nigga",
		"penis",
		"piss",
		"pussy",
		"queer",
		"scrotum",
		"sex",
		"shit",
		"slut",
		"spunk",
		"tit",
		"twat",
		"vagina",
		"wank",
		"whore"
	};

	public static boolean isANaughtyWord(String word) {
	   
		for (int i = 0; i < naughtyWords.length; i++) {
			if (word.contains(naughtyWords[i])) {
				return true;
			}
		}
		return false;
	   
	}

}

