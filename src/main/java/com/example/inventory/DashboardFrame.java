package com.example.inventory;

import javax.swing.*;
import java.awt.*;


public class DashboardFrame extends JFrame {
    private JButton inventoryButton, supplierButton, exitButton;

    public DashboardFrame() {
        super("Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        inventoryButton = new JButton("Manage Inventory");
        supplierButton = new JButton("Manage Suppliers");
        exitButton = new JButton("Exit");

        panel.add(inventoryButton);
        panel.add(supplierButton);
        panel.add(exitButton);

        add(panel, BorderLayout.CENTER);

        inventoryButton.addActionListener(e -> new InventoryFrame().setVisible(true));
        supplierButton.addActionListener(e -> new SupplierFrame().setVisible(true));
        exitButton.addActionListener(e -> System.exit(0));
    }
}
