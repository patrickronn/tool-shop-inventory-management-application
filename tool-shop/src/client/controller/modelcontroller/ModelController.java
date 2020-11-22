package client.controller.modelcontroller;

import client.controller.clientcontroller.ClientController;
import client.messagemodel.*;

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
//        this.customerList = cl;
        // TODO: switch this back to aggregation now composition later
        this.customerList = new CustomerList(new LinkedHashSet<Customer>());
    }

    public void sendMessage(CustomerList customerList) { // this would have inventory and potentially allow them to be null
        System.out.println("Serializes the model and sends through Serializer object");
    }

    public void sendCustomerSearchParam(String searchParamType, String searchParamValue) {
        // Good idea to add check if last search parameter was the same as last time
        // Add attribute currentSearchParam
        // If it isn't the same, send a query to server
        System.out.println("sendSearchParam() called");
        System.out.println("Search Param Type: " + searchParamType);
        System.out.println("Search Param Value: " + searchParamValue);
    }

    public void updateCustomer(Map<String, String> customerInfoMap) {
        System.out.println("updateCustomer() called");
        customerInfoMap.values().forEach(System.out::println);

        // Create a temp attribute too maybe? Might be too many params
        customerList.updateCustomer(customerInfoMap);
    }

    public void addNewCustomer(Map<String, String> customerInfoMap) {
        System.out.println("addNewCustomer() called");
        Customer customer = createCustomer(customerInfoMap);
        // Later, this would create send it to the server instead of storing it locally
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
