package server.controller.modelcontroller;

import messagemodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Reads objects sent from the client
 */
public class Deserializer {
    /**
     * Stream connecting to client socket comm.
     */
    private ObjectInputStream objectIn;

    public Deserializer() {
        this.objectIn = null;
    }

    /**
     * Creates the object input stream
     * @param inStream inStream which should be from the socket to the client
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
     * @return a Message object received from the client
     */
    public Message readMessage() {
        return (Message) readObjectIn();
    }

    /**
     * @return an Object received from the client
     */
    private Object readObjectIn() {
        try {
            return objectIn.readObject();
        } catch (IOException e) {
            // client has disconnected or connection was lost
            return null;
        }
        catch (ClassNotFoundException e) {
            System.err.println("Class of a serialized object cannot be found");
            e.printStackTrace();
        }
        return null;
    }
}
