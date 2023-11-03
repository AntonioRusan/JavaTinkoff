package edu.project1.responses;

public record WinResponse(String answeredWord) implements GuessResponse {
    @Override
    public String message() {
        return "Hit!\n" + getWord(answeredWord) + "\nYou won!";
    }

    @Override
    public String answeredWord() {
        return answeredWord;
    }

}
