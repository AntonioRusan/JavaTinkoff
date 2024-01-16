package edu.hw8.ClientServerSelector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

@SuppressWarnings("MagicNumber")
public class Client {
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    private SocketChannel socketChannel;
    private Selector selector;

    public void start() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(8080));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(byte[] bytes) {
        byteBuffer.clear().put(bytes);
        byteBuffer.flip();
        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] waitResponse() {
        try {
            selector.select();
            byteBuffer.clear();
            socketChannel.read(byteBuffer);
            return byteBuffer.array();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            socketChannel.close();
            selector.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
