package edu.hw6;

import edu.hw6.Task5.HackerNews;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class Task5Test {
    @Test
    @DisplayName("Проверка получения наиболее обсуждаемых новостей")
    void checkHackerNewsTopStories() {
        // given

        //when
        long[] result = HackerNews.hackerNewsTopStories();

        //then
        assertDoesNotThrow(HackerNews::hackerNewsTopStories);
        assertThat(result.length > 0).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "37570037, JDK 21 Release Notes",
        "38313609, What Ilya Sutskever really wants",
        "38286163, iMessage for Android",
    })
    @DisplayName("Проверка получения названия новости")
    void checkGetNewsTitle(long newsId, String expectedTitle) {
        // given
        //when
        String actual = HackerNews.getNewsTitle(newsId);
        //then
        assertThat(actual).isEqualTo(expectedTitle);
    }
}
