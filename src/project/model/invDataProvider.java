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

    // This is an observable list from the fxcollections class.
    // I'm using the <> operator to specify which class will be in the list
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    // these two functions will handle the addPart and getPart
    public static void addPart(Part part){
        allParts.add(part);
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
}
