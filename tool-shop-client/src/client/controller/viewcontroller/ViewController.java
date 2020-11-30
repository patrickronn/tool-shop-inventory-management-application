package client.controller.viewcontroller;

import client.controller.modelcontroller.ModelController;
import client.view.CustomerManagementGUI;
import client.view.InventoryManagementGUI;

import javax.swing.*;

/**
 * Creates the GUIs as well as specialized view controllers based on user selection
 */
public class ViewController {

    private CustomerViewController customerViewController;
    private InventoryViewController inventoryViewController;

    /**
     * Prompts user for the type of menus to open then instantiates GUIs and controllers accordingly
     * @param modelController the model controller which manages model components
     */
    public ViewController(ModelController modelController) {
        String menuSelected = displayMainMenu();

        if (menuSelected == null || (!menuSelected.equalsIgnoreCase("customer")
                && !menuSelected.equalsIgnoreCase("inventory")
                && !menuSelected.equalsIgnoreCase("both"))) {
            System.out.println("Invalid selection - only enter 'customer', 'inventory', or 'both'");
            return;
        }

        if (menuSelected.equalsIgnoreCase("customer") || menuSelected.equalsIgnoreCase("both"))
            customerViewController = new CustomerViewController(new CustomerManagementGUI(), modelController.getCustomerModelController());
        if (menuSelected.equalsIgnoreCase("inventory") || menuSelected.equalsIgnoreCase("both"))
            inventoryViewController = new InventoryViewController(new InventoryManagementGUI(), modelController.getInventoryModelController());
    }

    public String displayMainMenu() {
        return JOptionPane.showInputDialog("Enter 'customer' or 'inventory' or 'both':");
    }
}
