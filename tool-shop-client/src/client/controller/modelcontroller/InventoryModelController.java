package client.controller.modelcontroller;

import messagemodel.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the client-side model related to inventory management.
 * Uses messagemodel package for serializing and deserializing objects from the server
 */
public class InventoryModelController {

    /**
     * Serializes objects and sending them to server.
     */
    private Serializer serializer;

    /**
     * Deserializes objects received from the server
     */
    private Deserializer deserializer;

    /**
     * Stores a copy of the inventory retrieved from the server.
     */
    private Inventory inventory;

    public InventoryModelController(Serializer serializer, Deserializer deserializer, Inventory inventory) {
        this.serializer = serializer;
        this.deserializer = deserializer;
        this.inventory = inventory;
    }

    public Map<String, String> getItemInfo(Map<String, String> inventorySearchParamMap) {
       return inventory.getItemStringMap(inventorySearchParamMap);
    }

    /**
     * Reequests for the list of items based on a set of item search parameters.
     * @param itemsSearchParamMap Map of search parameters to tell the server what items to include
     * @return true if item list was successfully received; else false
     */
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

    /**
     * Manages decreases an item's quantity by sending a request to server then updates client-side if successful.
     * @param itemDecreaseParamMap Map of item info retrieved from the user/GUI
     * @return true if server decrease the item's quantity; false if item couldn't be updated.
     */
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

    /**
     * Checks whether an order has no order lines
     * @return true order contains no orderlines; otherwise false
     */
    public boolean orderIsEmpty() {
        return inventory.getOrder().orderIsEmpty();
    }

    /**
     * Sends the finalized order to the server
     * @return true if order was stored successfully, otherwise false.
     */
    public boolean saveOrder() {
        Order order = inventory.getOrder();
        if (order != null && !order.orderIsEmpty()) {
            // Send order to server
            Message message = new Message("insert", "order", order);
            serializer.sendMessage(message);

            // Await response and update order (in case order ID was modified by server)
            String response = deserializer.awaitResponseMessage();
            if (response.equals("success")) {
                Message responseMessage = deserializer.readMessage();
                this.inventory.setOrder((Order) responseMessage.getObject());
                return true;
            }
        }
        // If order failed to save, send false
        return false;
    }

    // Getters below
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
