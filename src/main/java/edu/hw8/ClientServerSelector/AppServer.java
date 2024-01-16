package edu.hw8.ClientServerSelector;

public class AppServer {
    private AppServer() {
    }

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
