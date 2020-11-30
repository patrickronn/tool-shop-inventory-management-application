package client.controller.clientcontroller;

import client.controller.modelcontroller.Deserializer;
import client.controller.modelcontroller.ModelController;
import client.controller.modelcontroller.Serializer;
import client.controller.viewcontroller.ViewController;
import messagemodel.*;

import java.util.LinkedHashSet;

/**
 * Initializes the Client Tool Shop Application.
 */
public class ClientInitializer {
    public static void initialize() {
        CustomerList customerList = new CustomerList(new LinkedHashSet<Customer>());
        Inventory inventory = new Inventory(new LinkedHashSet<Item>(), new Order());
        ClientController clientController = new ClientController("localhost", 8099);
        Serializer serializer = new Serializer();
        Deserializer deserializer = new Deserializer();
        ModelController modelController = new ModelController(clientController, serializer, deserializer, customerList, inventory);
        ViewController viewController = new ViewController(modelController);
    }
}
