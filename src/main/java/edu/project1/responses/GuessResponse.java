package edu.project1.responses;

public sealed interface GuessResponse permits FailGuessResponse,
    GameCanceledResponse,
    GameOverResponse,
    SuccessGuessResponse,
    WinResponse {
    String message();

    default String getWord(String word) {
        return "The word: " + word;
    }
}

