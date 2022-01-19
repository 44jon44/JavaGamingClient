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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class PurchaseFormController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
