package server.controller.modelcontroller;

import messagemodel.*;
import server.controller.databasecontroller.DatabaseController;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the server-side model related to customer components.
 * Uses messagemodel package for serializing and deserializing objects with connected clients.
 */
public class CustomerModelController {
    /**
     * Serializes objects and sends them to client.
     */
    private Serializer serializer;

    /**
     * Deserialize objects received from the client
     */
    private Deserializer deserializer;

    /**
     * Queries for and makes updates to customer related information
     */
    private DatabaseController databaseController;

    public CustomerModelController(Serializer s, Deserializer ds, DatabaseController dbc) {
        serializer = s;
        deserializer = ds;
        databaseController = dbc;
    }

    /**
     * Translates Messages that have object type Customer and manages the action requested.
     *
     * @param message a Message containing a Customer object and requested action
     */
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

    /**
     * Inserts a new customer into the database.
     * Once inserted successfully, it sends the assigned customer ID back to the client
     *
     * @param customer a new Customer to add to database
     */
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

    /**
     * Updates existing customer info in the database.
     * Notifies the client whether or not the update was successful
     *
     * @param customer a Customer to update in the database
     */
    private void updateCustomerInfo(Customer customer) {
        // Query DB
        boolean updateSucceeded = databaseController.updateCustomer(customer);
        // Send status
        if (updateSucceeded) {serializer.sendServerResponse("success");}
        else {serializer.sendServerResponse("failed");}
    }

    /**
     * Deletes a customer from the database.
     * Notifies the client whether or not the deletion was successful
     *
     * @param customer a Customer to delete in the database
     */
    private void deleteCustomer(Customer customer) {
        // Query DB
        boolean deleteSucceeded = databaseController.deleteCustomer(customer);
        // Send status
        if (deleteSucceeded) {serializer.sendServerResponse("success");}
        else {serializer.sendServerResponse("failed");}
    }

    /**
     * Translates messages for CustomerList object types and manages the requested actions.
     * @param message a Message containing search parameters for a customer list
     */
    @SuppressWarnings("unchecked")
    public void interpretCustomerListMessage(Message message) {
        switch (message.getAction()) {
            case "search":
                queryCustomerList((HashMap<String, String>) message.getObject());
                break;
            default:
                serializer.sendServerResponse("failed");
        }
    }

    /**
     * Retrieves a list of customers from the database based on search parameters.
     * Sends the CustomerList to the client.
     *
     * @param searchParamMap a Map of search parameters for the SQL query
     */
    private void queryCustomerList(Map<String, String> searchParamMap) {
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
