import client.controller.clientcontroller.ClientController;
import client.controller.modelcontroller.Deserializer;
import client.controller.modelcontroller.ModelController;
import client.controller.modelcontroller.Serializer;
import client.controller.viewcontroller.ViewController;
import messagemodel.Customer;
import messagemodel.CustomerList;

import java.util.LinkedHashSet;

public class ClientMain {
    public static void main(String[] args) {
        CustomerList customerList = new CustomerList(new LinkedHashSet<Customer>());
        ClientController clientController = new ClientController("localhost", 8099);
        Serializer serializer = new Serializer();
        Deserializer deserializer = new Deserializer();
        ModelController modelController = new ModelController(clientController, serializer, deserializer, customerList);
        ViewController viewController = new ViewController(modelController);
    }
}
