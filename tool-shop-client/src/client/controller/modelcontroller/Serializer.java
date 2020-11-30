package client.controller.modelcontroller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import messagemodel.*;

/**
 * Sends objects to the server
 */
public class Serializer {

    /**
     * Stream connecting to server socket comm.
     */
    private ObjectOutputStream objectOut;

    public Serializer() {
        this.objectOut = null;
    }

    /**
     * Creates the object out stream
     * @param outStream socket to server's output stream
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

    /**
     * Sends Messages to the server which contain objects from messagemodel package.
     * @param message a Message object to send
     */
    public void sendMessage(Message message) {
        sendSerializableObject(message);
    }

    /**
     * Sends a Serializable object to the server.
     * @param object Serializable object to send
     */
    private void sendSerializableObject(Serializable object) {
        try {
            objectOut.writeObject(object);
            objectOut.flush();
        } catch (IOException e) {
            System.err.println("Error sending object to output stream of server.");
            e.printStackTrace();
            System.exit(1);
        }
    }


}
