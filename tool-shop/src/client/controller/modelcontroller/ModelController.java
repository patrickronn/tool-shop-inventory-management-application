package client.controller.modelcontroller;

import client.controller.clientcontroller.*;
import messagemodel.*;

public class ModelController {
    private ClientController clientController;
    private Serializer serializer;
    private Deserializer deserializer;
    private CustomerList customerList;
    private CustomerModelController customerModelController;

    public ModelController(ClientController cc, Serializer s, Deserializer ds, CustomerList cl) {
        this.clientController = cc;
        this.serializer = s;
        this.deserializer = ds;
        this.customerList = cl;

        this.customerModelController = new CustomerModelController(s, ds, customerList);
        openStreams();
    }

    public CustomerModelController getCustomerModelController() {
        return customerModelController;
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
