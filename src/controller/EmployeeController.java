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
    private Stage stageEmployee;
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
     */
    public Stage getStageEmployee() {
        return stageEmployee;
    }

    //setter de 
    public void setStageSignUp(Stage stageSignUp) {
        this.stageEmployee = stageSignUp;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void initStage(){}

    void initStage(Parent root) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
