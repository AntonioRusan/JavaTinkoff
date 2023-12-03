package edu.hw8.ClientServerSelector;

import java.util.logging.Logger;

public class AppClient {
    private AppClient() {
    }

    static Logger logger = Logger.getAnonymousLogger();

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
        String clientRequest = "глупый";
        client.send(Converter.strToByte(clientRequest));
        String serverResponse = Converter.byteToStr(client.waitResponse());
        logger.info(serverResponse);
        client.close();
    }
}
