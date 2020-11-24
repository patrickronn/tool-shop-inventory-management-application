package client.controller.modelcontroller;

import client.controller.clientcontroller.*;
import messagemodel.*;

import java.util.ArrayList;
import java.util.HashMap;
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
        openStreams();
    }

    public void requestCustomerList(Map<String, String> customerSearchParamMap) {
        System.out.println("requestCustomerList() called");
        System.out.println("Search Param Type: " + customerSearchParamMap.get("paramType"));
        System.out.println("Search Param Value: " + customerSearchParamMap.get("paramValue"));

        // Send customer search parameters
        Message message = new Message("search", "customerlist", (HashMap<String, String>) customerSearchParamMap);
        serializer.sendMessage(message);

        // Await response and store results
        String response = deserializer.awaitResponseMessage();
        if (response.equals("success")) {
            Message responseMessage = deserializer.readMessage();
            this.customerList = (CustomerList) responseMessage.getObject();
        }
    }

    public ArrayList<String> getAllCustomerStrings() {
        return customerList.getCustomerStringList();
    }

    public Map<String, String> getCustomerInfo(int customerId) {
        return this.customerList.getCustomerInfo(customerId);
    }

    public boolean updateCustomer(Map<String, String> customerInfoMap) {
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
        Customer customer = createCustomer(customerInfoMap);
        Message message = new Message("insert", "customer", customer);
        serializer.sendMessage(message);

        // Await response and return ID assigned to new customer
        // TODO: update this to match how it's done on server side
        String response = deserializer.awaitResponseMessage();
        if (response.equals("success")) {
            Customer customerWithId = (Customer) deserializer.readMessage().getObject();
            customerList.addCustomer(customerWithId);
            return customerWithId.getId();
        }
        else
            return -1;
    }

    public void deleteCustomer(int customerId) {
        System.out.println("deleteCustomer() called");
        customerList.deleteCustomer(customerId);
    }

    private Customer createCustomer(Map<String, String> customerInfoMap) {
        switch (customerInfoMap.get("customerType")) {
            case "R":
                return new ResidentialCustomer(customerInfoMap);
            case "C":
                return new CommercialCustomer(customerInfoMap);
            default:
                return null;
        }
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
