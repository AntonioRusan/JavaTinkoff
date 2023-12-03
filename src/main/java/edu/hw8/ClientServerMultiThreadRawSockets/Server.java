package edu.hw8.ClientServerMultiThreadRawSockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("RegexpSinglelineJava")
public class Server {
    private final ExecutorService executor;
    private static final List<String> QUOTE_DICTIONARY = new ArrayList<>() {{
        add("Не переходи на личности там, где их нет");
        add("Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами");
        add("А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.");
        add("Чем ниже интеллект, тем громче оскорбления");
    }};

    public Server(int numThreads) {
        executor = Executors.newFixedThreadPool(numThreads);
    }

    public void start(int port) {
        int clientNumber = 1;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket connection = serverSocket.accept();
                int finalClientNumber = clientNumber;
                executor.execute(() -> handleClient(connection, finalClientNumber));
                clientNumber++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleClient(Socket connection, int clientNumber) {
        try (ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(connection.getInputStream())) {
            while (true) {
                String request = (String) input.readObject();
                System.out.printf("Client number: %d, request %s%n%n", clientNumber, request);
                String response = getQuote(request);
                output.writeObject(response);
                output.flush();
                if (request.equals("end")) {
                    break;
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getQuote(String request) {
        return QUOTE_DICTIONARY.stream().filter(q -> q.contains(request)).findFirst()
            .orElse("Нет подходящей цитаты");
    }
}
