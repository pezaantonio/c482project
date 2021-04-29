package project;

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
import project.model.invDataProvider;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    // The observable list for the parts associated with the product
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    @FXML
    public Label changeMe;

    @FXML
    public RadioButton inHouseRadio;

    @FXML
    public RadioButton outSourcedRadio;

    // Parts table and columns

    @FXML
    private TextField partIDTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partIDCompNameTxt;

    @FXML
    private TextField partMinTxt;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableView.setItems(invDataProvider.getAllParts());
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
    public void toMain(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setContentText("Are you sure you want to cancel and return to the main window? Unsaved progress will be deleted");
        Optional<ButtonType> result=alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    // These two functions will change the the last box in the add part depending on which radio button is selected
    public void inHouse (ActionEvent actionEvent) {
        changeMe.setText("Machine ID: ");
    }

    public void outSourced (ActionEvent actionEvent) {
        changeMe.setText("Company Name: ");
    }

    @FXML
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
    void onActionSave(ActionEvent event) {

    }
}
