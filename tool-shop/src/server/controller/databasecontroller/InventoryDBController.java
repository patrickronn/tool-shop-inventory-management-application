package server.controller.databasecontroller;

import java.sql.*;

public class InventoryDBController implements DBConstants{
    private Connection jdbc_connection;
    private PreparedStatement statement;

    public InventoryDBController() {
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

    public ResultSet getInventoryToolResultSet(String searchParam, String searchValue) {
        // Query for all tool items info including power type for Electrical items
        if (searchParam.equals("inventory") && searchValue.equals("all")) {
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

    public void close() {
        try {
            jdbc_connection.close();
        } catch (SQLException e) {
            System.err.println("System: error when closing connection to " + CONNECTION_INFO);
        }
    }
}
