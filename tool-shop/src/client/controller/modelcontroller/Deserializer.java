package client.controller.modelcontroller;

import client.messagemodel.*;

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

    public String awaitResponseMessage() {
        try {
            return (String) objectIn.readObject();
        } catch (IOException e) {
            System.err.println("Error while trying to deserialize server response");
            System.exit(1);
        }
        catch (ClassNotFoundException e) {
            System.err.println("Class of a serialized object cannot be found");
            System.exit(1);
        }
        return null;
    }

    public Customer readCustomer() {
        try {
            return (Customer) objectIn.readObject();
        } catch (IOException e) {
            System.err.println("Error while trying to deserialize customer list");
            System.exit(1);
        }
        catch (ClassNotFoundException e) {
            System.err.println("Class of a serialized object cannot be found");
            System.exit(1);
        }
        return null;
    }

    public CustomerList readCustomerList() {
        try {
            return (CustomerList) objectIn.readObject();
        } catch (IOException e) {
            System.err.println("Error while trying to deserialize customer list");
            System.exit(1);
        }
        catch (ClassNotFoundException e) {
            System.err.println("Class of a serialized object cannot be found");
            System.exit(1);
        }
        return null;
    }

    public void closeObjectInStream() {
        try {
            objectIn.close();
        } catch (IOException e) {
            System.err.println("Error closing input stream to server.");
            System.exit(1);
        }
    }
}
