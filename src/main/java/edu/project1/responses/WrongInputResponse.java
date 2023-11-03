package edu.project1.responses;

public record WrongInputResponse(String answeredWord) implements GuessResponse {
    @Override
    public String message() {
        return "Wrong input! Try again!\n";
    }

    @Override
    public String answeredWord() {
        return answeredWord;
    }

}
