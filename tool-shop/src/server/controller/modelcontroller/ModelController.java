package server.controller.modelcontroller;

import messagemodel.*;
import server.controller.servercontroller.*;

public class ModelController implements Runnable {
    ServerController serverController;
    Serializer serializer;
    Deserializer deserializer;

    public ModelController(ServerController sc, Serializer s, Deserializer ds) {
        this.serverController = sc;
        this.serializer = s;
        this.deserializer = ds;
        openStreams();
    }

    @Override
    public void run() {
        boolean clientConnected = true;
        while(clientConnected) {
            Message message = deserializer.readMessage();
            if (message.getAction().equals("disconnect"))
                clientConnected = false;
            else {
                interpretMessage(message);
                serializer.sendServerResponse("success");
            }
        }
        closeStreams();
    }

    public void interpretMessage(Message message) {
        switch (message.getObjectType()) {
            case ("Customer"):
                interpretCustomerMessage(message);
        }
    }

    public void interpretCustomerMessage(Message message) {
        System.out.println("Message details:");
        System.out.println(message.getObjectType());
        System.out.println(message.getAction());
        Customer customer = (Customer) message.getObject();
        System.out.println(customer.getFirstName() + " " + customer.getLastName());
    }

    public void openStreams() {
        this.serializer.openObjectOutStream(this.serverController.getClientSocketOutStream());
        this.deserializer.openObjectInStream(this.serverController.getClientSocketInStream());
    }

    public void closeStreams() {
        serializer.closeObjectOutStream();
        deserializer.closeObjectInStream();
    }
}
