package server.controller.databasecontroller;

public interface DBConstants {
    String DATABASE_NAME = "project_ensf_607_608";
    String CUSTOMER_TABLE_NAME = "CUSTOMER";
    String INVENTORY_TABLE_NAME = "TOOL";
    String ORDER_TABLE_NAME = "ORDER";
    String CONNECTION_INFO = "jdbc:mysql://localhost:3307/" + DATABASE_NAME;
    String USER = "root";
    String PASSWORD = "root";
}
