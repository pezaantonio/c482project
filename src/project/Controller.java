package project;

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
import project.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //This is the part that is selected to be modified
    private static Part modifyPart;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableView.setItems(invDataProvider.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory((new PropertyValueFactory<>("stock")));
        partPriceCol.setCellValueFactory((new PropertyValueFactory<>("price")));

        productTableView.setItems(invDataProvider.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory((new PropertyValueFactory<>("stock")));
        productPriceCol.setCellValueFactory((new PropertyValueFactory<>("price")));

    }

    // This function will direct users from the main window to the add part window
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/AddPart.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    public static Part getModifyPart() {
        return modifyPart;
    }

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

    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/AddProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    public void toModifyProduct(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("view/ModifyProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

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
                invDataProvider.deletePart(selectedPart);
            }
        }
    }

    @FXML
    void deleteProduct(ActionEvent event) {

    }

    // This is the exit button
    public void exitApplication(ActionEvent actionEvent) {
        System.exit(0);
    }

}
