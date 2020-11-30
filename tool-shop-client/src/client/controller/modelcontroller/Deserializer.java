package client.controller.modelcontroller;

import messagemodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Reads objects sent from the server
 */
public class Deserializer {
    /**
     * Stream connecting to server socket comm.
     */
    private ObjectInputStream objectIn;

    public Deserializer() {
        this.objectIn = null;
    }

    /**
     * Creates the object input stream
     * @param inStream inStream which should be from the socket to the server
     */
    public void openObjectInStream(InputStream inStream) {
        try {
            objectIn = new ObjectInputStream(inStream);
        } catch (IOException e) {
            System.err.println("Error opening input stream to server.");
            System.exit(1);
        }
    }

    /**
     * Closes the stream
     */
    public void closeObjectInStream() {
        try {
            objectIn.close();
        } catch (IOException e) {
            System.err.println("Error closing input stream to server.");
            System.exit(1);
        }
    }

    /**
     * Reads a server response message
     * @return server response message (e.g. "success" or "failed")
     */
    public String awaitResponseMessage() {
        return (String) readObjectIn();
    }

    /**
     * @return a Message object received from the server
     */
    public Message readMessage() {
        return (Message) readObjectIn();
    }

    /**
     * @return an Object received from the server
     */
    private Object readObjectIn() {
        try {
            return objectIn.readObject();
        } catch (IOException e) {
            System.err.println("Error while trying to deserialize incoming object");
            e.printStackTrace();
            closeObjectInStream();
            System.exit(1);
        }
        catch (ClassNotFoundException e) {
            System.err.println("Class of a serialized object cannot be found");
            e.printStackTrace();
            closeObjectInStream();
            System.exit(1);
        }
        return null;
    }
}
