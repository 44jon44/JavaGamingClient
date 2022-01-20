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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class EmployeeController implements Initializable {
    private Stage employeeStage;
    /**
     * Create user data button.
     */
    @FXML
    private Button btCrear;
    /**
     * Modify user data button.
     */
    @FXML
    private Button btModificar;
    /**
     * Delete user data button.
     */
    @FXML
    private Button btEliminar;

    /**
     * Quit application button.
     * @return 
     */
    public Stage employeeStage() {
        return employeeStage;
    }

    //setter de 
    public void setStageSignUp(Stage employeeStage) {
        this.employeeStage = employeeStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initStage(Parent root) {
        
    }



}
