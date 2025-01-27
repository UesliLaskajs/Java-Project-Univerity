package com.example.inventory;

import javax.swing.SwingUtilities;

/**
 * Entry point for the Inventory Management application.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
