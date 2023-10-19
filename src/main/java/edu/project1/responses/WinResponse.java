package edu.project1.responses;

public record WinResponse(String answeredWord) implements GuessResponse {
    public String message() {
        return "Hit!\n" + getWord(answeredWord) + "\nYou won!";
    }

}
