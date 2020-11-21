package client.controller.viewcontroller;

import client.controller.modelcontroller.ModelController;
import client.view.CustomerManagementGUI;

public class ViewController {

    private CustomerViewController customerViewController;

    public ViewController() {
        customerViewController = new CustomerViewController(new CustomerManagementGUI());
    }

    public void addListeners(ModelController modelController) {
        customerViewController.addActionListeners(modelController);
    }
}
