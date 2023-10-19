package edu.project1;

import java.util.ArrayList;
import java.util.List;

public class HangmanSession {

    private final String codeWord;
    private String answeredWord;
    private final int maxNumberOfAttempts;
    private int numberOfAttempts;

    public HangmanSession(String codeWord, int maxNumberOfAttempts) {
        this.codeWord = codeWord;
        this.maxNumberOfAttempts = maxNumberOfAttempts;
        this.numberOfAttempts = maxNumberOfAttempts;
        this.answeredWord = "*".repeat(codeWord.length());
    }

    public GuessResponse guessWord(char letter) {
        List<Integer> allAppearancesList = getAllAppearancesInCodeWord(letter);
        if (allAppearancesList.isEmpty()) {
            if (numberOfAttempts == maxNumberOfAttempts)
            {
                return new GameOverResponse(numberOfAttempts, maxNumberOfAttempts, answeredWord);
            }
            else
            {
                return new FailGuessResponse(numberOfAttempts, maxNumberOfAttempts, answeredWord);
            }
        } else {
            for (ind: allAppearancesList){

            }
        }
    }

    private List<Integer> getAllAppearancesInCodeWord(char letter) {
        List<Integer> result = new ArrayList<Integer>();
        int index = this.codeWord.indexOf(letter);
        while (index >= 0) {
            result.add(index);
            index = this.codeWord.indexOf(letter, index + 1);
        }
        return result;
    }
}
