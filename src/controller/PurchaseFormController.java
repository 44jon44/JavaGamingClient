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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class PurchaseFormController{

    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private Hyperlink hlBack;
    @FXML
    private DatePicker dpPurchaseDate;
    @FXML
    private TextField tfGamePrice;
    @FXML
    private ComboBox<?> cbGameName;
    @FXML
    private ComboBox<?> cbGameGenre;
    @FXML
    private ComboBox<?> cbGamePegi;
    @FXML
    private ComboBox<?> cbClient;
    @FXML
    private Label lblErrorClient;
    @FXML
    private Label lblErrorGame;
    
    private Stage stage;
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    public void initStage(Parent root) {
        Scene purchaseFormScene = new Scene(root);
        stage.setScene(purchaseFormScene);
    }
}
