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
    private JTextField searachParameterField;
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
    private JTextField textField3;
    private JComboBox comboBox1;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton clearButton;
    private JPanel customerInfoButtons;
    private JSplitPane customerManagementSplitPanel;

    public CustomerManagementGUI() {
        JFrame frame = new JFrame("CustomerManagementGUI");
        frame.setContentPane(this.customerManagementPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public String getCustomerIdValue() {
        return customerIdField.getText();
    }

    public static void main(String[] args) {
        CustomerManagementGUI gui = new CustomerManagementGUI();
    }
}
