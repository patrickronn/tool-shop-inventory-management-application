package client.controller.viewcontroller;

import client.controller.modelcontroller.Deserializer;
import client.controller.modelcontroller.InventoryModelController;
import client.controller.modelcontroller.Serializer;
import client.view.InventoryManagementGUI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventoryViewController {
    private InventoryManagementGUI inventoryManagementGUI;
    private InventoryModelController inventoryModelController;

    public InventoryViewController(InventoryManagementGUI gui, InventoryModelController inventoryModelController) {
        this.inventoryManagementGUI = gui;
        this.inventoryModelController = inventoryModelController;

        addActionListeners();
    }

    public void addActionListeners() {
        addShowAllToolsListener();
    }

    private void addShowAllToolsListener() {
        inventoryManagementGUI.addShowAllToolsListener(e -> {
            Map<String, String> inventorySearchParamMap = new HashMap<>();
            inventorySearchParamMap.put("paramType", "ToolId");
            inventorySearchParamMap.put("paramValue", "*");
            boolean requestSucceeded = inventoryModelController.requestInventory(inventorySearchParamMap);
            if (requestSucceeded) {
                ArrayList<String> inventoryStringList = inventoryModelController.getInventoryStringList();
                for (String itemString: inventoryStringList)
                    inventoryManagementGUI.addToolSearchResult(itemString);
                inventoryManagementGUI.displayMessage("Inventory tool list updated.");
            }
            else {inventoryManagementGUI.displayMessage("Inventory tool list couldn't be retrieved.");};

        });
    }

    public static void main(String[] args) {
        InventoryModelController imc = new InventoryModelController(new Serializer(), new Deserializer(), null);
        InventoryViewController ivc = new InventoryViewController(new InventoryManagementGUI(),imc);
    }
}
