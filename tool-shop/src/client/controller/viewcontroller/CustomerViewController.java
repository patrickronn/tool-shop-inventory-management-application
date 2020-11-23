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

        addActionListeners();
    }

    public void addActionListeners() {
        customerManagementGUI.addClearSearchListener(new ClearSearchListener());
        customerManagementGUI.addClearCustInfoListener(new ClearCustomerInfoListener());
        customerManagementGUI.addDeleteButtonListener(new CustomerDeleteListener());
        customerManagementGUI.addCustomerSearchListener(new SearchListener());
        customerManagementGUI.addSaveButtonListener(new SaveInfoListener());
    }

    public void displayMessage(String message) {
        customerManagementGUI.displayMessage(message);
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
            Map<String, String> customerSearchParamMap = readSearchParamInfo();

            if (isParamValid(customerSearchParamMap.get("paramType"), customerSearchParamMap.get("paramValue")))
                modelController.sendCustomerSearchParam(customerSearchParamMap);
            else
                customerManagementGUI.displayMessage("Invalid search parameter value.");
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
                    if (!searchParamValue.equals("R") && !searchParamValue.equals("C"))
                        return false;
                default:
                    return true;
            }
        }

        private Map<String, String> readSearchParamInfo() {
            Map<String, String> customerSearchParamMap = new HashMap<>();
            customerSearchParamMap.put("paramValue", customerManagementGUI.getCustomerSearchParameter());
            customerSearchParamMap.put("paramType", customerManagementGUI.getCustomerSearchParameterType());

            return customerSearchParamMap;
        }
    }

    class SaveInfoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Check for valid customer id and read form fields
                Integer.parseInt(customerManagementGUI.getCustomerIdStringValue());
                Map<String, String> customerInfoMap = readCustomerInfo();

                // Update customer info
                if (hasNoEmptyInfo(customerInfoMap) && hasValidCharLengths(customerInfoMap)) {
                    if (modelController.updateCustomer(customerInfoMap))
                        customerManagementGUI.displayMessage("Customer info was successfully saved.");
                    else
                        customerManagementGUI.displayMessage("Customer info did not save.");
                }
            } catch(NumberFormatException err) {
                customerManagementGUI.displayMessage("Customer ID must be an integer.");
            } catch (IllegalArgumentException err) {
                customerManagementGUI.displayMessage(err.getMessage());
            }
        }

        private Map<String, String> readCustomerInfo() {
            Map<String, String> customerInfoMap = new HashMap<>();
            customerInfoMap.put("customerId", customerManagementGUI.getCustomerIdStringValue());
            customerInfoMap.put("firstName", customerManagementGUI.getFirstNameValue());
            customerInfoMap.put("lastName", customerManagementGUI.getLastNameValue());
            customerInfoMap.put("address", customerManagementGUI.getAddressValue());
            customerInfoMap.put("postalCode", customerManagementGUI.getPostalCode());
            customerInfoMap.put("phoneNum", customerManagementGUI.getPhoneNumValue());
            customerInfoMap.put("customerType", customerManagementGUI.getCustomerType());

            return customerInfoMap;
        }

        private boolean hasNoEmptyInfo(Map<String, String> stringsMap) throws IllegalArgumentException {
            stringsMap.values().forEach(val -> {
                if (val.length() == 0 || val.equals("-"))
                    throw new IllegalArgumentException("Cannot have empty customer information.");
            });
            return true;
        }

        private boolean hasValidCharLengths(Map<String, String> customerInfoMap) throws IllegalArgumentException {
            if (customerInfoMap.get("firstName").length() > 20)
                throw new IllegalArgumentException("First name cannot be greater than 20 chars.");
            else if (customerInfoMap.get("lastName").length() > 20)
                throw new IllegalArgumentException("Last name cannot be greater than 20 chars.");
            else if (customerInfoMap.get("address").length() > 50)
                throw new IllegalArgumentException("Address cannot be greater than 50 chars.");
            else if (customerInfoMap.get("postalCode").length() > 7)
                throw new IllegalArgumentException("Postal code cannot be greater than 7 chars.");
            else if (customerInfoMap.get("phoneNum").length() > 12)
                throw new IllegalArgumentException("Phone Number cannot be greater than 12 chars.");
            else
                return true;
        }
    }

    class CustomerDeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int customerId = Integer.parseInt(customerManagementGUI.getCustomerIdStringValue());
                modelController.deleteCustomer(customerId);
            } catch(NumberFormatException err) {
                customerManagementGUI.displayMessage("Customer ID must be an integer.");
            }
        }
    }

    public static void main(String[] args) {
//        CustomerViewController vc = new CustomerViewController(new CustomerManagementGUI(), new ModelController(null, null, null, null));
    }
}
