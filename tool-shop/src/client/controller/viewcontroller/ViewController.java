package client.controller.viewcontroller;

import client.controller.modelcontroller.ModelController;
import client.view.CustomerManagementGUI;
import client.view.InventoryManagementGUI;

public class ViewController {

    private CustomerViewController customerViewController;
    private InventoryViewController inventoryViewController;

    public ViewController(ModelController modelController) {
        customerViewController = new CustomerViewController(new CustomerManagementGUI(), modelController.getCustomerModelController());
//        inventoryViewController = new InventoryViewController(new InventoryManagementGUI(), modelController.getInventoryModelController());
    }
}
