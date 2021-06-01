package project;
/**
 * This is the controller that controls the modify product fxml
 * @author Antonio Peza
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
import project.model.Product;
import project.model.Inventory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    /**
     * This is the variable to store the product that is selected
     * */
    Product selectedProduct;

    /**
     * these are all the variables used in this controller
     * */
    @FXML
    public Label changeMe;

    @FXML
    public RadioButton inHouseRadio;

    @FXML
    public RadioButton outSourcedRadio;

    /**
     * This returns the list of associated parts
     * @return associatedParts
     * */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    // Associated parts table and columns
    @FXML
    private TableView<Part> assocPartTableView;

    @FXML
    private TableColumn<Part, Integer> assocPartIDCol;

    @FXML
    private TableColumn<Part, String> assocPartNameCol;

    @FXML
    private TableColumn<Part, Integer> assocPartInvCol;

    @FXML
    private TableColumn<Part, Float> assocPartPriceCol;

    // Parts table and columns
    /**
     * these are all the variables used in this controller
     * */

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
    private TextField prodIDTxt;

    @FXML
    private TextField prodNameTxt;

    @FXML
    private TextField prodInvTxt;

    @FXML
    private TextField prodPriceTxt;

    @FXML
    private TextField prodMaxTxt;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private TextField prodMinTxt;

    /**
     * This method is used to initialize the tables with the information for the selected product
     *
     * Runtime Error:
     * I experienced a runtime error here. The error was that, when I selected a product
     * it would not send me to the modify part window and would give me "nullpointerexception".
     * There error was in the assocPartTableView portion of the FXML. I forgot to name the table
     * so the application didn't know what to initialize, therefore it would not go to the page
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedProduct = Controller.getModifyProduct();

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory((new PropertyValueFactory<>("stock")));
        partPriceCol.setCellValueFactory((new PropertyValueFactory<>("price")));
        partTableView.setItems(Inventory.getAllParts());

        assocPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        assocPartTableView.setItems(selectedProduct.getAllAssocParts());

        prodIDTxt.setText(String.valueOf(selectedProduct.getProductID()));
        prodNameTxt.setText(selectedProduct.getProductName());
        prodInvTxt.setText(String.valueOf(selectedProduct.getProductInv()));
        prodPriceTxt.setText(String.valueOf(selectedProduct.getProductPrice()));
        prodMaxTxt.setText(String.valueOf(selectedProduct.getProductMax()));
        prodMinTxt.setText(String.valueOf(selectedProduct.getProductMin()));


    }

    // This function is the button that will change from the main window to the modify part window
    /**
     * This method will send the user back to the main menu if they cancel
     * */
    public void toMain(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setContentText("Are you sure you want to cancel and return to the main window? Unsaved progress will be deleted");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    // These two functions will change the the last box in the add part depending on which radio button is selected
    /**
     * These are the methods that will change the text depending on which radio button is being used
     * */
    public void inHouse(ActionEvent actionEvent) {
        changeMe.setText("Machine ID: ");
    }

    /**
     * These are the methods that will change the text depending on which radio button is being used
     * */
    public void outSourced(ActionEvent actionEvent) {
        changeMe.setText("Company Name: ");
    }


    /**
     * This is the method that will add a part to the list
     * */
    @FXML
    void onActionAddPart(ActionEvent event) {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Product Addition Warning");
            alert.setHeaderText("No part selected");
            alert.showAndWait();
        } else {
            assocParts.add(selectedPart);
            assocPartTableView.setItems(assocParts);
        }

    }

    /**
     * This method will remove a selected part from the associated parts list
     * */
    @FXML
    void onActionRemoveAssocPart(ActionEvent event) {
        Part selectedPart = assocPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Product Removal Warning");
            alert.setHeaderText("No product selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                assocParts.remove((selectedPart));
                assocPartTableView.setItems(assocParts);
            }


        }
    }

    /**
     * This method will save all changes once they are made
     * */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            int prodIDTxt = 0;
            String prodName = prodNameTxt.getText();
            Float prodPrice = Float.parseFloat(prodPriceTxt.getText());
            int prodInv = Integer.parseInt(prodInvTxt.getText());
            int prodMin = Integer.parseInt(prodMinTxt.getText());
            int prodMax = Integer.parseInt(prodMaxTxt.getText());

            if (prodName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("Please enter the part name");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                if (minValid(prodMin, prodMax) && inventoryIsValid(prodMin, prodMax, prodInv)) {
                    Product newProduct = new Product(prodIDTxt, prodName, prodInv, prodPrice, prodMin, prodMax);

                    for (Part part : assocParts) {
                        newProduct.addAssocParts(part);
                    }

                    newProduct.setProductID(Inventory.getNewProductID());
                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(selectedProduct);
                    saveRedirect(event);
                }

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("There is an error in the entries, please re-enter the values and try again");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * This method is being used to confirm that the min is lower than the max
     * @param min
     * @param max
     * */
    private boolean minValid(int min, int max) {

        boolean partMinValid = true;

        // if min is less than or equal to 0 OR min is greater than max, fail valid check
        if (min <= 0 || min >= max) {
            partMinValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Minimum cannot be larger than the max");
            Optional<ButtonType> result = alert.showAndWait();
        }
        return partMinValid;
    }

    /**
     * This method is being used to send the users back to the main menu after a successful add
     * */
    public void saveRedirect(ActionEvent actionEvent) throws IOException{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Main Screen");
        alert.setContentText("Save Successful, returning to main menu");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     * This will check to make sure that the inventory is between the min and the max
     * @param min
     * @param max
     * @param stock
     * @return boolean
     *
     * */
    private boolean inventoryIsValid (int min, int max, int stock){
        boolean isValid = true;

        if (stock < min || stock > max){
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Inventory is not valid, please enter a valid inventory");
            Optional<ButtonType> result=alert.showAndWait();
        }
        return isValid;
    }
    /**
     * This will search parts from the add product window
     *
     *
     *
     * */
    public void onSearchAction(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearchTxt.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) || part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }

        partTableView.setItems(partsFound);

        if (partsFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Unable to find the part id");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }
}
