package project.model;
/**
 * @author Antonio Peza
 *
 * Data Provider for Observable list of inventory for each part/product
 *
 * */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.model.Product;

public class Inventory {

    /**
     * This is the variable to start the partID at 0
     * */
    private static int partID = 0;

    /**
     *
     * This returns the new partID +1
     * @return partID +1
     */
    public static int getNewPartID(){
        return partID++;
    }

    /**
     * This is the variable to start the productID at 0
     * */
    private static int productID = 0;

    /**
     *
     * This returns the new productID +1
     * @return productID +1
     */
    public static int getNewProductID(){
        return productID++;
    }

    // This is an observable list from the fxcollections class.
    // I'm using the <> operator to specify which class will be in the list
    /**
     *
     * This is the list for all parts
     * @return a list of allParts
     * */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     *
     * This is the list for all products
     * @return a list of allProducts
     * */
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // these two functions will handle the addPart and getPart
    /**
     *
     * method is being used to add a part to the allparts list
     * @param part
     * */
    public static void addPart(Part part){
        allParts.add(part);
    }

    /**
     *
     * method is being used to return all parts
     * @return allParts
     * */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    // Delete part
    /**
     *
     * boolean method being used to delete parts
     * @return true if part is removed
     * @return false if part is not removed
     * */
    public static boolean deletePart(Part selectedPart){
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }


    // Thes two functions will handle the addProduct, getAllProducts
    /**
     *
     * Method is being used to return all products
     * @return allProducts
     * */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     *
     * Method is being used to add product
     * @param newProduct
     * */
    public static void addProduct (Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Searches the list by part ID
     * @param partID
     * @return part found, if no part return null
     * */
    public static Part lookupPart(int partID) {
        Part partFound = null;

        for (Part part : allParts){
            if(part.getId() == partID){
                partFound = part;
            }
        }

        return partFound;
    }
    /**
     * Searches the list by part Name
     * @param partName
     * @return partFound
     * */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                partsFound.add(part);
            }
        }
        return partsFound;
    }
    /**
     * Searches for product by ID
     * @param productID
     * @return productFound
     * */
    public static Product lookupProduct(int productID) {
        Product productFound = null;

        for (Product product : allProducts){
            if (product.getProductID() == productID) {
                productFound = product;
            }
        }
        return productFound;
    }
    /**
     * Searches for product by Name
     * @param productName
     * @return foundProduct
     * */
    public static ObservableList<Product> lookupProduct (String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getProductName() == productName){
                productsFound.add(product);
            }
        }
        return productsFound;
    }
    /**
     * If a part on the list is updated, it will replace it with this
     * @param index
     * @param selectedPart
     * */
    public static void updatePart (int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    /**
     * If a product on the list is updated it will replace it with this
     * @param index
     * @param selectedProduct
     * */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }
    /**
     * Deletes selected part
     * @param selectedPart
     * @return boolean
     * */
    public static boolean deletePart(Product selectedPart){
        if (allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * Deletes selected product
     * @param selectedProduct
     * @return boolean
     * */
    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }
}

