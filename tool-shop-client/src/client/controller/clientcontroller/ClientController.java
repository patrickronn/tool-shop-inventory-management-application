package client.controller.clientcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Controller that attempts to connect to the server.
 */
public class ClientController {
    /**
     * Connects to the server.
     */
    private Socket socketToServer;

    /**
     * Creates a new socket connection.
     * @param serverName name of the server address (e.g. "localhost")
     * @param portNumber port to connect to (e.g. 8099)
     */
    public ClientController(String serverName, int portNumber) {
        try {
            socketToServer = new Socket(serverName, portNumber);
        } catch (IOException e) {
            System.err.println("Error connecting to server");
            System.exit(1);
        }
    }

    /**
     * @return Output stream of the server socket.
     */
    public OutputStream getSocketOutStream() {
        try {
            return socketToServer.getOutputStream();
        } catch (IOException e) {
            System.err.println("Error connecting to server.");
            System.exit(1);
        }
        return null;
    }

    /**
     * @return Input stream of the server socket
     */
    public InputStream getSocketInStream() {
        try {
            return socketToServer.getInputStream();
        } catch (IOException e) {
            System.err.println("Error connecting to server");
            System.exit(1);
        }
        return null;
    }

    /**
     * Closes the socket comms with server.
     */
    private void closeSocket() {
        try {
            socketToServer.close();
        } catch (IOException e) {
            System.err.println("Error connecting to server.");
            System.exit(1);
        }
    }


}
