package client.controller.viewcontroller;

import client.controller.modelcontroller.CustomerModelController;
import client.view.CustomerManagementGUI;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages customer management system's interactions with the user. Reads input from GUI and sends customer model
 * related requests to the model controller
 */
public class CustomerViewController {

    /**
     * GUI for customer management
     */
    private CustomerManagementGUI customerManagementGUI;

    /**
     * Controller for managing the customer model
     */
    private CustomerModelController customerModelController;

    public CustomerViewController(CustomerManagementGUI gui, CustomerModelController customerModelController) {
        this.customerManagementGUI = gui;
        this.customerModelController = customerModelController;

        addActionListeners();
    }

    /**
     * Adds listeners to GUI components
     */
    public void addActionListeners() {
        customerManagementGUI.addClearSearchListener(new ClearSearchListener());
        customerManagementGUI.addClearCustInfoListener(new ClearCustomerInfoListener());
        customerManagementGUI.addDeleteButtonListener(new CustomerDeleteListener());
        customerManagementGUI.addCustomerSearchListener(new SearchParamListener());
        customerManagementGUI.addSearchResultsListener(new SearchResultSelectionListener());
        customerManagementGUI.addSaveButtonListener(new SaveInfoListener());
    }

    /**
     * Updates the customer form
     * @param customerInfoMap a Map of customer info
     */
    public void loadCustomerInfoForm(Map<String, String> customerInfoMap) {
        if (customerInfoMap != null) {
            customerManagementGUI.setCustomerIdStringValue(customerInfoMap.get("customerId"));
            customerManagementGUI.setFirstNameValue(customerInfoMap.get("firstName"));
            customerManagementGUI.setLastNameValue(customerInfoMap.get("lastName"));
            customerManagementGUI.setAddressValue(customerInfoMap.get("address"));
            customerManagementGUI.setPostalCodeValue(customerInfoMap.get("postalCode"));
            customerManagementGUI.setPhoneNumValue(customerInfoMap.get("phoneNum"));
            customerManagementGUI.setCustomerTypeValue(customerInfoMap.get("customerType"));
        }
    }

