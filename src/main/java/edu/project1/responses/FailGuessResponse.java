package edu.project1.responses;

public record FailGuessResponse(int currentAttempts, int maxAttempts, String answeredWord) implements GuessResponse {
    public String message() {
        return String.format("Missed, mistake %d out of %d.\n", currentAttempts, maxAttempts) + getWord(answeredWord);
    }

    public String answeredWord() {
        return answeredWord;
    }

}
