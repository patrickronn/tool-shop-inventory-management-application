package server.controller.databasecontroller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDBController {
    private String databaseName;
    private String customerTableName;

    private String connectionInfo;
    private Connection jdbc_connection;

    public CustomerDBController(String databaseName, String customerTableName) {
        this.databaseName = databaseName;
        this.customerTableName = customerTableName;
        this.connectionInfo = "jdbc:mysql://localhost:3307/" + databaseName;

        try {
            // If this throws an error, make sure you have added the mySQL connector JAR to the project
            Class.forName("com.mysql.jdbc.Driver");

            // If this fails make sure your connectionInfo and login/password are correct
            jdbc_connection = DriverManager.getConnection(connectionInfo, "root", "root");
            System.out.println("System: connected to " + connectionInfo);
        } catch(SQLException e) {
            System.err.println("System: error connecting to " + connectionInfo);
            e.printStackTrace();
        }
		catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void retrieveCustomerList(String searchParam, String searchValue) {
        String searchField;
        if (searchParam.equals("customerId")) searchField = "CustomerId";
        else if (searchParam.equals("lastName")) searchField = "LName";
        else if (searchParam.equals("customerType")) searchField = "Type";
        else
            throw new IllegalArgumentException("Search parameter cannot be identified");

        String query = "SELECT * FROM " + databaseName + "WHERE " + searchField + "= ?";

        try {
            PreparedStatement statement = jdbc_connection.prepareStatement(query);
        } catch (SQLException e) {
            System.err.println("System: error when retrieving customer list, "+e.getMessage());
        }

    }

    public void close() {
        try {
            jdbc_connection.close();
        } catch (SQLException e) {
            System.err.println("System: error when closing connection to " + connectionInfo);
        }
    }

    public static void main(String[] args) {
        CustomerDBController dbcontroller = new CustomerDBController("project_ensf_607_608", "CUSTOMER");
    }
}
