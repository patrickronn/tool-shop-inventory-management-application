package server.controller.modelcontroller;

import messagemodel.*;
import server.controller.databasecontroller.DatabaseController;
import server.model.*;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class InventoryModelController {
    private Serializer serializer;
    private Deserializer deserializer;
    private DatabaseController databaseController;
    private ShopManager shopManager;

    public InventoryModelController(Serializer s, Deserializer ds, DatabaseController dbc) {
        serializer = s;
        deserializer = ds;
        databaseController = dbc;
    }

    @SuppressWarnings("unchecked")
    public void interpretInventoryMessage(Message message) {
        switch (message.getAction()) {
            case "search":
                queryInventory((HashMap<String, String>) message.getObject());
                break;
            default:
                serializer.sendServerResponse("failed");
        }
    }

    private void queryInventory(HashMap<String, String> searchParamMap) {
        String searchParamType = searchParamMap.get("paramType");
        String searchParamValue = searchParamMap.get("paramValue");

        LinkedHashSet<Item> itemsList;
        if (searchParamValue.toLowerCase().equals("all")) {
            itemsList = databaseController.getItems(searchParamType, searchParamValue);
            Inventory inventory = new Inventory(itemsList, new Order());
            // Send to client
            if (itemsList != null) {
                serializer.sendServerResponse("success");
                serializer.sendMessage(new Message("update", "inventory", inventory));
            }
        }
        // Send failure if search param value cannot be understood or database wasn't queried
        serializer.sendServerResponse("failed");

    }
}
