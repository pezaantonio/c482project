package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Label theLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onButtonAction(ActionEvent actionEvent) {
        System.out.println("button clicked");
        theLabel.setText("You Clicked");
    }

}
