package com.example.inventory.model;


public class PerishableItem extends AbstractItem {
    private int expirationDays;

    public PerishableItem(String name, int quantity, double price, int expirationDays) {
        super(name, quantity, price);
        this.expirationDays = expirationDays;
    }

    @Override
    public String getItemType() {
        return "Perishable";
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Expires in " + expirationDays + " days";
    }
}
