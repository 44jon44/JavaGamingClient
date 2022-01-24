/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class EmployeeFormController{

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
    private HBox hbMenuAdm;
    
    private Stage stage;
    public void initStageAdd(Parent root) {
        btnModify.setDisable(true);
    }

    public void initStageModify() {
        btnAdd.setDisable(true);
    }

    void initStage(Parent root) {
        //Creamos una nueva escena para la ventana SignIn
        Scene employeeFormScene = new Scene(root);
        //creamos un nuevo escenario para la nueva ventana
        stage.setScene(employeeFormScene);
        stage.showAndWait();
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }
}
