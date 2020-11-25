package server.controller.databasecontroller;

import java.sql.*;

public class CustomerDBController implements DBConstants {
    private Connection jdbc_connection;

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
        String columnName = convertSearchParamToColumnName(searchParam);

        String query = "SELECT * FROM " + CUSTOMER_TABLE_NAME + " WHERE " + columnName + "= ?";

        try {
            PreparedStatement statement = jdbc_connection.prepareStatement(query);
            setStatementValue(statement, 1, columnName, searchValue);
            return statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("System: error when retrieving customer list:\n"+e.getMessage());
            return null;
        }
    }

    private String convertSearchParamToColumnName(String searchParam) {
        if (searchParam.equals("customerId")) return "CustomerId";
        else if (searchParam.equals("lastName")) return "LName";
        else if (searchParam.equals("customerType")) return "Type";
        else throw new IllegalArgumentException("Search parameter cannot be identified");
    }

    private void setStatementValue(PreparedStatement statement, int index, String columnName, String value) throws SQLException {
            if (columnName.equals("CustomerId"))
                statement.setInt(index, Integer.parseInt(value));
            else if (columnName.equals("LName"))
                statement.setString(index, value);
            else if (columnName.equals("Type"))
                statement.setString(index, value);
    }

    public void close() {
        try {
            jdbc_connection.close();
        } catch (SQLException e) {
            System.err.println("System: error when closing connection to " + CONNECTION_INFO);
        }
    }
}
