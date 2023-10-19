package edu.project1;

public record FailGuessResponse(int currentAttempts, int maxAttempts, String answeredWord) implements GuessResponse {
    public String message() {
        return String.format("Missed, mistake %d out of %d.", currentAttempts, maxAttempts) + "The word: " +
            answeredWord;
    }

}
