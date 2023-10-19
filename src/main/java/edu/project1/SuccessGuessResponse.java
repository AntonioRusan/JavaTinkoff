package edu.project1;

public record SuccessGuessResponse(int currentAttempts, int maxAttempts, String answeredWord) implements GuessResponse {
    public String message() {
        return "Hit!\nThe word: " + answeredWord;
    }
}
