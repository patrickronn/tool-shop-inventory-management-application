package server.controller.modelcontroller;

import messagemodel.*;
import server.controller.databasecontroller.DatabaseController;

import java.util.HashMap;

public class CustomerModelController {
    private Serializer serializer;
    private Deserializer deserializer;
    private DatabaseController databaseController;

    public CustomerModelController(Serializer s, Deserializer ds, DatabaseController dbc) {
        serializer = s;
        deserializer = ds;
        databaseController = dbc;
    }

    public void interpretCustomerMessage(Message message) {
        Customer customer = (Customer) message.getObject();
        System.out.println("Message details:");
        System.out.println(message.getObjectType());
        System.out.println(message.getAction());
        switch(message.getAction()) {
            case "update":
                updateCustomerInfo(customer);
                break;
            case "insert":
                insertCustomer(customer);
                break;
            case "delete":
                deleteCustomer(customer);
                break;
            default:
                serializer.sendServerResponse("failed");
        }
    }

    private void insertCustomer(Customer customer) {
        // Query DB
        int customerIdAssigned = databaseController.insertCustomer(customer);
        // Return updated Customer (with ID) if successful
        if (customerIdAssigned != -1) {
            customer.setId(customerIdAssigned);
            serializer.sendServerResponse("success");
            serializer.sendMessage(new Message("insert", "customer", customer));
        }
        else {serializer.sendServerResponse("failed");}
    }

    private void updateCustomerInfo(Customer customer) {
        // Query DB
        boolean updateSuceeded = databaseController.updateCustomer(customer);
        // Send status
        if (updateSuceeded) {serializer.sendServerResponse("success");}
        else {serializer.sendServerResponse("failed");}
    }

    private void deleteCustomer(Customer customer) {
        // Query DB
        boolean deleteSucceeded = databaseController.deleteCustomer(customer);
        // Send status
        if (deleteSucceeded) {serializer.sendServerResponse("success");}
        else {serializer.sendServerResponse("failed");}
    }

    public void interpretCustomerListMessage(Message message) {
        switch (message.getAction()) {
            case "search":
                queryCustomerList((HashMap<String, String>) message.getObject());
                break;
            default:
                serializer.sendServerResponse("failed");
        }
    }

    private void queryCustomerList(HashMap<String, String> searchParamMap) {
        // Query database
        String searchParamType = searchParamMap.get("paramType");
        String searchParamValue = searchParamMap.get("paramValue");
        CustomerList customerList = databaseController.getCustomerList(searchParamType, searchParamValue);
        // Send to client
        if (customerList != null) {
            serializer.sendServerResponse("success");
            serializer.sendMessage(new Message("update", "customerlist", customerList));
        } else serializer.sendServerResponse("failed");
    }
}
