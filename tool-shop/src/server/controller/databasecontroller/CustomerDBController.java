package server.controller.databasecontroller;

import java.sql.*;
import java.util.Map;

public class CustomerDBController implements DBConstants {
    private Connection jdbc_connection;
    private PreparedStatement statement;

    public CustomerDBController() {
        try {
            // If this throws an error, make sure you have added the mySQL connector JAR to the project
            Class.forName("com.mysql.jdbc.Driver");

            // If this fails make sure your connectionInfo and login/password are correct
            jdbc_connection = DriverManager.getConnection(CONNECTION_INFO, USER, PASSWORD);
            System.out.println("System: connected to " + CONNECTION_INFO);
        } catch(SQLException e) {
            System.err.println("System: error connecting to " + CONNECTION_INFO);
            e.printStackTrace();
        }
		catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getCustomerListResultSet(String searchParam, String searchValue) {
        String columnName = convertParamToColName(searchParam);

        String query = "SELECT * FROM " + CUSTOMER_TABLE_NAME + " WHERE " + columnName + " LIKE ?";

        if (columnName.equals("LName"))
            searchValue = "%"+searchValue +"%";

        try {
            statement = jdbc_connection.prepareStatement(query);
            setStatementValue(statement, 1, columnName, searchValue);
            return statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("System: error when retrieving customer list:\n"+e.getMessage());
            return null;
        }
    }

    public int insertCustomer(Map<String, String> customerInfoMap) {
        String insertString = "INSERT INTO " + CUSTOMER_TABLE_NAME +
                "(LName, FName, Type, PhoneNum, Address, PostalCode) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            statement = jdbc_connection.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            setStatementValuesFromMap(statement, customerInfoMap);
            statement.executeUpdate();

            // Return the auto-assigned Id
            ResultSet generatedKeys = statement.getGeneratedKeys();
            statement.close();

            if (generatedKeys.next()) {return generatedKeys.getInt(1);}
            else {return -1;}
        } catch (SQLException e) {
            System.err.println("System: error when inserting new customer.");
            e.printStackTrace();
            return -1;
        }
    }

    public boolean updateCustomer(Map<String, String> customerInfoMap) {
        String updateString = "UPDATE " + CUSTOMER_TABLE_NAME +
                " SET LName = ?, FName = ?, Type = ?, PhoneNum = ?, Address = ?, PostalCode = ?" +
                " WHERE CustomerId = ?";

        try {
            statement = jdbc_connection.prepareStatement(updateString);
            setStatementValuesFromMap(statement, customerInfoMap);
            statement.setString(7, customerInfoMap.get("customerId"));
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.err.println("System: error when deleting customer.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCustomer(Map<String, String> customerInfoMap) {
        String deleteString = "DELETE FROM " + CUSTOMER_TABLE_NAME + " WHERE CustomerId = ?";

        try {
            statement = jdbc_connection.prepareStatement(deleteString);
            setStatementValue(statement, 1, "CustomerId", customerInfoMap.get("customerId"));
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.err.println("System: error when deleting customer.");
            e.printStackTrace();
            return false;
        }
    }

    private String convertParamToColName(String searchParam) {
        switch (searchParam) {
            case "customerId":
                return "CustomerId";
            case "lastName":
                return "LName";
            case "customerType":
                return "Type";
            default:
                throw new IllegalArgumentException("Search parameter cannot be identified");
        }
    }

    private void setStatementValue(PreparedStatement statement, int index, String columnName, String value) throws SQLException {
        switch (columnName) {
            case "CustomerId":
                statement.setInt(index, Integer.parseInt(value));
                break;
            case "LName":
            case "FName":
            case "Type":
            case "PhoneNum":
            case "Address":
            case "PostalCode":
                statement.setString(index, value);
                break;
            default:
                throw new IllegalArgumentException(columnName + "isn't a valid column name");
        }
    }

    private void setStatementValuesFromMap(PreparedStatement statement, Map<String, String> customerInfoMap) throws SQLException {
        setStatementValue(statement, 1, "LName", customerInfoMap.get("lastName"));
        setStatementValue(statement, 2, "FName", customerInfoMap.get("firstName"));
        setStatementValue(statement, 3, "Type", customerInfoMap.get("customerType"));
        setStatementValue(statement, 4, "PhoneNum", customerInfoMap.get("phoneNum"));
        setStatementValue(statement, 5, "Address", customerInfoMap.get("address"));
        setStatementValue(statement, 6, "PostalCode", customerInfoMap.get("postalCode"));
    }

    public void close() {
        try {
            jdbc_connection.close();
        } catch (SQLException e) {
            System.err.println("System: error when closing connection to " + CONNECTION_INFO);
        }
    }
}
