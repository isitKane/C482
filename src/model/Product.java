/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Product.
 *
 * @author Kane
 */
public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Instantiates a new Product.
     *
     * @param productID    the product id
     * @param productName  the product name
     * @param productPrice the product price
     * @param productStock the product stock
     * @param min          the min
     * @param max          the max
     */
    public Product(int productID, String productName, double productPrice, int productStock, int min, int max){
        this.id = productID;
        this.name = productName;
        this.price = productPrice;
        this.stock = productStock;
        this.min = min;
        this.max = max;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets stock.
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets stock.
     *
     * @param stock the stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets min.
     *
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets min.
     *
     * @param min the min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets max.
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets max.
     *
     * @param max the max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Add associated part.
     *
     * @param part the part
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
        
    }

    /**
     * Delete associated part boolean.
     *
     * @param selectedAssociatePart the selected associate part
     * @return the boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatePart){
        return associatedParts.remove(selectedAssociatePart);
    }

    /**
     * Gets associated parts.
     *
     * @return the associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
    
}


