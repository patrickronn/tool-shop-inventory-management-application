package server.controller.databasecontroller;

/**
 * Contains the constant values used by many database controllers.
 */
public interface DBConstants {
    String DATABASE_NAME = "project_ensf_607_608";

    String TOOL_TABLE_NAME = "TOOL";
    String ELECTRICAL_TOOL_TABLE_NAME = "ELECTRICAL";
    String SUPPLIER_TABLE_NAME = "SUPPLIER";
    String INTL_SUPPLIER_TABLE_NAME = "INTERNATIONAL";
    String ORDER_TABLE_NAME = "TOOLSORDER";
    String ORDERLINE_TABLE_NAME = "ORDERLINE";
    String CUSTOMER_TABLE_NAME = "CUSTOMER";
    String CONNECTION_INFO = "jdbc:mysql://localhost:3307/" + DATABASE_NAME;
    String USER = "root";
    String PASSWORD = "root";
}
