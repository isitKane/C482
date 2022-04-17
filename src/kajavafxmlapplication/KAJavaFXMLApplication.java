/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kajavafxmlapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Inventory Management System for C482 - Software I
 * Future Enhancement: To extend functionality to the next version if I were to update the application, I would add the feature to be able to track which items are popular and which aren't. Also, adding a way to track pricing would beneficial as it would allow to know the best prices.
 * Error: Location is at onActionSavePart in the AddPartFormController.
 * JavaDoc location can be found in the src folder.
 * @author Kane
 */
public class KAJavaFXMLApplication extends Application {
    /**
     * This is the start method and it loads the main screen.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 1000, 400));
        stage.show();
    }

    /**
     * The entry point of application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

}

