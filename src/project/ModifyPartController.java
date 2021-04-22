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
import project.model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    // This is a variable that imports my Part class. It is used to hold the part the user selects
    private Part selectedPart;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    public void inHouse(ActionEvent actionEvent) {
        changeMe.setText("Machine ID: ");
    }

    public void outSourced(ActionEvent actionEvent) {
        changeMe.setText("Company Name: ");
    }
}