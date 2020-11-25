package server.controller.modelcontroller;

import messagemodel.*;
import server.controller.databasecontroller.CustomerDBController;
import server.controller.databasecontroller.DatabaseController;

import java.util.HashMap;
import java.util.LinkedHashSet;

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

    private void updateCustomerInfo(Customer customer) {
        // Query DB
        System.out.println("Updating Customer info:");
        serializer.sendServerResponse("success");
    }

    private void insertCustomer(Customer customer) {
        // Query DB
        System.out.println("Inserting a new customer:");
        customer.setId(100);
        serializer.sendServerResponse("success");
        serializer.sendMessage(new Message("insert", "customer", customer));
    }

    private void deleteCustomer(Customer customer) {
        // Query DB
        System.out.println("Deleting a new customer:");
        serializer.sendServerResponse("success");
    }

    public void interpretCustomerListMessage(Message message) {
        switch (message.getAction()) {
            case "search":
                sendCustomerList((HashMap<String, String>) message.getObject());
                break;
            default:
                serializer.sendServerResponse("failed");
        }
    }

    private void sendCustomerList(HashMap<String, String> searchParamMap) {
        String searchParamType = searchParamMap.get("paramType");
        String searchParamValue = searchParamMap.get("paramValue");
        // Query DB using search parameters
        System.out.println("Querying Customer List using: " + searchParamType + ", " + searchParamValue);

//        // For testing
//        LinkedHashSet<Customer> customers = new LinkedHashSet<>();
//        for (int i = 1; i < 20; i++) {
//            customers.add(new ResidentialCustomer(i, "First"+i, "Last"+i,
//                    "Address"+i, "PostalCode"+i, "PhoneNum"+i));
//        }
//        CustomerList customerList = new CustomerList(customers);

        CustomerList customerList = databaseController.getCustomerList(searchParamType, searchParamValue);


        serializer.sendServerResponse("success");
        serializer.sendMessage(new Message("update", "customerlist", customerList));
    }
}
