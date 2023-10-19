package edu.project1;

import edu.project1.responses.FailGuessResponse;
import edu.project1.responses.GameCanceledResponse;
import edu.project1.responses.GameOverResponse;
import edu.project1.responses.GuessResponse;
import edu.project1.responses.SuccessGuessResponse;
import edu.project1.responses.WinResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HangmanSession {

    private final String codeWord;
    private char[] answeredWord;
    private final int maxNumberOfAttempts;
    private int numberOfAttempts;

    public HangmanSession(String codeWord) {
        this.codeWord = codeWord;
        this.maxNumberOfAttempts = codeWord.length();
        this.numberOfAttempts = 0;
        this.answeredWord = new char[codeWord.length()];
        Arrays.fill(answeredWord, '*');
    }

    public GuessResponse guessWord(char letter) {
        List<Integer> allAppearancesList = getAllAppearancesInCodeWord(letter);
        if (allAppearancesList.isEmpty()) {
            numberOfAttempts++;
            if (numberOfAttempts == maxNumberOfAttempts) {
                return new GameOverResponse(numberOfAttempts, maxNumberOfAttempts, new String(answeredWord));
            } else {
                return new FailGuessResponse(numberOfAttempts, maxNumberOfAttempts, new String(answeredWord));
            }
        } else {
            for (Integer ind : allAppearancesList) {
                answeredWord[ind] = codeWord.charAt(ind);
            }
            if (!(new String(answeredWord).contains("*"))) {
                return new WinResponse(new String(answeredWord));
            } else {
                return new SuccessGuessResponse(new String(answeredWord));
            }
        }
    }

    public GuessResponse cancelGame() {
        return new GameCanceledResponse(new String(answeredWord));
    }

    private List<Integer> getAllAppearancesInCodeWord(char letter) {
        List<Integer> result = new ArrayList<>();
        int index = this.codeWord.indexOf(letter);
        while (index >= 0) {
            result.add(index);
            index = this.codeWord.indexOf(letter, index + 1);
        }
        return result;
    }
}
