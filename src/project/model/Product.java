package project.model;

/**
 * This Java Class is for the class of the products.
 *
 * @author Antonio Peza
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Product {

    /**
     * These are the variables for the product object
     * */
    private int productID;
    private String productName;
    private int productInv;
    private float productPrice;
    private int productMax;
    private int productMin;

    // Observable LIst of parts associated with product
    /**
     * This method returns a list of all associated parts
     * @return assocParts
     * */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    // This is the constructor for the Product class
    /**
     * This is the product constructor
     * @param productID
     * @param productName
     * @param productInv
     * @param productPrice
     * @param productMin
     * @param productMax
     * */
    public Product(int productID, String productName, int productInv, float productPrice, int productMin, int productMax) {
        this.productID = productID;
        this.productName = productName;
        this.productInv = productInv;
        this.productPrice = productPrice;
        this.productMax = productMax;
        this.productMin = productMin;
    }
    // This is the function being used to grab Parts and add them to the associated parts list
    /**
     * This method will add a selected part to the list of associated parts
     * @param part
     * */
    public void addAssocParts(Part part) {
        assocParts.add(part);
    }

    // This is the function being used to delete parts from the associated parts list
    /**
     * Method to determine if associated part is deleted
     * */
    public boolean deleteAssocPart(Part selectAssocPart) {
        if (assocParts.contains(selectAssocPart)) {
            assocParts.remove(selectAssocPart);
            return true;
        } else
            return false;
    }

    // This will return the whole list of assoc parts
    /**
     * method to return list of associated parts
     * @return assocParts
     * */
    public ObservableList<Part> getAllAssocParts() {
        return assocParts;
    }

    /**
     * returns product ID
     * @return productID
     * */
    public int getProductID() {
        return productID;
    }

    /**
     * sets product ID
     * @param productID
     * */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     * returns product name
     * @return productName
     * */
    public String getProductName() {
        return productName;
    }

    /**
     * sets productname
     * @param productName
     * */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * returns product invetory
     * @return productInv
     * */
    public int getProductInv() {
        return productInv;
    }

    /**
     * sets product inventory
     * @param productInv
     * */
    public void setProductInv(int productInv) {
        this.productInv = productInv;
    }

    /**
     * returns productPrice
     * @return productPrice
     * */
    public float getProductPrice() {
        return productPrice;
    }

    /**
     * sets product price
     * @param productPrice
     * */
    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * return product max
     * @return productMax
     * */
    public int getProductMax() {
        return productMax;
    }

    /**
     * sets product max
     * @param productMax
     * */
    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }

    /**
     * returns product min
     * @return productMin
     * */
    public int getProductMin() {
        return productMin;
    }

    /**
     * @return productMin
     * */
    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }
}
