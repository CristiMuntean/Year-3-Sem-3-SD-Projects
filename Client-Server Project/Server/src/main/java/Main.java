import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Server server = new Server(5678);
            server.waitForConnections();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}