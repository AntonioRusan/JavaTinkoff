package edu.project1;

import edu.project1.responses.GameOverResponse;
import edu.project1.responses.GuessResponse;
import edu.project1.responses.WinResponse;
import edu.project1.responses.WordDictionary;
import java.util.Scanner;

@SuppressWarnings({"RegexpSinglelineJava"})
public class ConsoleHangman {

    public void runGame() {
        System.out.println("WELCOME TO HANGMAN GAME!");
        Scanner inScanner = new Scanner(System.in);
        HangmanSession game = new HangmanSession(WordDictionary.getRandomWord());
        boolean gameIsRunnig = true;
        while (gameIsRunnig) {
            try {
                System.out.println("\nGuess a letter:");
                String inputLetter = inScanner.nextLine();
                GuessResponse guessResult = tryGuess(game, inputLetter);
                if (guessResult != null) {
                    printState(guessResult);
                    if (guessResult instanceof WinResponse || guessResult instanceof GameOverResponse) {
                        gameIsRunnig = false;
                    }
                } else {
                    System.out.println("Wrong input! Try again!");
                }
            } catch (Exception e) {
                GuessResponse cancelGameResponse = game.cancelGame();
                printState(cancelGameResponse);
                gameIsRunnig = false;
            }
        }
        System.out.println("\nEND GAME\nGOODBYE!");
    }

    private GuessResponse tryGuess(HangmanSession session, String input) {
        if (input.length() == 1 && input.matches("[a-zA-Z]+")) {
            return session.guessWord(input.toLowerCase().charAt(0));
        } else {
            return null;
        }
    }

    private void printState(GuessResponse response) {
        System.out.println(response.message());
    }
}
