package server.controller.databasecontroller;

import java.sql.*;

public class InventoryDBController implements DBConstants{
    private Connection jdbc_connection;
    private PreparedStatement statement;

    public InventoryDBController(Connection jdbc_connection) {
        this.jdbc_connection = jdbc_connection;
        this.statement = null;
    }

    public ResultSet getItemListResultSet(String searchParam, String searchValue) {
        // Query for all tool items info including power type for Electrical items
        if (searchParam.equals("toolId") && searchValue.equals("all")) {
            String query = "SELECT " + TOOL_TABLE_NAME + ".*, " + ELECTRICAL_TOOL_TABLE_NAME + ".PowerType FROM " + TOOL_TABLE_NAME +
                    " LEFT JOIN " + ELECTRICAL_TOOL_TABLE_NAME + " ON " + TOOL_TABLE_NAME + ".ToolId = " + ELECTRICAL_TOOL_TABLE_NAME + ".ToolId";
            try {
                // Prepare statement and return
                statement = jdbc_connection.prepareStatement(query);
                return statement.executeQuery();
            }
            catch (SQLException e) {
                System.err.println("System: error when retrieving customer list:\n" + e.getMessage());
                return null;
            }
        } else return null;
    }

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
                System.err.println("System: error when retrieving customer list:\n" + e.getMessage());
                return null;
            }
        } else return null;
    }
}
