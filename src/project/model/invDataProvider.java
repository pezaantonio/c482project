/*
* @author Antonio Peza
*
* Data Provider for Observable list of inventory for each part/product
*
* */
package project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class invDataProvider {

    //unique partID initializing as 0
    private static int partID = 0;

    public static int getNewPartID(){
        return partID++;
    }

    //unique productID initializing as 0
    private static int productID = 0;

    public static int getNewProductID(){
        return productID++;
    }

    // This is an observable list from the fxcollections class.
    // I'm using the <> operator to specify which class will be in the list
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // these two functions will handle the addPart and getPart
    public static void addPart(Part part){
        allParts.add(part);
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    // Thes two functions will handle the addProduct, getAllProducts
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void addProduct (Product newProduct){
        allProducts.add(newProduct);
    }
}
