package client.controller.viewcontroller;

import client.controller.modelcontroller.ModelController;
import client.view.CustomerManagementGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class CustomerViewController {

    private CustomerManagementGUI customerManagementGUI;
    private ModelController modelController;

    public CustomerViewController(CustomerManagementGUI gui, ModelController modelController) {
        this.customerManagementGUI = gui;
        this.modelController = modelController;
    }

    public void addActionListeners() {
        customerManagementGUI.addClearSearchListener(new ClearSearchListener());
        customerManagementGUI.addClearCustInfoListener(new ClearCustomerInfoListener());
        customerManagementGUI.addDeleteButtonListener(new CustomerDeleteListener());
        customerManagementGUI.addCustomerSearchListener(new SearchListener());
        customerManagementGUI.addSaveButtonListener(new SaveInfoListener());
    }

    class ClearSearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            customerManagementGUI.clearSearch();
        }
    }

    class ClearCustomerInfoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            customerManagementGUI.clearCustomerInfo();
        }
    }

    class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchParamValue = customerManagementGUI.getCustomerSearchParameter();
            String searchParamType = customerManagementGUI.getCustomerSearchParameterType();

            if (isParamValid(searchParamType, searchParamValue))
                modelController.sendCustomerSearchParam(searchParamType, searchParamValue);
            else
                customerManagementGUI.displayErrorMessage("Invalid search parameter value.");
        }

        private boolean isParamValid(String searchParamType, String searchParamValue) {
            switch(searchParamType) {
                case "customerId":
                    try {
                        Integer.parseInt(searchParamValue);
                        return true;
                    }
                    catch (NumberFormatException e) {
                        return false;
                    }
                case "customerType":
                    if (searchParamValue.length() != 1 ||
                            (searchParamValue.charAt(0) != 'R' && searchParamValue.charAt(0) != 'C'))
                        return false;
                default:
                    return true;
            }
        }
    }

    class SaveInfoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int customerId = Integer.parseInt(customerManagementGUI.getCustomerIdValue());

                Map<String, String> customerInfoMap = new HashMap<>();
                customerInfoMap.put("customerId", String.valueOf(customerId));
                customerInfoMap.put("firstName", customerManagementGUI.getFirstNameValue());
                customerInfoMap.put("lastName", customerManagementGUI.getLastNameValue());
                customerInfoMap.put("address", customerManagementGUI.getAddressValue());
                customerInfoMap.put("postalCode", customerManagementGUI.getPostalCode());
                customerInfoMap.put("phoneNum", customerManagementGUI.getPhoneNumValue());
                customerInfoMap.put("customerType", customerManagementGUI.getCustomerType());

                modelController.updateCustomer(customerInfoMap);

            } catch(NumberFormatException err) {
                customerManagementGUI.displayErrorMessage("Customer ID must be an integer.");
            }
        }
    }

    class CustomerDeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int customerId = Integer.parseInt(customerManagementGUI.getCustomerIdValue());
                modelController.deleteCustomer(customerId);
            } catch(NumberFormatException err) {
                customerManagementGUI.displayErrorMessage("Customer ID must be an integer.");
            }
        }
    }

    public static void main(String[] args) {
        CustomerViewController vc = new CustomerViewController(new CustomerManagementGUI(), new ModelController(null, null, null, null));
        vc.addActionListeners();
    }
}
