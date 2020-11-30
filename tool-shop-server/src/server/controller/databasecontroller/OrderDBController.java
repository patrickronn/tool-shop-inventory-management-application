package server.controller.databasecontroller;

import java.sql.*;

public class OrderDBController implements DBConstants{
    private Connection jdbc_connection;
    private PreparedStatement statement;

    public OrderDBController(Connection jdbc_connection) {
        this.jdbc_connection = jdbc_connection;
        this.statement = null;
    }

    public boolean isOrderIdUnique(int orderId) {
        String query = "SELECT * FROM " + ORDER_TABLE_NAME + " WHERE OrderId = ?";
        try {
            statement = jdbc_connection.prepareStatement(query);
            statement.setInt(1, orderId);

            ResultSet resultSet = statement.executeQuery();
            return !resultSet.isBeforeFirst();

        } catch (SQLException e) {
            System.err.println("Server: error when querying for order ID");
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getOrder(String searchParam, String searchValue) {
        if (searchParam.equals("date")) {
            String query = "SELECT * FROM " + ORDER_TABLE_NAME + " WHERE Date = ?";
            try {
                statement = jdbc_connection.prepareStatement(query);
                statement.setDate(1, Date.valueOf(searchValue));
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.isBeforeFirst())
                    return resultSet;
                else {
                    statement.close();
                    return null;
                }
            } catch (SQLException e) {
                System.err.println("Server: error when retrieving order list:\n" + e.getMessage());
                return null;
            }
        } else return null;
    }

    public ResultSet getOrderLines(String searchParam, String searchValue) {
        if (searchParam.equals("orderId")) {
            String query = "SELECT * FROM " + ORDERLINE_TABLE_NAME + " WHERE OrderId = ?";
            try {
                statement = jdbc_connection.prepareStatement(query);
                statement.setInt(1, Integer.parseInt(searchValue));
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.isBeforeFirst())
                    return resultSet;
                else {
                    statement.close();
                    return null;
                }
            } catch (SQLException e) {
                System.err.println("Server: error when retrieving order lines: \n" + e.getMessage());
                return null;
            }
        } else return null;
    }

    public boolean insertOrder(int orderId, String date) {
        String insertString = "INSERT INTO " + ORDER_TABLE_NAME + "(OrderId, Date) VALUES (?, ?)";

        try {
            statement.close();
            statement = jdbc_connection.prepareStatement(insertString);
            statement.setInt(1, orderId);
            statement.setDate(2, Date.valueOf(date));
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Server: error when inserting new order.");
            e.printStackTrace();
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("Server: error closing PreparedStatement object.");
            }
        }
    }

    public boolean insertOrderLine(int orderId, int toolId, int supplierId, int quantity) {
        String insertString = "INSERT INTO " + ORDERLINE_TABLE_NAME +
                "(OrderId, ToolId, SupplierId, Quantity) VALUES (?, ?, ?, ?)";

        try {
            statement.close();
            statement = jdbc_connection.prepareStatement(insertString);
            statement.setInt(1, orderId);
            statement.setInt(2, toolId);
            statement.setInt(3, supplierId);
            statement.setInt(4, quantity);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Server: error when inserting new order line.");
            e.printStackTrace();
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("Server: error closing PreparedStatement object.");
            }
        }
    }
}
