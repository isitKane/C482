/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author Kane
 */
public class AddPartFormController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML
    private RadioButton AddPartInHouseRBtn;

    @FXML
    private RadioButton AddPartOutsourceRBtn;

    @FXML
    private TextField AddPartIDTxt;

    @FXML
    private TextField AddPartNameTxt;

    @FXML
    private TextField AddPartInvTxt;

    @FXML
    private TextField AddPartMIDCompTxt;

    @FXML
    private TextField AddPartMaxTxt;

    @FXML
    private TextField AddPartMinTxt;

    @FXML
    private TextField AddPartPriceCostTxt;

    @FXML
    private Label FinalLabel;

    @FXML
    private ToggleGroup addpartTG;
    
    //for the random id number.
    int idNum;

    /**
     * On action display main form.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionDisplayMainForm(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * On action in house.
     *
     * @param event the event
     */
    @FXML
    void onActionInHouse(ActionEvent event) {
        FinalLabel.setText("Machine ID");
    }

    /**
     * On action outsourced.
     *
     * @param event the event
     */
    @FXML
    void onActionOutsourced(ActionEvent event) {
        FinalLabel.setText("Company Name");
    }

    /**
     * On action save part.
     * Logical or Runtime Error: The error that was occurring was the alerts were not popping up properly for when the min was greater than the max, when the inventory was not in between the min and mix, and for when the form was empty. I managed to solve the issue by re-reading my code and realizing that I did not have the alert.showAndWait(); on the alert sections of the code. I added it and the alert boxes showed up and the issue was fixed.
     * @param event the event
     * @throws NumberFormatException the number format exception
     * @throws NullPointerException  the null pointer exception
     * @throws IOException           the io exception
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        try {
            int partID = Integer.parseInt(AddPartIDTxt.getText());
            String partName = AddPartNameTxt.getText();
            int partInv = Integer.parseInt(AddPartInvTxt.getText());
            double partPrice = Double.parseDouble(AddPartPriceCostTxt.getText());
            int partMin = Integer.parseInt(AddPartMinTxt.getText());
            int partMax = Integer.parseInt(AddPartMaxTxt.getText());
            int machineID;
            String companyName;

            if (partName.isEmpty()) {
                Alert empty = new Alert(Alert.AlertType.ERROR);
                empty.setTitle("ERROR");
                empty.setContentText("Please fill out the name.");
                empty.showAndWait();
            } else if (partMin > partMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("The minimum value must have a value less than the max.");
                alert.showAndWait();
            } else if (partInv < partMin || partInv > partMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("The inventory must be between the minimum and maximum value.");
                alert.showAndWait();
            } else {
                try {
                    if (AddPartInHouseRBtn.isSelected()) {
                        machineID = Integer.parseInt(AddPartMIDCompTxt.getText());
                        InHouse newInHouse = new InHouse(partID, partName, partPrice, partInv, partMin, partMax, machineID);
                        Inventory.addPart(newInHouse);
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Only integers are allowed and the field has to be filled.");
                    alert.showAndWait();
                } if (AddPartOutsourceRBtn.isSelected()) {
                    companyName = AddPartMIDCompTxt.getText();
                    Outsourced newOutsourced = new Outsourced(partID, partName, partPrice, partInv, partMin, partMax, companyName);
                    Inventory.addPart(newOutsourced);
                }
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please check the form");
            alert.showAndWait();
        }
    }

    /**
     * Part id number.
     * @return 
     */
    public int partIDNumber() {
        Random uniqueID = new Random();
        int upperbound = 9999;
        int randomID = uniqueID.nextInt(upperbound);

        for (Part parts : Inventory.getAllParts()) {
            if (parts.getId() == randomID) {
                partIDNumber();
            }
        } 
        return randomID;
    }

    /**
     * Initializes the randomize id number.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        idNum = partIDNumber();
        AddPartIDTxt.setText(Integer.toString(idNum));
    }

}
