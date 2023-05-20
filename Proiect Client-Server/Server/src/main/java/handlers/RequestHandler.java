package handlers;

import java.io.*;
import java.net.Socket;

/**
 * RESPONSE CODES
 *  SUCCESSFUL RESPONSES
 *  250 - Successful employee login
 *  251 - Successful manager login
 *  252 - Successful admin login
 *  CLIENT ERRORS RESPONSES
 *  460 - Wrong credentials format
 *  461 - Invalid credentials
 *  462 - Role not supported
 *  470 - Wrong GET request format
 *  471 - Unsupported GET request
 */

public class RequestHandler {
    private Socket client;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public RequestHandler(Socket client) throws IOException {
        this.client = client;
        inputStream = new ObjectInputStream(client.getInputStream());
        outputStream = new ObjectOutputStream(client.getOutputStream());
    }

    public void receiveRequests() throws IOException, ClassNotFoundException {
        while (true) {
            String request = (String) inputStream.readObject();
            System.out.println("Request: " + request);
            processRequest(request);
        }
    }

    private void processRequest(String request) throws IOException {
        Object response = -1;
        switch (request.split(" ")[0]) {
            case "GET" -> {
                GetRequestHandler getRequestHandler = new GetRequestHandler();
                response = getRequestHandler.handleGetRequest(request);
            }
            case "DELETE" -> {
                String[] parts = request.split(" ");
                DeleteRequestHandler deleteRequestHandler = new DeleteRequestHandler();
                sendResponse("Ack delete");
                try {
                    Object deletedObject = inputStream.readObject();
                    response = deleteRequestHandler.handleDeleteRequest(deletedObject, parts[1]);

                } catch (ClassNotFoundException e) {
                    System.out.println("Error while reading the second request of the delete process");
                }
            }
            case "INSERT" -> {
                String[] parts = request.split(" ");
                InsertRequestHandler insertRequestHandler = new InsertRequestHandler();
                sendResponse("Ack insert");
                try {
                    Object insertedObject = inputStream.readObject();
                    response = insertRequestHandler.handleInsertRequest(insertedObject, parts[1]);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case "UPDATE" -> {
                String[] parts = request.split(" ");
                UpdateRequestHandler updateRequestHandler = new UpdateRequestHandler();
                sendResponse("Ack update");
                try {
                    Object newObject = inputStream.readObject();
                    sendResponse("Ack newObject");
                    Object oldObject = inputStream.readObject();
                    response = updateRequestHandler.handleUpdateRequest(newObject,oldObject,parts[1]);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        sendResponse(response);
    }

    private void sendResponse(Object response) throws IOException {
        outputStream.writeObject(response);
        outputStream.flush();
    }
}
