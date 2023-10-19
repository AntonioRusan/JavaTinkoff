package edu.project1;

public sealed interface GuessResponse permits WinResponse, GameOverResponse, SuccessGuessResponse, FailGuessResponse {
    String message();

}

