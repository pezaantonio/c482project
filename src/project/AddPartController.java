package project;
/**
 * This is the controller for the AddPart FXML
 *
 * @author Antonio Peza
 *
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

public class AddPartController implements Initializable {

    // All these variables reference the FXML file that the UI is based off of
    // The radio buttons will change the labels depending on which is selected
    /**
     * These are all the variables being used in this controller
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

    /**
     * This method is being used to keep the inhouse radio button selected by default
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        inHouseRadio.setSelected(true);

    }

    // This function is the button that will change from one window to the next window
    /**
     * This method is being used to send the user back to the main menu
     * */
    public void toMain(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Main Screen");
        alert.setContentText("Are you sure you want to return to the main window? Unsaved progress will be deleted");
        Optional<ButtonType> result=alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This method is being used to send the user back to the main menu after saving
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

    // These two functions will change the the last box in the add part depending on which radio button is selected
    /**
     * This first method will change the text depending on which radio button is selected
     * */
    public void inHouse (ActionEvent actionEvent) {
        changeMe.setText("Machine ID: ");
    }

    /**
     * This first method will change the text depending on which radio button is selected
     * */
    public void outSourced (ActionEvent actionEvent) {
        changeMe.setText("Company Name: ");
    }

    @FXML
    void onActionAddPart(ActionEvent event) {

    }

    /**
     * This method will save everything in the fields
     * */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

        // The application will not crash when inappropriate user data is entered in the forms, error messages generated
        // Using the Integer.parseInt method to get what's in the text box and change it into an int
        try {
            int partID = 0;
            String partName = partNameTxt.getText();
            Float partPrice = Float.parseFloat(partPriceTxt.getText());
            int partInv = Integer.parseInt(partInvTxt.getText());
            int partMin = Integer.parseInt(partMinTxt.getText());
            int partMax = Integer.parseInt(partMaxTxt.getText());
            int partMachineID;
            String partCompName;
            boolean partAddition = false;

            if (partName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("Please enter the part name");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                if (minValid(partMin, partMax) && inventoryIsValid(partMin, partMax, partInv))
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
                            alert.setContentText("The Machine ID must be a number");
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
                        saveRedirect(event);
                }

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("There is an error in one or more entries. Please re-enter and try again");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    // Min should be less than Max; and Inv should be between those two values
    /**
     * This is being used to confirm that the minimum is not greater than the maximum
     * @param min
     * @param max
     *
     * */
    private boolean minValid(int min, int max) {

        boolean partMinValid = true;

        // if min is less than or equal to 0 OR min is greater than max, fail valid check
        if (min <= 0 || min >= max){
            partMinValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Maximum must be larger than the minimum");
            Optional<ButtonType> result=alert.showAndWait();
        }
        return partMinValid;
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
}
