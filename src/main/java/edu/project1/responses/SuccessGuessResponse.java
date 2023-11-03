package edu.project1.responses;

public record SuccessGuessResponse(String answeredWord) implements GuessResponse {
    @Override
    public String message() {
        return "Hit!\n" + getWord(answeredWord);
    }

    @Override
    public String answeredWord() {
        return answeredWord;
    }
}
