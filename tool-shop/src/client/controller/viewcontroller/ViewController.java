package client.controller.viewcontroller;

import client.controller.modelcontroller.ModelController;
import client.view.CustomerManagementGUI;

public class ViewController {

    private ModelController modelController;

    private CustomerViewController customerViewController;

    public ViewController(ModelController modelController) {
        customerViewController = new CustomerViewController(new CustomerManagementGUI(), modelController);
    }
}
