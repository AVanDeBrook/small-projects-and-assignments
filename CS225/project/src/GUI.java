// Class: GUI
// Author: Aaron Van De Brook
// Last Modified: 11/15/2018
//
// Description: Class to set up and run GUI
//
//
// Methods: start(Stage primaryStage): void
//          main(String[] args): void
//
// Attributes:
//

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {

    public void start(Stage primaryStage) {
        // Create pane that will draw GUI elements
        ProjectPane pane = new ProjectPane();
        // Create stage and scene 
        Stage stage = new Stage();
        Scene scene = new Scene(pane, 600, 600);

        // Set window title, set window scene and show the stage
        stage.setTitle("Final Project -- Elevator Study -- Aaron Van De Brook");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}