package edu.project1;

public record WinResponse(int currentAttempts, int maxAttempts, String answeredWord) implements GuessResponse {
    public String message() {
        return "You won!";
    }

}
