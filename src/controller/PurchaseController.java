/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.PurchaseManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import transferObjects.Client;
import transferObjects.Game;
import transferObjects.Purchase;

/**
 * FXML Controller class
 *
 * @author Alex Hurtado
 */
public class PurchaseController{

    private static final Logger LOG = Logger.getLogger(PurchaseController.class.getName());
    
    private PurchaseManager purchasesManager;
    
    private ObservableList<Purchase> purchaseObservableList;
    @FXML
    private Pane purchasePane;
    @FXML
    private TableView tvPurchases;
    @FXML
    private TableColumn tcPurchaseDate;
    @FXML
    private TableColumn<Client, String> tcClientName;
    @FXML
    private TableColumn<Game, String> tcGameName;
    @FXML
    private TableColumn<Game, String> tcGameGenre;
    @FXML
    private TableColumn<Game, Integer> tcGamePegi;
    @FXML
    private TableColumn<Game, Integer> tcGamePrice;
    @FXML
    private Button btnModifyPurchase;
    @FXML
    private Button btnAddPurchase;
    @FXML
    private Button btnDeletePurchase;
    @FXML
    private Label lblError;
    @FXML
    private ComboBox cbClients;
    @FXML
    private DatePicker dpPurchaseDate;
    @FXML
    private ComboBox cbPrice;
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

    public void initStageOriginal(Parent root) {
        Scene purchaseScene = new Scene(root);
        stage.setScene(purchaseScene);
        btnAddPurchase.setOnAction(this::addPurchase);
        btnModifyPurchase.setDisable(true);
        btnDeletePurchase.setDisable(true);
    }
    
    public void initStage(Parent root) {
        Scene purchaseScene = new Scene(root);
        stage.setScene(purchaseScene);
        stage.setResizable(false);
        stage.initModality(Modality.NONE);
        btnAddPurchase.setOnAction(this::addPurchase);
        btnModifyPurchase.setDisable(true);
        btnDeletePurchase.setDisable(true);
        //TableView
        tcPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
//        tcClientName.setCellValueFactory(new PropertyValueFactory<>("client.name"));
//        tcGameName.setCellValueFactory(new PropertyValueFactory<>("game.name"));
//        tcGameGenre.setCellValueFactory(new PropertyValueFactory<>("game.genre"));
//        tcGamePegi.setCellValueFactory(new PropertyValueFactory<>("game.pegi"));
//        tcGamePrice.setCellValueFactory(new PropertyValueFactory<>("game.price"));
        tcClientName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFullName()));
        tcGameName.setCellValueFactory(new PropertyValueFactory<>("game.name"));
        tcGameGenre.setCellValueFactory(new PropertyValueFactory<>("game.genre"));
        tcGamePegi.setCellValueFactory(new PropertyValueFactory<>("game.pegi"));
        tcGamePrice.setCellValueFactory(new PropertyValueFactory<>("game.price"));
        
        loadPurchaseData();
        stage.showAndWait();
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

    void setPurchaseManager(PurchaseManager puchasesManager) {
        this.purchasesManager = puchasesManager;
    }

    private void loadPurchaseData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
