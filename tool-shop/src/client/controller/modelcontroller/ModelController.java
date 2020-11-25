package client.controller.modelcontroller;

import client.controller.clientcontroller.*;
import messagemodel.*;

public class ModelController {
    private ClientController clientController;
    private Serializer serializer;
    private Deserializer deserializer;
    private CustomerList customerList;
    private CustomerModelController customerModelController;
    private Inventory inventory;
    private InventoryModelController inventoryModelController;

    public ModelController(ClientController cc, Serializer s, Deserializer ds, CustomerList cl, Inventory inv) {
        this.clientController = cc;
        this.serializer = s;
        this.deserializer = ds;
        this.customerList = cl;
        this.inventory = inv;

        this.customerModelController = new CustomerModelController(s, ds, customerList);
        this.inventoryModelController = new InventoryModelController(s, ds, inv);
        openStreams();
    }

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
