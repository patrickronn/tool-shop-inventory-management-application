package client.controller.viewcontroller;

import client.controller.modelcontroller.ModelController;
import client.view.CustomerManagementGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerViewController {

    private CustomerManagementGUI customerManagementGUI;

    public CustomerViewController(CustomerManagementGUI gui) {
        customerManagementGUI = gui;
    }

    public void addActionListeners(ModelController modelController) {
        customerManagementGUI.addSaveButtonListener(new CustomerButtonListeners(modelController));
    }

    public CustomerManagementGUI getCustomerManagementGUI() {
        return customerManagementGUI;
    }

    class CustomerButtonListeners implements ActionListener {
        ModelController modelController;

        public CustomerButtonListeners(ModelController modelController) {
            this.modelController = modelController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            modelController.test();
        }
    }
}
