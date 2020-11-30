package server.controller.modelcontroller;

import messagemodel.*;
import server.controller.databasecontroller.DatabaseController;
import server.controller.servercontroller.*;

/**
 * Manages the model and instantiates specialized controllers for customer and inventory
 * components. This is ran in the server thread pool.
 */
public class ModelController implements Runnable {
    /**
     * The controller that communicates with incoming client connections
     */
    ServerController serverController;

    /**
     * Serializes objects and sends them to client.
     */
    Serializer serializer;

    /**
     * Deserialize objects received from the client
     */
    Deserializer deserializer;

    /**
     * Manages customer model objects
     */
    CustomerModelController customerModelController;

    /**
     * Manages inventory model objects
     */
    InventoryModelController inventoryModelController;

    /**
     * Queries for and makes updates to the database
     */
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

    /**
     * Runs server management of a specific client until the client has disconnected
     */
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

    /**
     * Translates messages received from the client
     * @param message a Message containing an object and requested action
     */
    private void interpretMessage(Message message) {
        try {
            switch (message.getObjectType().toLowerCase()) {
                case "customer":
                    customerModelController.interpretCustomerMessage(message);
                    break;
                case "customerparameters":
                    customerModelController.interpretCustomerListMessage(message);
                    break;
                case "itemparameters":
                    inventoryModelController.interpretInventoryMessage(message);
                    break;
                case "order":
                    inventoryModelController.interpretOrderMessage(message);
                default:
                    System.out.println("Server: cannot interpret incoming message..");
                    serializer.sendServerResponse("failed");
            }
        }catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            serializer.sendServerResponse("failed");
        }
    }

    /**
     * Open streams
     */
    private void openStreams() {
        this.serializer.openObjectOutStream(this.serverController.getClientSocketOutStream());
        this.deserializer.openObjectInStream(this.serverController.getClientSocketInStream());
    }

    /**
     * Close streams
     */
    private void close() {
        serializer.closeObjectOutStream();
        deserializer.closeObjectInStream();
        databaseController.close();
    }
}
