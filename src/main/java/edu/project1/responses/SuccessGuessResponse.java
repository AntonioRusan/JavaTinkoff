package edu.project1.responses;

public record SuccessGuessResponse(String answeredWord) implements GuessResponse {
    public String message() {
        return "Hit!\n" + getWord(answeredWord);
    }

    public String answeredWord() {
        return answeredWord;
    }
}
