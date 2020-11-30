package client.controller.modelcontroller;

import client.controller.clientcontroller.*;
import messagemodel.*;

/**
 * Manages the model and instantiates the specialized controllers for customer and inventory model components
 * Opens and closes streams used for serialization/deserialization.
 */
public class ModelController {
    /**
     * Controller which connects to the server
     */
    private ClientController clientController;

    /**
     * Used to send objects to the server
     */
    private Serializer serializer;

    /**
     * Used to read objects from the server
     */
    private Deserializer deserializer;

    /**
     * Manages customer model objects
     */
    private CustomerModelController customerModelController;

    /**
     * Manages inventory model objects
     */
    private InventoryModelController inventoryModelController;

    public ModelController(ClientController cc, Serializer s, Deserializer ds, CustomerList cl, Inventory inv) {
        this.clientController = cc;
        this.serializer = s;
        this.deserializer = ds;
        this.customerModelController = new CustomerModelController(s, ds, cl);
        this.inventoryModelController = new InventoryModelController(s, ds, inv);
        openStreams();
    }

    // Getters bellow
    public CustomerModelController getCustomerModelController() {
        return customerModelController;
    }

    public InventoryModelController getInventoryModelController() {
        return inventoryModelController;
    }


    public void openStreams() {
        this.serializer.openObjectOutStream(this.clientController.getSocketOutStream());
        this.deserializer.openObjectInStream(this.clientController.getSocketInStream());
    }

    public void closeStreams() {
        this.serializer.closeObjectOutStream();
        this.deserializer.closeObjectInStream();
    }
}
