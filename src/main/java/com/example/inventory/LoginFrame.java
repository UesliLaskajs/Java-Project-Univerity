package com.example.inventory;

import javax.swing.*;
import java.awt.*;

/**
 * Simple login frame with "admin" / "admin123" as default credentials.
 */
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        super("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(new JLabel(""));
        panel.add(loginButton);

        add(panel);

        // Simple login check
        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (user.equalsIgnoreCase("admin") && pass.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new DashboardFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Try admin / admin123.");
            }
        });
    }
}
