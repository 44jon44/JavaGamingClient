/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alex Hurtado
 */
public class PurchaseController{

    private static final Logger LOG = Logger.getLogger(PurchaseController.class.getName());
    
    @FXML
    private Pane purchasePane;
    @FXML
    private TableView<?> tvPurchases;
    @FXML
    private TableColumn<?, ?> tcPurchaseDate;
    @FXML
    private TableColumn<?, ?> tcClientName;
    @FXML
    private TableColumn<?, ?> tcGameName;
    @FXML
    private TableColumn<?, ?> tcGameGenre;
    @FXML
    private TableColumn<?, ?> tcGamePegi;
    @FXML
    private TableColumn<?, ?> tbGamePrice;
    @FXML
    private Button btnModifyPurchase;
    @FXML
    private Button btnAddPurchase;
    @FXML
    private Button btnDeletePurchase;
    @FXML
    private Label lblError;
    @FXML
    private ComboBox<?> cbClients;
    @FXML
    private DatePicker dpPurchaseDate;
    @FXML
    private ComboBox<?> cbPrice;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnClearSeach;
    @FXML
    private HBox hbMenuAdm;
    
    private Stage stage;
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene purchaseScene = new Scene(root);
        stage.setScene(purchaseScene);
        btnAddPurchase.setOnAction(this::addPurchase);
        btnModifyPurchase.setDisable(true);
        btnDeletePurchase.setDisable(true);
    }
    
    private void addPurchase(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader purchaseForm = new FXMLLoader(getClass().getResource("/view/purchaseForm.fxml"));
            Parent root = (Parent) purchaseForm.load();
            //controlador de la ventana
            PurchaseFormController controller = purchaseForm.getController();
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.INFO,"Ha saltado este error");
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
