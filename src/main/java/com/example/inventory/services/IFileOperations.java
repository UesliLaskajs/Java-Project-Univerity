package com.example.inventory.services;


public interface IFileOperations {
    void writeToFile(String filename);
    void readFromFile(String filename);
    void deleteFromFile(String filename);
}
