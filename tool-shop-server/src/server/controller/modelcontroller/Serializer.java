package server.controller.modelcontroller;

import messagemodel.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Sends objects to the client
 */
public class Serializer {
    /**
     * Stream connecting to client socket comm.
     */
    private ObjectOutputStream objectOut;

    public Serializer() {
        this.objectOut = null;
    }

    /**
     * Creates the object out stream
     * @param outStream socket to client's output stream
     */
    public void openObjectOutStream(OutputStream outStream) {
        try {
            objectOut = new ObjectOutputStream(outStream);
        } catch (IOException e) {
            System.err.println("Error opening output stream to server.");
            System.exit(1);
        }
    }

    /**
     * Sends a String-based server response message
     * @param response a String status (e.g. "success", "failed")
     */
    public void sendServerResponse(String response) {
        sendSerializableObject(response);
    }

    /**
     * Sends Messages to the client which contain objects from messagemodel package.
     * @param message a Message object to send
     */
    public void sendMessage(Message message) {
        sendSerializableObject(message);
    }

    /**
     * Sends a Serializable object to the client.
     * @param object Serializable object to send
     */
    private void sendSerializableObject(Serializable object) {
        try {
            objectOut.writeObject(object);
            objectOut.flush();
        } catch (IOException e) {
            System.err.println("Error sending object to output stream of server.");
            e.printStackTrace();
        }
    }

    /**
     * Closes the stream
     */
    public void closeObjectOutStream() {
        try {
            objectOut.close();
        } catch (IOException e) {
            System.err.println("Error closing output stream to server.");
            System.exit(1);
        }
    }
}
