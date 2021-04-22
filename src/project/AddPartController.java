package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    // These are my variables that change the last box depending on the Radio button
    public Label changeMe;
    public RadioButton inHouse;
    public RadioButton outSourced;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // This function is the button that will change from one window to the next window
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Window");
        stage.setScene(scene);
        stage.show();
    }

    // These two functions will change the the last box in the add part depending on which radio button is selected
    public void inHouse (ActionEvent actionEvent) {
        changeMe.setText("Machine ID: ");
    }

    public void outSourced (ActionEvent actionEvent) {
        changeMe.setText("Company Name: ");
    }
}
