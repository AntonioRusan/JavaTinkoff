package edu.project1;

public record GameOverResponse(int currentAttempts, int maxAttempts, String answeredWord) implements GuessResponse {
    public String message() {
        return "You lost!";
    }
}
