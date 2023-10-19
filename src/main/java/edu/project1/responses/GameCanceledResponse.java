package edu.project1.responses;

public record GameCanceledResponse(String answeredWord) implements GuessResponse {
    public String message() {
        return "Game was canceled!\n" + getWord(answeredWord);
    }
}
