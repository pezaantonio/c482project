package project;
/**
 * This is the main controller, controlling the main menu
 * @author Antonio Peza
 *
 * */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.model.Part;
import project.model.Inventory;
import project.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //This is the part that is selected to be modified
    /**
     * This is the variable that will hold the selected part
     * */
    private static Part modifyPart;

    /**
     * This will be the variable that will hold the selected product to modify
     * */
    private static Product modifyProduct;

    /**
     * These are the variables used in the main controller
     * */
    @FXML
    private Label theLabel;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIDCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, Float> productPriceCol;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Float> partPriceCol;

    @FXML
    private TextField searchTxtBox;

    @FXML
    private TextField searchProdTxt;

    /**
     * This method fills the lists when parts and products are added
     * */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableView.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory((new PropertyValueFactory<>("stock")));
        partPriceCol.setCellValueFactory((new PropertyValueFactory<>("price")));

        productTableView.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productInvCol.setCellValueFactory((new PropertyValueFactory<>("productInv")));
        productPriceCol.setCellValueFactory((new PropertyValueFactory<>("productPrice")));

    }

    // This function will direct users from the main window to the add part window
    /**
     * this is the method that will send the user to the add part window
     * */
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/AddPart.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This will return a modified part
     * @return modifyPart
     * */
    public static Part getModifyPart() {
        return modifyPart;
    }

    /**
     * This will return the product to modify
     * @return modifyProduct
     * */
    public static Product getModifyProduct() {
        return modifyProduct;
    }

    /**
     * This will send the user to the modify part window when a part is selected
     *
     * Runtime Error:
     * I experienced a runtime error here. The error was that, when I selected a product
     * it would not send me to the modify part window and would give me "nullpointerexception".
     * There error was in the assocPartTableView portion of the FXML. I forgot to name the table
     * so the application didn't know what to initialize, therefore it would not go to the page
     * */
    // This function is the button that will change from one window to the next window
    public void toModifyPart(ActionEvent actionEvent) throws IOException {

        modifyPart = partTableView.getSelectionModel().getSelectedItem();

        if(modifyPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a part");
            Optional<ButtonType> result=alert.showAndWait();
        } else {

            Parent root = FXMLLoader.load(getClass().getResource("view/ModifyPart.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * This will send a user to the add product window
     * */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/AddProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This will send a user to the modify product window
     * */
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {

        modifyProduct = productTableView.getSelectionModel().getSelectedItem();

        if(modifyProduct == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a product");
            Optional<ButtonType> result=alert.showAndWait();
        } else {

            Parent root = FXMLLoader.load(getClass().getResource("view/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This method will delete the part that is selected
     * */
    @FXML
    void deletePart(ActionEvent event) {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a part");
            Optional<ButtonType> result=alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to delete selected part?");
            Optional<ButtonType> result=alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }

    /**
     * This method will be used to delete a product from the list on the main menu
     * */
    @FXML
    void deleteProduct(ActionEvent event) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if(selectedProduct == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a product");
            Optional<ButtonType> result=alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to delete selected product?");
            Optional<ButtonType> result=alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
            }
        }
    }

    // This is the exit button
    public void exitApplication(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void searchForPart(ActionEvent actionEvent) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchPartString = searchTxtBox.getText();

        for (Part part : allParts){
            if (String.valueOf(part.getId()).contains(searchPartString) || part.getName().contains(searchPartString)) {
            partsFound.add(part);
            }
        }
        partTableView.setItems(partsFound);
        if (partsFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Part not found");
            Optional<ButtonType> result=alert.showAndWait();
            partTableView.setItems(allParts);
    }
    }

    public void searchForProd(ActionEvent actionEvent) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchProdString = searchProdTxt.getText();

        for (Product product : allProducts){
            if (String.valueOf(product.getProductID()).contains(searchProdString) || product.getProductName().contains(searchProdString)){
            productsFound.add(product);
            }
        }
        productTableView.setItems(productsFound);
        if (productsFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Product not found");
            Optional<ButtonType> result=alert.showAndWait();
            productTableView.setItems(allProducts);
        }
    }
}
