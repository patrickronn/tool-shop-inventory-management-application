package client.controller.modelcontroller;

import client.controller.clientcontroller.ClientController;
import client.controller.viewcontroller.ViewController;

public class ModelController {
    private ViewController viewController;
    private ClientController clientController;

    public ModelController(ViewController viewController, ClientController clientController) {
        this.viewController = viewController;
        this.clientController = clientController;
        viewController.addListeners(this);
    }

    public void test() {
        // Add steps to populate the current view
        System.out.println("Save button was pressed");
    }
}
