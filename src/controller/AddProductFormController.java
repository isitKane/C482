/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Kane
 */
public class AddProductFormController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML
    private TextField AddProductIdTxt;

    @FXML
    private TableColumn<Part, Integer> AddProductInvCol1;

    @FXML
    private TableColumn<Part, Integer> AddProductInvCol2;

    @FXML
    private TextField AddProductInvTxt;

    @FXML
    private TextField AddProductMaxTxt;

    @FXML
    private TextField AddProductMinTxt;

    @FXML
    private TextField AddProductNameTxt;

    @FXML
    private TableColumn<Part, Integer> AddProductPartIdCol1;

    @FXML
    private TableColumn<Part, Integer> AddProductPartIdCol2;

    @FXML
    private TableColumn<Part, String> AddProductPartNameCol1;

    @FXML
    private TableColumn<Part, String> AddProductPartNameCol2;

    @FXML
    private TableColumn<Part, Double> AddProductPriceCol1;

    @FXML
    private TableColumn<Part, Double> AddProductPriceCol2;

    @FXML
    private TextField AddProductPriceTxt;

    @FXML
    private TextField AddProductSearchTxt;

    @FXML
    private TableView<Part> AddProductTableView1;

    @FXML
    private TableView<Part> AddProductTableView2;

    //for the random id number.
    int idNum;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * On action add part.
     *
     * @param event the event
     */
    @FXML
    void onActionAddPart(ActionEvent event) {
        Part selectPart = AddProductTableView1.getSelectionModel().getSelectedItem();

        if (selectPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No part was selected. Please select a part before procceeding.");
            alert.showAndWait();
        } else {
            associatedParts.add(selectPart);
            AddProductTableView2.setItems(associatedParts);
        }

    }

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
     * On action remove associated part.
     *
     * @param event the event
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        Part selectedRemovePart = AddProductTableView2.getSelectionModel().getSelectedItem();

        if (selectedRemovePart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No part was selected. Please select a part before procceeding");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ALERT");
            alert.setContentText("Would you like to remove selected part?");
            Optional<ButtonType> confirmation = alert.showAndWait();

            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                associatedParts.remove(selectedRemovePart);
                AddProductTableView2.setItems(associatedParts);
            }
        }
    }

    /**
     * On action save product.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        try {
            int productID = Integer.parseInt(AddProductIdTxt.getText());
            String productName = AddProductNameTxt.getText();
            int productInv = Integer.parseInt(AddProductInvTxt.getText());
            double productPrice = Double.parseDouble(AddProductPriceTxt.getText());
            int productMin = Integer.parseInt(AddProductMinTxt.getText());
            int productMax = Integer.parseInt(AddProductMaxTxt.getText());

            if (productName.isEmpty()) {
                Alert empty = new Alert(Alert.AlertType.ERROR);
                empty.setTitle("ERROR");
                empty.setContentText("Please fill out the name.");
                empty.showAndWait();
            } else if (productMin > productMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("The minimum value must have a value less than the max.");
                alert.showAndWait();
            } else if (productInv < productMin || productInv > productMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("The inventory must be between the minimum and maximum value.");
                alert.showAndWait();
            } else {
                Product newProduct = new Product(productID, productName, productPrice, productInv, productMin, productMax);
                for (Part partP : associatedParts) {
                    newProduct.addAssociatedPart(partP);
                }
                Inventory.addProduct(newProduct);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please check the form");
            alert.showAndWait();

        }

    }

    /**
     * On action search part.
     *
     * @param event the event
     */
    @FXML
    void onActionSearchPart(ActionEvent event) {
        String searchText = AddProductSearchTxt.getText();
        ObservableList<Part> partReturn = Inventory.lookUpPart(searchText);

        if (partReturn.isEmpty()) {
            try {
                int idNumber = Integer.parseInt(searchText);
                Part partName = Inventory.lookupPart(idNumber);
                if (partName != null) {
                    partReturn.add(partName);
                }
            } catch (NumberFormatException e) {
                //Ignore
            }
        }
        if (partReturn.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR: No Product Found");
            alert.setContentText("No matching product was found.");
            alert.showAndWait();

        }
        AddProductTableView1.setItems(partReturn);
        AddProductSearchTxt.setText("");

    }

    /**
     * Product id number.
     *
     * @return
     */
    public int productIDNumber() {
        Random uniqueID = new Random();
        int upperbound = 9999;
        int randomID = uniqueID.nextInt(upperbound);

        for (Product products : Inventory.getAllProducts()) {
            if (products.getId() == randomID) {
                productIDNumber();
            }
        }
        return randomID;
    }

    /**
     * Initializes the tables for the product screen
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        AddProductTableView1.setItems(Inventory.getAllParts());
        AddProductPartIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProductPartNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductInvCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductPriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

        AddProductPartIdCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProductPartNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductInvCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductPriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));

        idNum = productIDNumber();
        AddProductIdTxt.setText(Integer.toString(idNum));
    }

}
