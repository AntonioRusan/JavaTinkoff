package edu.hw8.ClientServerMultiThreadRawSockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public void start(int port) {
        try {
            this.connection = new Socket("localhost", port);
            this.output = new ObjectOutputStream(connection.getOutputStream());
            this.input = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendRequest(String message) {
        try {
            output.writeObject(message);
            output.flush();
            return (String) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            sendRequest("end");
            this.connection.close();
            this.output.close();
            this.input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
