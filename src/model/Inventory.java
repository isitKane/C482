/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Inventory.
 *
 * @author Kane
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Add part.
     *
     * @param newPart the new part
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Add product.
     *
     * @param newProduct the new product
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Lookup part part.
     *
     * @param partId the part id
     * @return the part
     */
    public static Part lookupPart(int partId) {
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getId() == partId) {
                    return allParts.get(i);
                }
            }
        }

        return null;
    }

    /**
     * Lookup product product.
     *
     * @param productId the product id
     * @return the product
     */
    public static Product lookupProduct(int productId) {
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getId() == productId) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Look up part observable list.
     *
     * @param partNameToLookUp the part name to look up
     * @return the observable list
     */
    public static ObservableList<Part> lookUpPart(String partNameToLookUp) {
        if (!allParts.isEmpty()) {
            ObservableList searchPartsList = FXCollections.observableArrayList();
            for (Part p : getAllParts()) {
                if (p.getName().contains(partNameToLookUp)) {
                    searchPartsList.add(p);
                }
            }
            return searchPartsList;
        }
        return null;
    }

    /**
     * Lookup product observable list.
     *
     * @param productName the product name
     * @return the observable list
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        if (!allProducts.isEmpty()) {
            ObservableList productSearchList = FXCollections.observableArrayList();
            for (Product p : getAllProducts()) {
                if (p.getName().contains(productName)) {
                    productSearchList.add(p);
                }
            }
            return productSearchList;
        }
        return null;
    }

    /**
     * Update part.
     *
     * @param index        the index
     * @param selectedPart the selected part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Update product.
     *
     * @param index      the index
     * @param newProduct the new product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Delete part boolean.
     *
     * @param selectedPart the selected part
     * @return the boolean
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Delete product boolean.
     *
     * @param selectedProduct the selected product
     * @return the boolean
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Gets all parts.
     *
     * @return the all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all products.
     *
     * @return the all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
