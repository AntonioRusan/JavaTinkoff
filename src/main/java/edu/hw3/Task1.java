package edu.hw3;

import java.util.ArrayList;
import java.util.HashMap;

public class Task1 {
    private Task1() {
    }

    public static final HashMap<Character, Character> LATIN_SYMBOL_MAP = getSymbolMap();
    private final static int NUMBER_OF_LETTERS = 26;
    private final static char FIRST_LETTER = 'a';
    private final static char LAST_LETTER = 'z';

    private static HashMap<Character, Character> getSymbolMap() {
        HashMap<Character, Character> resultMap = new HashMap<>();
        for (int i = 0; i < NUMBER_OF_LETTERS; i++) {
            char nextLetter = (char) ((int) FIRST_LETTER + i);
            char previousLetter = (char) ((int) LAST_LETTER - i);
            resultMap.put(nextLetter, previousLetter);
            resultMap.put(Character.toUpperCase(nextLetter), Character.toUpperCase(previousLetter));
        }
        return resultMap;
    }

    public static String atbashCipher(String inputString) {
        ArrayList<Character> cipheredArray = new ArrayList<>();
        for (int i = 0; i < inputString.length(); i++) {
            char currentSymbol = inputString.charAt(i);
            cipheredArray.add(LATIN_SYMBOL_MAP.getOrDefault(currentSymbol, currentSymbol));
        }
        StringBuilder cipherStrBuilder = new StringBuilder(cipheredArray.size());
        for (Character ch : cipheredArray) {
            cipherStrBuilder.append(ch);
        }
        return cipherStrBuilder.toString();
    }
}



