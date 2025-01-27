package com.example.inventory;

import com.example.inventory.model.AbstractItem;
import com.example.inventory.model.ElectronicItem;
import com.example.inventory.model.PerishableItem;
import com.example.inventory.services.InventoryManager;
import com.example.inventory.utils.Validator;

import javax.swing.*;
import java.awt.*;

public class InventoryFrame extends JFrame {
    private InventoryManager manager;
    private InventoryTableModel tableModel;
    private JTable inventoryTable;

    // Fields for "Add New Item"
    private JTextField txtName, txtQuantity, txtPrice;
    private JComboBox<String> cmbType;

    // Buttons
    private JButton btnAdd, btnSave, btnLoad, btnDelete;
    private JButton btnSearch, btnSort;

    // Fields for "Search"
    private JTextField txtSearch;

    public InventoryFrame() {
        super("Inventory Management");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        manager = new InventoryManager(); 
        tableModel = new InventoryTableModel(manager.getItems());
        inventoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);

        // ===================== TOP PANEL (ADD ITEM) =====================
        JPanel topPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("Add New Item"));

        topPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        topPanel.add(txtName);

        topPanel.add(new JLabel("Quantity:"));
        txtQuantity = new JTextField();
        topPanel.add(txtQuantity);

        topPanel.add(new JLabel("Price:"));
        txtPrice = new JTextField();
        topPanel.add(txtPrice);

        topPanel.add(new JLabel("Type:"));
        cmbType = new JComboBox<>(new String[] {"Perishable", "Electronic"});
        topPanel.add(cmbType);

        // Fill the second row
        for (int i = 0; i < 5; i++) {
            topPanel.add(new JLabel(""));
        }


        JPanel btnPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        btnAdd    = new JButton("Add");
        btnSave   = new JButton("Save to File");
        btnLoad   = new JButton("Load from File");
        btnDelete = new JButton("Delete File");

        btnPanel.add(btnAdd);
        btnPanel.add(btnSave);
        btnPanel.add(btnLoad);
        btnPanel.add(btnDelete);

 
        JPanel searchSortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchSortPanel.setBorder(BorderFactory.createTitledBorder("Search & Sort"));

        JLabel lblSearch = new JLabel("Search Name:");
        txtSearch = new JTextField(10);
        btnSearch = new JButton("Search");
        btnSort   = new JButton("Sort Items (Name)");

        searchSortPanel.add(lblSearch);
        searchSortPanel.add(txtSearch);
        searchSortPanel.add(btnSearch);
        searchSortPanel.add(btnSort);


        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(searchSortPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(scrollPane,  BorderLayout.CENTER);
        add(btnPanel,    BorderLayout.SOUTH);


        btnAdd.addActionListener(e -> addItem());
        btnSave.addActionListener(e -> {
            manager.writeToFile("src/main/java/com/example/inventory/resources/inventory_data.txt");
            JOptionPane.showMessageDialog(this, "Data saved!");
        });
        btnLoad.addActionListener(e -> {
            manager.readFromFile("src/main/java/com/example/inventory/resources/inventory_data.txt");
            tableModel.fireTableDataChanged();
            JOptionPane.showMessageDialog(this, "Data loaded!");
        });
        btnDelete.addActionListener(e -> {
            manager.deleteFromFile("src/main/java/com/example/inventory/resources/inventory_data.txt");
            tableModel.fireTableDataChanged();
            JOptionPane.showMessageDialog(this, "File deleted!");
        });

        // NEW: Searching & Sorting
        btnSearch.addActionListener(e -> searchItem());
        btnSort.addActionListener(e -> sortItems());
    }

    private void addItem() {
        try {
            String name = txtName.getText();
            String qStr = txtQuantity.getText();
            String pStr = txtPrice.getText();
            String type = (String) cmbType.getSelectedItem();

            // Validate with Regex
            if(!Validator.isValidQuantity(qStr)) {
                throw new NumberFormatException("Invalid quantity. Digits only.");
            }
            if(!Validator.isValidPrice(pStr)) {
                throw new NumberFormatException("Invalid price. Format: 12 or 12.34");
            }

            int quantity = Integer.parseInt(qStr);
            double price = Double.parseDouble(pStr);

            AbstractItem newItem;
            if("Perishable".equals(type)) {
                newItem = new PerishableItem(name, quantity, price, 7);
            } else {
                newItem = new ElectronicItem(name, quantity, price, 12);
            }

            manager.addItem(newItem);
            tableModel.fireTableDataChanged();  

          
            txtName.setText("");
            txtQuantity.setText("");
            txtPrice.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unexpected error: " + e.getMessage());
        }
    }

    private void sortItems() {
        manager.quickSortByName(); 
        tableModel.fireTableDataChanged();
        JOptionPane.showMessageDialog(this, "Items sorted by name!");
    }


    private void searchItem() {
        String searchName = txtSearch.getText().trim();
        if (searchName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name to search.");
            return;
        }


        manager.quickSortByName();


        AbstractItem found = manager.binarySearchItemByName(searchName);
        if (found != null) {
            JOptionPane.showMessageDialog(this,
                "Found item:\n" + found.getDescription(),
                "Search Result",
                JOptionPane.INFORMATION_MESSAGE
            );


            int rowIndex = manager.getItems().indexOf(found);
            if (rowIndex >= 0) {
                inventoryTable.setRowSelectionInterval(rowIndex, rowIndex);
                inventoryTable.scrollRectToVisible(
                    inventoryTable.getCellRect(rowIndex, 0, true)
                );
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "No item found with name: " + searchName,
                "Not Found",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
}
