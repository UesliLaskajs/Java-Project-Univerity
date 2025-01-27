package com.example.inventory.model;


public class ElectronicItem extends Electronic {
    private int warrantyMonths;

    public ElectronicItem(String name, int quantity, double price, int warrantyMonths) {
        super(name, quantity, price);
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public int getWarrantyPeriodMonths() {
        return warrantyMonths;
    }

    @Override
    public String getItemType() {
        return "Electronic";
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Warranty: " + warrantyMonths + " months";
    }
}
