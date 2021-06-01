package project;
/**
 * This is the controller for the modify part fxml
 * @author Antonio Peza
 * */
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
import project.model.Inventory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    // This is a variable that imports my Part class. It is used to hold the part the user selects
    /**
     * This variable is used to hold the selected part
     * */
    private Part selectedPart;


    // All these variables reference the FXML file that the UI is based off of
    // The radio buttons will change the labels depending on which is selected
    /**
     * These are all the variables used in the modify part controller
     * */
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

    /**
     * This method is being used to initialize all the lists
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = Controller.getModifyPart();

        if (selectedPart instanceof InHouse) {
            inHouseRadio.setSelected(true);
            partIDCompNameTxt.setText(String.valueOf(((InHouse) selectedPart).getPartMachineID()));
        }

        if (selectedPart instanceof OutSourced) {
            outSourcedRadio.setSelected(true);
            changeMe.setText("Company Name: ");
            partIDCompNameTxt.setText(String.valueOf(((OutSourced) selectedPart).getPartCompName()));
        }

        //This will initialize the part that's going to get modified
        partIDTxt.setText(String.valueOf(selectedPart.getId()));
        partNameTxt.setText(selectedPart.getName());
        partInvTxt.setText(String.valueOf(selectedPart.getStock()));
        partPriceTxt.setText(String.valueOf(selectedPart.getPrice()));
        partMaxTxt.setText(String.valueOf(selectedPart.getMax()));
        partMinTxt.setText(String.valueOf(selectedPart.getMin()));
    }

    // This function is the button that will change from the main window to the modify part window
    /**
     * This method is being used to return the user back to the main menu
     * */
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
     * This is the method that will save the part once its been edited
     * */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

        // The application will not crash when inappropriate user data is entered in the forms, error messages generated
        // Using the Integer.parseInt method to get what's in the text box and change it into an int
        try {
            int partID = selectedPart.getId();
            String partName = partNameTxt.getText();
            Float partPrice = Float.parseFloat(partPriceTxt.getText());
            int partInv = Integer.parseInt(partInvTxt.getText());
            int partMin = Integer.parseInt(partMinTxt.getText());
            int partMax = Integer.parseInt(partMaxTxt.getText());
            int partMachineID;
            String partCompName;
            boolean partAddition = false;

            // This is verifying that the min is smaller than the max
            if (minValid(partMin, partMax) && inventoryIsValid(partMin, partMax, partInv)) {
                if (inHouseRadio.isSelected()) {
                    try {
                        partMachineID = Integer.parseInt(partIDCompNameTxt.getText());
                        InHouse partInHouse = new InHouse(partID, partName, partPrice, partInv, partMin, partMax, partMachineID);
                        partInHouse.setId(Inventory.getNewPartID());
                        Inventory.addPart(partInHouse);
                        partAddition = true;
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setContentText("There is an error in the entries, please re-enter the values and try again");
                        Optional<ButtonType> result = alert.showAndWait();
                    }
                }

                if (outSourcedRadio.isSelected()) {
                    partCompName = partIDCompNameTxt.getText();
                    OutSourced partOutSourced = new OutSourced(partID, partName, partPrice, partInv, partMin, partMax, partCompName);
                    partOutSourced.setId(Inventory.getNewPartID());
                    Inventory.addPart(partOutSourced);
                    partAddition = true;
                }

                // After saving, the user is automatically redirected to the main form
                if (partAddition) {
                    // This will make it so that the selected part gets deleted upon update
                    Inventory.deletePart(selectedPart);
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

    // Min should be less than Max; and Inv should be between those two values
    /**
     * This method will be used to determine if the min is smaller than the max
     * @param min
     * @param max
     * @return boolean minValid
     * */
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

    /**
     * This method will send the users back to the main menu after saving
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
            alert.setContentText("Inventory cannot be greater than the max/min allowed");
            Optional<ButtonType> result=alert.showAndWait();
        }
        return isValid;
    }
}