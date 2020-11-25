package client.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class InventoryManagementGUI {
    private JPanel inventoryManagementPanel;
    private JPanel buttonPanel;
    private JButton showAllToolsButton;
    private JButton searchByNameButton;
    private JButton searchByIDButton;
    private JPanel resultsPanel;
    private JList inventoryResultsList;
    private JButton decreaseQuantityButton;
    private JScrollPane inventoryScrollPane;
    private JTextField quantityTextField;
    private JButton viewOrderButton;
    private JFrame frame;

    public InventoryManagementGUI() {
        frame = new JFrame("CustomerManagementGUI");
        frame.setContentPane(this.inventoryManagementPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
    }

    public void addShowAllToolsListener(ActionListener listener) {
        showAllToolsButton.addActionListener(listener);
    }

    public void addSearchByNameListener(ActionListener listener) {
        searchByNameButton.addActionListener(listener);
    }

    public void addSearchByIdListener(ActionListener listener) {
        searchByIDButton.addActionListener(listener);
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

    public void setQuantityValue(String quantityString) {
        quantityTextField.setText(quantityString);
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(inventoryManagementPanel, message);
    }

    public static void main(String[] args) {
        InventoryManagementGUI gui = new InventoryManagementGUI();
        gui.setQuantityValue("10");
    }
}
