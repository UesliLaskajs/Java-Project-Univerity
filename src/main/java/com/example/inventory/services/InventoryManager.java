package com.example.inventory.services;

import com.example.inventory.model.AbstractItem;
import com.example.inventory.model.ElectronicItem;
import com.example.inventory.model.PerishableItem;

import java.io.*;
import java.util.ArrayList;

public class InventoryManager implements IFileOperations, IPrintable {
    private ArrayList<AbstractItem> items;

    public InventoryManager() {
        items = new ArrayList<>();
    }

    public ArrayList<AbstractItem> getItems() {
        return items;
    }

    public void addItem(AbstractItem item) {
        items.add(item);
    }


    public void quickSortByName() {
        quickSort(0, items.size() - 1);
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pivotIndex = partition(low, high);
            quickSort(low, pivotIndex - 1);
            quickSort(pivotIndex + 1, high);
        }
    }

    private int partition(int low, int high) {
        // Pivot = item name at "high" index
        String pivotName = items.get(high).getName().toLowerCase();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            String currentName = items.get(j).getName().toLowerCase();
            if (currentName.compareTo(pivotName) <= 0) {
                i++;
                // swap
                AbstractItem temp = items.get(i);
                items.set(i, items.get(j));
                items.set(j, temp);
            }
        }
        // Place pivot in correct position
        AbstractItem temp = items.get(i + 1);
        items.set(i + 1, items.get(high));
        items.set(high, temp);

        return i + 1;
    }

    // =========================== BINARY SEARCH (ALGORITHM) ===========================
    // Assumes items are already sorted by name (via quickSortByName) 
    public AbstractItem binarySearchItemByName(String name) {
        return binarySearchHelper(name.toLowerCase(), 0, items.size() - 1);
    }

    private AbstractItem binarySearchHelper(String target, int left, int right) {
        if (left > right) {
            return null;  // Not found
        }
        int mid = (left + right) / 2;
        String midName = items.get(mid).getName().toLowerCase();

        int cmp = midName.compareTo(target);
        if (cmp == 0) {
            return items.get(mid);
        } else if (cmp > 0) {
            return binarySearchHelper(target, left, mid - 1);
        } else {
            return binarySearchHelper(target, mid + 1, right);
        }
    }

 
    public int getTotalQuantity() {
        return recursiveSum(0, 0);
    }

    private int recursiveSum(int index, int sum) {
        if (index >= items.size()) {
            return sum;
        }
        return recursiveSum(index + 1, sum + items.get(index).getQuantity());
    }


    @Override
    public void writeToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (AbstractItem item : items) {
                writer.write(item.toString());
                writer.newLine();
            }
            System.out.println("Data saved to file: " + filename);
        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }
    }

    @Override
    public void readFromFile(String filename) {
        items.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String name = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    double price = Double.parseDouble(parts[2]);
                    String type = parts[3];

                    if ("Perishable".equalsIgnoreCase(type)) {
                        items.add(new PerishableItem(name, quantity, price, 7));
                    } else if ("Electronic".equalsIgnoreCase(type)) {
                        items.add(new ElectronicItem(name, quantity, price, 12));
                    }
                }
            }
            System.out.println("Data loaded from file: " + filename);
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
        }
    }

    @Override
    public void deleteFromFile(String filename) {
        File f = new File(filename);
        if (f.exists() && f.delete()) {
            System.out.println("File deleted: " + filename);
        } else {
            System.out.println("Could not delete file or file not found.");
        }
    }

    // =========================== PRINT ALL ITEMS (INTERFACE) ===========================
    @Override
    public void printAllItems() {
        // Example: sort before printing
        quickSortByName();
        for (AbstractItem item : items) {
            System.out.println(item.getDescription());
        }
    }
}
