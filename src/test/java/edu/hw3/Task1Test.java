package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {

    @Test
    @DisplayName("Простой текст с строкой из латиницы")
    void simpleTextCipherTest() {
        // given
        String inputString = "Hello world!";
        String expectedCipher = "Svool dliow!";
        // when
        String actualCipher = Task1.atbashCipher(inputString);
        // then
        assertThat(actualCipher).isEqualTo(expectedCipher);
    }

    @Test
    @DisplayName("Большой текст с строкой из латиницы")
    void bigTextCipherTest() {
        // given
        String inputString =
            "Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler";
        String expectedCipher =
            "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi";
        // when
        String actualCipher = Task1.atbashCipher(inputString);
        // then
        assertThat(actualCipher).isEqualTo(expectedCipher);
    }

    @Test
    @DisplayName("Текст без символов латиницы")
    void noLatinCipherTest() {
        // given
        String inputString = "1.{9}";
        String expectedCipher = "1.{9}";
        // when
        String actualCipher = Task1.atbashCipher(inputString);
        // then
        assertThat(actualCipher).isEqualTo(expectedCipher);
    }

    @Test
    @DisplayName("Текст из разных символов")
    void differentSymbolsTextCipherTest() {
        // given
        String inputString = "1A.{y9}C23";
        String expectedCipher = "1Z.{b9}X23";
        // when
        String actualCipher = Task1.atbashCipher(inputString);
        // then
        assertThat(actualCipher).isEqualTo(expectedCipher);
    }
}
