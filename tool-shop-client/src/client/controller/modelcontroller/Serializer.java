package client.controller.modelcontroller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import messagemodel.*;

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

    public void closeObjectOutStream() {
        try {
            objectOut.close();
        } catch (IOException e) {
            System.err.println("Error closing output stream to server.");
            System.exit(1);
        }
    }

    /**
     * Used to notify the server of the incoming object.
     * @param objectType String of class type of the serializable object to send
     */
    private void notifyObjectType(String objectType) {
        sendSerializableObject(objectType);
    }

    public void sendMessage(Message message) {
        sendSerializableObject(message);
    }

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
