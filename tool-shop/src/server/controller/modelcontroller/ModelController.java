package server.controller.modelcontroller;

import messagemodel.*;
import server.controller.databasecontroller.DatabaseController;
import server.controller.servercontroller.*;

public class ModelController implements Runnable {
    ServerController serverController;
    Serializer serializer;
    Deserializer deserializer;
    CustomerModelController customerModelController;
    InventoryModelController inventoryModelController;
    DatabaseController databaseController;

    public ModelController(ServerController sc, Serializer s, Deserializer ds, DatabaseController dbc) {
        this.serverController = sc;
        this.serializer = s;
        this.deserializer = ds;
        this.databaseController = dbc;

        this.customerModelController = new CustomerModelController(s, ds, dbc);
        this.inventoryModelController = new InventoryModelController(s, ds, dbc);
        openStreams();
    }

    @Override
    public void run() {
        System.out.println("Server: a new client is being managed on " + Thread.currentThread().getName());
        boolean clientConnected = true;
        while(clientConnected) {
            Message message = deserializer.readMessage();
            if (message == null || message.getAction().equals("disconnect"))
                clientConnected = false;
            else {
                interpretMessage(message);
            }
        }
        System.out.println("Server: a client has disconnected on " + Thread.currentThread().getName());
        close();
    }

    private void interpretMessage(Message message) {
        try {
            switch (message.getObjectType().toLowerCase()) {
                case "customer":
                    customerModelController.interpretCustomerMessage(message);
                    break;
                case "customerlist":
                    customerModelController.interpretCustomerListMessage(message);
                    break;
                case "inventory":
                    inventoryModelController.interpretInventoryMessage(message);
                    break;
                default:
                    System.out.println("Server: cannot interpret incoming message..");
                    serializer.sendServerResponse("failed");
            }
        }catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            serializer.sendServerResponse("failed");
        }
    }

    private void openStreams() {
        this.serializer.openObjectOutStream(this.serverController.getClientSocketOutStream());
        this.deserializer.openObjectInStream(this.serverController.getClientSocketInStream());
    }

    private void close() {
        serializer.closeObjectOutStream();
        deserializer.closeObjectInStream();
        databaseController.close();
    }
}
