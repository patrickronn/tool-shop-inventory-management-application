package client.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemInfoDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JPanel navigationPanel;
    private JTextField toolIdField;
    private JLabel toolNameLabel;
    private JTextField toolNameField;
    private JTextField quantityField;
    private JLabel quantityLabel;
    private JLabel priceLabel;
    private JTextField priceField;
    private JLabel typeLabel;
    private JTextField typeField;
    private JLabel supplierIdLabel;
    private JTextField supplierIdField;
    private JLabel toolIdLabel;
    private JPanel itemInfoPanel;
    private JLabel powerTypeLabel;
    private JTextField powerTypeField;

    public ItemInfoDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    public void setToolId(String toolId) {
        this.toolIdField.setText(toolId);
    }

    public void setToolName(String toolName) {
        this.toolNameField.setText(toolName);
    }

    public void setQuantity(String quantity) {
        this.quantityField.setText(quantity);
    }

    public void setPrice(String price) {
        this.priceField.setText(price);
    }

    public void setType(String type) {
        this.typeField.setText(type);
    }

    public void setSupplierId(String supplierId) {
        this.supplierIdField.setText(supplierId);
    }

    public void setPowerType(String powerType) {
        this.powerTypeField.setText(powerType);
    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
