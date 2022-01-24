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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ibai Arriola
 */
public class EmployeeFormController{

    @FXML
    private Pane employeeFormPane;
    @FXML
    private Label lblName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblLogin;
    @FXML
    private Label lblhiringDate;
    @FXML
    private Label lblSalary;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfSalary;
    @FXML
    private DatePicker dpHiringDate;
    @FXML
    private Hyperlink hpReturn;
    @FXML
    private HBox hbMenuAdm;
    private Stage stage;

    void setStage(Stage stage) {
       this.stage = stage;
    }

    void initStage(Parent root) {
        Scene gameFormScene = new Scene(root);
        stage.setScene(gameFormScene);
        stage.show();
        tfName.setDisable(false);
        hbMenuAdm.setDisable(false);
    } 
}
