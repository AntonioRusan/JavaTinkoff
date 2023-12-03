package edu.hw8.ClientServerSelector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("MagicNumber")
public class Server {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final List<String> QUOTE_DICTIONARY = Collections.synchronizedList(new ArrayList<>() {{
        add("Не переходи на личности там, где их нет");
        add("Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами");
        add("А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.");
        add("Чем ниже интеллект, тем громче оскорбления");
    }});

    public void start() {
        try (Selector selector = Selector.open()) {
            try (ServerSocketChannel serverSocketChannel = configureServerSocketChannel(selector)) {
                while (true) {
                    selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        if (key.isAcceptable()) {
                            configureSocketChannel(serverSocketChannel, selector);
                        }
                        if (key.isReadable()) {
                            executor.execute(() -> handleClient(key));
                        }
                        iter.remove();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerSocketChannel configureServerSocketChannel(Selector selector) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8080));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            return serverSocketChannel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SocketChannel configureSocketChannel(ServerSocketChannel serverSocketChannel, Selector selector) {
        SocketChannel client;
        try {
            client = serverSocketChannel.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            return client;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleClient(SelectionKey clientKey) {
        try {
            SocketChannel client = (SocketChannel) clientKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int r = client.read(byteBuffer);
            String request = Converter.byteToStr(byteBuffer.array());
            String response = getQuote(request);
            byte[] responseInBytes = Converter.strToByte(response);
            client.write(ByteBuffer.wrap(responseInBytes));
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getQuote(String request) {
        return QUOTE_DICTIONARY.stream().filter(q -> q.contains(request)).findFirst()
            .orElse("Нет подходящей цитаты");
    }
}
