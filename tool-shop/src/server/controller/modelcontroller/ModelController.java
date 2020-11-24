package server.controller.modelcontroller;

import messagemodel.*;
import server.controller.servercontroller.*;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class ModelController implements Runnable {
    ServerController serverController;
    Serializer serializer;
    Deserializer deserializer;

    public ModelController(ServerController sc, Serializer s, Deserializer ds) {
        this.serverController = sc;
        this.serializer = s;
        this.deserializer = ds;
        openStreams();
    }

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
        closeStreams();
    }

    private void interpretMessage(Message message) {
        switch (message.getObjectType().toLowerCase()) {
            case ("customer"):
                interpretCustomerMessage(message);
                break;
            case("customerlist"):
                interpretCustomerListMessage(message);
                break;
            default:
                System.out.println("Server: cannot interpret incoming message..");
                serializer.sendServerResponse("failed");
        }
    }

    private void interpretCustomerMessage(Message message) {
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

    private void interpretCustomerListMessage(Message message) {
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

        // For testing
        LinkedHashSet<Customer> customers = new LinkedHashSet<>();
        for (int i = 1; i < 20; i++) {
            customers.add(new ResidentialCustomer(i, "First"+i, "Last"+i,
                    "Address"+i, "PostalCode"+i, "PhoneNum"+i));
        }

        CustomerList customerList = new CustomerList(customers);

        serializer.sendServerResponse("success");
        serializer.sendMessage(new Message("update", "customerlist", customerList));
    }

    private void openStreams() {
        this.serializer.openObjectOutStream(this.serverController.getClientSocketOutStream());
        this.deserializer.openObjectInStream(this.serverController.getClientSocketInStream());
    }

    private void closeStreams() {
        serializer.closeObjectOutStream();
        deserializer.closeObjectInStream();
    }
}
