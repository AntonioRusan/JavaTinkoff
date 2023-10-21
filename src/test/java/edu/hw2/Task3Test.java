package edu.hw2;

import edu.hw2.Task3.ConnectionException;
import edu.hw2.Task3.DefaultConnectionManager;
import edu.hw2.Task3.FaultyConnectionManager;
import edu.hw2.Task3.PopularCommandExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task3Test {
    @Test
    @DisplayName("Проврка стабильного соединения")
    void checkPopularCommandExecutorStable() {
        // given
        PopularCommandExecutor executor = new PopularCommandExecutor(new DefaultConnectionManager(0), 3);

        // when

        // then
        assertDoesNotThrow(() -> executor.updatePackages());
    }

    @Test
    @DisplayName("Проврка проблемного соединения: кончились попытки")
    void checkPopularCommandExecutorFaulty() {
        // given
        PopularCommandExecutor executor = new PopularCommandExecutor(new FaultyConnectionManager(1), 3);

        // when
        Exception exception = assertThrows(ConnectionException.class, () -> executor.updatePackages());

        String expectedMessage = "Number of connection attempts exceeded!";
        String actualMessage = exception.getMessage();

        // then
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

    @Test
    @DisplayName("Проврка проблемного соединения: всегда возвращает исключение")
    void checkFaultyConnectionManager() {
        // given
        String command = "ls";
        FaultyConnectionManager faultyManager = new FaultyConnectionManager(1);

        // when
        Exception exception =
            assertThrows(ConnectionException.class, () -> faultyManager.getConnection().execute(command));

        String expectedMessage = "Faulty connection error!";
        String actualMessage = exception.getMessage();

        // then
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

    @Test
    @DisplayName("Проврка дефолтного соединения: всегда возвращает проблемное")
    void checkDefaultConnectionManagerFaulty() {
        // given
        String command = "ls";
        DefaultConnectionManager defaultManager = new DefaultConnectionManager(1);

        // when
        Exception exception =
            assertThrows(ConnectionException.class, () -> defaultManager.getConnection().execute(command));

        String expectedMessage = "Faulty connection error!";
        String actualMessage = exception.getMessage();

        // then
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

    @Test
    @DisplayName("Проврка дефолтного соединения: всегда возвращает стабильное")
    void checkDefaultConnectionManagerStable() {
        // given
        String command = "ls";
        DefaultConnectionManager defaultManager = new DefaultConnectionManager(0);

        // when

        // then
        assertDoesNotThrow(() -> defaultManager.getConnection().execute(command));
    }
}
