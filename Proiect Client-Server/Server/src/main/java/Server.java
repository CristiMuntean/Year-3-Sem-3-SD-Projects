import handlers.RequestHandler;
import persistence.DatabaseFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket client;

    public Server(int port) throws IOException {
        DatabaseFactory databaseFactory = new DatabaseFactory();
        if(databaseFactory.initializeDatabase())
            System.out.println("Database created");
        else System.out.println("Database already created");
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
    }

    public void waitForConnections() {

        System.out.println("Server is waiting for connections on port: " + serverSocket.getLocalPort());
        while (true) {
            try {
                client = serverSocket.accept();
                System.out.println("Server connected to socket with address: "
                        + client.getInetAddress().getHostAddress()
                        + ", and port: " + client.getPort());
                receiveRequests();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Client connection has been lost.\nServer is waiting for connections on port: " + serverSocket.getLocalPort());
            }
        }
    }

    public void receiveRequests() throws IOException, ClassNotFoundException {
        RequestHandler handler = new RequestHandler(client);
        handler.receiveRequests();
    }

}
