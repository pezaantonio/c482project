package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import project.model.InHouse;
import project.model.Part;
import project.model.invDataProvider;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    // All these variables reference the FXML file that the UI is based off of
    // The radio buttons will change the labels depending on which is selected
    @FXML
    public Label changeMe;

    @FXML
    public RadioButton inHouse;

    @FXML
    public RadioButton outSourced;

    @FXML
    public TextField partIDTxt;

    @FXML
    public TextField partNameTxt;

    @FXML
    public TextField partInvTxt;

    @FXML
    public TextField partPriceTxt;

    @FXML
    public TextField partMaxTxt;

    @FXML
    public TextField partMinTxt;

    @FXML
    public TextField partIDCompNameTxt;

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

    }

    // This function is the button that will change from one window to the next window
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

    }

    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

        // Using the Integer.parseInt method to get what's in the text box and change it into an int
        int partID = Integer.parseInt(partIDTxt.getText());
        String partName = partNameTxt.getText();
        Float partPrice = Float.parseFloat(partPriceTxt.getText());
        int partInv = Integer.parseInt(partInvTxt.getText());
        int partMin = Integer.parseInt(partMinTxt.getText());
        int partMax = Integer.parseInt(partMaxTxt.getText());
        int partMachineID;
        String partCompName;
        boolean partAddition = false;

        if (partName.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Please enter the part name");
            Optional<ButtonType> result=alert.showAndWait();
        } else {
            if (inHouse.isSelected()){
                try {
                    partMachineID = Integer.parseInt(partIDCompNameTxt.getText());
                    InHouse partInHouse = new InHouse(partID, partName, partPrice, partInv, partMin, partMax, partMachineID);
                    partInHouse.setId(invDataProvider.getNewPartID());
                    invDataProvider.addPart(partInHouse);
                    partAddition = true;
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("There is an error in the entries, please re-enter the values and try again");
                    Optional<ButtonType> result=alert.showAndWait();
                }
            }

        }
    }
}
