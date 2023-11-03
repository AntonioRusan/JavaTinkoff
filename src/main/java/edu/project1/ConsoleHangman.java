package edu.project1;

import edu.project1.responses.GameOverResponse;
import edu.project1.responses.GuessResponse;
import edu.project1.responses.WinResponse;
import edu.project1.responses.WrongInputResponse;
import java.util.Scanner;

@SuppressWarnings({"RegexpSinglelineJava"})
public class ConsoleHangman {

    public void runGame() {
        System.out.println("WELCOME TO HANGMAN GAME!");
        Scanner inScanner = new Scanner(System.in);
        String codeWord = WordDictionary.getRandomWord();
        try {
            HangmanSession game = createGameSession(codeWord);
            boolean gameIsRunnig = true;
            while (gameIsRunnig) {
                try {
                    System.out.println("\nGuess a letter:");
                    String inputLetter = inScanner.nextLine();
                    GuessResponse guessResult = tryGuess(game, inputLetter);
                    printState(guessResult);
                    if (guessResult instanceof WinResponse || guessResult instanceof GameOverResponse) {
                        gameIsRunnig = false;
                    }
                } catch (Exception e) {
                    GuessResponse cancelGameResponse = game.cancelGame();
                    printState(cancelGameResponse);
                    gameIsRunnig = false;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            System.out.println("\nEND GAME\nGOODBYE!");
        }

    }

    public GuessResponse tryGuess(HangmanSession session, String input) {
        if (input.length() == 1 && input.matches("[a-zA-Z]+")) {
            return session.guessWord(input.toLowerCase().charAt(0));
        } else {
            return new WrongInputResponse(session.getAnsweredWord());
        }
    }

    private void printState(GuessResponse response) {
        System.out.println(response.message());
    }

    public HangmanSession createGameSession(String codeWord) throws IllegalArgumentException {
        if (codeWord.isEmpty()) {
            throw new IllegalArgumentException("\nWrong code word input!");
        } else {
            return new HangmanSession(codeWord);
        }
    }
}
