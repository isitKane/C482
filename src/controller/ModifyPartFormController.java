/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
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
public class ModifyPartFormController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML
    private RadioButton ModifyPartInhouseRBtn;
    
    @FXML
    private RadioButton ModifyPartOutsourceRBtn;
    
    @FXML
    private TextField ModifyPartIDTxt;
    
     @FXML
    private TextField ModifyPartNameTxt;

    @FXML
    private TextField ModifyPartInvTxt;
    
    @FXML
    private TextField ModifyPartPriceCostTxt;

    @FXML
    private TextField ModifyPartMaxTxt;

    @FXML
    private TextField ModifyPartMinTxt;

    @FXML
    private TextField ModifyPartMIDCompTxt;
    
    @FXML
    private Label FinalLabel;

    @FXML
    private ToggleGroup modifypartTG;

    int index;
    Part modifyPart;

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
     * On action save modify part.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionSaveModifyPart(ActionEvent event) throws IOException {
        try {
            int partID = Integer.parseInt(ModifyPartIDTxt.getText());
            String partName = ModifyPartNameTxt.getText();
            int partInv = Integer.parseInt(ModifyPartInvTxt.getText());
            double partPrice = Double.parseDouble(ModifyPartPriceCostTxt.getText());
            int partMin = Integer.parseInt(ModifyPartMinTxt.getText());
            int partMax = Integer.parseInt(ModifyPartMaxTxt.getText());
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
                    if (ModifyPartInhouseRBtn.isSelected()) {
                        machineID = Integer.parseInt(ModifyPartMIDCompTxt.getText());
                        InHouse selectedPart = new InHouse(partID, partName, partPrice, partInv, partMin, partMax, machineID);
                        Inventory.updatePart(index, selectedPart);
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Only integers are allowed and the field has to be filled.");
                    alert.showAndWait();
                }
                if (ModifyPartOutsourceRBtn.isSelected()) {
                    companyName = ModifyPartMIDCompTxt.getText();
                    Outsourced selectedPart = new Outsourced(partID, partName, partPrice, partInv, partMin, partMax, companyName);
                    Inventory.updatePart(index, selectedPart);
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
     * This for the part retreival method.
     *
     * @param part
     */
    public void getPart(Part part) {
        modifyPart = part;

        if (part instanceof InHouse) {
            FinalLabel.setText("Machine ID");
            InHouse inhouseSelect = (InHouse)part;
            ModifyPartIDTxt.setText(String.valueOf(inhouseSelect.getId()));
            ModifyPartNameTxt.setText(inhouseSelect.getName());
            ModifyPartInvTxt.setText(String.valueOf(inhouseSelect.getStock()));
            ModifyPartPriceCostTxt.setText(String.valueOf(inhouseSelect.getPrice()));
            ModifyPartMinTxt.setText(String.valueOf(inhouseSelect.getMin()));
            ModifyPartMaxTxt.setText(String.valueOf(inhouseSelect.getMax()));
            ModifyPartMIDCompTxt.setText(String.valueOf(inhouseSelect.getMachineID()));
            ModifyPartInhouseRBtn.setSelected(true);
            
        }
        if (part instanceof Outsourced) {
            FinalLabel.setText("Company Name");
            Outsourced outsourcedSelect = (Outsourced)part;
            ModifyPartIDTxt.setText(String.valueOf(outsourcedSelect.getId()));
            ModifyPartNameTxt.setText(outsourcedSelect.getName());
            ModifyPartInvTxt.setText(String.valueOf(outsourcedSelect.getStock()));
            ModifyPartPriceCostTxt.setText(String.valueOf(outsourcedSelect.getPrice()));
            ModifyPartMinTxt.setText(String.valueOf(outsourcedSelect.getMin()));
            ModifyPartMaxTxt.setText(String.valueOf(outsourcedSelect.getMax()));
            ModifyPartMIDCompTxt.setText(String.valueOf(outsourcedSelect.getCompanyName()));
            ModifyPartOutsourceRBtn.setSelected(true);
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
