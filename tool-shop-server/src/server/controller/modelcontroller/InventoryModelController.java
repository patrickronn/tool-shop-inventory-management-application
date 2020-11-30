package server.controller.modelcontroller;

import messagemodel.*;
import server.controller.databasecontroller.DatabaseController;
import server.model.*;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class InventoryModelController {
    private Serializer serializer;
    private Deserializer deserializer;
    private DatabaseController databaseController;
    private SupplierList supplierList;

    public InventoryModelController(Serializer s, Deserializer ds, DatabaseController dbc) {
        serializer = s;
        deserializer = ds;
        databaseController = dbc;
        loadSupplierList();
    }

    private void loadSupplierList() {
        this.supplierList = databaseController.getSupplierList("supplierId", "all");
    }

    @SuppressWarnings("unchecked")
    public void interpretInventoryMessage(Message message) {
        switch (message.getAction()) {
            case "search":
                queryItemList((HashMap<String, String>) message.getObject());
                break;
            case "decrease":
                decreaseItemQuantity((HashMap<String, String>) message.getObject());
                break;
            default:
                serializer.sendServerResponse("failed");
        }
    }

    private void queryItemList(Map<String, String> searchParamMap) {
        String searchParamType = searchParamMap.get("paramType");
        String searchParamValue = searchParamMap.get("paramValue");

        LinkedHashSet<Item> itemsList;
        if (searchParamValue.toLowerCase().equals("all")) {
            itemsList = databaseController.getItemList(searchParamType, searchParamValue);
            // Send to client
            if (itemsList != null) {
                serializer.sendServerResponse("success");
                serializer.sendMessage(new Message("update", "itemlist", itemsList));
            } else serializer.sendServerResponse("failed");
        } else serializer.sendServerResponse("failed");
    }

    private void decreaseItemQuantity(Map<String, String> searchParamMap) {
        String searchParamType = searchParamMap.get("paramType");
        String searchParamValue = searchParamMap.get("paramValue");
        String searchParamRemoveQuantity = searchParamMap.get("paramQuantityToRemove");
        boolean decreaseSucceeded = databaseController.decreaseItemQuantity(
                searchParamType, searchParamValue, searchParamRemoveQuantity);
        if (decreaseSucceeded)
            serializer.sendServerResponse("success");
        else { serializer.sendServerResponse("failed"); }
    }

    public void interpretOrderMessage(Message message) {
        switch (message.getAction()) {
            case "insert":
                insertOrder((Order) message.getObject());
                break;
            default:
                serializer.sendServerResponse("failed");
        }
    }

    private void insertOrder(Order order) {
        // Check order validity
        if (!checkOrderIsValid(order)) {
            System.err.println("Server: received an order with invalid order details");
            serializer.sendServerResponse("failed");
            return;
        }

        boolean orderInserted = databaseController.insertOrder(order);
        // If successfully save, return the confirmed order object in case anything was updated by server
        if (orderInserted) {
            serializer.sendServerResponse("success");
            Message message = new Message("update", "order", order);
            serializer.sendMessage(message);
        }
        else
            serializer.sendServerResponse("failed");
    }

    /**
     * Used to check that all order lines in the order are valid.
     *
     * For example, ensure that each order line's item has a proper supplier id before
     * it can added to the database.
     *
     * @param order an Order to check validity
     * @return true if all order lines are valid, else false if at least one order line is invalid
     */
    public boolean checkOrderIsValid(Order order) {
        for (OrderLine orderLine: order.getOrderLines()) {
            int supplierId = orderLine.getItemToOrder().getSupplierId();
            if (!checkSupplierIdExists(supplierId)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSupplierIdExists(int supplierId) {
        if (supplierList.searchSupplier(supplierId) != null)
            return true;
        else
            return false;
    }
}