    /**
     * Retrieves inputted to customer form
     * @return a Map of customer info
     */
    public Map<String, String> readCustomerInfo() {
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

    /**
     * Updates the search results
     * @param searchResults a Map of search results
     */
    public void loadSearchResults(ArrayList<String> searchResults) {
        customerManagementGUI.clearSearchResults();
        for (String searchResult: searchResults)
            customerManagementGUI.addSearchResult(searchResult);
    }

    /**
     * ActionListener that clears the search results
     */
    class ClearSearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            customerManagementGUI.clearSearchResults();
            customerManagementGUI.clearSearchParam();
            customerManagementGUI.clearCustomerId();
            customerManagementGUI.clearCustomerInfo();
            customerManagementGUI.disableCustomerInfo();
        }
    }

    /**
     * ActionListener that clears customer info fields
     */
    class ClearCustomerInfoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            customerManagementGUI.clearCustomerInfo();
        }
    }

    /**
     * ActionListener that interprets user input search parameters and sends the request to model controller.
     * Includes validation check to ensure user entered proper search parameters
     */
    class SearchParamListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Map<String, String> customerSearchParamMap = readSearchParamInfo();

            if (isParamValid(customerSearchParamMap.get("paramType"), customerSearchParamMap.get("paramValue"))) {
                customerModelController.requestCustomerList(customerSearchParamMap);
                loadSearchResults(customerModelController.getCustomerStringList());
            }
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
            customerSearchParamMap.put("paramType", customerManagementGUI.getCustomerSearchParameterType());
            customerSearchParamMap.put("paramValue", customerManagementGUI.getCustomerSearchParameter());

            return customerSearchParamMap;
        }
    }

    /**
     * ActionListener that monitors the search result selected by the user. Whenever a user changes
     * their selection, the customer info form will update.
     */
    class SearchResultSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                customerManagementGUI.enableCustomerInfo();
                String searchResultSelected = customerManagementGUI.getSearchResultSelected();
                if (searchResultSelected.equals(customerManagementGUI.getNewCustomerString())) {
                    customerManagementGUI.setNewCustomerFlag(true);
                    customerManagementGUI.clearCustomerId();
                    customerManagementGUI.clearCustomerInfo();
                }
                else if (!searchResultSelected.equals("null")) {
                    customerManagementGUI.setNewCustomerFlag(false);
                    int customerId = extractCustomerId(searchResultSelected);
                    if (customerId > 0)
                        loadCustomerInfoForm(customerModelController.getCustomerInfo(customerId));
                }
            }
        }

        private int extractCustomerId(String searchResultSelected) {
            try {
                String[] stringArr = searchResultSelected.split(" ");
                return Integer.parseInt(stringArr[0]);
            } catch (NumberFormatException e) {
                customerManagementGUI.displayMessage("Cannot find customer id in:" + searchResultSelected);
                return -1;
            }
        }
    }

    /**
     * ActionListener that interprets a user saving the customer info form. Sends the request to the model controller.
     * Includes methods for requesting to update an existing customer or request a new customer to be created.
     */
    class SaveInfoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                Map<String, String> customerInfoMap = readCustomerInfo();

                if (customerManagementGUI.getCustomerIdStringValue().equals("-")) {
                    // Set to '-1' so server can deal with assigning a proper ID
                    customerInfoMap.put("customerId", "-1");
                    addCustomer(customerInfoMap);
                }
                else {
                    updateCustomer(customerInfoMap);
                }
        }

        private void updateCustomer(Map<String, String> customerInfoMap) {
            try {
                Integer.parseInt(customerManagementGUI.getCustomerIdStringValue());
                assertNoEmptyInfo(customerInfoMap);
                assertValidCharLengths(customerInfoMap);

                boolean updateSucceeded = customerModelController.updateCustomer(customerInfoMap);
                if (updateSucceeded) {
                    customerManagementGUI.displayMessage("Customer info was successfully saved.");
                    loadSearchResults(customerModelController.getCustomerStringList());
                    customerManagementGUI.clearCustomerId();
                    customerManagementGUI.clearCustomerInfo();
                    customerManagementGUI.disableCustomerInfo();
                }
                else
                    customerManagementGUI.displayMessage("Customer info did not save properly..");
            } catch (NumberFormatException err) {
                customerManagementGUI.displayMessage("Invalid Customer ID: " +
                        customerManagementGUI.getCustomerIdStringValue());
            }
            catch (IllegalArgumentException err) {
                customerManagementGUI.displayMessage(err.getMessage());
            }
        }

        private void addCustomer(Map<String, String> customerInfoMap) {
            try {
                assertNoEmptyInfo(customerInfoMap);
                assertValidCharLengths(customerInfoMap);

                // Add new customer and update the GUI if successful
                int newCustomerId = customerModelController.addNewCustomer(customerInfoMap);

                if (newCustomerId != -1) {
                    customerManagementGUI.displayMessage("New customer was successfully created.");
                    loadCustomerInfoForm(customerModelController.getCustomerInfo(newCustomerId));
                    loadSearchResults(customerModelController.getCustomerStringList());
                    customerManagementGUI.selectLastSearchResult();
                }
                else
                    customerManagementGUI.displayMessage("New customer couldn't be created.");
            } catch (IllegalArgumentException err) {
                customerManagementGUI.displayMessage(err.getMessage());
            }
        }

        private void assertNoEmptyInfo(Map<String, String> stringsMap) throws IllegalArgumentException {
            stringsMap.values().forEach(val -> {
                if (val.length() == 0 || val.equals("-"))
                    throw new IllegalArgumentException("Cannot have empty customer information.");
            });
        }

        private void assertValidCharLengths(Map<String, String> customerInfoMap) throws IllegalArgumentException {
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
        }
    }

    /**
     * ActionListener that interprets a user delete the customer info form. Sends the request to the model controller.
     * If the customer was successfully removed, it will remove it from the search results list
     */
    class CustomerDeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Integer.parseInt(customerManagementGUI.getCustomerIdStringValue());
                Map<String, String> customerInfoMap = readCustomerInfo();
                boolean deleteSucceeded = customerModelController.deleteCustomer(customerInfoMap);
                if (deleteSucceeded) {
                    customerManagementGUI.displayMessage("Customer was successfully deleted.");
                    loadSearchResults(customerModelController.getCustomerStringList());
                    customerManagementGUI.clearCustomerId();
                    customerManagementGUI.clearCustomerInfo();
                    customerManagementGUI.disableCustomerInfo();
                }
                else
                    customerManagementGUI.displayMessage("Attempt to delete customer failed.");
            } catch(NumberFormatException err) {
                customerManagementGUI.displayMessage("No customer selected.");
            }
        }
    }
}
