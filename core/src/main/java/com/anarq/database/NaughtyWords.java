package com.anarq.database;

/**
 * A utility class containing the naughty words of the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class NaughtyWords {
    /**
     * The naughty words.
     */
    public static final String[] naughtyWords = new String[] {
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
            "whore",
            "bum"
    };

    /**
     * Determines whether or not the specified word is naughty.
     *
     * @param word the word to be used in the operation
     * @return {@code true}, if the specified word is naughty and {@code false} otherwise
     */
    public static boolean isANaughtyWord(String word) {
        for (String naughtyWord : naughtyWords) {
            if (word.contains(naughtyWord)) {
                return true;
            } //end if
        } //end for

        return false;
    } //isANaughtyWord
}