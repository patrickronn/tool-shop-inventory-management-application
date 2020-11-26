package client.controller.modelcontroller;

import messagemodel.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Map;

public class InventoryModelController {

    private Serializer serializer;
    private Deserializer deserializer;
    private Inventory inventory;

    public InventoryModelController(Serializer serializer, Deserializer deserializer, Inventory inventory) {
        this.serializer = serializer;
        this.deserializer = deserializer;
        this.inventory = inventory;
    }

    public Map<String, String> getItemInfo(Map<String, String> inventorySearchParamMap) {
       return inventory.getItemStringMap(inventorySearchParamMap);
    }

    @SuppressWarnings("unchecked")
    public boolean requestItemList(Map<String, String> itemsSearchParamMap) {
        // Send item list search parameters
        Message message = new Message("search", "itemparameters", (HashMap<String, String>) itemsSearchParamMap);
        serializer.sendMessage(message);

        // Await response and store inventory list on client side
        String response = deserializer.awaitResponseMessage();
        if (response.equals("success")) {
            Message responseMessage = deserializer.readMessage();
            this.inventory.setItems((LinkedHashSet<Item>) responseMessage.getObject());
            return true;
        }
        else return false;
    }

    public boolean decreaseQuantity(Map<String, String> itemDecreaseParamMap) {

        int itemId = Integer.parseInt(itemDecreaseParamMap.get("paramValue"));
        int quantityToRemove = Integer.parseInt(itemDecreaseParamMap.get("paramQuantityToRemove"));

        // Check if quantity to remove is valid
        if (inventory.isQuantityToRemoveValid(itemId, quantityToRemove)) {
            // Send item decrease parameters
            Message message = new Message("decrease", "itemparameters", (HashMap<String, String>) itemDecreaseParamMap);
            serializer.sendMessage(message);

            // Await response and apply quantity decrease on client side
            String response = deserializer.awaitResponseMessage();
            if (response.equals("success")) {
                Item item = inventory.searchItem(itemId);
                inventory.manageItem(item, quantityToRemove);
                return true;
            }
        }
        // If decrease was unsuccessful, send false
        return false;
    }

    public int getItemQuantity(int itemId) {
        return inventory.getItemQuantity(itemId);
    }

    public String getCurrentOrder() {
        return inventory.getOrder().toString();
    }

    public ArrayList<String> getInventoryStringList() {
        return inventory.getInventoryStringList();
    }
}
