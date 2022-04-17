/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
public class ModifyProductFormController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML
    private TextField AddProductSearchTxt;

    @FXML
    private TextField ModifyProductIdTxt;

    @FXML
    private TableColumn<Part, Integer> ModifyProductInvCol1;

    @FXML
    private TableColumn<Part, Integer> ModifyProductInvCol2;

    @FXML
    private TextField ModifyProductInvTxt;

    @FXML
    private TextField ModifyProductMaxTxt;

    @FXML
    private TextField ModifyProductMinTxt;

    @FXML
    private TextField ModifyProductNameTxt;

    @FXML
    private TableColumn<Part, Integer> ModifyProductPartIdCol1;

    @FXML
    private TableColumn<Part, Integer> ModifyProductPartIdCol2;

    @FXML
    private TableColumn<Part, String> ModifyProductPartNameCol1;

    @FXML
    private TableColumn<Part, String> ModifyProductPartNameCol2;

    @FXML
    private TableColumn<Part, Double> ModifyProductPriceCol1;

    @FXML
    private TableColumn<Part, Double> ModifyProductPriceCol2;

    @FXML
    private TextField ModifyProductPriceTxt;

    @FXML
    private TableView<Part> ModifyProductTableView1;

    @FXML
    private TableView<Part> ModifyProductTableView2;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    int index;
    Product modifyProduct;

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
     * On action modify add product.
     *
     * @param event the event
     */
    @FXML
    void onActionModifyAddProduct(ActionEvent event) {
        Part selectPart = ModifyProductTableView1.getSelectionModel().getSelectedItem();
        if (selectPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No part was selected. Please select a part before procceeding.");
            alert.showAndWait();
        } else {
            associatedParts.add(selectPart);
            ModifyProductTableView2.setItems(associatedParts);
        }

    }

    /**
     * On action remove associated part.
     *
     * @param event the event
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        Part selectedRemovePart = ModifyProductTableView2.getSelectionModel().getSelectedItem();

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
                ModifyProductTableView2.setItems(associatedParts);
            }
        }
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
            int productID = Integer.parseInt(ModifyProductIdTxt.getText());
            String productName = ModifyProductNameTxt.getText();
            int productInv = Integer.parseInt(ModifyProductInvTxt.getText());
            double productPrice = Double.parseDouble(ModifyProductPriceTxt.getText());
            int productMin = Integer.parseInt(ModifyProductMinTxt.getText());
            int productMax = Integer.parseInt(ModifyProductMaxTxt.getText());

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
                Product modifyProduct = new Product(productID, productName, productPrice, productInv, productMin, productMax);
                for (Part partP : associatedParts) {
                    modifyProduct.addAssociatedPart(partP);
                }
                Inventory.updateProduct(index, modifyProduct);
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
        ModifyProductTableView1.setItems(partReturn);
        AddProductSearchTxt.setText("");
    }

    /**
     * This is for the product retrieval method.
     *
     * @param product
     */
    public void getProduct(Product product) {
        modifyProduct = product;

        ModifyProductIdTxt.setText(String.valueOf(product.getId()));
        ModifyProductNameTxt.setText(product.getName());
        ModifyProductInvTxt.setText(String.valueOf(product.getStock()));
        ModifyProductPriceTxt.setText(String.valueOf(product.getPrice()));
        ModifyProductMinTxt.setText(String.valueOf(product.getMin()));
        ModifyProductMaxTxt.setText(String.valueOf(product.getMax()));
        ModifyProductTableView2.setItems(modifyProduct.getAllAssociatedParts());
    }

    /**
     * Initializes the tables for the product screen and also fills em with the
     * part objects.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //top
        ModifyProductTableView1.setItems(Inventory.getAllParts());
        ModifyProductPartIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductPartNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductInvCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductPriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

        //bottom
        ModifyProductPartIdCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductPartNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductInvCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductPriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
