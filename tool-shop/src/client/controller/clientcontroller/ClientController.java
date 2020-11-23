package client.controller.clientcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientController {
    /**
     * Connects to the server.
     */
    private Socket socketToServer;

    public ClientController(String serverName, int portNumber) {
        try {
            socketToServer = new Socket(serverName, portNumber);
        } catch (IOException e) {
            System.err.println("Error connecting to server");
            System.exit(1);
        }
    }

    public OutputStream getSocketOutStream() {
        try {
            return socketToServer.getOutputStream();
        } catch (IOException e) {
            System.err.println("Error connecting to server.");
            System.exit(1);
        }
        return null;
    }

    public InputStream getSocketInStream() {
        try {
            return socketToServer.getInputStream();
        } catch (IOException e) {
            System.err.println("Error connecting to server");
            System.exit(1);
        }
        return null;
    }


    private void closeSocket() {
        try {
            socketToServer.close();
        } catch (IOException e) {
            System.err.println("Error connecting to server.");
            System.exit(1);
        }
    }


}
