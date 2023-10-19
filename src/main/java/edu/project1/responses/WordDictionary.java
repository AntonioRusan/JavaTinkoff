package edu.project1.responses;

import java.util.Random;

public class WordDictionary {
    private static final String[] WORDS = new String[] {"hello", "game", "cucumber"};

    private WordDictionary() {
    }

    public static String getRandomWord() {
        Random rn = new Random();
        int ind = rn.nextInt(WORDS.length);
        return WORDS[ind];
    }
}
