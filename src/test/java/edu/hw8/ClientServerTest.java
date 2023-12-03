package edu.hw8;

import edu.hw8.ClientServerMultiThreadRawSockets.Client;
import edu.hw8.ClientServerMultiThreadRawSockets.Server;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ClientServerTest {

    @Test
    void testSingleClient() throws InterruptedException {

        Thread serverThread = new Thread(() -> {
            Server server = new Server(1);
            server.start(8080);
        });

        serverThread.start();

        Thread.sleep(1000);

        Client client = new Client();
        client.start(8080);
        String serverResponse = client.sendRequest("личности");
        assertThat(serverResponse).isEqualTo("Не переходи на личности там, где их нет");
        serverResponse = client.sendRequest("интеллект");
        assertThat(serverResponse).isEqualTo("Чем ниже интеллект, тем громче оскорбления");
        client.close();
        serverThread.interrupt();
    }

    @Test
    void testManyClients() throws InterruptedException {

        Thread serverThread = new Thread(() -> {
            Server server = new Server(3);
            server.start(8080);
        });

        serverThread.start();

        Thread.sleep(1000);

        List<Thread> threads = new ArrayList<>() {{
            add(new Thread(() -> createClientThread("личности", "Не переходи на личности там, где их нет")));
            add(new Thread(() -> createClientThread("интеллект", "Чем ниже интеллект, тем громче оскорбления")));
            add(new Thread(() -> createClientThread(
                "глупый",
                "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма."
            )));
            add(new Thread(() -> createClientThread("оскорбления",
                "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами")));
        }};
        for (var thread : threads) {
            thread.start();
        }
        for (var thread : threads) {
            thread.join();
        }
        serverThread.interrupt();
    }

    private void createClientThread(String word, String expectedAnswer) {
        Client client = new Client();
        client.start(8080);
        String serverResponse = client.sendRequest(word);
        assertThat(serverResponse).isEqualTo(expectedAnswer);
        client.close();
    }
}
