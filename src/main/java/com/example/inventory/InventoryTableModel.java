package com.example.inventory;

import com.example.inventory.model.AbstractItem;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Custom TableModel to display items in a JTable.
 */
public class InventoryTableModel extends AbstractTableModel {
    private final List<AbstractItem> itemList;
    private final String[] columnNames = {"Name", "Quantity", "Price", "Type", "Description"};

    public InventoryTableModel(List<AbstractItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getRowCount() {
        return itemList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int colIndex) {
        AbstractItem item = itemList.get(rowIndex);
        switch (colIndex) {
            case 0: return item.getName();
            case 1: return item.getQuantity();
            case 2: return item.getPrice();
            case 3: return item.getItemType();
            case 4: return item.getDescription();
            default: return "";
        }
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
}
