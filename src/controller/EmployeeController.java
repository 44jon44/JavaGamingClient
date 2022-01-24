/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class EmployeeController{

    private static final Logger LOG = Logger.getLogger(EmployeeController.class.getName());
    
    @FXML
    private Pane employeePane;
    
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
    /**
     * Delete user data button.
     */
    @FXML
    private Button btnDelete;
    
    @FXML
    private Label lblError;
    
    @FXML
    private HBox hbMenuAdm;
    
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene employeeScene = new Scene(root);   
        //definimos como modal la nueva ventana
        stage.initModality(Modality.NONE);
        //añadimos la escena en el stage
        stage.setScene(employeeScene);
        //por defecto no podra redimensionarse
        stage.setResizable(false);
       // menuController.setStage(stage);
        btnAdd.setOnAction(this::create);
        btnDelete.setOnAction(this::delete);
        btnModify.setOnAction(this::modify);
        //lblError se inicializa vacio
        lblError.setText("");
        //Se deshabilitan los botones btnDelete y bntModify
        btnDelete.setDisable(true);
        btnModify.setDisable(true);
        stage.showAndWait();
    }

    private void create(ActionEvent event) {

        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader employeeForm = new FXMLLoader(getClass().getResource("/view/employeeForm.fxml"));
            Parent root = (Parent) employeeForm.load();
            hbMenuAdm.getScene().getWindow().hide();
            //controlador de la ventana
            EmployeeFormController controller = employeeForm.getController();
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.INFO,"Ha saltado este error");
            LOG.log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void delete(ActionEvent event) {

    }

    @FXML
    private void modify(ActionEvent event) {

    }

}
