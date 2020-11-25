package client.controller.modelcontroller;

import messagemodel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerModelController {
    private Serializer serializer;
    private Deserializer deserializer;
    private CustomerList customerList;

    public CustomerModelController(Serializer serializer, Deserializer deserializer, CustomerList customerList) {
        this.serializer = serializer;
        this.deserializer = deserializer;
        this.customerList = customerList;
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
        // TODO: Need to consider how to deal with a user updating a customer from Residential to Commercial (different types!)
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
