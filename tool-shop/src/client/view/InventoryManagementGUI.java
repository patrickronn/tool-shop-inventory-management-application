package client.view;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class InventoryManagementGUI {
    private JPanel inventoryManagementPanel;
    private JPanel buttonPanel;
    private JButton loadAllToolsButton;
    private JButton searchByNameButton;
    private JButton searchByIDButton;
    private JPanel resultsPanel;
    private JList inventoryResultsList;
    private JButton decreaseQuantityButton;
    private JScrollPane inventoryScrollPane;
    private JTextField quantityTextField;
    private JButton viewOrderButton;
    private JFrame frame;

    private boolean inventoryLoaded;

    public InventoryManagementGUI() {
        frame = new JFrame("CustomerManagementGUI");
        frame.setContentPane(this.inventoryManagementPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        inventoryLoaded = false;
        setButtonsClickable(false);
    }

    public void close() {
        frame.dispose();
    }

    public boolean isInventoryLoaded() {
        return inventoryLoaded;
    }

    public void setInventoryLoaded(boolean condition) {
        inventoryLoaded = condition;
        setButtonsClickable(condition);
    }

    public void setButtonsClickable(boolean condition) {
        searchByIDButton.setEnabled(condition);
        searchByNameButton.setEnabled(condition);
        viewOrderButton.setEnabled(condition);
        decreaseQuantityButton.setEnabled(condition);
    }

    public void addWindowListener(WindowListener listener) {
        frame.addWindowListener(listener);
    }

    public void addLoadAllToolsListener(ActionListener listener) {
        loadAllToolsButton.addActionListener(listener);
    }

    public void addSearchByNameListener(ActionListener listener) {
        searchByNameButton.addActionListener(listener);
    }

    public void addSearchByIdListener(ActionListener listener) {
        searchByIDButton.addActionListener(listener);
    }

    public void addInventoryResultsListener(ListSelectionListener listener) {
        ListSelectionModel listSelectionModel = inventoryResultsList.getSelectionModel();
        listSelectionModel.addListSelectionListener(listener);
    }
    public void addDecreaseQuantityListener(ActionListener listener) {
        decreaseQuantityButton.addActionListener(listener);
    }

    public void addViewOrderListener(ActionListener listener) {
        viewOrderButton.addActionListener(listener);
    }

    public void clearSearchResults() {
        ((DefaultListModel)inventoryResultsList.getModel()).clear();
    }

    public void addToolSearchResult(String searchResult) {
        ((DefaultListModel)inventoryResultsList.getModel()).addElement(searchResult);
    }

    public String getSearchResultSelected() {
        return String.valueOf(inventoryResultsList.getSelectedValue());
    }

    public String getQuantityValue() {
        return quantityTextField.getText();
    }

    public void setQuantityValue(String quantityString) {
        quantityTextField.setText(quantityString);
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(inventoryManagementPanel, message);
    }

    public String promptForInput(String prompt) {
        return JOptionPane.showInputDialog(prompt);
    }

    public static void main(String[] args) {
        InventoryManagementGUI gui = new InventoryManagementGUI();
        gui.setQuantityValue("10");
    }
}
