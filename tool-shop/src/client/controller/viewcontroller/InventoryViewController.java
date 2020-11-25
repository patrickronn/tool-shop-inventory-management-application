package client.controller.viewcontroller;

import client.controller.modelcontroller.Deserializer;
import client.controller.modelcontroller.InventoryModelController;
import client.controller.modelcontroller.Serializer;
import client.view.InventoryManagementGUI;
import client.view.OrderForm;
import messagemodel.Inventory;
import messagemodel.Item;
import messagemodel.Order;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
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
        inventoryManagementGUI.addShowAllToolsListener(new SearchButtonListener("ToolId", true));
        inventoryManagementGUI.addSearchByIdListener(new SearchButtonListener("ToolId", false));
        inventoryManagementGUI.addSearchByNameListener(new SearchButtonListener("Name", false));
        inventoryManagementGUI.addInventoryResultsListener(new SearchResultSelectionListener());
        inventoryManagementGUI.addViewOrderListener(new ViewOrderButtonListener());
        inventoryManagementGUI.addDecreaseQuantityListener(new DecreaseQuantityButtonListener());
    }

    class SearchButtonListener implements ActionListener {
        private String paramType;
        private String paramValue;
        private boolean searchAll;

        public SearchButtonListener(String paramType, boolean searchAll) {
            this.paramType = paramType;
            this.searchAll = searchAll;
            if (searchAll) this.paramValue = "all";
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get user input for search parameter value if needed
            if (!searchAll) {getParamValueInput();}

            // Send search parameters to inventory
            Map<String, String> inventorySearchParamMap = new HashMap<>();
            inventorySearchParamMap.put("paramType", paramType);
            inventorySearchParamMap.put("paramValue", paramValue);

            // Update the GUI search results if successful
            boolean requestSucceeded = inventoryModelController.requestInventory(inventorySearchParamMap);
            if (requestSucceeded) {
                ArrayList<String> inventoryStringList = inventoryModelController.getInventoryStringList();
                inventoryManagementGUI.clearSearchResults();
                for (String itemString: inventoryStringList)
                    inventoryManagementGUI.addToolSearchResult(itemString);
                inventoryManagementGUI.displayMessage("Inventory tool list updated.");
            }
            else {inventoryManagementGUI.displayMessage("Inventory tool list couldn't be retrieved.");}
        }

        private void getParamValueInput() {
            String inputString;
            do {
                inputString = inventoryManagementGUI.promptForInput("Please enter a " + paramType + " to search:");
            } while (inputString == null || (paramType.equals("ToolId") && isNotInteger(inputString)));
            // Assign parameter value as the integer input string
            this.paramValue = inputString;
        }

        private boolean isNotInteger(String str) {
            try {
                Integer.parseInt(str);
                return false;
            } catch (NumberFormatException e) {
                return true;
            }
        }
    }

    class SearchResultSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                String searchResultSelected = inventoryManagementGUI.getSearchResultSelected();
                if (searchResultSelected.equals("null")) return;;

                int itemId = extractToolId(searchResultSelected);
                int itemQuantity;
                if (itemId > 0) {
                    itemQuantity = inventoryModelController.getItemQuantity(itemId);
                    if (itemQuantity > 0)
                        inventoryManagementGUI.setQuantityValue(String.valueOf(itemQuantity));
                    else
                        inventoryManagementGUI.displayMessage("Cannot find item with item id " + itemId + ".");
                }
            }
        }

        private int extractToolId(String searchResultSelected) {
            try {
                String[] stringArr = searchResultSelected.split(",");
                return Integer.parseInt(stringArr[0]);
            } catch (NumberFormatException e) {
                inventoryManagementGUI.displayMessage("Cannot extract tool id in: " + searchResultSelected);
                return -1;
            }
        }
    }

    class ViewOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String orderString = inventoryModelController.getCurrentOrder();
            OrderForm dialog = new OrderForm(orderString);
            dialog.pack();
            dialog.setVisible(true);
        }
    }

    class DecreaseQuantityButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (inventoryManagementGUI.getSearchResultSelected().equals("null")) {
                inventoryManagementGUI.displayMessage("Please select an item first.");
                return;
            }

            // Create item decrease parameters and attempt to decrease
            Map<String, String> itemDecreaseParamMap = createParamMap();
            boolean decreaseSucceeded = inventoryModelController.decreaseQuantity(itemDecreaseParamMap);

            // Update the GUI if decrease was successful
            if (decreaseSucceeded) {
                int itemId = Integer.parseInt(itemDecreaseParamMap.get("paramValue"));
                int updatedQuantity = inventoryModelController.getItemQuantity(itemId);
                inventoryManagementGUI.displayMessage("Quantity successfully decreased. (Updated Quantity = " +
                        updatedQuantity + ")");
                inventoryManagementGUI.setQuantityValue(String.valueOf(updatedQuantity));
            } else {
                inventoryManagementGUI.displayMessage("Quantity failed to decrease.");
            }
        }

        private Map<String, String> createParamMap() {
            String searchResultSelected = inventoryManagementGUI.getSearchResultSelected();
            String toolIdSelected = String.valueOf(extractToolId(searchResultSelected));
            String quantityToRemove = getQuantityToRemoveInput();
            Map<String, String> itemDecreaseParamMap = new HashMap<>();
            itemDecreaseParamMap.put("paramType", "ToolId");
            itemDecreaseParamMap.put("paramValue", toolIdSelected);
            itemDecreaseParamMap.put("paramQuantityToRemove", quantityToRemove);
            return itemDecreaseParamMap;
        }

        private int extractToolId(String searchResultSelected) {
            try {
                String[] stringArr = searchResultSelected.split(",");
                return Integer.parseInt(stringArr[0]);
            } catch (NumberFormatException e) {
                inventoryManagementGUI.displayMessage("Cannot extract tool id in: " + searchResultSelected);
                return -1;
            }
        }

        private String getQuantityToRemoveInput() {
            String inputString;
            do {
                inputString = inventoryManagementGUI.promptForInput(
                        "Please enter amount to remove (Current Quantity = " +
                                inventoryManagementGUI.getQuantityValue() + "):");
            } while (inputString == null || (isNotInteger(inputString)));
            return inputString;
        }

        private boolean isNotInteger(String str) {
            try {
                Integer.parseInt(str);
                return false;
            } catch (NumberFormatException e) {
                return true;
            }
        }
    }

    public static void main(String[] args) {
        Inventory inv = new Inventory(new LinkedHashSet<Item>(), new Order());
        InventoryModelController imc = new InventoryModelController(new Serializer(), new Deserializer(), inv);
        InventoryViewController ivc = new InventoryViewController(new InventoryManagementGUI(),imc);
    }
}
