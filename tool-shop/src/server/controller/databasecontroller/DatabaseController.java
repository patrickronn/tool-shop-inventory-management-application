package server.controller.databasecontroller;

import messagemodel.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Map;

public class DatabaseController implements DBConstants {
    private CustomerDBController customerDBController;
    private InventoryDBController inventoryDBController;
    private OrderDBController orderDBController;
    private Connection jdbc_connection;

    public DatabaseController() {
        try {
            // If this throws an error, make sure you have added the mySQL connector JAR to the project
            Class.forName("com.mysql.jdbc.Driver");
            // If this fails make sure your connectionInfo and login/password are correct
            this.jdbc_connection = DriverManager.getConnection(CONNECTION_INFO, USER, PASSWORD);
            System.out.println("System: connected to " + CONNECTION_INFO);

            // Instantiate sub-controllers
            this.customerDBController = new CustomerDBController(jdbc_connection);
            this.inventoryDBController = new InventoryDBController(jdbc_connection);
            this.orderDBController = new OrderDBController(jdbc_connection);

        } catch(SQLException e) {
            System.err.println("System: error connecting to " + CONNECTION_INFO);
            e.printStackTrace();
            System.exit(1);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public LinkedHashSet<Item> getItems(String searchParam, String searchValue) {
        ResultSet inventoryToolsResult = inventoryDBController.getInventoryToolResultSet(searchParam, searchValue);
        if (inventoryToolsResult != null)
            return convertItemListResultSet(inventoryToolsResult);
        else
            throw new IllegalArgumentException(searchParam + ": " + searchValue + " is not a proper query");
    }

    public CustomerList getCustomerList(String searchParam, String searchValue) {
        ResultSet customerListResult = customerDBController.getCustomerListResultSet(searchParam, searchValue);
        if (customerListResult != null)
            return convertCustomerListResultSet(customerListResult);
        else
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

    private LinkedHashSet<Item> convertItemListResultSet(ResultSet inventoryToolsResult) {
        try {
            LinkedHashSet<Item> items = new LinkedHashSet<>();
            while (inventoryToolsResult.next())
                items.add(convertItemResultSet(inventoryToolsResult));
            inventoryToolsResult.close();
            return items;
        } catch (SQLException e) {
            System.err.println("System: error converting ResultSet to Inventory.");
            e.printStackTrace();
            return null;
        }
    }

    private Item convertItemResultSet(ResultSet itemResult) {
        try {
            int id = itemResult.getInt("ToolId");
            String name = itemResult.getString("Name");
            String type = itemResult.getString("Type");
            int quantity = itemResult.getInt("Quantity");
            double price = itemResult.getDouble("Price");
            int supplierId = itemResult.getInt("SupplierId");
            String powerType = itemResult.getString("PowerType");

            if (type.equals("Electrical"))
                return new ElectricalItem(id, name, quantity, price, supplierId, powerType);
            else
                return new NonElectricalItem(id, name, quantity, price, supplierId);
        } catch (SQLException e) {
            System.err.println("System: error converting ResultSet to Item object");
            e.printStackTrace();
            return null;
        }
    }

    private CustomerList convertCustomerListResultSet(ResultSet customerListResult) {
        try {
            LinkedHashSet<Customer> customers = new LinkedHashSet<>();
            while (customerListResult.next())
                customers.add(convertCustomerResultSet(customerListResult));
            customerListResult.close();
            return new CustomerList(customers);
        } catch (SQLException e) {
            System.err.println("System: error converting ResultSet to CustomerList");
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
            System.err.println("System: error converting ResultSet to Customer object");
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            jdbc_connection.close();
        } catch (SQLException e) {
            System.err.println("System: error when closing connection to " + CONNECTION_INFO);
        }
    }

    public static void main(String[] args) {
        DatabaseController dbcontroller = new DatabaseController();
        CustomerList customerList = dbcontroller.getCustomerList("lastName", "Smith");
        System.out.println(customerList.getCustomerStringList());

        dbcontroller = new DatabaseController();
        LinkedHashSet<Item> items = dbcontroller.getItems("toolId", "all");
        for (Item item: items) {
            System.out.println(item);
        }
        dbcontroller.close();
    }
}
