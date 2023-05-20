package client;

import user_domain.controller.LoginController;

import java.io.*;
import java.net.Socket;

public class Client implements ClientInterface{
    private Socket socket;
    private  ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public Client(int port) throws IOException {
        socket = new Socket("localhost", port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessageToServer(Object message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
    }

    public Object receiveResponseFromServer() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

}
