/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class PurchaseController{

    @FXML
    private Pane clientPane;
    @FXML
    private TableView<?> tvPurchase;
    @FXML
    private Label lblError;
    @FXML
    private ComboBox<?> cbClients;
    @FXML
    private DatePicker dpPurchaseDate;
    @FXML
    private ComboBox<?> cbPrice;
 
    private Stage stage;
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene purchaseScene = new Scene(root);
        stage.setScene(purchaseScene);
    }
    
    
}
