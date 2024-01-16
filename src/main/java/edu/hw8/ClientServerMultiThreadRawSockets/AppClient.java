package edu.hw8.ClientServerMultiThreadRawSockets;

import java.util.ArrayList;
import java.util.List;

public class AppClient {
    private AppClient() {
    }

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>() {{
            add(new Thread(AppClient::createClientThread));
            add(new Thread(AppClient::createClientThread));
            add(new Thread(AppClient::createClientThread));
            add(new Thread(AppClient::createClientThread));
        }};
        for (var thread : threads) {
            thread.start();
        }
        for (var thread : threads) {
            thread.join();
        }
    }

    @SuppressWarnings({"RegexpSinglelineJava", "MagicNumber"})
    public static void createClientThread() {
        Client client = new Client();
        client.start(8080);
        System.out.println(client.sendRequest("глупый"));
        System.out.println(client.sendRequest("оскорбления"));
        client.close();
    }
}
