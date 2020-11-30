package client.controller.modelcontroller;

import messagemodel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the client-side model related to customer management.
 * Uses messagemodel package for serializing and deserializing objects from the server
 */
public class CustomerModelController {
    /**
     * Serializes objects and sends them to server.
     */
    private Serializer serializer;

    /**
     * Deserializes objects received from the server
     */
    private Deserializer deserializer;

    /**
     * Stores a copy of the customer list retrieved from the server.
     */
    private CustomerList customerList;

    public CustomerModelController(Serializer serializer, Deserializer deserializer, CustomerList customerList) {
        this.serializer = serializer;
        this.deserializer = deserializer;
        this.customerList = customerList;
    }

    /**
     * Requests a list of customers from the server.
     * @param customerSearchParamMap a Map of search parameters to tell the server what customers to include
     */
    public void requestCustomerList(Map<String, String> customerSearchParamMap) {
        // Send customer search parameters
        Message message = new Message("search", "customerparameters", (HashMap<String, String>) customerSearchParamMap);
        serializer.sendMessage(message);

        // Await response and store results
        String response = deserializer.awaitResponseMessage();
        if (response.equals("success")) {
            Message responseMessage = deserializer.readMessage();
            this.customerList = (CustomerList) responseMessage.getObject();
        }
    }

    /**
     * Getter method for customer list as strings
     * @return ArrayList of string representatoins of customers
     */
    public ArrayList<String> getCustomerStringList() {
        return customerList.getCustomerStringList();
    }

    /**
     * Searches for a customer's info and returns info as a Map.
     * @param customerId customer Id to search for
     * @return Map of string values representing each attribute value; null if customerId wasn't found
     */
    public Map<String, String> getCustomerInfo(int customerId) {
        return this.customerList.getCustomerInfoMap(customerId);
    }

    /**
     * Manages updating a customer by sending an update request to server then updates client-side if successful.
     * @param customerInfoMap Map of customer info retrieved from the user/GUI
     * @return true if server updated the customer; false if customer couldn't be updated.
     */
    public boolean updateCustomer(Map<String, String> customerInfoMap) {
        Customer customer = createCustomer(customerInfoMap);
        if (customer == null) return false;

        // Send updated customer info to server
        Message message = new Message("update", "customer", customer);
        serializer.sendMessage(message);

        // Await response
        String response = deserializer.awaitResponseMessage();

        if (response.equals("success")) {
            customerList.updateCustomer(customerInfoMap);
            return true;
        }
        else return false;
    }

    /**
     * Manages adding a new customer by sending an insert request to the server then updates client-side if successful.
     * @param customerInfoMap Map of customer info retrieve from the user/GUI
     * @return customer id assigned by the server if customer was successfully created; -1 otherwise.
     */
    public int addNewCustomer(Map<String, String> customerInfoMap) {
        Customer customer = createCustomer(customerInfoMap);
        if (customer == null) return -1;

        // Send new customer info to server
        Message message = new Message("insert", "customer", customer);
        serializer.sendMessage(message);

        // Await response and add customer with assigned ID to client's customer list
        String response = deserializer.awaitResponseMessage();
        if (response.equals("success")) {
            Customer customerWithId = (Customer) deserializer.readMessage().getObject();
            customerList.addCustomer(customerWithId);
            return customerWithId.getId();
        }
        else return -1;
    }

    /**
     * Manages deleting a customer by sending an delete request to the server then updates client-side if successful.
     * @param customerInfoMap Map of customer info retrieve from the user/GUI
     * @return true if server deleted the customer; false if customer couldn't be deleted
     */
    public boolean deleteCustomer(Map<String, String> customerInfoMap) {
        Customer customer = createCustomer(customerInfoMap);
        if (customer == null) return false;

        // Send server the customer to delete
        Message message = new Message("delete", "customer", customer);
        serializer.sendMessage(message);

        // Await response and delete customer from client's customer list
        String response = deserializer.awaitResponseMessage();
        if (response.equals("success")) {
            customerList.deleteCustomer(customerInfoMap);
            return true;
        }
        else {return false;}
    }

    /**
     * Helper method for creating a customer from a Map of customer info
     * @param customerInfoMap Map of customer info retrieve from the user/GUI
     * @return Customer object created using the info map
     */
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
}
