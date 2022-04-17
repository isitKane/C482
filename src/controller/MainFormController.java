/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
public class MainFormController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML
    private TableView<Part> PartTableView;

    @FXML
    private TableColumn<Part, Integer> PartIDCol;

    @FXML
    private TableColumn<Part, String> PartNameCol;

    @FXML
    private TableColumn<Part, Integer> PartInvCol;

    @FXML
    private TableColumn<Part, Double> PartPriceCol;

    @FXML
    private TableView<Product> ProductTableView;

    @FXML
    private TableColumn<Product, Integer> ProductIDCol;

    @FXML
    private TableColumn<Product, String> ProductNameCol;

    @FXML
    private TableColumn<Product, Integer> ProductInvCol;

    @FXML
    private TableColumn<Product, Double> ProductPriceCol;

    @FXML
    private TextField PartsSearchTxt;

    @FXML
    private TextField ProductSearchTxt;

    /**
     * On action delete parts.
     *
     * @param event the event
     */
    @FXML
    void onActionDeleteParts(ActionEvent event) {
        Part partDelete = (Part) PartTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING");
        alert.setHeaderText("WARNING: Deletion");
        alert.setContentText("You are about to proceed with the part deletion.");
        Optional<ButtonType> deleteConfirm = alert.showAndWait();

        if (deleteConfirm.isPresent() && deleteConfirm.get() == ButtonType.OK) {
            Inventory.deletePart(partDelete);
        }
    }

    /**
     * On action delete products.
     *
     * @param event the event
     */
    @FXML
    void onActionDeleteProducts(ActionEvent event) {
        Product productDelete = ProductTableView.getSelectionModel().getSelectedItem();
        
        if (productDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR: No Product Selected");
            alert.setContentText("Product must be selected before procceeding with deletion");
            alert.showAndWait();
            return;
        }
        if (!productDelete.getAllAssociatedParts().isEmpty()) {
            Alert alertAP = new Alert(Alert.AlertType.ERROR);
            alertAP.setTitle("WARNING");
            alertAP.setHeaderText("WARNING: Product Has Associated Part");
            alertAP.setContentText("The product selected has an associated part. Please remove the associated part before deletion.");
            alertAP.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WARNING");
            alert.setHeaderText("WARNING: Deletion");
            alert.setContentText("You are about to proceed with the part deletion.");
            Optional<ButtonType> deleteConfirm = alert.showAndWait();

            if (deleteConfirm.isPresent() && deleteConfirm.get() == ButtonType.OK) {
                Inventory.deleteProduct(productDelete);
                ProductTableView.refresh();
            }
        }
    }
    

    /**
     * On action add part form.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionAddPartForm(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * On action add product form.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionAddProductForm(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * On action exit.
     *
     * @param event the event
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);

    }

    /**
     * On action modify part form.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionModifyPartForm(ActionEvent event) throws IOException {
        if (PartTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No part selected.");
            alert.showAndWait();

        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
            loader.load();
            ModifyPartFormController controller = loader.getController();
            controller.getPart(PartTableView.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * On action modify product form.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionModifyProductForm(ActionEvent event) throws IOException {
        Product productSelected = ProductTableView.getSelectionModel().getSelectedItem();
        if (productSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No product selected.");
            alert.showAndWait();

        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
            loader.load();
            ModifyProductFormController controller = loader.getController();
            controller.getProduct(ProductTableView.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * On action search part.
     *
     * @param event the event
     */
    @FXML
    void onActionSearchPart(ActionEvent event) {
        String searchText = PartsSearchTxt.getText();
        ObservableList<Part> partSearchReturn = Inventory.lookUpPart(searchText);

        if (partSearchReturn.size() == 0) {
            try {
                int partIDNumber = Integer.parseInt(searchText);
                Part partName = Inventory.lookupPart(partIDNumber);
                if (partName != null) {
                    partSearchReturn.add(partName);
                }
            } catch (NumberFormatException e) {
                //Ignore
            }
        }
        if (partSearchReturn.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR: No Part Found");
            alert.setContentText("No matching part was found.");
            alert.showAndWait();

        }
        PartTableView.setItems(partSearchReturn);
        PartsSearchTxt.setText("");
    }

    /**
     * On action search product.
     *
     * @param event the event
     */
    @FXML
    void onActionSearchProduct(ActionEvent event) {
        String searchText = ProductSearchTxt.getText();
        ObservableList<Product> partProductReturn = Inventory.lookupProduct(searchText);

        if (partProductReturn.isEmpty()) {
            try {
                int productIDNumber = Integer.parseInt(searchText);
                Product productName = Inventory.lookupProduct(productIDNumber);
                if (productName != null) {
                    partProductReturn.add(productName);
                }
            } catch (NumberFormatException e) {
                //Ignore
            }
        }
        if (partProductReturn.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR: No Product Found");
            alert.setContentText("No matching product was found.");
            alert.showAndWait();

        }
        ProductTableView.setItems(partProductReturn);
        ProductSearchTxt.setText("");

    }

    /**
     * Initializes the tables.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        PartTableView.setItems(Inventory.getAllParts());
        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductTableView.setItems(Inventory.getAllProducts());
        ProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

}
