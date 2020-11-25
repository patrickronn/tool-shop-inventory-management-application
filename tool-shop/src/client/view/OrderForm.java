package client.view;

import javax.swing.*;
import java.awt.event.*;

public class OrderForm extends JDialog {
    private JPanel orderPane;
    private JButton buttonOK;
    private JTextArea orderTextArea;
    private JPanel navigationPanel;
    private JPanel orderBodyPanel;
    private JScrollPane orderScrollPane;

    public OrderForm(String orderString) {
        setContentPane(orderPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // Displays order
        setOrderText(orderString);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onOK() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        // call onOK() on ESCAPE
        orderPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        dispose();
    }

    public void setOrderText(String orderString) {
        orderTextArea.setText(orderString);
    }

    public static void main(String[] args) {
        OrderForm dialog = new OrderForm("Test Order Here");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
