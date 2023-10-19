package edu.project1;

import edu.project1.responses.FailGuessResponse;
import edu.project1.responses.GameOverResponse;
import edu.project1.responses.GuessResponse;
import edu.project1.responses.SuccessGuessResponse;
import edu.project1.responses.WinResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HangmanSessionTest {
    @Test
    @DisplayName("Проверка успешного угадывания буквы")
    void checkSuccessfulGuess() {
        // given
        String codeWord = "grass";
        String expectedGuessResult = "***ss";
        char inputLatter = 's';
        HangmanSession game = new HangmanSession(codeWord);

        // when
        GuessResponse guessResult = game.guessWord(inputLatter);

        // then
        assertThat(guessResult).isExactlyInstanceOf(SuccessGuessResponse.class);

        assertThat(guessResult.answeredWord()).isEqualTo(expectedGuessResult);
    }

    @Test
    @DisplayName("Проверка неуспешного угадывания буквы")
    void checkFailGuess() {
        // given
        String codeWord = "grass";
        String expectedGuessResult = "*****";
        char inputLatter = 'l';
        HangmanSession game = new HangmanSession(codeWord);

        // when
        GuessResponse guessResult = game.guessWord(inputLatter);

        // then
        assertThat(guessResult).isExactlyInstanceOf(FailGuessResponse.class);

        assertThat(guessResult.answeredWord()).isEqualTo(expectedGuessResult);
    }

    @Test
    @DisplayName("Проверка победного угадывания буквы")
    void checkWinGuess() {
        // given
        String codeWord = "grass";
        char[] inputLetters = new char[] {'a', 'r', 'g', 's'};
        String[] expectedAnswers = new String[] {"**a**", "*ra**", "gra**", "grass"};
        HangmanSession game = new HangmanSession(codeWord);

        // when

        for (int i = 0; i < inputLetters.length - 1; i++) {
            GuessResponse guessResult = game.guessWord(inputLetters[i]);
            assertThat(guessResult).isExactlyInstanceOf(SuccessGuessResponse.class);
            assertThat(guessResult.answeredWord()).isEqualTo(expectedAnswers[i]);
        }

        GuessResponse guessResult = game.guessWord(inputLetters[inputLetters.length - 1]);

        // then
        assertThat(guessResult).isExactlyInstanceOf(WinResponse.class);

        assertThat(guessResult.answeredWord()).isEqualTo(expectedAnswers[expectedAnswers.length - 1]);
    }

    @Test
    @DisplayName("Проверка последнего неугадывания буквы: кончились попытки")
    void checkGameOverGuess() {
        // given
        String codeWord = "grass";
        char[] inputLetters = new char[] {'b', 'c', 'l', 'k', 'm'};
        String[] expectedAnswers = new String[] {"*****", "*****", "*****", "*****", "*****"};
        HangmanSession game = new HangmanSession(codeWord);

        // when

        for (int i = 0; i < inputLetters.length - 1; i++) {
            GuessResponse guessResult = game.guessWord(inputLetters[i]);
            assertThat(guessResult).isExactlyInstanceOf(FailGuessResponse.class);
            assertThat(guessResult.answeredWord()).isEqualTo(expectedAnswers[i]);
        }

        GuessResponse guessResult = game.guessWord(inputLetters[inputLetters.length - 1]);

        // then
        assertThat(guessResult).isExactlyInstanceOf(GameOverResponse.class);

        assertThat(guessResult.answeredWord()).isEqualTo(expectedAnswers[expectedAnswers.length - 1]);
    }

    @Test
    @DisplayName("Проверка что игра возвращает сообщение о поражении, когда угадываем после того, как кончились попытки")
    void checkAfterGameOverGuess() {
        // given
        String codeWord = "grass";
        char[] inputLetters = new char[] {'b', 'c', 'l', 'k', 'm'};
        String[] expectedAnswers = new String[] {"*****", "*****", "*****", "*****", "*****"};
        HangmanSession game = new HangmanSession(codeWord);

        // when

        for (int i = 0; i < inputLetters.length - 1; i++) {
            GuessResponse guessResult = game.guessWord(inputLetters[i]);
            assertThat(guessResult).isExactlyInstanceOf(FailGuessResponse.class);
            assertThat(guessResult.answeredWord()).isEqualTo(expectedAnswers[i]);
        }

        GuessResponse guessResult = game.guessWord(inputLetters[inputLetters.length - 1]);
        assertThat(guessResult).isExactlyInstanceOf(GameOverResponse.class);
        assertThat(guessResult.answeredWord()).isEqualTo(expectedAnswers[expectedAnswers.length - 1]);

        // then
        char afterLetter = 's';
        guessResult = game.guessWord(afterLetter);

        assertThat(guessResult).isExactlyInstanceOf(GameOverResponse.class);
        assertThat(guessResult.answeredWord()).isEqualTo(expectedAnswers[expectedAnswers.length - 1]);
    }
}
