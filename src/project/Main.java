package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main class that will run the application
 * @author Antonio Peza
 *
 * Future Enhancement: I would like to add the UX of this application's front end. To make it more user friendly, give one person the
 *                      ability to add parts manually, and have the other users select these parts from a drop down menu. This would
 *                         also require a login/logout funciton to retain that information
 * */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
