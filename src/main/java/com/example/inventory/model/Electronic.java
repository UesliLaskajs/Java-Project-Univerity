package com.example.inventory.model;

//Klasa Abstrakte 2 qe inheriton abstrakten
public abstract class Electronic extends AbstractItem {
    public Electronic(String name, int quantity, double price) {
        super(name, quantity, price);
    }
    public abstract int getWarrantyPeriodMonths();
}
