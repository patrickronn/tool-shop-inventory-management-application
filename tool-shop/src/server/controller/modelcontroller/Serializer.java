package server.controller.modelcontroller;

import messagemodel.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class Serializer {
    private ObjectOutputStream objectOut;

    public Serializer() {
        this.objectOut = null;
    }

    public void openObjectOutStream(OutputStream outStream) {
        try {
            objectOut = new ObjectOutputStream(outStream);
        } catch (IOException e) {
            System.err.println("Error opening output stream to server.");
            System.exit(1);
        }
    }

    public void sendServerResponse(String response) {
        sendSerializableObject(response);
    }

    public void sendMessage(Message message) {
        sendSerializableObject(message);
    }

    private void sendSerializableObject(Serializable object) {
        try {
            objectOut.writeObject(object);
        } catch (IOException e) {
            System.err.println("Error sending object to output stream of server.");
            System.exit(1);
        }
    }

    public void closeObjectOutStream() {
        try {
            objectOut.close();
        } catch (IOException e) {
            System.err.println("Error closing output stream to server.");
            System.exit(1);
        }
    }
}
