package edu.hw8.ClientServerMultiThreadRawSockets;

public class AppServer {
    private AppServer() {
    }

    @SuppressWarnings({"UncommentedMain", "MagicNumber"})
    public static void main(String[] args) {
        Server server = new Server(8080);
        server.start(8080);
    }
}
