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
import project.model.InHouse;
import project.model.OutSourced;
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
    public RadioButton inHouseRadio;

    @FXML
    public RadioButton outSourcedRadio;

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
        selectedPart = Controller.modifyThisPart();

        if (selectedPart instanceof InHouse) {
            inHouseRadio.setSelected(true);
            partIDCompNameTxt.setText(String.valueOf(((InHouse) selectedPart).getPartMachineID()));
        }

        if (selectedPart instanceof OutSourced) {
            outSourcedRadio.setSelected(true);
            partIDCompNameTxt.setText(String.valueOf(((OutSourced) selectedPart).getPartCompName()));
        }

        //This will initialize the part that's going to get modified
        partIDTxt.setText(String.valueOf(selectedPart.getId()));
        partNameTxt.setText(selectedPart.getName());
        partInvTxt.setText(String.valueOf(selectedPart.getStock()));
        partMaxTxt.setText(String.valueOf(selectedPart.getMax()));
        partMinTxt.setText(String.valueOf(selectedPart.getMin()));
    }

    // This function is the button that will change from the main window to the modify part window
    public void toMain(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setContentText("Are you sure you want to return to the main window? Unsaved progress will be deleted");
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
    // Min should be less than Max; and Inv should be between those two values
    private boolean minValid(int min, int max) {

        boolean partMinValid = true;

        // if min is less than or equal to 0 OR min is greater than max, fail valid check
        if (min <= 0 || min >= max){
            partMinValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Minimum cannot be larger than the max");
            Optional<ButtonType> result=alert.showAndWait();
        }
        return partMinValid;
}
}