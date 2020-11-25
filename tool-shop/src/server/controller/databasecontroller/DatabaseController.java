package server.controller.databasecontroller;

import messagemodel.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Map;

public class DatabaseController implements DBConstants {
    private CustomerDBController customerDBController;
    private InventoryDBController inventoryDBController;
    private OrderDBController orderDBController;

    public DatabaseController() {
        customerDBController = new CustomerDBController();
        inventoryDBController = new InventoryDBController();
        orderDBController = new OrderDBController();
    }

    public CustomerList getCustomerList(String searchParam, String searchValue) {
        ResultSet customerListResult = customerDBController.getCustomerListResultSet(searchParam, searchValue);
        if (customerListResult != null)
            return convertCustomerListResultSet(customerListResult);
        throw new IllegalArgumentException(searchParam + ": " + searchValue + " is not a proper query");
    }

    public int insertCustomer(Customer customer) {
        int customerIdAssigned = customerDBController.insertCustomer(customer.getCustomerInfoMap());
        return customerIdAssigned;
    }

    public boolean updateCustomer(Customer customer) {
        return customerDBController.updateCustomer(customer.getCustomerInfoMap());
    }

    public boolean deleteCustomer(Customer customer) {
        Map<String, String> customerInfoMap = customer.getCustomerInfoMap();
        return customerDBController.deleteCustomer(customerInfoMap);
    }

    private CustomerList convertCustomerListResultSet(ResultSet customerListResult) {
        try {
            LinkedHashSet<Customer> customers = new LinkedHashSet<>();
            while(customerListResult.next())
                customers.add(convertCustomerResultSet(customerListResult));
            customerListResult.close();
            return new CustomerList(customers);
        } catch (SQLException e) {
            System.err.println("System: error converting SQL query to CustomerList");
            e.printStackTrace();
            return null;
        }
    }

    private Customer convertCustomerResultSet(ResultSet customerResult) {
        try {
            int id = customerResult.getInt("CustomerId");
            String firstName = customerResult.getString("FName");
            String lastName = customerResult.getString("LName");
            String address = customerResult.getString("Address");
            String postalCode = customerResult.getString("PostalCode");
            String phoneNum = customerResult.getString("PhoneNum");
            String type = customerResult.getString("Type");
            if (type.equals("R"))
                return new ResidentialCustomer(id, firstName, lastName, address, postalCode, phoneNum);
            else if (type.equals("C"))
                return new CommercialCustomer(id, firstName, lastName, address, postalCode, phoneNum);
            else
                return null;
        } catch (SQLException e) {
            System.err.println("System: error converting SQL query to Customer object");
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        customerDBController.close();
    }

    public static void main(String[] args) {
        DatabaseController dbcontroller = new DatabaseController();
        CustomerList customerList = dbcontroller.getCustomerList("lastName", "Smith");
        System.out.println(customerList.getCustomerStringList());
        dbcontroller.close();
    }
}
