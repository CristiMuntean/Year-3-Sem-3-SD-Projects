import client.Client;
import client.ProxyClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            ProxyClient client = new ProxyClient(5678);
        } catch (IOException ignored) {}
    }
}