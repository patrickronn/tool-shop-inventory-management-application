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
        customerManagementGUI.addSaveButtonListener(new CustomerButtonListener(modelController));
    }

    public CustomerManagementGUI getCustomerManagementGUI() {
        return customerManagementGUI;
    }

    class CustomerButtonListener implements ActionListener {
        ModelController modelController;

        public CustomerButtonListener(ModelController modelController) {
            this.modelController = modelController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            modelController.test();
        }
    }
}
