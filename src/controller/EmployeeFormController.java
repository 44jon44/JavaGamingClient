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
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class EmployeeFormController implements Initializable {

    /**
     * Initializes the controller class.
     */
    /**
     * Create user data button.
     */
    @FXML
    private Button btnAdd;
    /**
     * Modify user data button.
     */
    @FXML
    private Button btnModify;
    @FXML
    private Label lblErrorName;
    @FXML
    private Label lblErrorEmail;
    @FXML
    private Label lblErrorLogin;
    @FXML
    private Label lblErrorHiringDate;
    @FXML
    private Label lblErrorSalary;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void initStageAdd(Parent root) {
        btnModify.setDisable(true);
    }

    public void initStageModify() {
        btnAdd.setDisable(true);
    }

    void initStage(Parent root) {
        lblErrorName.setText("");
        lblErrorEmail.setText("");
        lblErrorLogin.setText("");
        lblErrorHiringDate.setText("");
        lblErrorSalary.setText("");
    }
}
