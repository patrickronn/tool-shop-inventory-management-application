package client.view;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;

public class CustomerManagementGUI {

    private JPanel customerManagementPanel;
    private JRadioButton customerIDRadioButton;
    private JRadioButton lastNameRadioButton;
    private JRadioButton customerTypeRadioButton;
    private JPanel searchTogglePanel;
    private JPanel searchCriteriaPanel;
    private JLabel searchCustomerLabel;
    private JTextField searchParameterField;
    private JButton searchButton;
    private JButton clearSearchButton;
    private JList searchResultsList;
    private JScrollPane searchResultsScrollPane;
    private JPanel searchParameterPanel;
    private JPanel searchCustomerPanel;
    private JPanel searchResultsPanel;
    private JPanel CustomerInfoPanel;
    private JLabel customerManagementLabel;
    private JTextField customerIdField;
    private JPanel customerAttributesPanel;
    private JTextField firstNameField;
    private JComboBox customerTypeComboBox;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton clearCustomerInfoButton;
    private JPanel customerInfoButtons;
    private JSplitPane customerManagementSplitPanel;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField postalCodeField;
    private JTextField phoneNumField;

    private static final String NEW_CUSTOMER_STRING = "<Add new customer>";

    private boolean newCustomerFlag;

    public String getNewCustomerString() {
        return NEW_CUSTOMER_STRING;
    }

    public void setNewCustomerFlag(boolean status) {
        newCustomerFlag = status;
    }

    public CustomerManagementGUI() {
        JFrame frame = new JFrame("CustomerManagementGUI");
        frame.setContentPane(this.customerManagementPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void addClearSearchListener(ActionListener listener) {
        clearSearchButton.addActionListener(listener);
    }

    public void addClearCustInfoListener(ActionListener listener) {
        clearCustomerInfoButton.addActionListener(listener);
    }

    public void clearSearchResults() {
        ((DefaultListModel)searchResultsList.getModel()).clear();
        ((DefaultListModel)searchResultsList.getModel()).addElement(NEW_CUSTOMER_STRING);
    }

    public void clearSearchParam() {
        searchParameterField.setText("");
    }

    public void addSearchResult(String searchResult) {
        ((DefaultListModel)searchResultsList.getModel()).addElement(searchResult);
    }

    public void clearCustomerId() {
        customerIdField.setText("-");
    }

    public void clearCustomerInfo() {
        firstNameField.setText("");
        lastNameField.setText("");
        addressField.setText("");
        postalCodeField.setText("");
        phoneNumField.setText("");
        customerTypeComboBox.setSelectedIndex(0);
    }

    public void addCustomerSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public String getCustomerSearchParameter() {
        return searchParameterField.getText();
    }

    public String getCustomerSearchParameterType() {
        if (customerIDRadioButton.isSelected())
            return "customerId";
        else if (lastNameRadioButton.isSelected())
            return "lastName";
        else if (customerTypeRadioButton.isSelected())
            return "customerType";
        else
            return null;
    }

    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void addSearchResultsListener(ListSelectionListener listener) {
        ListSelectionModel listSelectionModel = searchResultsList.getSelectionModel();
        listSelectionModel.addListSelectionListener(listener);
    }

    public String getCustomerIdStringValue() {
        return customerIdField.getText();
    }

    public void setCustomerIdStringValue(String customerIdField) {
        this.customerIdField.setText(customerIdField);
    }

    public String getFirstNameValue() {
        return firstNameField.getText();
    }

    public void setFirstNameValue(String firstName) {
        this.firstNameField.setText(firstName);
    }

    public String getLastNameValue() {
        return lastNameField.getText();
    }

    public void setLastNameValue(String lastName) {
        this.lastNameField.setText(lastName);
    }

    public String getAddressValue() {
        return addressField.getText();
    }

    public void setAddressValue(String address) {
        this.addressField.setText(address);
    }

    public String getPostalCode() {
        return postalCodeField.getText();
    }

    public void setPostalCodeValue(String postalCode) {
        this.postalCodeField.setText(postalCode);
    }

    public String getPhoneNumValue() {
        return phoneNumField.getText();
    }

    public void setPhoneNumValue(String phoneNum) {
        this.phoneNumField.setText(phoneNum);
    }

    public String getCustomerType() {
        return String.valueOf(customerTypeComboBox.getSelectedItem());
    }

    public void setCustomerTypeValue(String type) {
        switch (type) {
            case "R":
                this.customerTypeComboBox.setSelectedIndex(1);
                break;
            case "C":
                this.customerTypeComboBox.setSelectedIndex(2);
            default:
                this.customerTypeComboBox.setSelectedIndex(0);
        }
    }

    public void disableCustomerInfo() {
        customerIdField.setEditable(false);
        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        addressField.setEditable(false);
        postalCodeField.setEditable(false);
        phoneNumField.setEditable(false);
        phoneNumField.setEditable(false);
        customerTypeComboBox.setEnabled(false);
        saveButton.setEnabled(false);
        deleteButton.setEnabled(false);
        clearCustomerInfoButton.setEnabled(false);
    }

    public void enableCustomerInfo() {
        customerIdField.setEditable(true);
        firstNameField.setEditable(true);
        lastNameField.setEditable(true);
        addressField.setEditable(true);
        postalCodeField.setEditable(true);
        phoneNumField.setEditable(true);
        phoneNumField.setEditable(true);
        customerTypeComboBox.setEnabled(true);
        saveButton.setEnabled(true);
        deleteButton.setEnabled(true);
        clearCustomerInfoButton.setEnabled(true);
    }

    public String getSearchResultSelected() {
        return String.valueOf(searchResultsList.getSelectedValue());
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(customerManagementPanel, message);
    }
}
