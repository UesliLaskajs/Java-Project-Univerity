package com.example.inventory.model;


public abstract class AbstractItem {
    protected String name;
    protected int quantity;
    protected double price;

    public AbstractItem(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }


    public abstract String getItemType();


    public String getDescription() {
        return "Item: " + name + ", Qty: " + quantity + ", Price: $" + price;
    }

    // Ketu Bejme Overload
    public void setQuantity(int q) {
        this.quantity = q;
    }
    public void setQuantity(String qStr) {
        this.quantity = Integer.parseInt(qStr);
    }

    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + "|" + quantity + "|" + price + "|" + getItemType();
    }
}
