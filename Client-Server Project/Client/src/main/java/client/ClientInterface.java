package client;

import java.io.IOException;

public interface ClientInterface {
    void sendMessageToServer(Object message) throws IOException;
    Object receiveResponseFromServer() throws IOException, ClassNotFoundException;
}
