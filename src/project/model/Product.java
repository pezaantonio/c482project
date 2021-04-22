/*
 * This Java Class is for the class of the products.
 *
 * @author Antonio Peza
 * */


package project.model;

public class Product {

    // These are the variables for the Product class
    private boolean productHouse;
    private int productID;
    private String productName;
    private int productInv;
    private float productPrice;
    private int productMax;
    private int productMin;

    // This is the constructor for the Product class
    public Product(boolean productHouse, int productID, String productName, int productInv, float productPrice, int productMax, int productMin) {
        this.productHouse = productHouse;
        this.productID = productID;
        this.productName = productName;
        this.productInv = productInv;
        this.productPrice = productPrice;
        this.productMax = productMax;
        this.productMin = productMin;
    }

    public boolean isProductHouse() {
        return productHouse;
    }

    public void setProductHouse(boolean productHouse) {
        this.productHouse = productHouse;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductInv() {
        return productInv;
    }

    public void setProductInv(int productInv) {
        this.productInv = productInv;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductMax() {
        return productMax;
    }

    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }

    public int getProductMin() {
        return productMin;
    }

    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }
}
