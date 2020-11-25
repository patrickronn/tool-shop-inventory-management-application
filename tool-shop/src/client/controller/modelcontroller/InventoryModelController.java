package client.controller.modelcontroller;

import messagemodel.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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

    public boolean requestInventory(Map<String, String> inventorySearchParamMap) {
        /*
        // Send inventory search parameters
        Message message = new Message("search", "inventory", (HashMap<String, String>)inventorySearchParamMap);
        serializer.sendMessage(message);

        // Await response and store results
        String response = deserializer.awaitResponseMessage();
        if (response.equals("success")) {
            Message responseMessage = deserializer.readMessage();
            this.inventory = (Inventory) responseMessage.getObject();
            return true;
        }
        else return false;
        */

        // For testing
        System.out.println("Request inventory from server.");
        ElectricalItem item1 = new ElectricalItem(1, "Phone Tool", 10, 43.95, "100 W");
        NonElectricalItem item2 = new NonElectricalItem(2, "Dowel", 200, 10.20);
        LinkedHashSet<Item> items = new LinkedHashSet<>();
        items.add(item1);
        items.add(item2);
        this.inventory = new Inventory(items, new Order());
        return true;
    }

    public ArrayList<String> getInventoryStringList() {
        return inventory.getInventoryStringList();
    }
}
