package client.controller.viewcontroller;

import client.controller.modelcontroller.InventoryModelController;
import client.view.InventoryManagementGUI;
import client.view.ItemInfoDialog;
import client.view.OrderDialog;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages inventory management system's interactions with the user. Reads input from GUI and sends inventory
 * model related requests to the model controller.
 */
public class InventoryViewController {
    /**
     * GUI for inventory management
     */
    private InventoryManagementGUI inventoryManagementGUI;

    /**
     * Controller for managing the inventory model
     */
    private InventoryModelController inventoryModelController;

    public InventoryViewController(InventoryManagementGUI gui, InventoryModelController inventoryModelController) {
        this.inventoryManagementGUI = gui;
        this.inventoryModelController = inventoryModelController;

        addActionListeners();
    }

    /**
     * Adds listeners to GUI components
     */
    public void addActionListeners() {
        inventoryManagementGUI.addLoadAllToolsListener(new LoadAllToolsListener());
        inventoryManagementGUI.addSearchByIdListener(new SearchToolInfoListener("toolId"));
        inventoryManagementGUI.addSearchByNameListener(new SearchToolInfoListener("name"));
        inventoryManagementGUI.addInventoryResultsListener(new SearchResultSelectionListener());
        inventoryManagementGUI.addViewOrderListener(new ViewOrderButtonListener());
        inventoryManagementGUI.addDecreaseQuantityListener(new DecreaseQuantityButtonListener());
        saveOrderWhenClosing();
    }

    /**
     * Adds a window listener to the GUI to manage saving the form once the window is closed.
     */
    private void saveOrderWhenClosing() {
        inventoryManagementGUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (inventoryModelController.orderIsEmpty()) {
                    inventoryManagementGUI.displayMessage("No order was created today.");
                    return;
                }

                // If order was created, save it to server
                boolean saveSucceeded = inventoryModelController.saveOrder();
                if (saveSucceeded) {
                    inventoryManagementGUI.displayMessage("Order successfully saved to server.");
                    String orderString = inventoryModelController.getCurrentOrder();
                    OrderDialog dialog = new OrderDialog(orderString);
                    dialog.pack();
                    dialog.setVisible(true);
                }
                else {
                    inventoryManagementGUI.displayMessage("Order failed to save.");
                }
            }
        });
    }

    /**
     * ActionListener that interprets user input search parameters and sends the request to model controller.
     * Includes validation check to ensure user entered proper search parameters
     */
    class SearchToolInfoListener implements ActionListener {
        private String paramType;
        private String paramValue;

        public SearchToolInfoListener(String paramType) {
            this.paramType = paramType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get user input for search parameter value
            getParamValueInput();

            // Send search parameters to inventory
            Map<String, String> itemSearchParam = new HashMap<>();
            itemSearchParam.put("paramType", paramType);
            itemSearchParam.put("paramValue", paramValue);

            Map<String, String> itemInfoMap = inventoryModelController.getItemInfo(itemSearchParam);
            if (itemInfoMap != null) {
                ItemInfoDialog dialog = new ItemInfoDialog();
                displayItemInfoToDialog(dialog, itemInfoMap);
                dialog.pack();
                dialog.setVisible(true);
            } else inventoryManagementGUI.displayMessage("Cannot find item with " + paramType + ": " + paramValue);
        }

        private void displayItemInfoToDialog(ItemInfoDialog dialog, Map<String, String> itemInfoMap) {
            dialog.setToolId(itemInfoMap.get("toolId"));
            dialog.setToolName(itemInfoMap.get("name"));
            dialog.setQuantity(itemInfoMap.get("quantity"));
            dialog.setPrice("$ " + itemInfoMap.get("price"));
            dialog.setType(itemInfoMap.get("toolType"));
            dialog.setSupplierId(itemInfoMap.get("supplierId"));

            String powerType = itemInfoMap.get("powerType");
            if (powerType != null) dialog.setPowerType(powerType);
            else dialog.setPowerType("N/A");
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

    /**
     * ActionListener for the button that will load all tools into the GUI.
     */
    class LoadAllToolsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // Send search parameters to inventory
            Map<String, String> inventorySearchParamMap = new HashMap<>();
            inventorySearchParamMap.put("paramType", "toolId");
            inventorySearchParamMap.put("paramValue", "all");

            // Update the GUI search results if successful
            boolean requestSucceeded = inventoryModelController.requestItemList(inventorySearchParamMap);
            if (requestSucceeded) {
                ArrayList<String> inventoryStringList = inventoryModelController.getInventoryStringList();
                inventoryManagementGUI.clearSearchResults();
                for (String itemString: inventoryStringList)
                    inventoryManagementGUI.addToolSearchResult(itemString);
                inventoryManagementGUI.displayMessage("Inventory tool list updated.");

                if (!inventoryManagementGUI.isInventoryLoaded())
                    inventoryManagementGUI.setInventoryLoaded(true);
            }
            else {
                inventoryManagementGUI.displayMessage("Inventory tool list couldn't be retrieved.");
                inventoryManagementGUI.setInventoryLoaded(false);
            }
        }
    }

    /**
     * ActionListener that monitors the search result selected by the user. Whenever a user changes
     * their item selection, the current quantity field will update to the newly selected item.
     */
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

    /**
     * ActionListener that will open up the order dialog to display current order info
     */
    class ViewOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String orderString = inventoryModelController.getCurrentOrder();
            OrderDialog dialog = new OrderDialog(orderString);
            dialog.pack();
            dialog.setVisible(true);
        }
    }

    /**
     * ActionListener that will reduce the quantity of an item. It will open up a prompt to the user to
     * input amount of quantity to remove. Sends the decrease request to the model controller.
     */
    class DecreaseQuantityButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (inventoryManagementGUI.getSearchResultSelected().equals("null")) {
                inventoryManagementGUI.displayMessage("Please select an item first.");
                return;
            }

            Map<String, String> itemDecreaseParamMap = createParamMap();
            if (itemDecreaseParamMap.get("paramQuantityToRemove").equals("0")) {
                inventoryManagementGUI.displayMessage("'Decrease Quantity' request was cancelled.");
                return;
            }

            // Create item decrease parameters and request to decrease
            boolean decreaseSucceeded = inventoryModelController.decreaseQuantity(itemDecreaseParamMap);

            // Update the GUI if decrease was successful
            if (decreaseSucceeded) {
                int itemId = Integer.parseInt(itemDecreaseParamMap.get("paramValue"));
                int updatedQuantity = inventoryModelController.getItemQuantity(itemId);
                inventoryManagementGUI.displayMessage("Quantity successfully decreased\n(Updated Quantity = " +
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
            itemDecreaseParamMap.put("paramType", "toolId");
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
                        "Please enter quantity to remove or '0' to cancel:\n(Current Quantity = " +
                                inventoryManagementGUI.getQuantityValue() + ")");
            } while (inputString == null || isNotPositiveInteger(inputString));
            return inputString;
        }

        private boolean isNotPositiveInteger(String str) {
            try {
                int num = Integer.parseInt(str);
                return num < 0;
            } catch (NumberFormatException e) {
                return true;
            }
        }
    }
}
