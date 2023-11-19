package edu.hw6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static edu.hw6.Task6.PortType.TCP;
import static edu.hw6.Task6.PortType.UDP;

@SuppressWarnings("MagicNumber")
public class Task6 {
    private Task6() {
    }

    private static final int MIN_PORT_NUMBER = 0;
    private static final int MAX_PORT_NUMBER = 49151;

    private static final String FREE = "Свободен";

    private static final String BUSY = "Занят";

    private static final String OUTPUT_FORMAT = "   %s \t  %s \t %s \t %s";

    private static final Map<Integer, PortInfo> PORT_INFO_MAP = new HashMap<>() {{
        put(80, new PortInfo(80, "HTTP (HyperText Transfer Protocol)"));
        put(21, new PortInfo(21, "FTP (File Transfer Protocol)"));
        put(25, new PortInfo(25, "SMTP (Simple Mail Transfer Protocol)"));
        put(22, new PortInfo(22, "SSH (Secure Shell)"));
        put(137, new PortInfo(137, "Служба имен NetBIOS"));
        put(1900, new PortInfo(1900, "Simple Service Discovery Protocol (SSDP)"));
        put(5432, new PortInfo(5432, "PostgreSQL Database"));
        put(135, new PortInfo(135, "EPMAP"));
    }};

    public static List<String> scanAllPorts() {
        List<String> scanResult = new ArrayList<>();
        scanResult.add("Протокол  Порт   Статус   \t Сервис");
        for (int port = MIN_PORT_NUMBER; port <= MAX_PORT_NUMBER; port++) {
            boolean portIsFree = true;
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                serverSocket.close();
            } catch (IOException e) {
                portIsFree = false;
            } finally {
                PortInfo portInfo = PORT_INFO_MAP.getOrDefault(port, new PortInfo(port, ""));
                String portStatus = portIsFree ? FREE : BUSY;
                scanResult.add(
                    String.format(
                        OUTPUT_FORMAT,
                        TCP,
                        portInfo.number,
                        portStatus,
                        portInfo.description
                    ));
            }

            try {
                DatagramSocket datagramSocket = new DatagramSocket(port);
                datagramSocket.close();
            } catch (IOException e) {
                portIsFree = false;
            } finally {
                PortInfo portInfo = PORT_INFO_MAP.getOrDefault(port, new PortInfo(port, ""));
                String portStatus = portIsFree ? FREE : BUSY;
                scanResult.add(
                    String.format(
                        OUTPUT_FORMAT,
                        UDP,
                        portInfo.number,
                        portStatus,
                        portInfo.description
                    ));
            }
        }
        return scanResult;
    }

    private record PortInfo(int number, String description) {
    }

    enum PortType {
        UDP,
        TCP
    }

}
