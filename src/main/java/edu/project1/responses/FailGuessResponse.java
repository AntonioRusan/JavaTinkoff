package edu.project1.responses;

public record FailGuessResponse(int currentAttempts, int maxAttempts, String answeredWord) implements GuessResponse {
    @Override
    public String message() {
        return String.format("Missed, mistake %d out of %d.\n", currentAttempts, maxAttempts) + getWord(answeredWord);
    }

    @Override
    public String answeredWord() {
        return answeredWord;
    }

}
