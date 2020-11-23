package client.view;

import javax.swing.*;
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

    public void clearSearch() {
        ((DefaultListModel)searchResultsList.getModel()).clear();
        ((DefaultListModel)searchResultsList.getModel()).addElement("<Add new customer>");
        searchParameterField.setText("");
    }

    public void clearCustomerInfo() {
        customerIdField.setText("");
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

    public String getCustomerIdStringValue() {
        return customerIdField.getText();
    }

    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public String getFirstNameValue() {
        return firstNameField.getText();
    }

    public String getLastNameValue() {
        return lastNameField.getText();
    }

    public String getAddressValue() {
        return addressField.getText();
    }

    public String getPostalCode() {
        return postalCodeField.getText();
    }

    public String getPhoneNumValue() {
        return phoneNumField.getText();
    }

    public String getCustomerType() {
        return String.valueOf(customerTypeComboBox.getSelectedItem());
//
//        if (type.equals("R")||type.equals("r"))
//            return "Residential";
//        else if (type.equals("C")||type.equals("c"))
//            return "Commercial";
//        else
//            return "-";
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(customerManagementPanel, message);
    }
}
