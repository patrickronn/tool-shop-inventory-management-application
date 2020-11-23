package client.controller.modelcontroller;

import client.controller.clientcontroller.ClientController;
import client.messagemodel.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Map;

public class ModelController {
    private ClientController clientController;
    private Serializer serializer;
    private Deserializer deserializer;
    private CustomerList customerList;

    public ModelController(ClientController cc, Serializer s, Deserializer ds, CustomerList cl) {
        this.clientController = cc;
        this.serializer = s;
        this.deserializer = ds;
        this.customerList = cl;
        this.serializer.openObjectOutStream(this.clientController.getSocketOutStream());
        this.deserializer.openObjectInStream(this.clientController.getSocketInStream());
    }

    public void sendCustomerSearchParam(Map<String, String> customerSearchParamMap) {
        // Good idea to add check if last search parameter was the same as last time
        // Add attribute currentSearchParam
        // If it isn't the same, send a query to server
        System.out.println("sendSearchParam() called");
        System.out.println("Search Param Type: " + customerSearchParamMap.get("paramType"));
        System.out.println("Search Param Value: " + customerSearchParamMap.get("paramValue"));

        // This is incorrect, you shouldn't be casting it to Serializable
        Message message = new Message("search", "customer", (Serializable) customerSearchParamMap);
    }

    public boolean updateCustomer(Map<String, String> customerInfoMap) {
        System.out.println("updateCustomer() called");
        customerInfoMap.values().forEach(System.out::println);

        // Send customer with updated attributes
        Customer customer = createCustomer(customerInfoMap);
        Message message = new Message("update", "customer", customer);
        serializer.sendMessage(message);

        // Await response and return status
        String response = deserializer.awaitResponseMessage();
        return response.equals("success");
    }

    public int addNewCustomer(Map<String, String> customerInfoMap) {
        // TODO: consider how to implement this in ViewController and GUI (no method for adding new customer atm)
        // TODO: consider sending customer without an Id
        System.out.println("addNewCustomer() called");

        // Send new customer to add
        Customer customerSent = createCustomer(customerInfoMap);
        Message message = new Message("insert", "customer", customerSent);
        serializer.sendMessage(message);

        // Await response and return ID assigned to new customer
        String response = deserializer.awaitResponseMessage();
        if (response.equals("success"))
            return deserializer.readCustomer().getId();
        else
            return -1;
    }

    public void deleteCustomer(int customerId) {
        System.out.println("deleteCustomer() called");
        customerList.deleteCustomer(customerId);
    }

    private Customer createCustomer(Map<String, String> customerInfoMap) {
        switch (customerInfoMap.get("customerType")) {
            case "Residential":
                return new ResidentialCustomer(customerInfoMap);
            case "Commercial":
                return new CommercialCustomer(customerInfoMap);
            default:
                return null;
        }
    }
}
