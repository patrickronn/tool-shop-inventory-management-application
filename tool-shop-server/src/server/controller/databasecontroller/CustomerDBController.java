package server.controller.databasecontroller;

import java.sql.*;
import java.util.Map;

/**
 * Develops PreparedStatements to retrieve information from and make updates to the database
 */
public class CustomerDBController implements DBConstants {
    /**
     * Connection to mySQL db
     */
    private Connection jdbc_connection;

    /**
     * Used to communicate with db
     */
    private PreparedStatement statement;

    public CustomerDBController(Connection jdbc_connection) {
        this.jdbc_connection = jdbc_connection;
        this.statement = null;
    }

    /**
     * @param searchParam parameter type
     * @param searchValue parameter value
     * @return a ResultSet containing a list of customer info
     */
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

    /**
     * @param customerInfoMap a Map of customer information attributes
     * @return the automatically generated id assigned by the database
     */
    public int insertCustomer(Map<String, String> customerInfoMap) {
        String insertString = "INSERT INTO " + CUSTOMER_TABLE_NAME +
                "(LName, FName, Type, PhoneNum, Address, PostalCode) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            statement = jdbc_connection.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            setStatementValuesFromMap(statement, customerInfoMap);
            statement.executeUpdate();

            // Return the auto-assigned Id
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {return generatedKeys.getInt(1);}
            else {return -1;}
        } catch (SQLException e) {
            System.err.println("System: error when inserting new customer.");
            e.printStackTrace();
            return -1;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("System: error closing PreparedStatement object.");
            }
        }
    }

    /**
     * @param customerInfoMap a Map of customer information attributes
     * @return true if customer was updated successfully
     */
    public boolean updateCustomer(Map<String, String> customerInfoMap) {
        String updateString = "UPDATE " + CUSTOMER_TABLE_NAME +
                " SET LName = ?, FName = ?, Type = ?, PhoneNum = ?, Address = ?, PostalCode = ?" +
                " WHERE CustomerId = ?";

        try {
            statement = jdbc_connection.prepareStatement(updateString);
            setStatementValuesFromMap(statement, customerInfoMap);
            statement.setString(7, customerInfoMap.get("customerId"));
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("System: error when deleting customer.");
            e.printStackTrace();
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("System: error closing PreparedStatement object.");
            }
        }
    }

    /**
     * @param customerInfoMap a Map of customer information attributes
     * @return true if customer was deleted successfully
     */
    public boolean deleteCustomer(Map<String, String> customerInfoMap) {
        String deleteString = "DELETE FROM " + CUSTOMER_TABLE_NAME + " WHERE CustomerId = ?";

        try {
            statement = jdbc_connection.prepareStatement(deleteString);
            setStatementValue(statement, 1, "CustomerId", customerInfoMap.get("customerId"));
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("System: error when deleting customer.");
            e.printStackTrace();
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("System: error closing PreparedStatement object.");
            }
        }
    }

    /**
     * Helper method to translate search parameters into SQL table column names
     * @param searchParam search parameter to convert
     * @return an existing SQL table column name
     */
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

    /**
     * Adds values to a prepared statement based on customer info map
     * @param statement prepared statement to add values to
     * @param customerInfoMap a Map of customer info to add to the statement
     * @throws SQLException throws exception if there's errors adding values to the statement
     */
    private void setStatementValuesFromMap(PreparedStatement statement, Map<String, String> customerInfoMap) throws SQLException {
        setStatementValue(statement, 1, "LName", customerInfoMap.get("lastName"));
        setStatementValue(statement, 2, "FName", customerInfoMap.get("firstName"));
        setStatementValue(statement, 3, "Type", customerInfoMap.get("customerType"));
        setStatementValue(statement, 4, "PhoneNum", customerInfoMap.get("phoneNum"));
        setStatementValue(statement, 5, "Address", customerInfoMap.get("address"));
        setStatementValue(statement, 6, "PostalCode", customerInfoMap.get("postalCode"));
    }

    /**
     * @param statement prepared statement to add value to
     * @param index the index of the wildcard in the prepared statement (one-indexed)
     * @param columnName used to recognize the type of value required
     * @param value the value to add
     * @throws SQLException errors adding value to the statement
     */
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
}
