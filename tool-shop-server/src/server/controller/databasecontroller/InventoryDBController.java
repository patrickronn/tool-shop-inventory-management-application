package server.controller.databasecontroller;

import java.sql.*;

/**
 * Develops PreparedStatements to retrieve information from and make updates to the database
 */
public class InventoryDBController implements DBConstants{
    /**
     * Connection to mySQL db
     */
    private Connection jdbc_connection;

    /**
     * Used to communicate with db
     */
    private PreparedStatement statement;

    public InventoryDBController(Connection jdbc_connection) {
        this.jdbc_connection = jdbc_connection;
        this.statement = null;
    }

    /**
     * @param searchParam parameter type
     * @param searchValue parameter value
     * @return a ResultSet from querying a list of items
     */
    public ResultSet getItemListResultSet(String searchParam, String searchValue) {
        // Query for all tool items info including power type for Electrical items
        if (searchParam.equals("toolId") && searchValue.equals("all")) {
            String query = "SELECT " + TOOL_TABLE_NAME + ".*, " + ELECTRICAL_TOOL_TABLE_NAME + ".PowerType FROM " + TOOL_TABLE_NAME +
                    " LEFT JOIN " + ELECTRICAL_TOOL_TABLE_NAME + " ON " + TOOL_TABLE_NAME + ".ToolId = " + ELECTRICAL_TOOL_TABLE_NAME + ".ToolId";
            try {
                // Prepare statement and return
                if(statement != null) statement.close();
                statement = jdbc_connection.prepareStatement(query);
                return statement.executeQuery();
            }
            catch (SQLException e) {
                System.err.println("System: error when retrieving item list:\n" + e.getMessage());
                return null;
            }
        } else return null;
    }

    /**
     * @param searchParam parameter type
     * @param searchValue parameter value
     * @return a ResultSet from querying an item
     */
    public ResultSet getItemResultSet(String searchParam, String searchValue) {
        if (searchParam.equals("toolId")) {
            // Query for all tool info for a specific item
            String query = "SELECT " + TOOL_TABLE_NAME + ".*, " + ELECTRICAL_TOOL_TABLE_NAME +
                    ".PowerType FROM " + TOOL_TABLE_NAME + " LEFT JOIN " + ELECTRICAL_TOOL_TABLE_NAME +
                    " ON " + TOOL_TABLE_NAME + ".ToolId = " + ELECTRICAL_TOOL_TABLE_NAME + ".ToolId" +
                    " WHERE " + TOOL_TABLE_NAME + ".ToolId = ?";
            try {
                // Prepare statement and return
                if(statement != null) statement.close();
                statement = jdbc_connection.prepareStatement(query);
                statement.setInt(1, Integer.parseInt(searchValue));
                return statement.executeQuery();
            }
            catch (SQLException e) {
                System.err.println("System: error when retrieving item:\n" + e.getMessage());
                return null;
            }
        } else return null;
    }

    /**
     * @param searchParam parameter type
     * @param searchValue parameter value
     * @param quantityToRemove quantity to remove as a String
     * @return true if decrease was successful
     */
    public boolean decreaseItemQuantity(String searchParam, String searchValue, String quantityToRemove) {
        if (searchParam.equals("toolId")) {
            String updateString = "UPDATE " + TOOL_TABLE_NAME +
                    " SET Quantity = Quantity - ? WHERE ToolId = ? AND Quantity > 0";
            try {
                // Attempt to decrease and return
                statement = jdbc_connection.prepareStatement(updateString);
                statement.setInt(1, Integer.parseInt(quantityToRemove));
                statement.setInt(2, Integer.parseInt(searchValue));
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.err.println("System: error when updating tool quantity:\n" + e.getMessage());
                return false;
            } finally {
                try {
                    if (statement != null) statement.close();
                } catch (SQLException e) {
                    System.err.println("System: error closing PreparedStatement object.");
                }
            }
        } else return false;
    }

    /**
     * @param searchParam parameter type
     * @param searchValue parameter value
     * @return ResultSet containing a list of customer info
     */
    public ResultSet getSupplierListResultSet(String searchParam, String searchValue) {
        // Query for all supplier info including import tax for International Suppliers
        if (searchParam.equals("supplierId") && searchValue.equals("all")) {
            String query = "SELECT " + SUPPLIER_TABLE_NAME + ".*, " + INTL_SUPPLIER_TABLE_NAME + ".ImportTax FROM " + SUPPLIER_TABLE_NAME +
                    " LEFT JOIN " + INTL_SUPPLIER_TABLE_NAME + " ON " + SUPPLIER_TABLE_NAME + ".SupplierId = " + INTL_SUPPLIER_TABLE_NAME + ".SupplierId";
            try {
                // Prepare statement and return
                statement = jdbc_connection.prepareStatement(query);
                return statement.executeQuery();
            }
            catch (SQLException e) {
                System.err.println("System: error when retrieving supplier list:\n" + e.getMessage());
                return null;
            }
        } else return null;
    }
}
