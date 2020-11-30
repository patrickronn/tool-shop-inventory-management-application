package server.controller.databasecontroller;

import messagemodel.*;
import server.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * This class manages the communications between server and the mySQL database.
 */
public class DatabaseController implements DBConstants {
    /**
     * Manages customer related tables
     */
    private CustomerDBController customerDBController;

    /**
     * Manages inventory related tables
     */
    private InventoryDBController inventoryDBController;

    /**
     * Manages order related tables
     */
    private OrderDBController orderDBController;

    /**
     * Connects Java program with MySQL server
     */
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

    /**
     * @param searchParam the parameter type (e.g. "toolId")
     * @param searchValue the value of the parameter (e.g. "all" for all Items)
     * @return a LinkedHashSet of Items that match the search parameters
     */
    public LinkedHashSet<Item> getItemList(String searchParam, String searchValue) {
        ResultSet inventoryToolsResult = inventoryDBController.getItemListResultSet(searchParam, searchValue);
        if (inventoryToolsResult != null)
            return convertItemListResultSet(inventoryToolsResult);
        else
            throw new IllegalArgumentException(searchParam + ": " + searchValue + " is not a proper query");
    }

    /**
     * @param searchParam the parameter type (e.g. "toolId")
     * @param searchValue the value of the parameter (e.g. id of an Item)
     * @return an Item that matches the search parameters
     */
    public Item getItem(String searchParam, String searchValue) throws SQLException {
        ResultSet itemResult = inventoryDBController.getItemResultSet(searchParam, searchValue);
        if (itemResult != null && itemResult.next())
            return convertItemResultSet(itemResult);
        else
            throw new IllegalArgumentException(searchParam + ": " + searchValue + " is not a proper query");
    }

    /**
     * @param searchParam the parameter type (e.g. "supplierId")
     * @param searchValue the parameter value (e.g. "all" for all Suppliers)
     * @return a LinkedHashSet of Suppliers that match the parameters
     */
    public SupplierList getSupplierList(String searchParam, String searchValue) {
        ResultSet supplierListResult = inventoryDBController.getSupplierListResultSet(searchParam, searchValue);
        if (supplierListResult != null)
            return convertSupplierListResultSet(supplierListResult);
        else
            throw new IllegalArgumentException(searchParam + ": " + searchValue + " is not a proper query");
    }

    /**
     * @param searchParam the parameter type (e.g. "toolId")
     * @param searchValue the parameter value (e.g. id of a Tool)
     * @param quantityToRemove the amount to decrease the tool's quantity by
     * @return true if quantity was successfully decreased in DB; otherwise false
     */
    public boolean decreaseItemQuantity(String searchParam, String searchValue, String quantityToRemove) {
        boolean decreaseSucceeded = inventoryDBController.decreaseItemQuantity(searchParam, searchValue, quantityToRemove);
        return decreaseSucceeded;
    }

    /**
     * @param searchParam the parameter type (e.g. "customerId", "lastName", or "customerType")
     * @param searchValue the parameter value
     * @return a CustomerList that match the search parameters
     */
    public CustomerList getCustomerList(String searchParam, String searchValue) {
        ResultSet customerListResult = customerDBController.getCustomerListResultSet(searchParam, searchValue);
        if (customerListResult != null)
            return convertCustomerListResultSet(customerListResult);
        else
            throw new IllegalArgumentException(searchParam + ": " + searchValue + " is not a proper query");
    }

    /**
     * @param customer a Customer to add into the db
     * @return the id assigned to the newly inserted customer
     */
    public int insertCustomer(Customer customer) {
        int customerIdAssigned = customerDBController.insertCustomer(customer.toMap());
        return customerIdAssigned;
    }

    /**
     * @param customer the Customer to update
     * @return true if the customer was found and properly updated
     */
    public boolean updateCustomer(Customer customer) {
        return customerDBController.updateCustomer(customer.toMap());
    }

    /**
     * @param customer the Customer to remove
     * @return true if the customer was found and deleted
     */
    public boolean deleteCustomer(Customer customer) {
        Map<String, String> customerInfoMap = customer.toMap();
        return customerDBController.deleteCustomer(customerInfoMap);
    }

    /**
     * Saves order information to the database. Also includes a check to determine that
     * the order ID is unique.
     *
     * @param order the Order to insert
     * @return true if the order was successfully saved to the database
     */
    public boolean insertOrder(Order order) {
        // Ensure order ID doesn't exist
        while(!orderDBController.isOrderIdUnique(order.getId())) {
            order.setRandomizedId();
        }

        // Insert order
        int orderId = order.getId();
        String orderDate = order.getDate();
        boolean orderInserted = orderDBController.insertOrder(orderId, orderDate);

        // Insert order lines
        boolean allOrderLinesInserted = true;
        if (orderInserted) {
            for (OrderLine orderLine: order.getOrderLines()) {
                if (!insertOrderLine(orderLine)) allOrderLinesInserted = false;
            }
            return allOrderLinesInserted;
        }
        else return false;
    }

    /**
     * @param orderLine OrderLine to insert to db
     * @return true if orderline successfully inserted
     */
    private boolean insertOrderLine(OrderLine orderLine) {
        int orderId = orderLine.getOrder().getId();
        int toolId = orderLine.getItemToOrder().getId();
        int supplierId = orderLine.getItemToOrder().getSupplierId();
        int quantity = orderLine.getQuantityToOrder();
        if (orderDBController.insertOrderLine(orderId, toolId, supplierId, quantity)) {
            return true;
        }
        else {
            System.err.println("Server: error inserting an order line: orderId=" +
                    orderId + ", toolId=" + toolId + ", supplierId=" + supplierId + ", quantity=" + quantity);
            return false;
        }
    }

