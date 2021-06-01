package project;
/**
 * This is the class to hold the AddProduct Controller
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
import project.model.Product;
import project.model.Inventory;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    // The observable list for the parts associated with the product
    /**
     * This returns a list of associated parts
     * @return assocParts
     * */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    /**
     * This is the list of all the variables being used in the controller
     * */
    @FXML
    public Label changeMe;

    @FXML
    public RadioButton inHouseRadio;

    @FXML
    public RadioButton outSourcedRadio;

    // Parts table and columns

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
    private TextField prodIDCompNameTxt;

    @FXML
    private TextField prodMinTxt;

    @FXML
    private TextField partSearchTxt;

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

    // Associated parts table and columns
    @FXML
    private TableView<Part> assocPartTable;

    @FXML
    private TableColumn<Part, Integer> assocPartIDCol;

    @FXML
    private TableColumn<Part, String> assocPartNameCol;

    @FXML
    private TableColumn<Part, Integer> assocPartInvCol;

    @FXML
    private TableColumn<Part, Float> assocPartPriceCol;

    // Initializes the two tables associated with products, the parts and associated parts
    /**
     * This is initializing method that will load up the lists for the product
     *
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableView.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory((new PropertyValueFactory<>("stock")));
        partPriceCol.setCellValueFactory((new PropertyValueFactory<>("price")));

        assocPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    // This function is the button that will change from the main window to the modify part window
    /**
     * This will return the user to the main menu
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

    @FXML
    /**
     * This method will add everything when the fields are filled out and the add button is selected
     * */
    void onActionAddPart(ActionEvent event) {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Product Addition Warning");
            alert.setHeaderText("No product selected");
            alert.showAndWait();
        } else {
            assocParts.add(selectedPart);
            assocPartTable.setItems(assocParts);
        }

    }

    @FXML
    void onActionRemoveAssocPart(ActionEvent event) {
        Part selectedPart = assocPartTable.getSelectionModel().getSelectedItem();

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
                assocPartTable.setItems(assocParts);
            }


        }
    }

    @FXML
    /**
     * This method will save everything that was input in the fields
     * */
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
                alert.setContentText("Please enter the product name");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                if (minValid(prodMin, prodMax) && inventoryIsValid(prodMin, prodMax, prodInv)){
                    Product newProduct = new Product(prodIDTxt, prodName, prodInv, prodPrice, prodMin, prodMax);

                    for (Part part : assocParts) {
                        newProduct.addAssocParts(part);
                    }

                    newProduct.setProductID(Inventory.getNewProductID());
                    Inventory.addProduct(newProduct);
                    saveRedirect(event);
                }

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("There is an error in one or more entries. Please check your entries and try again");
            Optional<ButtonType> result = alert.showAndWait();
        }

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
            alert.setContentText("Unable to find the part");
            Optional<ButtonType> result = alert.showAndWait();

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

    // Min should be less than Max; and Inv should be between those two values
    private boolean minValid(int min, int max) {

        boolean partMinValid = true;

        // if min is less than or equal to 0 OR min is greater than max, fail valid check
        if (min <= 0 || min >= max) {
            partMinValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("The maximum must be larger than the minimum");
            Optional<ButtonType> result = alert.showAndWait();
        }
        return partMinValid;
    }

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
}

