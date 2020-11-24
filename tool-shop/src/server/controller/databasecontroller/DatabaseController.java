package server.controller.databasecontroller;

public class DatabaseController {
    private CustomerDBController customerDBController;
    private InventoryDBController inventoryDBController;
    private OrderDBController orderDBController;

    private String databaseName;
    private String customerTableName;
    private String inventoryTableName;
    private String orderTableName;

    public DatabaseController(String database, String custTable, String invTable, String orderTable) {
        databaseName = database;
        customerTableName = custTable;
        inventoryTableName = invTable;
        orderTableName = orderTable;

        customerDBController = new CustomerDBController(databaseName, customerTableName);
        inventoryDBController = new InventoryDBController();
        orderDBController = new OrderDBController();
    }

    public void close() {
        customerDBController.close();
    }
}