    /**
     * @param searchParam parameter type (e.g. "orderId")
     * @param searchValue parameter value (e.g. the id of an Order object)
     * @return Order that matches the search parameters; else null
     */
    public Order getOrder(String searchParam, String searchValue) {
        ResultSet orderResult = orderDBController.getOrder(searchParam, searchValue);
        Order order = convertOrderResult(orderResult);

        if (order == null) {
            System.err.println("Server: error retrieving order with: " + searchParam + "=" + searchValue);
            return null;
        }

        try {
            addOrderLines(order);
            return order;
        } catch (SQLException e) {
            System.err.println("Server: error adding order lines to queried order\n" + e.getMessage());
            return null;
        }
    }

    /**
     * @param order the Order to include orderlines to
     * @throws SQLException error reading from the ResultSet returned
     */
    private void addOrderLines(Order order) throws SQLException {
        String orderId = String.valueOf(order.getId());
        ResultSet orderLinesResult = orderDBController.getOrderLines("orderId", orderId);

        while (orderLinesResult.next())
            order.addOrderLine(convertOrderLineResult(orderLinesResult));
    }

    /**
     * @param inventoryToolsResult ResultSet from a query for a list of items/tools
     * @return LinkedHashSet of Items
     */
    private LinkedHashSet<Item> convertItemListResultSet(ResultSet inventoryToolsResult) {
        try {
            LinkedHashSet<Item> items = new LinkedHashSet<>();
            while (inventoryToolsResult.next())
                items.add(convertItemResultSet(inventoryToolsResult));
            inventoryToolsResult.close();
            return items;
        } catch (SQLException e) {
            System.err.println("Server: error converting ResultSet to Inventory.");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param itemResult ResultSet to read item/tool's information
     * @return Item object retrieved
     */
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
            System.err.println("Server: error converting ResultSet to Item object");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param supplierListResult ResultSet from a query for a list of suppliers
     * @return SupplierList object
     */
    private SupplierList convertSupplierListResultSet(ResultSet supplierListResult) {
        try {
            LinkedHashSet<Supplier> suppliers = new LinkedHashSet<>();
            while (supplierListResult.next())
                suppliers.add(convertSupplierResultSet(supplierListResult));
            supplierListResult.close();
            return new SupplierList(suppliers);
        } catch (SQLException e) {
            System.err.println("Server: error converting ResultSet to CustomerList");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param supplierResult ResultSet to read supplier information
     * @return Supplier object retrieved
     */
    private Supplier convertSupplierResultSet(ResultSet supplierResult) {
        try {
            int id = supplierResult.getInt("SupplierId");
            String name = supplierResult.getString("Name");
            String type = supplierResult.getString("Type");
            String address = supplierResult.getString("Address");
            String salesContact = supplierResult.getString("CName");
            String phoneNum = supplierResult.getString("Phone");
            if (type.equals("International")) {
                double importTax = supplierResult.getDouble("ImportTax");
                return new IntlSupplier(id, name, address, salesContact, phoneNum, importTax);
            }
            else if (type.equals("Local"))
                return new LocalSupplier(id, name, address, salesContact, phoneNum);
            else
                return null;
        } catch (SQLException e) {
            System.err.println("Server: error converting ResultSet to Customer object");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param customerListResult ResultSet from a query for a list of customers
     * @return CustomerList object
     */
    private CustomerList convertCustomerListResultSet(ResultSet customerListResult) {
        try {
            LinkedHashSet<Customer> customers = new LinkedHashSet<>();
            while (customerListResult.next())
                customers.add(convertCustomerResultSet(customerListResult));
            customerListResult.close();
            return new CustomerList(customers);
        } catch (SQLException e) {
            System.err.println("Server: error converting ResultSet to CustomerList");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param customerResult ResultSet to read customer information
     * @return Customer object retrieved
     */
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
            System.err.println("Server: error converting ResultSet to Customer object");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param orderResult ResultSet containing order info
     * @return Order object retrieved
     */
    private Order convertOrderResult(ResultSet orderResult) {
        if (orderResult == null)
            return null;

        try {
            if (orderResult.next()) {
                int orderId = orderResult.getInt("OrderId");
                String date = orderResult.getString("Date");
                Order order = new Order();
                order.setId(orderId);
                order.setDate(date);
                return order;
            } else return null;
        }
        catch (SQLException e) {
            System.err.println("Server: error converting ResultSet to Order object\n");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param orderLineResult ResultSet containing order line info
     * @return OrderLine object retrieved
     */
    private OrderLine convertOrderLineResult(ResultSet orderLineResult) {
        if (orderLineResult == null)
            return null;

        try {
            String toolId = orderLineResult.getString("ToolId");
            int quantity = orderLineResult.getInt("Quantity");

            Item item = getItem("toolId", toolId);
            if (item != null)
                return new OrderLine(item, quantity);
            else
                return null;
        }
        catch (SQLException e) {
            System.err.println("Server: error converting ResultSet to OrderLine object\n");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Closes connection to db
     */
    public void close() {
        try {
            jdbc_connection.close();
        } catch (SQLException e) {
            System.err.println("Server: error when closing connection to " + CONNECTION_INFO);
        }
    }

    public static void main(String[] args) {
//        DatabaseController dbController = new DatabaseController();
//        CustomerList customerList = dbController.getCustomerList("lastName", "Smith");
//        System.out.println(customerList.getCustomerStringList());
//
//        LinkedHashSet<Item> items = dbController.getItemList("toolId", "all");
//        for (Item item: items) {
//            System.out.println(item);
//        }
//
//        SupplierList supplierList = dbController.getSupplierList("supplierId", "all");
//        System.out.println(supplierList);
//        dbController.close();
//
//        Order order = dbController.getOrder("date", "2020-11-27");
//        System.out.println(order);
    }
}
