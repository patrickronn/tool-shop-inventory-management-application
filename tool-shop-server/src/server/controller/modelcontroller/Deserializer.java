package server.controller.modelcontroller;

import messagemodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class Deserializer {
    private ObjectInputStream objectIn;

    public Deserializer() {
        this.objectIn = null;
    }

    public void openObjectInStream(InputStream inStream) {
        try {
            objectIn = new ObjectInputStream(inStream);
        } catch (IOException e) {
            System.err.println("Error opening input stream to server.");
            System.exit(1);
        }
    }

    public void closeObjectInStream() {
        try {
            objectIn.close();
        } catch (IOException e) {
            System.err.println("Error closing input stream to server.");
            System.exit(1);
        }
    }

    public Message readMessage() {
        return (Message) readObjectIn();
    }

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
