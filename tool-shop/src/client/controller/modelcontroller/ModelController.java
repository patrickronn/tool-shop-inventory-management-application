package client.controller.modelcontroller;

import client.controller.clientcontroller.ClientController;
import client.messagemodel.CustomerList;

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
        customerInfoMap.values().stream().forEach(System.out::println);
        // Create a temp attribute too maybe? Might be too many params
        customerList.updateCustomerInfo(customerInfoMap);
    }

    public void deleteCustomer(int customerId) {
        System.out.println("deleteCustomer() called");
        customerList.deleteCustomer(customerId);
    }
}
