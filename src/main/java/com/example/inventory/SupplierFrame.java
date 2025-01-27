package com.example.inventory;

import javax.swing.*;
import java.awt.*;

/**
 * Example supplier management frame demonstrating
 * simple assertion usage, text area logging, etc.
 */
public class SupplierFrame extends JFrame {
    private JTextField txtSupplierName, txtContact;
    private JTextArea textArea;
    private JButton btnAdd, btnClear;

    public SupplierFrame() {
        super("Supplier Management");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("Supplier Info"));

        topPanel.add(new JLabel("Supplier Name:"));
        txtSupplierName = new JTextField();
        topPanel.add(txtSupplierName);

        topPanel.add(new JLabel("Contact Info:"));
        txtContact = new JTextField();
        topPanel.add(txtContact);

        JPanel btnPanel = new JPanel();
        btnAdd = new JButton("Add Supplier");
        btnClear = new JButton("Clear");
        btnPanel.add(btnAdd);
        btnPanel.add(btnClear);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(topPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Events
        btnAdd.addActionListener(e -> {
            try {
                assert !txtSupplierName.getText().isEmpty() : "Supplier name cannot be empty";
                String sName = txtSupplierName.getText();
                String sContact = txtContact.getText();

                textArea.append("Added Supplier: " + sName + " | Contact: " + sContact + "\n");
                txtSupplierName.setText("");
                txtContact.setText("");
            } catch (AssertionError err) {
                JOptionPane.showMessageDialog(this, "Error: " + err.getMessage());
            }
        });

        btnClear.addActionListener(e -> textArea.setText(""));
    }
}
