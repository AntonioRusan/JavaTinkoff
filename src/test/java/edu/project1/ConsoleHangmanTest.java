package edu.project1;

import edu.project1.responses.GuessResponse;
import edu.project1.responses.SuccessGuessResponse;
import edu.project1.responses.WrongInputResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConsoleHangmanTest {
    @Test
    @DisplayName("Проверка различных опечаток")
    void tryGuessWrongInputTest() {
        // given
        String codeWord = "grass";
        String expectedGuessResult = "*****";
        String inputLetterString = "test";
        String inputLetterSymbol = ";";
        String inputLetterDigit = "1";

        ConsoleHangman hangmanGame = new ConsoleHangman();

        HangmanSession gameSession = new HangmanSession(codeWord);

        // when
        GuessResponse responseString = hangmanGame.tryGuess(gameSession, inputLetterString);
        GuessResponse responseSymbol = hangmanGame.tryGuess(gameSession, inputLetterSymbol);
        GuessResponse responseDigit = hangmanGame.tryGuess(gameSession, inputLetterDigit);

        // then
        assertThat(responseString).isExactlyInstanceOf(WrongInputResponse.class);
        assertThat(responseString.answeredWord()).isEqualTo(expectedGuessResult);

        assertThat(responseSymbol).isExactlyInstanceOf(WrongInputResponse.class);
        assertThat(responseSymbol.answeredWord()).isEqualTo(expectedGuessResult);

        assertThat(responseDigit).isExactlyInstanceOf(WrongInputResponse.class);
        assertThat(responseDigit.answeredWord()).isEqualTo(expectedGuessResult);
    }

    @Test
    @DisplayName("Проверка, что при опечатке не меняется состояние игры")
    void tryGuessWrongInputAfterSuccessTest() {
        // given
        String codeWord = "grass";
        String expectedGuessResult = "***ss";
        String inputLetterString = "test";
        String inputLetter = "s";

        ConsoleHangman hangmanGame = new ConsoleHangman();

        HangmanSession gameSession = new HangmanSession(codeWord);

        // when
        GuessResponse guessResult = hangmanGame.tryGuess(gameSession, inputLetter);
        GuessResponse responseString = hangmanGame.tryGuess(gameSession, inputLetterString);

        // then
        assertThat(guessResult).isExactlyInstanceOf(SuccessGuessResponse.class);
        assertThat(guessResult.answeredWord()).isEqualTo(expectedGuessResult);

        assertThat(responseString).isExactlyInstanceOf(WrongInputResponse.class);
        assertThat(responseString.answeredWord()).isEqualTo(expectedGuessResult);

    }

    @Test
    @DisplayName("Проверка, что игра не запускается при некорректной длине слова")
    void createGameSessionWrongInputTest() {
        // given
        String codeWord = "";

        ConsoleHangman hangmanGame = new ConsoleHangman();

        // when
        Exception exception =
            assertThrows(IllegalArgumentException.class, () -> hangmanGame.createGameSession(codeWord));

        String expectedMessage = "\nWrong code word input!";
        String actualMessage = exception.getMessage();

        // then
        assertThat(actualMessage).isEqualTo(expectedMessage);

    }
}
